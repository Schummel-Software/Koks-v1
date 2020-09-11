package koks.modules;

import koks.ClientSwitch;
import koks.Koks;
import koks.modules.impl.combat.*;
import koks.modules.impl.movement.*;
import koks.modules.impl.player.*;
import koks.modules.impl.utilities.*;
import koks.modules.impl.visuals.*;
import koks.modules.impl.world.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class ModuleManager {

    private final List<Module> MODULES = new ArrayList<>();

    public ModuleManager() {
        if (ClientSwitch.currentType == ClientSwitch.ClientType.KOKS) {
            // Combat
            addModule(new KillAura());
            addModule(new SuperHit());
            addModule(new Velocity());
            addModule(new Reach());
            addModule(new TriggerBot());

            // Movement
            addModule(new BoatFly());
            addModule(new InventoryMove());
            addModule(new Jesus());
            addModule(new NoCobweb());
            addModule(new Speed());
            addModule(new Fly());
            addModule(new NoSlowdown());
            addModule(new Sprint());
            addModule(new LongJump());
            addModule(new TargetStrafe());
            addModule(new AirJump());
            addModule(new Parkour());
            addModule(new Step());

            // Player
            addModule(new AntiFire());
            addModule(new BedFucker());
            addModule(new FastConsume());
            addModule(new NoFall());
            addModule(new Phase());
            addModule(new SetBack());
            addModule(new NoRotate());
            addModule(new InventoryManager());
            addModule(new ChestStealer());
            addModule(new AntiVoid());
            addModule(new AutoArmor());
            addModule(new Blink());

            // Utilities
            addModule(new ClickGUI());
            addModule(new HUD());
            addModule(new Debug());
            addModule(new Disabler());
            addModule(new Cosmetics());
            addModule(new CustomBlock());
            addModule(new AntiFlag());

            // Visuals
            addModule(new Ambiance());
            addModule(new Animations());
            addModule(new BlockESP());
            addModule(new ChestESP());
            addModule(new ClearTag());
            addModule(new CustomEnchant());
            addModule(new TrailESP());
            addModule(new ItemESP());
            addModule(new NameTags());
            addModule(new NoBob());
            addModule(new NoFov());
            addModule(new NoHurtcam());
            addModule(new CameraClip());
            addModule(new PlayerESP());
            addModule(new HitAnimation());
            addModule(new FullBright());

            // World
            addModule(new ScaffoldWalk());
            addModule(new FastBridge());
            addModule(new FastPlace());
            addModule(new SafeWalk());
        }

        if (ClientSwitch.currentType == ClientSwitch.ClientType.LEGIT) {
            // Combat
            addModule(new Reach());
            addModule(new TriggerBot());

            // Movement
            addModule(new Sprint());
            addModule(new Parkour());

            // Player
            addModule(new AntiFire());
            addModule(new InventoryManager());
            addModule(new ChestStealer());
            addModule(new AutoArmor());
            addModule(new Blink());

            // Utilities
            addModule(new ClickGUI());
            addModule(new HUD());
            addModule(new Debug());
            addModule(new Disabler());
            addModule(new Cosmetics());
            addModule(new CustomBlock());
            addModule(new AntiFlag());

            // Visuals
            addModule(new Ambiance());
            addModule(new Animations());
            addModule(new BlockESP());
            addModule(new ChestESP());
            addModule(new ClearTag());
            addModule(new CustomEnchant());
            addModule(new TrailESP());
            addModule(new ItemESP());
            addModule(new NameTags());
            addModule(new NoBob());
            addModule(new NoFov());
            addModule(new NoHurtcam());
            addModule(new CameraClip());
            addModule(new PlayerESP());
            addModule(new HitAnimation());
            addModule(new FullBright());

            // World
            addModule(new FastBridge());
            addModule(new FastPlace());
            addModule(new SafeWalk());
        }
        getModules().sort(Comparator.comparing(Module::getModuleName));
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