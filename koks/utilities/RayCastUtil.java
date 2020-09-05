package koks.utilities;

import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import optifine.Reflector;

import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 15:23
 */
public class RayCastUtil {

    private final Minecraft mc = Minecraft.getMinecraft();

    public boolean isRayCastBlock(BlockPos blockPos, float yaw, float pitch) {
        float range = mc.playerController.getBlockReachDistance();

        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        MovingObjectPosition ray = mc.theWorld.rayTraceBlocks(vec3,vec32,false);
            return ray.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockPos.equals(ray.getBlockPos());
    }

    public Entity getRayCastedEntity(double range, float yaw, float pitch) {
        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        Entity pointedEntity = null;

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
