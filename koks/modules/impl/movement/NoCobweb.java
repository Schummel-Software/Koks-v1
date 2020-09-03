package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:01
 */
public class NoCobweb extends Module {

    public NoCobweb() {
        super("NoCobweb", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            BlockPos bPos = new BlockPos(mc.thePlayer.getPosition());

            if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY - 0.1,mc.thePlayer.posZ)).getBlock() == Blocks.web) {

                mc.thePlayer.motionY = 0.06;
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
