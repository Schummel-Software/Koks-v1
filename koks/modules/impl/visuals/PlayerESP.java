package koks.modules.impl.visuals;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.modules.Module;
import koks.utilities.CornerESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:58
 */
public class PlayerESP extends Module {

    public CornerESPUtil cornerESPUtil = new CornerESPUtil();

    public PlayerESP() {
        super("PlayerESP", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            float partialTicks = ((EventRender3D) event).getPartialTicks();
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (Koks.getKoks().moduleManager.getModule(NameTags.class).isValid(entity)) {
                    double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks) - mc.getRenderManager().renderPosX;
                    double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) - mc.getRenderManager().renderPosY;
                    double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks) - mc.getRenderManager().renderPosZ;

                    cornerESPUtil.drawCorners(x, y + 0.9, z, 20, 40, 40, 0.7F);
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}