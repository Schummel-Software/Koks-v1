package koks.modules.impl.combat;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:07
 */
public class Velocity extends Module {

    public Velocity() {
        super("Velocity", Category.COMBAT);
    }

    @Override
    public void onEvent(Event event) {
/*        if (event instanceof PacketEvent) {
            if (((PacketEvent) event).getType() == PacketEvent.Type.RECIVE) {
                if (((PacketEvent) event).getPacket() instanceof S12PacketEntityVelocity || ((PacketEvent) event).getPacket() instanceof S27PacketExplosion) {
                        event.setCanceled(true);
                }
            }
        }*/

        if (event instanceof EventUpdate) {
            if (mc.thePlayer.hurtTime == 10 && mc.thePlayer.onGround)
                mc.thePlayer.jump();
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}