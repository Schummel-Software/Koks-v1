package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.AnimationEvent;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.modules.impl.movement.modes.HypixelFly;
import koks.modules.impl.visuals.Animations;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 18:14
 */
public class Fly extends Module {

    private final HypixelFly hypixelFly;
    public final ModeValue<String> modeValue = new ModeValue<>("Mode", "Hypixel", new String[]{"Hypixel","AAC3.2.2"}, this);
    public final NumberValue<Integer> aac322boost = new NumberValue<Integer>("AAC3.2.2-Boost",9,10,5,this);

    public Fly() {
        super("Fly", Category.MOVEMENT);
        addValue(aac322boost);
        Koks.getKoks().valueManager.addValue(modeValue);
        hypixelFly = new HypixelFly();
    }

    @Override
    public void onEvent(Event event) {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onEvent(event);
                break;
        }

        if(event instanceof EventUpdate) {
            setDisplayName(getModuleName() + " §7" + modeValue.getSelectedMode());
            switch (modeValue.getSelectedMode()) {
                case "AAC3.2.2":
                    aac322();
                    break;
            }
        }

        if(event instanceof AnimationEvent && Koks.getKoks().moduleManager.getModule(Animations.class).isToggled()) {
            AnimationEvent animationEvent = (AnimationEvent) event;
            animationEvent.setRightLeg(1.5F,0F,0F);
            animationEvent.setLeftLeg(1.5F,0F,0F);
            animationEvent.setBody(1.5F,0F,0F);
            animationEvent.setBodyPos(0, 0.7F ,-0.5F);
            animationEvent.setHead(0.6F,0F,0F);
            animationEvent.setLeftArm(-1.5F,0F,0F);
            animationEvent.setRightArm(-1.5F,0F,0F);
            animationEvent.setRightArmPos(0, 0.7F ,-0.5F);
            animationEvent.setLeftArmPos(0, 0.7F ,-0.5F);
            animationEvent.setHeadPos(0, 0.7F ,-0.5F);
        }
    }

    public void aac322() {
        if(mc.thePlayer.posY <= -70) {
            mc.thePlayer.motionY = aac322boost.getDefaultValue();
        }
        if(mc.gameSettings.keyBindSprint.pressed && !mc.thePlayer.onGround) {
            mc.timer.timerSpeed = 0.1F;
            mc.rightClickDelayTimer = 0;
        }else{
            mc.timer.timerSpeed = 1F;
            mc.rightClickDelayTimer = 6;
        }
    }

    @Override
    public void onEnable() {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onEnable();
                break;
        }
    }

    @Override
    public void onDisable() {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onDisable();
                break;
            case "AAC3.2.2":
                mc.timer.timerSpeed = 1F;
                mc.rightClickDelayTimer = 6;
                break;
        }
    }


}
