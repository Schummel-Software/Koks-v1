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

    public ModeValue<String> mode = new ModeValue<>("LongJump Mode", "Mineplex", new String[]{"Mineplex"}, this);

    public LongJump() {
        super("LongJump", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());

            switch (mode.getSelectedMode()) {
                case "Mineplex":
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.44;
                    } else {
                        MovementUtil movementUtil = new MovementUtil();
                        mc.thePlayer.motionY *= 0.95;
                        if (mc.thePlayer.fallDistance > 0)
                            movementUtil.setSpeed(mc.thePlayer.fallDistance / 2);
                    }
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