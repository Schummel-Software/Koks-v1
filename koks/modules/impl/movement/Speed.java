package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.MovementUtil;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:15
 */
public class Speed extends Module {

    public Speed() {
        super("Speed", Category.MOVEMENT);
    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindBack.isKeyDown()) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.42;
                } else {
                    MovementUtil.setSpeed(0.4);
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
