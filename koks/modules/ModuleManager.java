package koks.modules;

import koks.modules.impl.movement.Speed;
import koks.modules.impl.movement.Sprint;
import koks.modules.impl.visuals.NoBob;
import koks.modules.impl.visuals.NoFov;
import koks.modules.impl.visuals.NoHurtcam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class ModuleManager {

    private final List<Module> MODULES = new ArrayList<>();

    public ModuleManager() {
        addModule(new Sprint());
        addModule(new Speed());
        addModule(new NoBob());
        addModule(new NoFov());
        addModule(new NoHurtcam());
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