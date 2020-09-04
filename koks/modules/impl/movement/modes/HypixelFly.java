package koks.modules.impl.movement.modes;

import koks.event.Event;
import koks.event.impl.EventMove;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.event.impl.PacketEvent;
import koks.utilities.MovementUtil;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 18:14
 */
public class HypixelFly {

    private final Minecraft mc = Minecraft.getMinecraft();

    private int stage;
    private double moveSpeed;

    private final List<Packet> blinkPackets = new ArrayList<>();
    public static boolean zoom = false;

    private final RandomUtil randomUtil = new RandomUtil();
    private final MovementUtil movementUtil = new MovementUtil();
    private final TimeUtil timeUtil = new TimeUtil();

    public void onEvent(Event event) {

        if (event instanceof PacketEvent) {
            if (stage > 2) {
                if (((PacketEvent) event).getType() == PacketEvent.Type.SEND && ((PacketEvent) event).getPacket() instanceof C03PacketPlayer) {
                    blinkPackets.add(((PacketEvent) event).getPacket());
                    event.setCanceled(true);
                }
            }
        }

        if (event instanceof EventMove) {
            if (mc.thePlayer.ticksExisted % 30 == 0) {
                ((EventMove) event).setY(mc.thePlayer.motionY = randomUtil.randomDouble(0.0000000005, 0.000000001));
            }
            switch (this.stage) {
                case 0:
                    this.moveSpeed = 0.5F;
                    break;
                case 1:
                    if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically)
                        ((EventMove) event).setY(mc.thePlayer.motionY = 0.3994D);
                    this.moveSpeed *= 2D;
                    break;
                case 2:
                    break;
                default:
                    this.moveSpeed = this.moveSpeed - this.moveSpeed / 75;
            }
            if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)
                movementUtil.setSpeed(Math.max(this.moveSpeed, 0.285D));
            this.stage++;
        }

        if (event instanceof MotionEvent) {
            if (((MotionEvent) event).getType() == MotionEvent.Type.PRE) {

                if (stage > 2 && timeUtil.hasReached(100)) {
                    mc.timer.timerSpeed = 1F;
                } else {
                    mc.timer.timerSpeed = 2F;
                }

                if (this.stage > 2) {
                    mc.thePlayer.motionY = 0.0D;
                }
                mc.thePlayer.cameraYaw = 0.025F;

                if (mc.thePlayer.ticksExisted % 30 == 0) {
                    PlayerCapabilities playerCapabilities = new PlayerCapabilities();
                    playerCapabilities.isCreativeMode = true;
                    mc.thePlayer.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(playerCapabilities));
                }
            }
        }
    }

    public void onEnable() {
        zoom = false;
        this.moveSpeed = 0.0D;
        this.stage = 0;
        mc.thePlayer.motionX = 0F;
        mc.thePlayer.motionZ = 0F;

        final double x = mc.thePlayer.posX;
        final double y = mc.thePlayer.posY;
        final double z = mc.thePlayer.posZ;
        final NetHandlerPlayClient netHandler = mc.getNetHandler();

        PlayerCapabilities playerCapabilities = new PlayerCapabilities();
        playerCapabilities.isCreativeMode = true;
        netHandler.addToSendQueue(new C13PacketPlayerAbilities(playerCapabilities));

        for (int i = 0; i < mc.thePlayer.getMaxFallHeight() / 0.055 + 1.0; ++i) {
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.06, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.016, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0049 + 0.0003, z, false));
        }
        netHandler.addToSendQueue(new C03PacketPlayer(true));
        zoom = true;
    }

    public void onDisable() {
        mc.thePlayer.motionX = 0F;
        mc.thePlayer.motionZ = 0F;

        final NetHandlerPlayClient netHandler = mc.getNetHandler();
        PlayerCapabilities playerCapabilities = new PlayerCapabilities();
        playerCapabilities.isCreativeMode = true;
        netHandler.addToSendQueue(new C13PacketPlayerAbilities(playerCapabilities));

        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        blinkPackets.forEach(mc.thePlayer.sendQueue.getNetworkManager()::sendPacket);

        zoom = false;
        mc.timer.timerSpeed = 1F;

        this.blinkPackets.clear();
    }

}