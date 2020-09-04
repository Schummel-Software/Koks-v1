package koks.gui.configs.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:24
 */
public class DrawConfigs {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final ArrayList<RightClickScreen> screens = new ArrayList<>();
    public int x, y, width, height, clickX, clickY;
    public final File file;
    public boolean choosing;

    public DrawConfigs(File file) {
        this.file = file;
    }

    public void updateValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        screens.add(new RightClickScreen(file));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String[] name = file.getName().split("\\.");
        Gui.drawRect(x + 4, y, x + width - 4, y + height, new Color(28, 28, 28, 255).getRGB());
        fr.drawString(name[0], x + width / 2 - fr.getStringWidth(name[0]) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);

        if (choosing) {
            for (RightClickScreen rightClickScreen : screens) {
                rightClickScreen.updateValues(x + clickX, y + clickY, width, height);
                rightClickScreen.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x + 4 && mouseX < x + width - 4 && mouseY > y && mouseY < y + height;
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
            clickX = mouseX - x;
            clickY = mouseY - y;
            choosing = !choosing;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}