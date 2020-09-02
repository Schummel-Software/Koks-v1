package koks.gui.clickgui;

import koks.modules.Module;
import koks.utilities.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:10
 */
public class ClickGUI extends GuiScreen {

    public List<CategoryButton> panelList = new ArrayList<>();
    public int x, y, width, height, dragX, dragY;
    private boolean dragging;
    private final RenderUtils renderUtils = new RenderUtils();

    public Module.Category category = Module.Category.COMBAT;

    public ClickGUI() {
        this.x = 50;
        this.y = 50;
        this.width = 350;
        this.height = 300;
        Arrays.stream(Module.Category.values()).forEach(category -> panelList.add(new CategoryButton(category)));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (dragging) {
            this.x = dragX + mouseX;
            this.y = dragY + mouseY;
        }

        Gui.drawRect(x, y, x + 80, y + height, new Color(12, 12, 12, 255).getRGB());
        Gui.drawRect(x + 50, y, x + 51, y + height, new Color(40, 39, 42, 255).getRGB());
        Gui.drawRect(x + 51, y, x + width, y + height, new Color(22, 22, 22, 255).getRGB());

        final float[] y = {this.y + 2.5F};
        panelList.forEach(panelButton -> {
            panelButton.setInformation(x + 3, y[0], 45, 45);
            panelButton.drawScreen(mouseX, mouseY);
            y[0] += 50;
        });

        renderUtils.drawOutlineRect(x, this.y, x + width, this.y + height, 2, new Color(40, 39, 42, 255));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + 4.5F && mouseButton == 0) {
            this.dragging = true;
            this.dragX = x - mouseX;
            this.dragY = y - mouseY;
        }
        panelList.forEach(panelButton -> panelButton.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        panelList.forEach(CategoryButton::mouseReleased);
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        panelList.forEach(panelButton -> panelButton.keyTyped(keyCode));
        super.keyTyped(typedChar, keyCode);
    }

}
