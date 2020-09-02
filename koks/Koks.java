package koks;

import koks.modules.ModuleManager;
import net.minecraft.client.Minecraft;

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

        moduleManager = new ModuleManager();
    }

    public void shutdownClient() {

    }

}
