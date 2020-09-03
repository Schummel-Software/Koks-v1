package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.modules.Module;
import koks.utilities.CornerESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:58
 */
public class ItemESP extends Module {

    public CornerESPUtil cornerESPUtil = new CornerESPUtil();

    public ItemESP() {
        super("ItemESP", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    EntityItem e = (EntityItem) entity;
                    double x = (e.posX - mc.getRenderManager().renderPosX);
                    double y = (e.posY - mc.getRenderManager().renderPosY);
                    double z = (e.posZ - mc.getRenderManager().renderPosZ);

                    cornerESPUtil.drawCorners(x, y + 0.25, z, 5, 5, 5, 0.7F);
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