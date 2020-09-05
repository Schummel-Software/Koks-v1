package koks.files.impl;

import koks.Koks;
import koks.files.Files;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 02:00
 */
public class client extends Files {

    public client() {
        super("client");
    }

    @Override
    public void writeToFile(FileWriter fileWriter) throws Exception {
        fileWriter.write("clientcolor:" + Koks.getKoks().client_color.getRed() + ":" + Koks.getKoks().client_color.getGreen() + ":" + Koks.getKoks().client_color.getBlue());
        fileWriter.close();
    }

    @Override
    public void readFromFile(BufferedReader fileReader) throws Exception {
        String line;
        while((line = fileReader.readLine()) != null) {
            String[] args = line.split(":");
            if(args[0].equalsIgnoreCase("clientcolor")) {
                Koks.getKoks().client_color = new Color(Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            }
        }
        fileReader.close();
    }
}
