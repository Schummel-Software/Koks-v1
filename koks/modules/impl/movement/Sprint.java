package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.gui.clickgui.ClickGUI;
import koks.modules.Module;
import koks.utilities.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:08
 */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
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