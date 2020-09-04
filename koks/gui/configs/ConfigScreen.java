package koks.gui.configs;

import koks.Koks;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:22
 */
public class ConfigScreen extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final RenderUtils renderUtils = new RenderUtils();
    public int x, y, width, height, panelHeight, dragX, dragY;
    private ScaledResolution sr;
    public boolean dragging;

    public ConfigScreen() {
        this.x = 100;
        this.y = 100;
        this.width = 300;
        this.height = 400;
        this.panelHeight = 20;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        Gui.drawGradientRect(x, y, x + width, y + panelHeight, new Color(32, 32, 32, 255).getRGB(), new Color(16, 16, 16, 255).getRGB());
        Gui.drawRect(x, y + panelHeight, x + width, y + height, new Color(22, 22, 22, 255).getRGB());
        renderUtils.drawOutlineRect(x, y, x + width, y + height, 2, Koks.getKoks().client_color);
        fr.drawString("Config Manager", x + width / 2 - fr.getStringWidth("Config Manager") / 2, y + panelHeight / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + panelHeight) {
            dragX = x - mouseX;
            dragY = y - mouseY;
            dragging = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}