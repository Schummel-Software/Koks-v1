package koks.modules.impl.combat;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.modules.Module;
import koks.utilities.RotationUtil;
import koks.utilities.TimeUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:35
 */
public class Killaura extends Module {

    public RotationUtil rotationUtil = new RotationUtil();
    public TimeUtil timeUtil = new TimeUtil();
    public float yaw, pitch, range;
    public boolean mineplex;
    public Entity entity;

    public Killaura() {
        super("Killaura", Category.COMBAT);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MotionEvent) {
            if (((MotionEvent) event).getType() == MotionEvent.Type.PRE) {
                if (entity != null && isValid(entity)) {
                    float[] rotations = rotationUtil.faceEntity(entity, yaw, pitch, 360);
                    ((MotionEvent) event).setYaw(rotations[0]);
                    ((MotionEvent) event).setPitch(rotations[1]);
                    for (int i = 0; i < 5; i++) {
                        mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
                        mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
                    }
                    if (timeUtil.isDelayComplete(100)) {
                        mc.thePlayer.swingItem();
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                        timeUtil.reset();
                    }
                } else {
                    yaw = mc.thePlayer.rotationYaw;
                    pitch = mc.thePlayer.rotationPitch;
                }
            }
        }

        if (event instanceof EventUpdate) {
            mineplex = true;
            range = 4.2F;
            getEntity();
        }
    }

    public void getEntity() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (isValid(entity)) {
                this.entity = entity;
            }
        }
    }

    public boolean isValid(Entity entity) {
        if (!(entity instanceof EntityPlayer) || entity == mc.thePlayer)
            return false;
        if (entity.isInvisible() || entity.isDead)
            return false;
        if (mineplex && !Float.isNaN(((EntityLivingBase) entity).getHealth()))
            return false;
        if (mc.thePlayer.getDistanceToEntity(entity) > range)
            return false;
        return true;
    }

    @Override
    public void onEnable() {
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
        entity = null;
    }

    @Override
    public void onDisable() {

    }

}