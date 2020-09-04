package koks.modules.impl.utilities;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventTick;
import koks.modules.Module;
import koks.utilities.value.values.BooleanValue;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 22:23
 */
public class HUD extends Module {

    public HUD() {
        super("HUD", Category.UTILITIES);
        this.setToggled(true);
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
