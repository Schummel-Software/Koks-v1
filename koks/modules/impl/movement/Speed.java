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

    public ModeValue<String> mode = new ModeValue<String>("Mode", "Mineplex",new String[] {"Mineplex"},this);

    public Speed() {
        super("Speed", Category.MOVEMENT);
        addValue(mode);
    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setDisplayName(getModuleName() + " §7" + mode.getSelectedMode());
            switch(mode.getSelectedMode()) {
                case "Mineplex":
                if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.42;
                    } else {
                        MovementUtil movementUtil = new MovementUtil();
                        movementUtil.setSpeed(0.41); // Absolute Maximum
                        mc.thePlayer.jumpMovementFactor = 0.025F;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 1.0;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0;
    }

}
