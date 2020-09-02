package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:08
 */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindBack.isKeyDown() && canSprint()) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }

    public boolean canSprint() {
        return true;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}