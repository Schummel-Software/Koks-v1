package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:25
 */
public class RotationUtil {

    private final Minecraft mc = Minecraft.getMinecraft();

    public float[] faceEntity(Entity entity, float currentYaw, float currentPitch, float speed) {
        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY + (double) entity.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double angle = MathHelper.sqrt_double(x * x + z * z);
        float yawAngle = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitchAngle = (float) (-(MathHelper.func_181159_b(y, angle) * 180.0D / Math.PI));
        float yaw = updateRotation(currentYaw, yawAngle, speed);
        float pitch = updateRotation(currentPitch, pitchAngle, speed);

        float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;

        return new float[]{yaw, pitch};
    }

    public float[] faceBlock(BlockPos pos, boolean scaffoldFix, float currentYaw, float currentPitch, float speed) {
        double x = (pos.getX() + (scaffoldFix ? 0.5F : 0.0F)) - mc.thePlayer.posX;
        double y = (pos.getY() - (scaffoldFix ? 3.0F : 0.0F)) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = (pos.getZ() + (scaffoldFix ? 0.5F : 0.0F)) - mc.thePlayer.posZ;

        double calculate = MathHelper.sqrt_double(x * x + z * z);
        float calcYaw = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float calcPitch = (float) -(MathHelper.func_181159_b(y, calculate) * 180.0D / Math.PI);
        float finalPitch = calcPitch >= 90 ? 90 : calcPitch;
        float yaw = updateRotation(currentYaw, calcYaw, speed);
        float pitch = updateRotation(currentPitch, finalPitch, speed);

        float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;

        return new float[]{yaw, pitch};
    }

    public float updateRotation(float current, float intended, float increment) {
        float f = MathHelper.wrapAngleTo180_float(intended - current);

        if (f > increment) {
            f = increment;
        }

        if (f < -increment) {
            f = -increment;
        }

        return current + f;
    }

}