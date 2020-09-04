package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:16
 */
public class MovementUtil {

    public final Minecraft mc = Minecraft.getMinecraft();

    public double getDirection(float rotationYaw) {
        float left = mc.gameSettings.keyBindLeft.pressed ? mc.gameSettings.keyBindBack.pressed ? 45 : -45 : 0;
        float right = mc.gameSettings.keyBindRight.pressed ? mc.gameSettings.keyBindBack.pressed ? -45 :  45 : 0;
        float back = mc.gameSettings.keyBindBack.pressed ? 180 : 0;
        float yaw = back + right + left;
        return rotationYaw + yaw;
    }

    public void setSpeed(double speed) {
        mc.thePlayer.motionX = -Math.sin(Math.toRadians(getDirection(mc.thePlayer.rotationYaw))) * speed;
        mc.thePlayer.motionZ = Math.cos(Math.toRadians(getDirection(mc.thePlayer.rotationYaw))) * speed;
    }

}
