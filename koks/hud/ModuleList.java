package koks.hud;

import koks.Koks;
import koks.modules.Module;
import koks.modules.impl.utilities.HUD;
import koks.modules.impl.visuals.ClearTag;
import koks.utilities.value.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:55
 */
public class ModuleList {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    public void drawList() {
        ScaledResolution sr = new ScaledResolution(mc);
        int[] y = {0};

        Koks.getKoks().moduleManager.getModules().stream().filter(Module::isToggled).sorted(Comparator.comparingDouble(module -> -Minecraft.getMinecraft().fontRendererObj.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getModuleName() : module.getDisplayName()))).forEach(module -> {
            if (Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled()) {
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.getModuleName()) - 4, y[0], sr.getScaledWidth(), y[0] + fr.FONT_HEIGHT + 1, Integer.MIN_VALUE);
                fr.drawStringWithShadow(module.getModuleName(), sr.getScaledWidth() - fr.getStringWidth(module.getModuleName()) - 2, y[0] + 1, 0xFFFFFFFF);
            } else {
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 4, y[0], sr.getScaledWidth(), y[0] + fr.FONT_HEIGHT + 1, Integer.MIN_VALUE);
                fr.drawStringWithShadow(module.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 2, y[0] + 1, 0xFFFFFFFF);
            }
            y[0] += fr.FONT_HEIGHT + 1;
        });
    }


}