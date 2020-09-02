package koks.command.impl;

import koks.Koks;
import koks.command.Command;
import koks.modules.Module;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:01
 */
public class Bind extends Command {

    public Bind() {
        super("Bind", "SetKey");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            sendmsg("Correct usage: §c§l.bind §7<§c§lMODULE§7> §7<§c§lKEY§7>", true);
            return;
        }

        String moduleArg = args[0].toUpperCase();
        String keyArg = args[1].toUpperCase();

        for (Module module : Koks.getKoks().moduleManager.getModules()) {
            if (module.getModuleName().equalsIgnoreCase(moduleArg)) {
                module.setKeyBind(Keyboard.getKeyIndex(keyArg));
                sendmsg("§fSet Key of §b§l" + moduleArg + " §fto §b§l" + Keyboard.getKeyName(Keyboard.getKeyIndex(keyArg)) + "§f", true);
            }
        }
    }

}