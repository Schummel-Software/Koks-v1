package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.MovementUtil;
import koks.utilities.value.values.ModeValue;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:15
 */
public class Speed extends Module {

    public ModeValue<String> mode = new ModeValue<>("Mode", "Mineplex", new String[]{"Mineplex", "AAC 3.2.2", "Hypixel", "MCCentral LongHop", "MCCentral LowHop", "MCCentral YPort", "MCCentral Ground"}, this);
    public boolean canSpeed;
    public MovementUtil movementUtil = new MovementUtil();
    public TargetStrafe targetStrafe = new TargetStrafe();

    public Speed() {
        super("Speed", Category.MOVEMENT);
        addValue(mode);
    }


    @Override
    public void onEvent(Event event) {
        if (targetStrafe.allowStrafing()) {
            targetStrafe.strafe(event, movementUtil.baseSpeed());
        }
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());
            switch (mode.getSelectedMode()) {
                case "Hypixel":
                    if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            movementUtil.setSpeed(movementUtil.baseSpeed() + 0.005);
                        } else {
                            if (!targetStrafe.allowStrafing() && mc.thePlayer.movementInput.moveForward != 0 || mc.thePlayer.movementInput.moveStrafe != 0)
                                movementUtil.setSpeed(movementUtil.baseSpeed());
                            mc.thePlayer.jumpMovementFactor = 0.035F;
                        }
                    }
                    break;
                case "Mineplex":
                    if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.42;
                        } else {
                            if (mc.thePlayer.fallDistance < 0.5) {
                                movementUtil.setSpeed(0.4743);
                                mc.thePlayer.jumpMovementFactor = 0.024F;
                            }
                        }
                    }
                    break;
                case "AAC 3.2.2":
                    if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.fallDistance > 0.4 && !canSpeed)
                            canSpeed = true;
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        } else if (canSpeed) {
                            mc.thePlayer.motionY -= 0.0249;
                            mc.thePlayer.jumpMovementFactor = 0.033F;
                        }
                    } else {
                        canSpeed = false;
                    }
                    break;
                case "MCCentral LongHop":
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.52;
                    }
                    movementUtil.setSpeed(0.6F);
                    break;
                case "MCCentral LowHop":
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.30;
                    }
                    movementUtil.setSpeed(0.7F);
                    break;
                case "MCCentral YPort":
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.15;
                    } else {
                        mc.thePlayer.motionY = -0.15;
                    }
                    movementUtil.setSpeed(0.6F);
                    break;
                case "MCCentral Ground":
                    movementUtil.setSpeed(0.4F);
                    break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (targetStrafe != null)
            this.targetStrafe = Koks.getKoks().moduleManager.getModule(TargetStrafe.class);
        mc.timer.timerSpeed = 1.0;
        mc.thePlayer.jumpMovementFactor = 0.02F;
        canSpeed = false;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0;
    }

}
