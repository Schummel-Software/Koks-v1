package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 18:59
 */
public class Jesus extends Module {

    public Jesus() {
        super("Jesus" , Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {//INTAVE
            BlockPos bPos = new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ);
            if(mc.theWorld.getBlockState(bPos).getBlock() == Blocks.water || mc.theWorld.getBlockState(bPos).getBlock() == Blocks.flowing_water || mc.theWorld.getBlockState(bPos).getBlock() == Blocks.lava || mc.theWorld.getBlockState(bPos).getBlock() == Blocks.flowing_lava) {

                mc.thePlayer.motionY = 0.005;

                mc.thePlayer.onGround = true;
                mc.thePlayer.motionZ *= 0.9F;
                mc.thePlayer.motionX *= 0.9F;
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
