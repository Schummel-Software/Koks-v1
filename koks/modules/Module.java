package koks.modules;

import koks.event.Event;
import net.minecraft.client.Minecraft;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public abstract class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    private String moduleName;
    private Category moduleCategory;
    private String moduleInfo;
    private boolean visible, enabled;
    private int keyBind;

    public Module(String moduleName, Category moduleCategory) {
        this.moduleName = moduleName;
        this.moduleCategory = moduleCategory;
    }

    public enum Category {
        COMBAT, MOVEMENT, PLAYER, UTILITIES, VISUALS, WORLD;
    }

    public abstract void onEvent(Event event);
    public abstract void onEnable();
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

    public void setEnabled(boolean enabled) {
        if (enabled)
            onEnable();
        else
            onDisable();
        this.enabled = enabled;
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