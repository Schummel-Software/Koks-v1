package koks.hud;

import koks.Koks;
import koks.modules.Module;
import koks.modules.impl.utilities.HUD;
import koks.modules.impl.visuals.ClearTag;
import koks.utilities.RenderUtils;
import koks.utilities.value.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:55
 */
public class ModuleList {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final RenderUtils renderUtils = new RenderUtils();

    public void drawList(boolean shadow) {
        ScaledResolution sr = new ScaledResolution(mc);
        int[] y = {Koks.getKoks().moduleManager.getModule(HUD.class).marioKART.isToggled() ? 9 : 0};

        Koks.getKoks().moduleManager.getModules().stream().filter(Module::isToggled).sorted(Comparator.comparingDouble(module -> -Minecraft.getMinecraft().fontRendererObj.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).forEach(module -> {
            if (module.isVisible()) {
                if (Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled()) {

                    if(shadow) {
                        GL11.glPushMatrix();
                        GlStateManager.disableAlpha();
                        GlStateManager.enableBlend();
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glColor4f(1, 1, 1, 1);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/arraylistshadow.png"), sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 4, y[0], fr.getStringWidth(module.getDisplayName()), 15, false);
                        GL11.glDisable(GL11.GL_BLEND);
                        GlStateManager.enableAlpha();
                        GlStateManager.disableBlend();
                        GL11.glPopMatrix();
                    }else{
                        Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 4, y[0], sr.getScaledWidth(), y[0] + fr.FONT_HEIGHT + 1, Integer.MIN_VALUE);
                    }
                    fr.drawStringWithShadow(module.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 2, y[0] + 1, Koks.getKoks().client_color.getRGB());
                } else {

                    if(shadow) {
                        GL11.glPushMatrix();
                        GlStateManager.disableAlpha();
                        GlStateManager.enableBlend();
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glColor4f(1, 1, 1, 1);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/arraylistshadow.png"), sr.getScaledWidth() - fr.getStringWidth(module.getNameForArrayList()) - 4, y[0], fr.getStringWidth(module.getNameForArrayList()), 15, false);
                        GL11.glDisable(GL11.GL_BLEND);
                        GlStateManager.enableAlpha();
                        GlStateManager.disableBlend();
                        GL11.glPopMatrix();
                    }else{
                        Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.getNameForArrayList()) - 4, y[0], sr.getScaledWidth(), y[0] + fr.FONT_HEIGHT + 1, Integer.MIN_VALUE);
                    }
                    fr.drawStringWithShadow(module.getNameForArrayList(), sr.getScaledWidth() - fr.getStringWidth(module.getNameForArrayList()) - 2, y[0] + 1, Koks.getKoks().client_color.getRGB());
                }
                y[0] += fr.FONT_HEIGHT + 1;
            }
        });
    }


}