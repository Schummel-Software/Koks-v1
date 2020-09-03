package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.modules.Module;
import koks.utilities.CornerESPUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:58
 */
public class ChestESP extends Module {

    public CornerESPUtil cornerESPUtil = new CornerESPUtil();

    public ChestESP() {
        super("ChestESP", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            for (TileEntity e : mc.theWorld.loadedTileEntityList) {
                if (e instanceof TileEntityChest) {
                    mc.theWorld.getBlockState(e.getPos()).getBlock();
                    double x = (e.getPos().getX() - mc.getRenderManager().renderPosX);
                    double y = (e.getPos().getY() - mc.getRenderManager().renderPosY);
                    double z = (e.getPos().getZ() - mc.getRenderManager().renderPosZ);

                    cornerESPUtil.drawCorners(x + 0.5, y + 0.5, z + 0.5, 15, 15, 15, 0.7F);
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
