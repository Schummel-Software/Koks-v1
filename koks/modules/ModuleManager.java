package koks.modules;

import koks.modules.impl.movement.Sprint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class ModuleManager {

    public List<Module> modules = new ArrayList<>();

    public ModuleManager() {
    addModule(new Sprint());
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for(Module module : modules) {
            if(module.getModuleName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public <T extends Module> T getModule(Class<T> tClass) {
        for (Module module : modules) {
            if (module.getClass() == tClass)
                return (T)module;
        }
        return null;
    }

}