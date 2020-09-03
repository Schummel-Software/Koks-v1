package koks.gui.clickgui.elements;

import koks.utilities.value.Value;
import koks.utilities.value.values.NumberValue;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 10:09
 */
public class ElementSlider extends Element {

    public boolean dragging;
    public final NumberValue numberValue;

    public ElementSlider(NumberValue value) {
        super(value);
        this.numberValue = value;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void keyTyped(int keyCode) {

    }
}
