package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 10.09.2020 : 09:01
 */
public class Blink extends Module {

    public ArrayList<Packet> packets = new ArrayList<>();

    public Blink() {
        super("Blink", Category.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (e.getType() == PacketEvent.Type.SEND) {
                if (e.getPacket() instanceof C03PacketPlayer) {
                    packets.add(e.getPacket());
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        packets.forEach(mc.thePlayer.sendQueue.getNetworkManager()::sendPacket);
        packets.clear();
    }

}