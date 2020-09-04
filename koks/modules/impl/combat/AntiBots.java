package koks.modules.impl.combat;

import koks.Koks;
import koks.event.Event;
import koks.modules.Module;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 21:56
 */
public class AntiBots extends Module {

    public BooleanValue<Boolean> player = new BooleanValue<>("Player", true, this);
    public BooleanValue<Boolean> animals = new BooleanValue<>("Animals", true, this);
    public BooleanValue<Boolean> mobs = new BooleanValue<>("Mobs", true, this);
    public BooleanValue<Boolean> ignoreInvisible = new BooleanValue<>("Ignore Invisible", true, this);
    public NumberValue<Integer> ticksExisting = new NumberValue<>("Ticks Existing", 50, 100, 0, this);

    public AntiBots() {
        super("AntiBots", Category.COMBAT);

        Koks.getKoks().valueManager.addValue(player);
        Koks.getKoks().valueManager.addValue(animals);
        Koks.getKoks().valueManager.addValue(mobs);
        Koks.getKoks().valueManager.addValue(ignoreInvisible);
        Koks.getKoks().valueManager.addValue(ticksExisting);
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}