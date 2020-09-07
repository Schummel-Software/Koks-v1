package koks.theme;

import koks.theme.themes.GAL;
import koks.theme.themes.Jello;
import koks.theme.themes.Moon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:43
 */
public class ThemeManager {

    private final List<Theme> themeList = new ArrayList<>();

    public ThemeManager() {
        addTheme(new Jello());
        addTheme(new Moon());
        addTheme(new GAL());
    }

    public void addTheme(Theme theme) {
        this.themeList.add(theme);
    }

    public List<Theme> getThemeList() {
        return themeList;
    }
}
