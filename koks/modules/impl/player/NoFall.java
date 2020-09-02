package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:39
 */
public class NoFall extends Module {

    public NoFall() {
        super("NoFall", Category.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.thePlayer.onGround)
                return;
            if (mc.thePlayer.fallDistance > 2) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                mc.thePlayer.fallDistance = 0;
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