package koks.modules;

import koks.Koks;
import koks.event.Event;
import koks.files.impl.KeyBindFile;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.FileWriter;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:08
 */
public abstract class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    private String moduleName;
    private Category moduleCategory;
    private String moduleInfo;
    private boolean visible = true, enabled;
    private int keyBind;

    public Module(String moduleName, Category moduleCategory) {
        this.moduleName = moduleName;
        this.moduleCategory = moduleCategory;
    }

    public enum Category {
        COMBAT, MOVEMENT, PLAYER, UTILITIES, VISUALS, WORLD;
    }

    public abstract void onEvent(Event event);
    public abstract void onEnable(); //Fixxen das es richtig funktioniert
    public abstract void onDisable();

    public void toggle() {
        if (enabled) {
            onDisable();
            enabled = false;
        } else {
            onEnable();
            enabled = true;
        }
    }

    public void setToggled(boolean enabled) {
        if (!enabled) {
            onDisable();
            this.enabled = false;
        } else {
            onEnable();
            this.enabled = true;
        }

    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Category getModuleCategory() {
        return moduleCategory;
    }

    public void setModuleCategory(Category moduleCategory) {
        this.moduleCategory = moduleCategory;
    }

    public String getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(String moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isToggled() {
        return enabled;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }
}