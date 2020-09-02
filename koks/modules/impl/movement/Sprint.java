package koks.modules.impl.movement;

import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
    }

    @Override
    public void onEvent() {
        if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindBack.isKeyDown() && canSprint()) {
            mc.thePlayer.setSprinting(true);
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