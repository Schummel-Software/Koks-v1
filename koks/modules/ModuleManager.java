package koks.modules;

import koks.modules.impl.combat.*;
import koks.modules.impl.movement.*;
import koks.modules.impl.player.*;
import koks.modules.impl.utilities.*;
import koks.modules.impl.visuals.*;
import koks.modules.impl.world.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class ModuleManager {

    private final List<Module> MODULES = new ArrayList<>();

    public ModuleManager() {
        addModule(new Animations());
        addModule(new Sprint());
        addModule(new Speed());
        addModule(new NoBob());
        addModule(new NoFov());
        addModule(new NoHurtcam());
        addModule(new NoFall());
        addModule(new AntiFire());
        addModule(new FastConsume());
        addModule(new Velocity());
        addModule(new NameTags());
        addModule(new PlayerESP());
        addModule(new ChestESP());
        addModule(new ItemESP());
        addModule(new ClickGUI());
        addModule(new CustomEnchant());
        addModule(new Jesus());
        addModule(new NoCobweb());
        addModule(new ScaffoldWalk());
        addModule(new SetBack());
        addModule(new AntiBots());
        addModule(new KillAura());
        addModule(new ClearTag());
        addModule(new HUD());
        addModule(new Disabler());
    }

    public void addModule(Module module) {
        MODULES.add(module);
    }

    public List<Module> getModules() {
        return MODULES;
    }

    public Module getModule(String name) {
        for (Module module : getModules()) {
            if (module.getModuleName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public <T extends Module> T getModule(Class<T> tClass) {
        return (T) this.getModules().stream().filter(module -> module.getClass() == tClass).findAny().orElse(null);
    }

}