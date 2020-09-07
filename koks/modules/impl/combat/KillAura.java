package koks.modules.impl.combat;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.*;
import koks.modules.Module;
import koks.utilities.*;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import koks.utilities.value.values.TitleValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 21:08
 */
public class KillAura extends Module {

    public BooleanValue<Boolean> player = new BooleanValue<>("Player", true, this);
    public BooleanValue<Boolean> animals = new BooleanValue<>("Animals", false, this);
    public BooleanValue<Boolean> mobs = new BooleanValue<>("Mobs", false, this);
    public BooleanValue<Boolean> invisible = new BooleanValue<>("Invisible", false, this);
    public BooleanValue<Boolean> ignoreTeam = new BooleanValue<>("Ignore Team", true, this);
    public BooleanValue<Boolean> ignoreFriend = new BooleanValue<>("Ignore Friends", true, this);
    public ModeValue<String> targets = new ModeValue<>("Targets", new BooleanValue[]{player, animals, mobs, invisible, ignoreTeam, ignoreFriend}, this);

    public ModeValue<String> targetMode = new ModeValue<>("Target Mode", "Hybrid", new String[]{"Single", "Switch", "Hybrid"}, this);
    public ModeValue<String> preferTarget = new ModeValue<>("Prefer Check", "Distance", new String[]{"Distance", "Health"}, this);
    public NumberValue<Double> range = new NumberValue<>("Hit Range", 4.0D, 6.0D, 3.4D, this);
    public BooleanValue<Boolean> preAim = new BooleanValue<>("Pre Aiming", false, this);
    public NumberValue<Double> preAimRange = new NumberValue<>("Aiming Range", 0.0D, 1.0D, 0.0D, this);
    public NumberValue<Integer> cps = new NumberValue<>("CPS", 7, 12, 20, 1, this);
    public BooleanValue<Boolean> smoothRotation = new BooleanValue<>("Smooth Rotation", false, this);
    public NumberValue<Integer> failingChance = new NumberValue<>("FailHit Percent", 0, 20, 0, this);
    public BooleanValue<Boolean> autoBlock = new BooleanValue<>("AutoBlock", false, this);
    public BooleanValue<Boolean> legitMovement = new BooleanValue<>("Legit Movement", false, this);
    public BooleanValue<Boolean> stopSprinting = new BooleanValue<>("Stop Sprinting", false, this);
    public BooleanValue<Boolean> hitSlow = new BooleanValue<>("Hit Slow", false, this);

    public TitleValue generalSettings = new TitleValue("General", true, new Value[]{range, preAim, preAimRange, cps, smoothRotation, failingChance, autoBlock, legitMovement, stopSprinting, hitSlow}, this);

    public BooleanValue<Boolean> needNaNHealth = new BooleanValue<>("NaN Health", false, this);
    public BooleanValue<Boolean> checkName = new BooleanValue<>("Check Name", true, this);
    public NumberValue<Integer> ticksExisting = new NumberValue<>("Ticks Existing", 25, 100, 0, this);
    public TitleValue antiBotSettings = new TitleValue("AntiBot Settings", false, new Value[]{needNaNHealth, checkName, ticksExisting}, this);

    public BooleanValue<Boolean> fakeBlocking = new BooleanValue<>("Fake Blocking", false, this);
    public BooleanValue<Boolean> silentSwing = new BooleanValue<>("Silent Swing", false, this);
    public BooleanValue<Boolean> serverSideSwing = new BooleanValue<>("Send SwingPacket", true, this);
    public NumberValue<Integer> swingChance = new NumberValue<>("ClientSide SwingChance", 100, 100, 0, this);
    public TitleValue visualSettings = new TitleValue("Visual Settings", false, new Value[]{fakeBlocking, silentSwing, serverSideSwing, swingChance}, this);

    public List<Entity> entities = new ArrayList<>();
    FriendManager friendManager = new FriendManager();
    public RandomUtil randomUtil = new RandomUtil();
    public AuraUtil auraUtil = new AuraUtil();
    public TimeUtil timeUtil = new TimeUtil();
    public RayCastUtil rayCastUtil = new RayCastUtil();
    public RotationUtil rotationUtil = new RotationUtil();
    public EntityUtil entityUtil = new EntityUtil();
    public Entity finalEntity;
    public boolean isFailing, canSwing;
    public float yaw, pitch;
    public int listCount;

    public KillAura() {
        super("KillAura", Category.COMBAT);

        addValue(targets);
        addValue(targetMode);
        addValue(preferTarget);

        addValue(generalSettings);

        addValue(range);
        addValue(preAim);
        addValue(preAimRange);
        addValue(cps);
        addValue(smoothRotation);
        addValue(failingChance);
        addValue(autoBlock);
        addValue(legitMovement);
        addValue(stopSprinting);
        addValue(hitSlow);
        addValue(antiBotSettings);

        addValue(needNaNHealth);
        addValue(checkName);
        addValue(ticksExisting);

        addValue(visualSettings);
        addValue(fakeBlocking);
        addValue(silentSwing);
        addValue(serverSideSwing);
        addValue(swingChance);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MotionEvent) {
            MotionEvent e = (MotionEvent) event;

            if (e.getType() == MotionEvent.Type.PRE) {

                if (finalEntity != null) {

                    e.setYaw(yaw);
                    e.setPitch(pitch);

                    if (mc.thePlayer.getDistanceToEntity(finalEntity) <= range.getDefaultValue())
                        attackEntity();
                }

            }

            if (e.getType() == MotionEvent.Type.POST) {
                if (finalEntity != null && autoBlock.isToggled()) {
                    if (mc.thePlayer.getCurrentEquippedItem().getItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                    }
                }
            }

        }

        if (event instanceof EventRender3D) {
            float partialTicks = ((EventRender3D) event).getPartialTicks();

            if (finalEntity != null) {
                double x = (finalEntity.lastTickPosX + (finalEntity.posX - finalEntity.lastTickPosX) * partialTicks) - mc.getRenderManager().renderPosX;
                double y = (finalEntity.lastTickPosY + (finalEntity.posY - finalEntity.lastTickPosY) * partialTicks) - mc.getRenderManager().renderPosY;
                double z = (finalEntity.lastTickPosZ + (finalEntity.posZ - finalEntity.lastTickPosZ) * partialTicks) - mc.getRenderManager().renderPosZ;

                float width = 0.16F;

                AxisAlignedBB axisalignedbb = finalEntity.getEntityBoundingBox();
                AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(
                        axisalignedbb.minX - finalEntity.posX + x - width,
                        axisalignedbb.maxY - finalEntity.posY + y + 0.30 - (finalEntity.isSneaking() ? 0.25 : 0),
                        axisalignedbb.minZ - finalEntity.posZ + z - width,
                        axisalignedbb.maxX - finalEntity.posX + x + width,
                        axisalignedbb.maxY - finalEntity.posY + y + 0.35 - (finalEntity.isSneaking() ? 0.25 : 0),
                        axisalignedbb.maxZ - finalEntity.posZ + z + width);

                BoxUtil boxUtil = new BoxUtil();
                boxUtil.renderOutline(axisalignedbb1);
            }
        }

        if (event instanceof EventUpdate) {
            setModuleInfo(targetMode.getSelectedMode() + (targetMode.getSelectedMode().equals("Switch") ? "" : ", "+ preferTarget.getSelectedMode()));
            manageEntities();
            setRotations(finalEntity);
            isFailing = new Random().nextInt(100) <= failingChance.getDefaultValue();
            canSwing = new Random().nextInt(100) <= swingChance.getDefaultValue();

            if (autoBlock.isToggled() && finalEntity != null) {
                mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
            }

            if (stopSprinting.isToggled() && mc.thePlayer.rotationYaw != yaw && finalEntity != null) {
                mc.gameSettings.keyBindSprint.pressed = false;
                mc.thePlayer.setSprinting(false);
            }
        }

        if (legitMovement.isToggled() && finalEntity != null) {
            if (event instanceof MoveFlyingEvent) {
                MoveFlyingEvent e = (MoveFlyingEvent) event;
/*                float difference = MathHelper.wrapAngleTo180_float(Math.abs(mc.thePlayer.rotationYaw - yaw));
                if (difference > 90) {
                    e.setForward(-e.getForward());
                    e.setStrafe(-e.getStrafe());
                }*/
                e.setYaw(yaw);
            }

            if (event instanceof JumpEvent) {
                JumpEvent e = (JumpEvent) event;
                e.setYaw(yaw);
            }
        }

    }

    public void attackEntity() {
        double cps = this.cps.getMinValue().equals(this.cps.getMaxValue()) ? this.cps.getMaxValue() : randomUtil.randomInt(this.cps.getMinValue(), this.cps.getMaxValue());
        Entity rayCast = rayCastUtil.getRayCastedEntity(range.getDefaultValue(), yaw, pitch);

        if (!isFailing && rayCast != null) {

            if (autoBlock.isToggled())
                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

            for (int i = 0; i < 2; i++)
                mc.effectRenderer.emitParticleAtEntity(rayCast, EnumParticleTypes.PORTAL);

            if (timeUtil.hasReached((long) (1000 / (cps + (cps > 10 ? 5 : 0))))) {
                if (silentSwing.isToggled()) {
                    if (canSwing) {
                        mc.thePlayer.swingItem();
                    } else {
                        if (serverSideSwing.isToggled())
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                } else
                    mc.thePlayer.swingItem();

                if (hitSlow.isToggled())
                    mc.playerController.attackEntity(mc.thePlayer, rayCast);
                else
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(rayCast, C02PacketUseEntity.Action.ATTACK));

                    if (listCount < entities.size() - 1 && !entities.isEmpty())
                        listCount++;
                    else
                        listCount = 0;

                timeUtil.reset();
            }
        }
    }

    public void setRotations(Entity entity) {
        float[] rotations = rotationUtil.faceEntity(entity, yaw, pitch, smoothRotation.isToggled());
        yaw = rotations[0];
        pitch = rotations[1];
    }

    public void manageEntities() {

        if (!entities.isEmpty()) {

            entities.removeIf(entity -> !isValidEntity(entity));
            Entity entityToSet = preferTarget.getSelectedMode().equals("Distance") ? auraUtil.getNearest(entities) : preferTarget.getSelectedMode().equals("Health") ? auraUtil.getLowest(entities) : null;
            switch (targetMode.getSelectedMode()) {
                case "Single":
                    if (finalEntity == null) finalEntity = entityToSet;
                    break;
                case "Switch":
                    finalEntity = entities.get(listCount);
                    break;
                case "Hybrid":
                    finalEntity = entityToSet;
                    break;
                default:
                    finalEntity = entities.get(0);
                    break;
            }
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (isValidEntity(entity)) {
                if (!entities.contains(entity))
                    entities.add(entity);
            } else {
                entities.remove(entity);
            }
        }

        if (finalEntity != null && !isValidEntity(finalEntity)) {
            finalEntity = null;
            listCount = 0;
        }

        if (finalEntity == null || entities.isEmpty() || listCount > entities.size() - 1)
            listCount = 0;
    }

    public boolean isValidEntity(Entity entity) {
        if (!(entity instanceof EntityLivingBase))
            return false;
        if (checkName.isToggled() && entity instanceof EntityPlayer && !isValidEntityName(entity))
            return false;
        if (entity instanceof EntityPlayer && entity == mc.thePlayer)
            return false;
        if (entity.isDead)
            return false;
        if (!mc.thePlayer.canEntityBeSeen(entity))
            return false;
        if (!player.isToggled() && entity instanceof EntityPlayer)
            return false;
        if (!animals.isToggled() && entity instanceof EntityAnimal)
            return false;
        if (!animals.isToggled() && entity instanceof EntityVillager)
            return false;
        if (!mobs.isToggled() && entity instanceof EntityMob)
            return false;
        if (!invisible.isToggled() && entity.isInvisible())
            return false;
        if (entity.ticksExisted < ticksExisting.getDefaultValue())
            return false;
        if (friendManager.isFriend(entity.getName()) && !ignoreFriend.isToggled())
            return false;
        if (!Float.isNaN(((EntityLivingBase) entity).getHealth()) && needNaNHealth.isToggled())
            return false;
        if (!ignoreTeam.isToggled() && entityUtil.isTeam(mc.thePlayer, (EntityPlayer) entity))
            return false;
        if (mc.thePlayer.getDistanceToEntity(entity) > range.getDefaultValue() + (preAim.isToggled() ? 0 : preAimRange.getDefaultValue()))
            return false;
        return true;
    }

    public boolean isValidEntityName(Entity entity) {
        String name = entity.getName();
        if (name.length() < 3 || name.length() > 16)
            return false;
        if (name.contains(".") || name.contains("-") || name.contains("§") || name.contains("&") || name.contains("/"))
            return false;
        return true;
    }

    @Override
    public void onEnable() {
        listCount = 0;
        timeUtil.reset();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }

    @Override
    public void onDisable() {
        finalEntity = null;
        entities.clear();
        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }

}