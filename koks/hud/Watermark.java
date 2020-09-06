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
import org.apache.logging.log4j.core.helpers.Clock;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(Calendar.getInstance().getTime());

            String render = name + " §7(" + time + ")";
            fr.drawStringWithShadow(render, 82 / 2 - fr.getStringWidth(render) / 2, 8, Koks.getKoks().client_color.getRGB());
        }
    }

}