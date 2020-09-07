package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.MovementUtil;
import koks.utilities.value.values.ModeValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:15
 */
public class Speed extends Module {

    public ModeValue<String> mode = new ModeValue<>("Mode", "Mineplex", new String[]{"Mineplex", "AAC 3.2.2", "Hypixel", "MCCentral"}, this);
    public boolean canSpeed;
    public MovementUtil movementUtil = new MovementUtil();


    public Speed() {
        super("Speed", Category.MOVEMENT);
        addValue(mode);
    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());
            switch (mode.getSelectedMode()) {
                case "Hypixel":
                    if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        } else {
                            movementUtil.setSpeed(0.285D);
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
                case "MCCentral":
                    if (mc.thePlayer.hurtTime == 0) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.52;
                        }
                        movementUtil.setSpeed(0.6F);
                    }
                    break;
            }
        }
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 1.0;
        mc.thePlayer.jumpMovementFactor = 0.02F;
        canSpeed = false;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0;
    }

}
