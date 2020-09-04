package koks.hud;

import koks.Koks;
import koks.modules.impl.utilities.HUD;
import koks.utilities.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:06
 */
public class Watermark {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    private ColorUtil colorUtil;

    public void drawWatermark() {
        ScaledResolution sr = new ScaledResolution(mc);
        if(Koks.getKoks().moduleManager.getModule(HUD.class).isToggled()) {
            String name = Koks.getKoks().CLIENT_NAME;
            String version = Koks.getKoks().CLIENT_VERSION;
            colorUtil = new ColorUtil();
            GL11.glPushMatrix();
            GL11.glScaled(3.4, 3.4, 3.4);
            fr.drawString(name.substring(0, 1), 2, 2, colorUtil.rainbow(3000, 1F, 1F));
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glScaled(2, 2, 2);
            fr.drawString(name.substring(1), 13, 8, colorUtil.rainbow(3000, 1F, 1F));
            GL11.glPopMatrix();

            //fr.drawStringWithShadow("v" + version, 20, 20, 0xFFFFFFFF);
        }
    }

}