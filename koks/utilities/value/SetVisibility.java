package koks.utilities.value;

import koks.Koks;
import koks.modules.Module;
import koks.modules.impl.combat.KillAura;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 16:43
 */
public class SetVisibility {

    public void setVisibility() {
        for (Module module : Koks.getKoks().moduleManager.getModules()) {
            if (module instanceof KillAura) {
                ((KillAura) module).preAimRange.setVisible(((KillAura) module).preAim.isToggled());
                ((KillAura) module).preferTarget.setVisible(!((KillAura) module).targetMode.getSelectedMode().equals("Switch"));
            }
        }
    }

}