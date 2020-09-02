package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:16
 */
public class MovementUtil {

    public static void setSpeed(double speed) {
        float forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        float strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;

        if (forward > 1 || strafe > 1) {
            forward = 1F;
            strafe = 1F;
        } else {
            Minecraft.getMinecraft().thePlayer.motionX = 0F;
            Minecraft.getMinecraft().thePlayer.motionZ = 0F;
        }

        float f1 = MathHelper.sin(Minecraft.getMinecraft().thePlayer.rotationYaw * (float) Math.PI / 180.0F);
        float f2 = MathHelper.cos(Minecraft.getMinecraft().thePlayer.rotationYaw * (float) Math.PI / 180.0F);
        
        Minecraft.getMinecraft().thePlayer.motionX += (double) (strafe * speed * f2 - forward * speed * f1);
        Minecraft.getMinecraft().thePlayer.motionZ += (double) (forward * speed * f2 + strafe * speed * f1);
    }

}
