package koks.modules.impl.world;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.event.impl.SafeWalkEvent;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.RayCastUtil;
import koks.utilities.RotationUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 20:12
 */
public class NewScaffold extends Module {

    private final RotationUtil rotationUtil = new RotationUtil();
    private final RayCastUtil rayCastUtil = new RayCastUtil();
    private final RandomUtil randomUtil = new RandomUtil();
    private final TimeUtil timeUtil = new TimeUtil();
    private BlockPos finalPos;
    public float yaw, pitch;
    public boolean down;

    public ModeValue<String> rotationMode = new ModeValue<>("Rotation Mode", "Force Block", new String[]{"Force Block", "Watch Simple"}, this);
    public ModeValue<String> safeWalkMode = new ModeValue<>("SafeWalk", "Enabled", new String[]{"Enabled", "Enabled on Ground", "Disabled"}, this);
    public NumberValue<Long> placeDelay = new NumberValue<>("Place Delay", 60L, 80L, 100L, 0L, this);
    public NumberValue<Double> motionOnPlace = new NumberValue<>("Motion on Place", 1D, 1D, 0D, this);
    public NumberValue<Double> timerSpeed = new NumberValue<>("Timer Speed", 1D, 2D, 1D, this);
    public BooleanValue<Boolean> rayTrace = new BooleanValue<>("RayTrace", true, this);
    public BooleanValue<Boolean> randomVec = new BooleanValue<>("Random Vec", true, this);
    public NumberValue<Float> randomRotation = new NumberValue<>("Rotation Random Center", 0F, 1F, 0F, this);
    public BooleanValue<Boolean> rotateAlways = new BooleanValue<>("Rotate Always", true, this);
    public BooleanValue<Boolean> sneak = new BooleanValue<>("Sneak", true, this);
    public BooleanValue<Boolean> sprint = new BooleanValue<>("Sprint", true, this);

    public NewScaffold() {
        super("NewScaffold", Category.WORLD);
        addValue(rotationMode);
        addValue(safeWalkMode);
        addValue(placeDelay);
        addValue(motionOnPlace);
        addValue(timerSpeed);
        addValue(rayTrace);
        addValue(randomVec);
        addValue(randomRotation);
        addValue(rotateAlways);
        addValue(sneak);
        addValue(sprint);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            BlockPos playerStandOn = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY - 1, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(playerStandOn).getBlock() == Blocks.air) {
                setBlockPos(playerStandOn);
            } else {
                finalPos = null;
            }

            setStuff();
        }

        if (event instanceof SafeWalkEvent) {
            boolean shouldStay = safeWalkMode.getSelectedMode().equals("Enabled") || (safeWalkMode.getSelectedMode().equals("Enabled on Ground") && mc.thePlayer.onGround);
            ((SafeWalkEvent) event).setSafe(shouldStay);
        }

        if (event instanceof MotionEvent) {
            ((MotionEvent) event).setYaw(yaw);
            ((MotionEvent) event).setPitch(pitch);
        }
    }

    public void setStuff() {
        mc.gameSettings.keyBindSneak.pressed = sneak.isToggled() && finalPos != null;
        mc.thePlayer.setSprinting(sprint.isToggled());
        mc.timer.timerSpeed = timerSpeed.getDefaultValue();

        //down = Keyboard.isKeyDown(Keyboard.KEY_CAPITAL);

        if (finalPos == null) {
            timeUtil.reset();
        } else {
            if (!rayCastUtil.isRayCastBlock(finalPos, yaw, pitch) && rayTrace.isToggled()) {
                timeUtil.reset();
            }
        }

        if (finalPos != null) {
            setYaw();
            setPitch();
        } else {
            if (!rotateAlways.isToggled()) {
                yaw = mc.thePlayer.rotationYaw;
                pitch = mc.thePlayer.rotationPitch;
            }
        }
    }

    public void place(BlockPos blockPos, EnumFacing enumFacing) {
        finalPos = blockPos;

        ItemStack silentItemStack = null;
        if (mc.thePlayer.getCurrentEquippedItem() == null || (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))) {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof net.minecraft.item.ItemBlock) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                    silentItemStack = mc.thePlayer.inventory.getStackInSlot(i);
                    break;
                }
            }
        } else {
            silentItemStack = (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock) ? mc.thePlayer.getCurrentEquippedItem() : null;
        }


        Vec3 vecToPlace = new Vec3(blockPos.getX() + (randomVec.isToggled() ? randomUtil.randomDouble(0, 0.7) : 0), blockPos.getY() + (randomVec.isToggled() ? randomUtil.randomDouble(0, 0.7) : 0), blockPos.getZ() + (randomVec.isToggled() ? randomUtil.randomDouble(0, 0.7) : 0));
        if (timeUtil.hasReached(mc.thePlayer.onGround ? randomUtil.randomLong(placeDelay.getMinDefaultValue(), placeDelay.getDefaultValue()) : 1)) {
            mc.thePlayer.motionX *= motionOnPlace.getDefaultValue();
            mc.thePlayer.motionZ *= motionOnPlace.getDefaultValue();

            if (rayCastUtil.isRayCastBlock(finalPos, yaw, pitch) || !rayTrace.isToggled()) {
                mc.thePlayer.swingItem();
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, silentItemStack, blockPos, enumFacing, vecToPlace);
            }
            timeUtil.reset();
        }
    }

    public void setYaw() {
        if (finalPos == null)
            return;
        if (rotationMode.getSelectedMode().equals("Force Block")) {
            float[] rotations = rotationUtil.faceBlock(finalPos, true, yaw, pitch, 360);
            yaw = (float) (rotations[0]);
        }

        boolean forward = mc.gameSettings.keyBindForward.isKeyDown();
        boolean left = mc.gameSettings.keyBindLeft.isKeyDown();
        boolean right = mc.gameSettings.keyBindRight.isKeyDown();
        boolean back = mc.gameSettings.keyBindBack.isKeyDown();

        if (rotationMode.getSelectedMode().equals("Watch Simple")) {
            float yaw = 0;

            if (forward && !left && !right && !back)
                yaw = 180;
            if (!forward && left && !right && !back)
                yaw = 90;
            if (!forward && !left && right && !back)
                yaw = -90;
            if (!forward && !left && !right && back)
                yaw = 0;

            if (forward && left && !right && !back)
                yaw = 135;
            if (forward && !left && right && !back)
                yaw = -135;

            if (!forward && left && !right && back)
                yaw = 45;
            if (!forward && !left && right && back)
                yaw = -45;

            this.yaw = (float) (mc.thePlayer.rotationYaw + yaw);
        }
    }

    public void setPitch() {
        if (finalPos == null)
            return;
        float calcPitch = rotationUtil.faceBlock(finalPos, true, yaw, pitch, 360)[1];
        if (rotationMode.getSelectedMode().equals("Force Block")) {
            pitch = (float) (calcPitch);
        }

        if (rotationMode.getSelectedMode().equals("Watch Simple")) {
            pitch = (float) ((mc.thePlayer.onGround ? 82 : calcPitch));
        }
    }

    public void setBlockPos(BlockPos pos) {
        BlockPos blockPos1 = pos.add(-1, 0, 0);
        BlockPos blockPos2 = pos.add(1, 0, 0);
        BlockPos blockPos3 = pos.add(0, 0, -1);
        BlockPos blockPos4 = pos.add(0, 0, 1);
        float down = 0;
        if (mc.theWorld.getBlockState(pos.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            place(pos.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(pos.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(pos.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            place(pos.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            place(pos.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos1.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos1.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos1.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos1.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos1.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            place(blockPos1.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            place(blockPos1.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos2.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos2.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos2.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos2.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos2.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            place(blockPos2.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            place(blockPos2.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos3.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos3.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos3.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos3.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos3.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            place(blockPos3.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            place(blockPos3.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos4.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos4.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos4.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos4.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            place(blockPos4.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            place(blockPos4.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            place(blockPos4.add(0, 0 - down, 1), EnumFacing.NORTH);
        }
    }

    @Override
    public void onEnable() {
        finalPos = null;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.00;
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
    }

}