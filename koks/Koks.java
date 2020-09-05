package koks;

import koks.command.CommandManager;
import koks.event.EventManager;
import koks.files.FileManager;
import koks.gui.clickgui.ClickGUI;
import koks.gui.clickgui.commonvalue.CommonValueManager;
import koks.gui.configs.ConfigScreen;
import koks.hud.ScreenManager;
import koks.manager.ConfigManager;
import koks.modules.ModuleManager;
import koks.utilities.value.ValueManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.awt.*;

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

    public final String CLIENT_NAME = "Koks";
    public final String[] CLIENT_DEVELOPER = new String[] {"avox", "lmao", "Kroko"};
    public final String CLIENT_VERSION = "1.0.0";
    public final String PREFIX = "§c" + CLIENT_NAME + " §7>> §f";
    public Color client_color = Color.PINK;

    public ISound koksSound = PositionedSoundRecord.create(new ResourceLocation("koks.sound"));

    public ModuleManager moduleManager;
    public ValueManager valueManager;
    public CommonValueManager commonValueManager;
    public ClickGUI clickGUI;
    public EventManager eventManager;
    public CommandManager commandManager;
    public ScreenManager screenManager;
    public FileManager fileManager;
    public ConfigScreen configScreen;
    public ConfigManager configManager;

    public void initClient() {

        Display.setTitle(CLIENT_NAME + " v" + CLIENT_VERSION + " by " + CLIENT_DEVELOPER[0] + " | " + CLIENT_DEVELOPER[1] + " | " + CLIENT_DEVELOPER[2]);
        valueManager = new ValueManager();
        commonValueManager = new CommonValueManager();
        moduleManager = new ModuleManager();
        clickGUI = new ClickGUI();
        commandManager = new CommandManager();
        eventManager = new EventManager();
        screenManager = new ScreenManager();
        fileManager = new FileManager();
        configManager = new ConfigManager();
        if(!configManager.DIR.exists())configManager.DIR.mkdirs();
        configScreen = new ConfigScreen();
        fileManager.createFiles();

    }

    public void shutdownClient() {
        fileManager.writeToAllFiles();
    }

}