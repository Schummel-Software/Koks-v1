package koks.modules.impl.world;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.MotionEvent;
import koks.event.impl.SafeWalkEvent;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 10:23
 */
public class ScaffoldWalk extends Module {

    private final List blackList;

    private BlockPos blockPos;

    private final TimeUtil timeUtil = new TimeUtil();
    private final RandomUtil randomutil = new RandomUtil();

    private final NumberValue<Long> delay = new NumberValue<>("Delay", 1L, 100L, 1000L, 1L, this);

    private final BooleanValue<Boolean> swingItem = new BooleanValue<>("Swing Item", true, this);
    private final BooleanValue<Boolean> safeWalk = new BooleanValue<>("SafeWalk", true, this);
    private final BooleanValue<Boolean> randomHit = new BooleanValue<>("Random Hit", true, this);
    public final BooleanValue<Boolean> sprint = new BooleanValue<>("Sprint", true, this);

    public final BooleanValue<Boolean> simpleRotations = new BooleanValue<>("Simple Rotations", false, this);

    private float yaw, pitch;

    private int currentInventorySlot;

    public ScaffoldWalk() {
        super("ScaffoldWalk", Category.WORLD);
        this.blackList = Arrays.asList(Blocks.crafting_table, Blocks.enchanting_table, Blocks.anvil, Blocks.sand, Blocks.gravel, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.ice, Blocks.packed_ice, Blocks.cobblestone_wall, Blocks.water, Blocks.lava, Blocks.web, Blocks.sapling, Blocks.rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.detector_rail, Blocks.tnt, Blocks.red_flower, Blocks.yellow_flower, Blocks.flower_pot, Blocks.tallgrass, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.ladder, Blocks.torch, Blocks.stone_button, Blocks.wooden_button, Blocks.redstone_torch, Blocks.redstone_wire, Blocks.furnace, Blocks.cactus, Blocks.oak_fence, Blocks.acacia_fence, Blocks.nether_brick_fence, Blocks.birch_fence, Blocks.dark_oak_fence, Blocks.jungle_fence, Blocks.oak_fence, Blocks.acacia_fence_gate, Blocks.snow_layer, Blocks.trapdoor, Blocks.ender_chest, Blocks.beacon, Blocks.hopper, Blocks.daylight_detector, Blocks.daylight_detector_inverted, Blocks.carpet);
        Koks.getKoks().valueManager.addValue(delay);
        Koks.getKoks().valueManager.addValue(swingItem);
        Koks.getKoks().valueManager.addValue(safeWalk);
        Koks.getKoks().valueManager.addValue(randomHit);
        Koks.getKoks().valueManager.addValue(sprint);
        Koks.getKoks().valueManager.addValue(simpleRotations);
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof SafeWalkEvent && safeWalk.isToggled())
            ((SafeWalkEvent) event).setSafe(mc.thePlayer.onGround);

        if (event instanceof MotionEvent) {
            BlockPos position = new BlockPos(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY - 1.0D, mc.thePlayer.posZ);
            if (((MotionEvent) event).getType() == MotionEvent.Type.PRE) {
                this.currentInventorySlot = mc.thePlayer.inventory.currentItem;
                setRotations();
                ((MotionEvent) event).setYaw(yaw);
                ((MotionEvent) event).setPitch(pitch);
            } else if (((MotionEvent) event).getType() == MotionEvent.Type.POST) {
                getBlockPosToPlaceOn(position);
            }
        }
    }

    public void yawSimple() {
        boolean forward = mc.gameSettings.keyBindForward.isKeyDown();
        boolean left = mc.gameSettings.keyBindLeft.isKeyDown();
        boolean right = mc.gameSettings.keyBindRight.isKeyDown();
        boolean back = mc.gameSettings.keyBindBack.isKeyDown();

        float yaw = 0;

        // Only one Key directions
        if (forward && !left && !right && !back)
            yaw = 180;
        if (!forward && left && !right && !back)
            yaw = 90;
        if (!forward && !left && right && !back)
            yaw = -90;
        if (!forward && !left && !right && back)
            yaw = 0;

        // Multi Key directions
        if (forward && left && !right && !back)
            yaw = 135;
        if (forward && !left && right && !back)
            yaw = -135;

        if (!forward && left && !right && back)
            yaw = 45;
        if (!forward && !left && right && back)
            yaw = -45;

        this.yaw = mc.thePlayer.rotationYaw + yaw;
    }

    public void setRotations() {
        if (simpleRotations.isToggled()) {
            yawSimple();
            pitch = getFaceDirectionToBlockPos(blockPos, yaw, pitch, 360)[1];
        } else {
            float[] rotations = getFaceDirectionToBlockPos(blockPos, yaw, pitch, 360);
            yaw = rotations[0];
            pitch = rotations[1];
        }
    }

    public void placeBlock(BlockPos pos, EnumFacing face) {
        blockPos = pos;

        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof net.minecraft.item.ItemBlock) {
                ItemBlock block = (ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem();
                if (!(blackList.contains(block.getBlock()))) {
                    mc.thePlayer.inventory.currentItem = i;
                    break;
                }
            }
        }

        ItemBlock block = (ItemBlock) mc.thePlayer.inventory.getCurrentItem().getItem();
        if (this.blackList.contains(block.getBlock())) {
            mc.thePlayer.inventory.currentItem = currentInventorySlot;
            return;
        }

        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof net.minecraft.block.BlockAir && !blackList.contains(block)) {
            if (timeUtil.hasReached(mc.thePlayer.onGround ? (randomutil.randomLong(delay.getMinDefaultValue(), delay.getDefaultValue())) : 0L)) {
                if (swingItem.isToggled())
                    mc.thePlayer.swingItem();
                else
                    mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem(), pos, face, new Vec3(pos.getX() + (randomHit.isToggled() ? 0 : randomutil.randomDouble(0, 1)), pos.getY() + (randomHit.isToggled() ? 0 : randomutil.randomDouble(0, 1)), pos.getZ() + (randomHit.isToggled() ? 0 : randomutil.randomDouble(0, 1))));
            }
        } else {
            timeUtil.reset();
        }
        mc.thePlayer.inventory.currentItem = currentInventorySlot;
    }

    @Override
    public void onEnable() {
        pitch = 0;
        yaw = 0;
        blockPos = null;
    }

    @Override
    public void onDisable() {

    }

    public void getBlockPosToPlaceOn(BlockPos pos) {
        BlockPos blockPos1 = pos.add(-1, 0, 0);
        BlockPos blockPos2 = pos.add(1, 0, 0);
        BlockPos blockPos3 = pos.add(0, 0, -1);
        BlockPos blockPos4 = pos.add(0, 0, 1);
        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, 0, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos1.add(1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(0, 0, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos2.add(-1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos2.add(1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(0, 0, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos3.add(-1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos3.add(1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(0, 0, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos4.add(-1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos4.add(1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
    }

    public float[] getFaceDirectionToBlockPos(BlockPos pos, float currentYaw, float currentPitch, float speed) {
        double x = (pos.getX() + 0.0F) - mc.thePlayer.posX;
        double y = (pos.getY() - 3.0F) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = (pos.getZ() + 0.0F) - mc.thePlayer.posZ;

        double calculate = MathHelper.sqrt_double(x * x + z * z);
        float calcYaw = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float calcPitch = (float) -(MathHelper.func_181159_b(y, calculate) * 180.0D / Math.PI);
        float finalPitch = calcPitch >= 90 ? 90 : calcPitch;

        float f = mc.gameSettings.mouseSensitivity * 0.9F;
        float gcd = f * f * f * 1.13F;

        float yaw = updateRotation(currentYaw, calcYaw, speed);
        float pitch = updateRotation(currentPitch, finalPitch, speed);

        yaw -= yaw % gcd;
        pitch -= pitch % gcd;

        return new float[]{yaw, pitch};
    }

    public float updateRotation(float current, float intended, float speed) {
        float f = MathHelper.wrapAngleTo180_float(intended - current);
        if (f > speed)
            f = speed;
        if (f < -speed)
            f = -speed;
        return current + f;
    }

}
