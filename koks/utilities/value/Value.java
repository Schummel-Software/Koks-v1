package koks.utilities.value;

import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:09
 */
public abstract class Value <T> {

    private String name;
    private Module module;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
