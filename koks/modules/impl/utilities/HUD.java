package koks.modules.impl.utilities;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender2D;
import koks.event.impl.KeyPressEvent;
import koks.hud.ModuleList;
import koks.hud.Watermark;
import koks.modules.Module;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 22:23
 */
public class HUD extends Module {

    public ModuleList moduleList = new ModuleList();
    public Watermark watermark = new Watermark();

    private BooleanValue<Boolean> tabGUI = new BooleanValue<>("Enabled", true, this);
    private BooleanValue<Boolean> tabGUI_shadow = new BooleanValue<>("Shadow", true, this);
    private BooleanValue<Boolean> tabGUI_client_color = new BooleanValue<>("Color", true, this);
    private BooleanValue<Boolean> tabGUICenteredString = new BooleanValue<>("Centered String", true, this);

    private BooleanValue<Boolean> shadowArrayList = new BooleanValue<>("Shadow", true, this);

    public ModeValue<String> tabGuiSettings = new ModeValue<>("TabGui Settings", new BooleanValue[]{tabGUI, tabGUI_shadow, tabGUI_client_color, tabGUICenteredString}, this);
    public ModeValue<String> arrayListSettings = new ModeValue<>("Arraylist Settings", new BooleanValue[]{shadowArrayList}, this);

    public HUD() {
        super("HUD", Category.UTILITIES);
        Koks.getKoks().valueManager.addValue(tabGuiSettings);
        Koks.getKoks().valueManager.addValue(arrayListSettings);
        this.setToggled(true);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender2D) {

            if (tabGUI.isToggled()) {
                tabGUI_shadow.setVisible(true);
                tabGUI_client_color.setVisible(true);
                tabGUICenteredString.setVisible(true);
            } else {
                tabGUI_shadow.setVisible(false);
                tabGUI_client_color.setVisible(false);
                tabGUICenteredString.setVisible(false);
            }

            moduleList.drawList(shadowArrayList.isToggled());
            watermark.drawWatermark();
            if (Koks.getKoks().tabGUI != null && tabGUI.isToggled())
                Koks.getKoks().tabGUI.drawScreen(2, 20, this.tabGUI_shadow.isToggled(), tabGUI_client_color.isToggled(), tabGUICenteredString.isToggled());
        }
        if (event instanceof KeyPressEvent) {
            if (Koks.getKoks().tabGUI != null && tabGUI.isToggled())
                Koks.getKoks().tabGUI.keyPress(((KeyPressEvent) event).getKey());
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
