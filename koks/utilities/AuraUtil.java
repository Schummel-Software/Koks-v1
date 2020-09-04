package koks.utilities;

import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import optifine.Reflector;

import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 11:59
 */
public class AuraUtil {

    private final RotationUtil rotationUtil = new RotationUtil();
    private final RandomUtil randomUtil = new RandomUtil();
    private final Minecraft mc = Minecraft.getMinecraft();
    private Entity pointedEntity;

    public Entity getNearest(List<Entity> entityList) {
        Entity nearestEntity = null;

        if (!entityList.isEmpty()) {
            for (Entity entity : entityList) {
                if (nearestEntity == null) {
                    nearestEntity = entity;
                } else if (mc.thePlayer.getDistanceToEntity(nearestEntity) > mc.thePlayer.getDistanceToEntity(entity)) {
                    nearestEntity = entity;
                }
            }
        }

        return nearestEntity;
    }

    public Entity getLowest(List<Entity> entityList) {
        Entity lowestEntity = null;

        if (!entityList.isEmpty()) {
            for (Entity entity : entityList) {
                if (Float.isNaN(((EntityLivingBase) entity).getHealth()) || ((EntityLivingBase) entity).getHealth() <= 0 || ((EntityLivingBase) entity).getHealth() > 24) {
                    lowestEntity = getNearest(entityList);
                } else {
                    if (lowestEntity == null) {
                        lowestEntity = entity;
                    } else if (((EntityLivingBase) lowestEntity).getHealth() > ((EntityLivingBase) entity).getHealth()) {
                        lowestEntity = entity;
                    }
                }
            }
        }

        return lowestEntity;
    }

    public float[] faceEntity(Entity entity, float currentYaw, float currentPitch, boolean smooth) {
        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY + (double) entity.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double distance = mc.thePlayer.getDistanceToEntity(entity);
        double angle = MathHelper.sqrt_double(x * x + z * z);
        float yawAngle = (float) ((float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F + randomUtil.randomGaussian((3 / distance)));
        float pitchAngle = (float) ((float) (-(MathHelper.func_181159_b(y, angle) * 180.0D / Math.PI )) + randomUtil.randomGaussian((3 / distance)));
        float speed = (float) (20 + distance * 5);
        float yaw = rotationUtil.updateRotation(currentYaw, yawAngle, smooth ? speed : 1000);
        float pitch = rotationUtil.updateRotation(currentPitch, pitchAngle, smooth ? speed : 1000);

        float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;

        if (pitch > 90)
            pitch = 90;
        if (pitch < -90)
            pitch = -90;

        return new float[]{yaw, pitch};
    }

    public Entity getRayCastedEntity(double range, float yaw, float pitch) {
        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        pointedEntity = null;

        float f = 1.0F;
        List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d2 = range;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity1 = list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

            if (axisalignedbb.isVecInside(vec3)) {
                if (d2 >= 0.0D) {
                    pointedEntity = entity1;
                    d2 = 0.0D;
                }
            } else if (movingobjectposition != null) {
                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                if (d3 < d2 || d2 == 0.0D) {
                    boolean flag2 = false;

                    if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                    }

                    if (entity1 == mc.getRenderViewEntity().ridingEntity && !flag2) {
                        if (d2 == 0.0D) {
                            pointedEntity = entity1;
                        }
                    } else {
                        pointedEntity = entity1;
                        d2 = d3;
                    }
                }
            }
        }
        return pointedEntity;
    }

}