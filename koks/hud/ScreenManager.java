package koks.hud;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:04
 */
public class ScreenManager {

    ModuleList moduleList = new ModuleList();
    Watermark watermark = new Watermark();

    public void drawScreenManager() {
        moduleList.drawList();
        watermark.drawWatermark();
    }

}