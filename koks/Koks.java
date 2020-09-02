package koks;

import koks.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:07
 */
public class Koks {

    private static final Koks KOKS;

    static {
        KOKS = new Koks();
    }

    public static Koks getKoks() {
        return KOKS;
    }

    private final String CLIENT_NAME = "Koks";
    private final String[] CLIENT_DEVELOPER = new String[] {"avox", "lmao", "Kroko"};
    private final String CLIENT_VERSION = "1.0.0";

    public ModuleManager moduleManager;

    public void initClient() {
        Minecraft.getMinecraft().gameSettings.guiScale = 2;
        Minecraft.getMinecraft().gameSettings.ofFastRender = false;

        Display.setTitle(CLIENT_NAME + " v" + CLIENT_VERSION + " by " + CLIENT_DEVELOPER[0] + " | " + CLIENT_DEVELOPER[1] + " | " + CLIENT_DEVELOPER[2]);

        moduleManager = new ModuleManager();
    }

    public void shutdownClient() {

    }

}