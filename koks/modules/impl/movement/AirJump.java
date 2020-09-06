package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 14:49
 */
public class AirJump extends Module {

    public AirJump() {
        super("AirJump", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            mc.thePlayer.onGround = true;
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}