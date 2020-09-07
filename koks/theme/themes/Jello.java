package koks.theme.themes;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:42
 */
public class Jello extends Theme {


    public Jello() {
        super(ThemeCategory.JELLO);
        setUpTabGUI(20, 20, 80, 20, true, true, false, true);
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, categoryTab.isCurrentCategory() ? -1 : Color.PINK.getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(categoryTab.category.name(), x, y, categoryTab.isCurrentCategory() ? Color.BLACK.getRGB() : -1);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, moduleTab.isSelectedModule() ? -1 : Color.PINK.getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(moduleTab.getModule().getModuleName(), x + 2, y + height / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2, moduleTab.isSelectedModule() ? moduleTab.getModule().isToggled() ? Koks.getKoks().client_color.brighter().getRGB() : -1 : moduleTab.getModule().isToggled() ? Koks.getKoks().client_color.getRGB() : -1);
    }

    @Override
    public void arrayListDesign() {

    }

    @Override
    public void waterMarkDesign() {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Jello", 0, 0, -1);
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
        return false;
    }

}
