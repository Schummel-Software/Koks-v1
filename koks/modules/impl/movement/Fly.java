package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.modules.Module;
import koks.modules.impl.movement.modes.HypixelFly;
import koks.utilities.value.values.ModeValue;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 18:14
 */
public class Fly extends Module {

    private final HypixelFly hypixelFly = new HypixelFly();
    public final ModeValue<String> modeValue = new ModeValue<>("Mode", "Hypixel", new String[]{"Hypixel"}, this);

    public Fly() {
        super("Fly", Category.MOVEMENT);
        Koks.getKoks().valueManager.addValue(modeValue);
    }

    @Override
    public void onEvent(Event event) {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onEvent(event);
                break;
        }
    }

    @Override
    public void onEnable() {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onEnable();
                break;
        }
    }

    @Override
    public void onDisable() {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onDisable();
                break;
        }
    }


}
