package koks.hud;

import koks.Koks;
import koks.modules.impl.utilities.HUD;
import koks.utilities.ColorUtil;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
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

        if (Koks.getKoks().moduleManager.getModule(HUD.class).isToggled()) {
            String name = Koks.getKoks().CLIENT_NAME;
            String version = Koks.getKoks().CLIENT_VERSION;
            RenderUtils renderUtils = new RenderUtils();
            Gui.drawRect(2, 2, 65, 35, Integer.MIN_VALUE);
            renderUtils.drawOutlineRect(2, 2, 65, 35, 1, Koks.getKoks().client_color);
            colorUtil = new ColorUtil();
            GL11.glPushMatrix();
            GL11.glScaled(3.4, 3.4, 3.4);
            fr.drawString(name.substring(0, 1), 2, 2, Koks.getKoks().client_color.getRGB());
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glScaled(2, 2, 2);
            fr.drawString(name.substring(1), 13, 8, Koks.getKoks().client_color.getRGB());
            GL11.glPopMatrix();

            //fr.drawStringWithShadow("v" + version, 20, 20, 0xFFFFFFFF);
        }
    }

}