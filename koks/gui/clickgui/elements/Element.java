package koks.gui.clickgui.elements;

import koks.utilities.RenderUtils;
import koks.utilities.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:30
 */
public abstract class Element {

    private float x, y, width, height;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private Value value;
    private final RenderUtils renderUtils = new RenderUtils();

    public Element(Value value) {
        this.value = value;
    }

    public void setPosition(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void drawScreen(int mouseX, int mouseY);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased();

    public abstract void keyTyped(int keyCode);

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

    public Minecraft getMc() {
        return mc;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }
}
