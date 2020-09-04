package koks.modules.impl.combat;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.JumpEvent;
import koks.event.impl.MotionEvent;
import koks.event.impl.MoveFlyingEvent;
import koks.modules.Module;
import koks.utilities.AuraUtil;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
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
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
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
    public ModeValue<String> targets = new ModeValue<>("Targets", new BooleanValue[]{player, animals, mobs, invisible}, this);

    public ModeValue<String> targetMode = new ModeValue<>("Target Mode", "Hybrid", new String[]{"Single", "Switch", "Hybrid"}, this);
    public ModeValue<String> preferTarget = new ModeValue<>("Prefer Check", "Distance", new String[]{"Distance", "Health"}, this);
    public NumberValue<Double> range = new NumberValue<>("Hit Range", 4.0D, 6.0D, 3.4D, this);
    public BooleanValue<Boolean> preAim = new BooleanValue<>("Pre Aiming", false, this);
    public NumberValue<Double> preAimRange = new NumberValue<>("Aiming Range", 0.0D, 1.0D, 0.0D, this);
    public NumberValue<Integer> cps = new NumberValue<>("CPS", 7,12,20,1, this);
    public BooleanValue<Boolean> smoothRotation = new BooleanValue<>("Smooth Rotation", false, this);
    public NumberValue<Integer> failingChance = new NumberValue<>("FailHit Percent", 0, 20, 0, this);
    public BooleanValue<Boolean> legitMovement = new BooleanValue<>("Legit Movement", false, this);
    public BooleanValue<Boolean> stopSprinting = new BooleanValue<>("Stop Sprinting", false, this);

    public TitleValue generalSettings = new TitleValue("General", true, new Value[]{range, preAim, preAimRange, cps, smoothRotation, failingChance, legitMovement, stopSprinting}, this);

    public BooleanValue<Boolean> needNaNHealth = new BooleanValue<>("NaN Health", false, this);
    public BooleanValue<Boolean> checkName = new BooleanValue<>("Check Name", true, this);
    public NumberValue<Integer> ticksExisting = new NumberValue<>("Ticks Existing", 25, 100, 0, this);
    public TitleValue antiBotSettings = new TitleValue("AntiBot Settings", false, new Value[]{needNaNHealth, checkName, ticksExisting}, this);

    public List<Entity> entities = new ArrayList<>();
    public RandomUtil randomUtil = new RandomUtil();
    public AuraUtil auraUtil = new AuraUtil();
    public TimeUtil timeUtil = new TimeUtil();
    public Entity finalEntity;
    public boolean isFailing;
    public float yaw, pitch;
    public int listCount;

    public KillAura() {
        super("KillAura", Category.COMBAT);

        Koks.getKoks().valueManager.addValue(targets);
        Koks.getKoks().valueManager.addValue(targetMode);
        Koks.getKoks().valueManager.addValue(preferTarget);

        Koks.getKoks().valueManager.addValue(generalSettings);

        Koks.getKoks().valueManager.addValue(range);
        Koks.getKoks().valueManager.addValue(preAim);
        Koks.getKoks().valueManager.addValue(preAimRange);
        Koks.getKoks().valueManager.addValue(cps);
        Koks.getKoks().valueManager.addValue(smoothRotation);
        Koks.getKoks().valueManager.addValue(failingChance);
        Koks.getKoks().valueManager.addValue(legitMovement);
        Koks.getKoks().valueManager.addValue(stopSprinting);
        Koks.getKoks().valueManager.addValue(antiBotSettings);

        Koks.getKoks().valueManager.addValue(needNaNHealth);
        Koks.getKoks().valueManager.addValue(checkName);
        Koks.getKoks().valueManager.addValue(ticksExisting);
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

            }

        }

        if (event instanceof EventUpdate) {
            manageEntities();
            setRotations(finalEntity);

            isFailing = new Random().nextInt(100) <= failingChance.getDefaultValue();

            if (stopSprinting.isToggled() && mc.thePlayer.rotationYaw != yaw && finalEntity != null) {
                mc.gameSettings.keyBindSprint.pressed = false;
                mc.thePlayer.setSprinting(false);
            }

            if (finalEntity == null)
                listCount = 0;
        }

        if (legitMovement.isToggled() && finalEntity != null) {
            if (event instanceof MoveFlyingEvent) {
                MoveFlyingEvent e = (MoveFlyingEvent) event;
                float difference = MathHelper.wrapAngleTo180_float(Math.abs(mc.thePlayer.rotationYaw - yaw));
                // Ist nen leichter Silent der aber manchmal buggt
/*                if (difference > 90) {
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

        double cps = this.cps.getMinValue().equals(this.cps.getMaxValue()) ? this.cps.getMaxValue() : randomUtil.randomInt(this.cps.getMinValue(),this.cps.getMaxValue());
        if (timeUtil.hasReached((long) (1000 / (cps + (cps > 10 ? 5 : 0))))) {

            Entity rayCast = auraUtil.getRayCastedEntity(range.getDefaultValue(), yaw, pitch);

            if (!isFailing && rayCast != null) {

                mc.thePlayer.swingItem();
                if (stopSprinting.isToggled())
                    mc.playerController.attackEntity(mc.thePlayer, rayCast);
                else
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(rayCast, C02PacketUseEntity.Action.ATTACK));

                if (!entities.isEmpty()) {
                    if (listCount < entities.size())
                        listCount++;
                    else
                        listCount = 0;
                }
            }

            timeUtil.reset();
        }
    }

    public void setRotations(Entity entity) {
        float[] rotations = auraUtil.faceEntity(entity, yaw, pitch, smoothRotation.isToggled());
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
                entities.add(entity);
            } else {
                entities.remove(entity);
            }
        }

        if (finalEntity != null && !isValidEntity(finalEntity))
            finalEntity = null;
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
    }

}