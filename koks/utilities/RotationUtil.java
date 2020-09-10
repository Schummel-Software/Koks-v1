package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;

import java.util.Random;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:25
 */
public class RotationUtil {

    private final Minecraft mc = Minecraft.getMinecraft();
    public RandomUtil randomUtil = new RandomUtil();

    public float[] faceEntity(Entity entity, float currentYaw, float currentPitch, boolean smooth) {
        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY + (double) entity.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double distance = mc.thePlayer.getDistanceToEntity(entity);
        double angle = MathHelper.sqrt_double(x * x + z * z);
        float yawAngle = (float) ((float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F + randomUtil.randomGaussian((3.5F + randomUtil.randomGaussian(1) / distance)));
        float pitchAngle = (float) ((float) (-(MathHelper.func_181159_b(y, angle) * 180.0D / Math.PI)) + randomUtil.randomGaussian((3 + randomUtil.randomGaussian(1) / distance)) + randomUtil.randomFloat(-2, 10) + (mc.thePlayer.onGround ? 0 : -7));
        float speed = (float) ((22 + distance * (5 + randomUtil.randomGaussian(1.25F))) + randomUtil.randomGaussian(4));

        float yaw = updateRotation(currentYaw, yawAngle, smooth ? speed : 1000);
        float pitch = updateRotation(currentPitch, pitchAngle, smooth ? speed : 1000);

        float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;

        /*float[] yawdiff = calculateDiff(currentYaw,yaw);
        float[] pitchdiff = calculateDiff(currentPitch,pitch);
        float[] fixed = fixedSensivity(mc.gameSettings.mouseSensitivity, yawdiff[0], pitchdiff[0]);
        yawdiff[0] = fixed[0];
        pitchdiff[0] = fixed[1];
        float der = (float) (yawdiff[0]/0.15f);
        float der2 = (float) (pitchdiff[0]/0.15f);
        if (der>0.01&&der<0.99) {
            System.out.println("yaw " + der);
        }
        if (der2>0.01&&der2<0.99) {
            System.out.println("pitch " + der2);
        }
//        System.out.println(fixed[0]/0.15);
        if (yawdiff[1] == 1) {
            yaw -= yawdiff[0];
        }else {
            yaw += yawdiff[0];
        }
        if (pitchdiff[1] == 1) {
            pitch -= pitchdiff[0];
        }else {
            pitch += pitchdiff[0];
        }*/
        return new float[]{yaw,pitch};
    }


    public float[] calculateDiff(float v1,float v2) {
        float diff1 = Math.abs(v1 - v2);
        float diff2 = 360 - diff1;
        if (diff2 >= 360) diff2 -= 360;
        if (diff2 < 0) diff2 += 360;
        if (diff1 > diff2) {
            return new float[] {diff2,0};
        }
        if (diff2 > diff1) {
            return new float[] {diff1,1};
        }
        return new float[] {diff2,0};
    }

    public float[] fixedSensivity(float sensitivity, float yawdiff, float pitchdiff)
    {
        float f = sensitivity * 0.6F + 0.2F;
        float gcd = f * f * f * 8f;
        yawdiff = (int) ((yawdiff) / gcd / 0.15f);
        pitchdiff = (int) ((pitchdiff) / gcd / 0.15f);
        yawdiff = (float) (yawdiff * gcd * 0.15f);
        pitchdiff = (float) (pitchdiff * gcd * 0.15f);
        return  new float[] {yawdiff,pitchdiff};
    }

    public float[] getAngles(float curYaw, float curPitch, float yaw, float pitch)
    {
        float yaw2 = (float)((double)curYaw + (double)yaw * 0.15D);
        float pitch2 = (float)((double)curPitch - (double)pitch * 0.15D);

        return new float[] {yaw2, pitch2};
    }

    public float[] faceEntity(Entity entity, float currentYaw, float currentPitch, float speed) {
        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY + (double) entity.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double angle = MathHelper.sqrt_double(x * x + z * z);
        float yawAngle = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitchAngle = (float) (-(MathHelper.func_181159_b(y, angle) * 180.0D / Math.PI));
        float yaw = updateRotation(currentYaw, yawAngle, speed);
        float pitch = updateRotation(currentPitch, pitchAngle, speed);

        /*float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;*/

        /*float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f1 = f * f * f * 8.0F;
        float f2 = (float)this.mc.mouseHelper.deltaX * f1;
        float f3 = (float)this.mc.mouseHelper.deltaY * f1;
        byte b0 = 1;

        float[] mouse = setAngles(f2, f3 * (float)b0, currentYaw, currentPitch);

        yaw-= mouse[0];
        pitch-= mouse[1];*/

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