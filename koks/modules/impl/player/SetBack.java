package koks.modules.impl.player;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

import java.util.Random;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:02
 */
public class SetBack extends Module {

    public double deathX, deathY, deathZ;

    public ModeValue<String> mode = new ModeValue<>("Mode", "Intave", new String[]{"Intave", "AAC3.0.1", "Hypixel"}, this);

    public SetBack() {
        super("SetBack", Category.PLAYER);
        Koks.getKoks().valueManager.addValue(mode);
    }

    public void aac301() {

        Random rnd = new Random();

        if (mc.thePlayer.hurtResistantTime != 0 && mc.thePlayer.getHealth() == 0) {

            for (int i = 0; i < 30; i++) {
                mc.thePlayer.setPosition(mc.thePlayer.posX + i, mc.thePlayer.posY + 40, mc.thePlayer.posZ + i);
            }
            mc.thePlayer.respawnPlayer();


        }

        if (mc.thePlayer.getHealth() <= 6F) {
            deathX = mc.thePlayer.posX;
            deathY = mc.thePlayer.posY;
            deathZ = mc.thePlayer.posZ;
            mc.thePlayer.setPosition(mc.thePlayer.posX + rnd.nextDouble() * 1.2, mc.thePlayer.posY - 0.15, mc.thePlayer.posZ + rnd.nextDouble() * 1.2);


        }

    }

    public void intave() {
        if (!mc.thePlayer.isInWater()) {
            if (mc.thePlayer.getHealth() < 3F) {
                for (int i = 0; i < 30; i++) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                            mc.thePlayer.posY, mc.thePlayer.posZ, true));
                }
            }
            if (mc.currentScreen instanceof GuiGameOver) {
                for (int i = 0; i < 30; i++) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                            mc.thePlayer.posY, mc.thePlayer.posZ, true));

                }
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {

            setModuleInfo(mode.getSelectedMode());

            switch (mode.getSelectedMode()) {
                case "Intave":
                    intave();
                    break;
                case "AAC3.0.1":
                    aac301();
                    break;
                case "Hypixel":

                    double distance = 0;

                    for (int i = 0; i < 256; i++) {
                        BlockPos currentPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ);
                        if ((this.mc.theWorld.getBlockState(currentPos).getBlock() != Blocks.air) && (this.mc.theWorld.getBlockState(currentPos).getBlock() != Blocks.grass) && (this.mc.theWorld.getBlockState(currentPos).getBlock() != Blocks.tallgrass) && (this.mc.theWorld.getBlockState(currentPos).getBlock() != Blocks.red_flower) && (this.mc.theWorld.getBlockState(currentPos).getBlock() != Blocks.yellow_flower)) {
                            distance = mc.thePlayer.posY - currentPos.getY();
                        }
                    }


                    if (mc.thePlayer.fallDistance > 4 && distance == 0) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 6D, mc.thePlayer.posZ, false));
                    }
                    if (mc.thePlayer.onGround)
                        mc.thePlayer.fallDistance = 0;

                    break;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        deathX = 0;
        deathY = 0;
        deathZ = 0;
    }
}
