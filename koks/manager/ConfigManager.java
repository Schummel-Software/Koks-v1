package koks.manager;

import koks.Koks;
import koks.files.FileManager;
import koks.modules.Module;
import koks.modules.impl.utilities.HUD;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 21:44
 */
public class ConfigManager {

    public Minecraft mc = Minecraft.getMinecraft();
    public File DIR = new File(mc.mcDataDir + "/" + Koks.getKoks().CLIENT_NAME + "/Configs");

    public boolean configExist(String name) {
        File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
        return file.exists();
    }

    public void loadConfig(String name) {

        for(Module module : Koks.getKoks().moduleManager.getModules()) {
            if(module.getModuleCategory() != Module.Category.VISUALS && module != Koks.getKoks().moduleManager.getModule(HUD.class)) {
                module.setToggled(false);
                module.setBypassed(false);
            }
        }

        File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
        try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] args = line.split(":");
                Module module = Koks.getKoks().moduleManager.getModule(args[1]);
                if(args[0].equalsIgnoreCase("Module")) {
                    module.setToggled(Boolean.parseBoolean(args[2]));
                    module.setBypassed(Boolean.parseBoolean(args[3]));
                }else
                if(args[0].equalsIgnoreCase("Setting")) {
                    Value value = Koks.getKoks().valueManager.getValue(module, args[2]);

                    if (value instanceof BooleanValue) {
                        ((BooleanValue<Boolean>) value).setToggled(Boolean.parseBoolean(args[3]));
                    } else if (value instanceof ModeValue) {
                        ((ModeValue) value).setSelectedMode(args[3]);
                    } else if (value instanceof NumberValue) {
                        if (((NumberValue) value).getMinDefaultValue() != null) {
                            ((NumberValue) value).setMinDefaultValue(NumberFormat.getInstance().parse(args[3]));
                            ((NumberValue) value).setDefaultValue(NumberFormat.getInstance().parse(args[4]));
                        } else {
                            ((NumberValue) value).setDefaultValue(NumberFormat.getInstance().parse(args[3]));
                        }
                    }
                }
            }
            bufferedReader.close();
        }catch (Exception ignored) {

        }

    }

    public void deleteAllConfigs() {
        for(File file : Objects.requireNonNull(DIR.listFiles())) {
            file.delete();
        }
    }

    public void deleteConfig(String name) {
        File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
        file.delete();
    }

    public void createConfig(String name) {
        try {

            if(!DIR.exists())DIR.mkdir();

            File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
            FileWriter fileWriter = new FileWriter(file);

            for(Module module : Koks.getKoks().moduleManager.getModules()) {
                if(module.isToggled() || module.isBypassed()) {
                    fileWriter.write("Module:" + module.getModuleName() + ":" + module.isToggled() + ":" + module.isBypassed() + "\n");
                }
            }

            for(Value value : Koks.getKoks().valueManager.getValues()) {
                Module module = value.getModule();
                if(module.isToggled() || module.isBypassed()) {
                    if (value instanceof BooleanValue) {
                        fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((BooleanValue) value).isToggled() + "\n");
                    } else if (value instanceof ModeValue) {
                        fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((ModeValue) value).getSelectedMode() + "\n");
                    } else if (value instanceof NumberValue) {
                        if (((NumberValue) value).getMinDefaultValue() != null) {
                            fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((NumberValue) value).getMinDefaultValue() + ":" + ((NumberValue) value).getDefaultValue() + "\n");
                        } else {
                            fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((NumberValue) value).getDefaultValue() + "\n");
                        }
                    }
                }

            }
            fileWriter.close();

        }catch (Exception ignored) {

        }
    }
}
