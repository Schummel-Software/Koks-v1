package koks.theme;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:37
 */
public abstract class Theme {

    private final ThemeCategory themeCategory;

    private boolean tabGuiShadow, longestWidthStringModule;
    private int tabGuiX, tabGuiY, tabGuiWidth, tabGuiHeight;
    private CustomFont tabGuiLengthFont;
    private final RenderUtils renderUtils = new RenderUtils();

    public Theme(ThemeCategory themeCategory) {
        this.themeCategory = themeCategory;
    }

    public void drawIngameTheme() {
        if (drawTabGUI()) {
            Koks.getKoks().tabGUI.drawScreen(tabGuiX, tabGuiY, tabGuiWidth, tabGuiHeight, tabGuiShadow, tabGuiLengthFont, false, false);
        }
        if (drawWaterMark()) {
            waterMarkDesign();
        }
        if (drawArrayList()) {
            arrayListDesign();
        }
    }

    public void setUpTabGUI(int x, int y, int width, int height, boolean tabGuiShadow, boolean longestWidthStringModule, CustomFont tabGuiLengthFont) {
        this.tabGuiX = x;
        this.tabGuiY = y;
        this.tabGuiWidth = width;
        this.tabGuiHeight = height;
        this.tabGuiShadow = tabGuiShadow;
        this.longestWidthStringModule = longestWidthStringModule;
        this.tabGuiLengthFont = tabGuiLengthFont;
    }

    public abstract void arrayListDesign();

    public abstract void waterMarkDesign();

    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
    }

    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
    }

    public abstract boolean drawTabGUI();

    public abstract boolean drawWaterMark();

    public abstract boolean drawArrayList();

    public boolean isLongestWidthStringModule() {
        return longestWidthStringModule;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }

    public enum ThemeCategory {
        JELLO,
        MOON,
        NONE;
    }

    public ThemeCategory getThemeCategory() {
        return themeCategory;
    }
}
