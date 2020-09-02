package koks.gui.clickgui;

import koks.Koks;
import koks.modules.Module;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 00:05
 */
public class CategoryButton {

    private Module.Category category;
    private float x, y, width, height;
    private final RenderUtils renderUtils = new RenderUtils();

    public CategoryButton(Module.Category category) {
        this.category = category;
    }

    public void drawScreen(int mouseX, int mouseY) {

        if (category == Koks.getKoks().clickGUI.category) {
            renderUtils.drawRect(7, x - 3, y, x + width + 3, y + height, new Color(22, 22, 22, 255));
            renderUtils.drawRect(7, x - 3, y, x + width + 3, y + 1, new Color(40, 39, 42, 255));
            renderUtils.drawRect(7, x - 3, y + height - 1, x + width + 3, y + height, new Color(40, 39, 42, 255));
        }

        renderUtils.drawImage(new ResourceLocation("client/icons/" + category.name().toLowerCase() + ".png"), this.x + this.width / 2 - 32 / 2, this.y + height / 2 - 32 / 2, 32, 32, category != Koks.getKoks().clickGUI.category);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            Koks.getKoks().clickGUI.category = this.category;
        }
    }

    public void mouseReleased() {

    }

    public void keyTyped(int keyButton) {

    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x - 3 && mouseX < x + width + 3 && mouseY > y && mouseY < y + height;
    }

    public Module.Category getCategory() {
        return category;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
    }

    public void setInformation(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
