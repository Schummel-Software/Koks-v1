package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.MovementUtil;
import koks.utilities.value.values.ModeValue;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 18:10
 */
public class LongJump extends Module {

    public ModeValue<String> mode = new ModeValue<>("LongJump Mode", "RedeSky", new String[]{"RedeSky"}, this);

    public LongJump() {
        super("LongJump", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());

            switch (mode.getSelectedMode()) {
                case "RedeSky":
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                    mc.thePlayer.motionY += 0.03;
                    mc.thePlayer.jumpMovementFactor = 0.15F;
                    break;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0;
    }

}