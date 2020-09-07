package koks.theme.themes;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:42
 */
public class Jello extends Theme {

    private final CustomFont WATER_MARK_FONT = new CustomFont("fonts/jellolight.ttf", 53);
    private final CustomFont TABGUI_FONT = new CustomFont("fonts/jelloregular.ttf", 18);
    private final CustomFont ARRAY_LIST_FONT = new CustomFont("fonts/jellolight.ttf", 18);

    public Jello() {
        super(ThemeCategory.JELLO);
        setUpTabGUI(5, 33, 75, 14, true, true, TABGUI_FONT);
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, Integer.MIN_VALUE);
        Gui.drawRect(x, y, x + width, y + height, categoryTab.isCurrentCategory() ? new Color(255, 255, 255, 125).getRGB() : new Color(255, 255, 255, 0).getRGB());

        TABGUI_FONT.drawStringWithShadow(categoryTab.category.name(), x + categoryTab.animationX, y + height / 2 - TABGUI_FONT.FONT_HEIGHT / 2, new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, Integer.MIN_VALUE);
        Gui.drawRect(x, y, x + width, y + height, moduleTab.isSelectedModule() ? new Color(255, 255, 255, 125).getRGB() : new Color(255, 255, 255, 0).getRGB());

        TABGUI_FONT.drawStringWithShadow(moduleTab.getModule().getModuleName(), x + 2, y + height / 2 - TABGUI_FONT.FONT_HEIGHT / 2, moduleTab.isSelectedModule() ? moduleTab.getModule().isToggled() ? Koks.getKoks().client_color.brighter().getRGB() : Koks.getKoks().client_color.getRGB() : moduleTab.getModule().isToggled() ? Koks.getKoks().client_color.getRGB() : new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public void arrayListDesign() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int[] y = {0};

        Koks.getKoks().moduleManager.getModules().stream().filter(Module::isToggled).sorted(Comparator.comparingDouble(module -> -ARRAY_LIST_FONT.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).forEach(module -> {
            if (module.isVisible()) {
                String finalText = module.getDisplayName();
                GL11.glPushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1, 1, 1, 1);
                getRenderUtils().drawImage(new ResourceLocation("client/shadows/arraylistshadow.png"), sr.getScaledWidth() - ARRAY_LIST_FONT.getStringWidth(finalText) * 1.7F - 4, y[0] - 5, ARRAY_LIST_FONT.getStringWidth(finalText) * 4, 30, false);
                GL11.glDisable(GL11.GL_BLEND);
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
                ARRAY_LIST_FONT.drawString(finalText, sr.getScaledWidth() - ARRAY_LIST_FONT.getStringWidth(finalText) - 2, y[0], -1);
                y[0] += ARRAY_LIST_FONT.FONT_HEIGHT + 1;
            }
        });
    }

    @Override
    public void waterMarkDesign() {
        GL11.glPushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        getRenderUtils().drawImage(new ResourceLocation("client/shadows/arraylistshadow.png"), -6, -2, 70, 40, false);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        WATER_MARK_FONT.drawString("Jello", 5, 0, -1);
    }

    @Override
    public boolean drawTabGUI() {
        return true;
    }

    @Override
    public boolean drawWaterMark() {
        return true;
    }

    @Override
    public boolean drawArrayList() {
        return true;
    }

}
