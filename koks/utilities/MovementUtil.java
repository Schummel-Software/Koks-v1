package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:16
 */
public class MovementUtil {

    public static void setSpeed(double speed) {

        double strafeSpeed = speed;
        float forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        float strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;

        if (forward > 0 && strafe > 0) {
            forward = 1;
            if(strafe > 0)
                strafeSpeed = speed / 2;
        } else {
            Minecraft.getMinecraft().thePlayer.motionX = 0;
            Minecraft.getMinecraft().thePlayer.motionZ = 0;
        }

        float f1 = MathHelper.sin(Minecraft.getMinecraft().thePlayer.rotationYaw * (float) Math.PI / 180.0F);
        float f2 = MathHelper.cos(Minecraft.getMinecraft().thePlayer.rotationYaw * (float) Math.PI / 180.0F);

        Minecraft.getMinecraft().thePlayer.motionX = (double) (strafe * strafeSpeed * f2 - forward * speed * f1);
        Minecraft.getMinecraft().thePlayer.motionZ = (double) (forward * speed * f2 + strafe * strafeSpeed * f1);

    }

}
