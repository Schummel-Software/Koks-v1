package koks.modules.impl.combat;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:07
 */
public class Velocity extends Module {

    public Velocity() {
        super("Velocity", Category.COMBAT);
        addValue(mode);
    }

    public ModeValue<String> mode = new ModeValue<String>("Mode", "Legit", new String[]{"AAC4", "Legit"}, this);

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
            setDisplayName(getModuleName() + " §7" + mode.getSelectedMode());
            switch (mode.getSelectedMode()) {
                case "Legit":
                    if (mc.thePlayer.hurtTime == 10 && mc.thePlayer.onGround)
                        mc.thePlayer.jump();
                    break;
                case "AAC4":
                    if(mc.thePlayer.hurtTime > 0) {
                        mc.thePlayer.motionX *= 0.6;
                        mc.thePlayer.motionZ *= 0.6;
                        mc.thePlayer.motionY *= 1;

                    }
                    break;
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