package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.Minecraft;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:08
 */
public class Sprint extends Module {

    public BooleanValue<Boolean> b = new BooleanValue<>("BooleanTest", true, this);

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        Koks.getKoks().valueManager.addValue(b);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
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
        Minecraft.getMinecraft().displayGuiScreen(Koks.getKoks().clickGUI);
    }

    @Override
    public void onDisable() {

    }

}