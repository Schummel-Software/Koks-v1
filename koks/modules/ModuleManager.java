package koks.modules;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ModuleManager {

    public List<Module> modules = new ArrayList<>();

    public ModuleManager() {

    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> tClass) {
        for (Module module : modules) {
            if (module.getClass() == tClass)
                return (T)module;
        }
        return null;
    }

}