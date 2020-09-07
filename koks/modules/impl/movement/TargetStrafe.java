package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventMove;
import koks.event.impl.MotionEvent;
import koks.modules.Module;
import koks.modules.impl.combat.KillAura;
import koks.utilities.MovementUtil;
import net.minecraft.entity.Entity;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 22:34
 */
public class TargetStrafe extends Module {

    public TargetStrafe() {
        super("TargetStrafe", Category.MOVEMENT);
    }

    int direction;

    public void strafe(Event event) {

        MovementUtil movementUtil = new MovementUtil();
        if (event instanceof MotionEvent) {
            if (((MotionEvent) event).getType() == MotionEvent.Type.PRE) {
                if (mc.thePlayer.isCollidedHorizontally) {
                    if (direction == 0)
                        direction = 1;
                    else
                        direction = 0;
                }
                if (mc.gameSettings.keyBindLeft.pressed)
                    direction = 0;
                if (mc.gameSettings.keyBindRight.pressed)
                    direction = 1;
            }
        }

        if (event instanceof EventMove) {
            mc.gameSettings.keyBindForward.pressed = false;
            mc.gameSettings.keyBindBack.pressed = false;
            if (allowStrafing()) {
                if (mc.thePlayer.getDistanceToEntity(Koks.getKoks().moduleManager.getModule(KillAura.class).finalEntity) <= 2) {
                    movementUtil.setSpeedEvent(0.285, Koks.getKoks().moduleManager.getModule(KillAura.class).yaw, false, true, direction == 0, direction == 1);
                } else {
                    if (mc.thePlayer.getDistanceToEntity(Koks.getKoks().moduleManager.getModule(KillAura.class).finalEntity) >= 3) {
                        movementUtil.setSpeedEvent(0.285, Koks.getKoks().moduleManager.getModule(KillAura.class).yaw, true, false, direction == 0, direction == 1);
                    } else {
                        movementUtil.setSpeedEvent(0.285, Koks.getKoks().moduleManager.getModule(KillAura.class).yaw, false, false, direction == 0, direction == 1);
                    }
                }
            }
        }
    }

    @Override
    public void onEvent(Event event) {

    }

    public boolean allowStrafing() {
        return Koks.getKoks().moduleManager.getModule(KillAura.class).finalEntity != null && isToggled();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
