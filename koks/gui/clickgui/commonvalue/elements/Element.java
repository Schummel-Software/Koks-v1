package koks.gui.clickgui.commonvalue.elements;

import entresto.hud.clickgui.commonsettings.CommonSetting;
import entresto.main.Main;
import entresto.module.Module;
import entresto.module.modules.visuals.ClickGUI;
import entresto.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.io.IOException;

public class Element {

    public final Minecraft mc = Minecraft.getMinecraft();
    public final FontRenderer fr = mc.fontRendererObj;
    public int x, y, width, height;
    public CommonSetting setting;

    public void updateValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}