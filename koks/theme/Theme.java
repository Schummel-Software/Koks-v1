package koks.theme;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:37
 */
public abstract class Theme {

    private final ThemeCategory themeCategory;

    private boolean tabGuiCenteredString, tabGuiShadow, tabGuiClientColor,longestWidthStringModule;
    private int tabGuiX, tabGuiY, tabGuiWidth, tabGuiHeight;


    public Theme(ThemeCategory themeCategory) {
        this.themeCategory = themeCategory;
    }

    public void drawIngameTheme() {
        if (drawTabGUI()) {
            Koks.getKoks().tabGUI.drawScreen(tabGuiX, tabGuiY, tabGuiWidth, tabGuiHeight, tabGuiShadow, tabGuiClientColor, tabGuiCenteredString);
        }
        if (drawWaterMark()) {
            waterMarkDesign();
        }
        if (drawArrayList()) {
            arrayListDesign();
        }
    }

    public void setUpTabGUI(int x, int y, int width, int height, boolean tabGuiShadow, boolean tabGuiClientColor, boolean tabGuiCenteredString, boolean longestWidthStringModule) {
        this.tabGuiX = x;
        this.tabGuiY = y;
        this.tabGuiWidth = width;
        this.tabGuiHeight = height;
        this.tabGuiShadow = tabGuiShadow;
        this.tabGuiClientColor = tabGuiClientColor;
        this.tabGuiCenteredString = tabGuiCenteredString;
        this.longestWidthStringModule = longestWidthStringModule;
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

    public enum ThemeCategory {
        JELLO,
        NONE;
    }

    public ThemeCategory getThemeCategory() {
        return themeCategory;
    }
}
