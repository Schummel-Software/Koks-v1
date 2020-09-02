package koks.command;

import koks.Koks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public abstract class Command {

    private String name,alias;

    public Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String alias){
        this.name = name;
        this.alias = alias;
    }

    public abstract void execute(String[] args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void sendmsg(String msg, boolean prefix) {
        String pref = prefix ? Koks.getKoks().PREFIX : "";
        mc.thePlayer.addChatMessage(new ChatComponentText(pref + msg));
    }

    public void sendError(String errorType, String fix, boolean prefix) {
        String pref = prefix ? Koks.getKoks().PREFIX : "";
        String message = pref + "§cERROR: " + errorType.toUpperCase() + ": §7" + fix;
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
