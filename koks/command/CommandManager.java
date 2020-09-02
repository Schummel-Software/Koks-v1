package koks.command;

import koks.command.impl.*;

import java.util.ArrayList;

public class CommandManager {

    public ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new Toggle());
        addCommand(new Bind());
    }

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
