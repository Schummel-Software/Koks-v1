package koks.theme;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 22:18
 */
public abstract class Theme {

    public abstract void drawScreen(int mouseX, int mouseY);

    public enum ThemeType {
        KOKS,
        SIGMA;
    }

}
