package koks.files;

import koks.Koks;
import koks.files.impl.KeyBindFile;
import koks.files.impl.Toggled;
import koks.files.impl.client;
import koks.files.impl.settings;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 16:30
 */
public class FileManager {

    public final Minecraft mc = Minecraft.getMinecraft();

    private final File DIR = new File(mc.mcDataDir + "/" + Koks.getKoks().CLIENT_NAME);

    public ArrayList<Files> files = new ArrayList<>();

    public FileManager() {
        addFile(new KeyBindFile());
        addFile(new Toggled());
/*
        addFile(new settings());
*/
        addFile(new client());
    }

    private void addFile(Files file) {
        files.add(file);
    }

    public Files getFiles(Class clazz) {
        for(Files file : files) {
            if(file.getClass() == clazz) {
                return file;
            }
        }
        return null;
    }

    public void writeToAllFiles() {
        for(Files file : files) {
            try {
                file.writeToFile(new FileWriter(file.getFile()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createFiles() {
        if(!DIR.exists())DIR.mkdir();

        for(Files file : files) {
            if(!file.existFile())file.createFile();
            try {
                file.readFromFile(new BufferedReader(new FileReader(file.getFile())));
            }catch(Exception ex){
                System.out.println("File doesn't exist.");
            }
        }
    }

    public ArrayList<Files> getFiles() {
        return files;
    }
}
