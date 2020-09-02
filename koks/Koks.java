package koks;

import koks.command.CommandManager;
import koks.event.EventManager;
import koks.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class Koks {

    private static final Koks KOKS;

    static {
        KOKS = new Koks();
    }

    public static Koks getKoks() {
        return KOKS;
    }

    public final String CLIENT_NAME = "Koks";
    public final String[] CLIENT_DEVELOPER = new String[] {"avox", "lmao", "Kroko"};
    public final String CLIENT_VERSION = "1.0.0";
    public final String PREFIX = "§c" + CLIENT_NAME + " §7>> §f";

    public ModuleManager moduleManager;
    public EventManager eventManager;
    public CommandManager commandManager;

    public void initClient() {

        Display.setTitle(CLIENT_NAME + " v" + CLIENT_VERSION + " by " + CLIENT_DEVELOPER[0] + " | " + CLIENT_DEVELOPER[1] + " | " + CLIENT_DEVELOPER[2]);

        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        eventManager = new EventManager();
    }

    public void shutdownClient() {

    }

}
