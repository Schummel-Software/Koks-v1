package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.AnimationEvent;
import koks.event.impl.EventUpdate;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.modules.impl.visuals.Animations;
import koks.modules.impl.world.ScaffoldWalk;
import koks.utilities.TimeUtil;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import koks.utilities.value.values.TitleValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:08
 */
public class Sprint extends Module {

    public BooleanValue<Boolean> b = new BooleanValue<>("BooleanTest", true, this);

    public BooleanValue<Boolean> b1 = new BooleanValue<>("BooleanTest1", true, this);
    public BooleanValue<Boolean> b2 = new BooleanValue<>("BooleanTest2", true, this);
    public BooleanValue<Boolean> b3 = new BooleanValue<>("BooleanTest3", true, this);
    public BooleanValue<Boolean> b4 = new BooleanValue<>("BooleanTest4", true, this);

    public ModeValue<String> boxCheckBox = new ModeValue<>("BooleanTest", new BooleanValue[]{b1, b2, b3, b4}, this);

    // IGNORE
    public ModeValue<String> box = new ModeValue<>("BooleanTest", "AAC", new String[]{"AAC", "NCP"}, this);

    public NumberValue<Float> vFloat = new NumberValue<>("BooleanTest", 10F, 1000F, 0F, this);
    public NumberValue<Double> vDouble = new NumberValue<>("BooleanTest", 10D, 100D, 0D, this);
    public NumberValue<Integer> vInteger = new NumberValue<>("BooleanTest", 10, 100, 0, this);
    public NumberValue<Long> vLong = new NumberValue<>("BooleanTest", 10L, 100000L, 0L, this);
    // IGNORE

    public NumberValue<Integer> cps = new NumberValue<>("BooleanTest", 1, 10, 20, 1, this);
    public NumberValue<Float> cpsF = new NumberValue<>("BooleanTest", 1F, 10F, 20F, 1F, this);
    public NumberValue<Long> cpsL = new NumberValue<>("BooleanTest", 1L, 10L, 20L, 1L, this);

    public TitleValue titleValue = new TitleValue("Title CPS", true, new Value[]{cps, cpsF, cpsL}, this);

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        Koks.getKoks().valueManager.addValue(b);

        Koks.getKoks().valueManager.addValue(box);
        Koks.getKoks().valueManager.addValue(boxCheckBox);

        Koks.getKoks().valueManager.addValue(vFloat);
        Koks.getKoks().valueManager.addValue(vDouble);
        Koks.getKoks().valueManager.addValue(vInteger);
        Koks.getKoks().valueManager.addValue(vLong);

        Koks.getKoks().valueManager.addValue(titleValue);
        Koks.getKoks().valueManager.addValue(cps);
        Koks.getKoks().valueManager.addValue(cpsF);
        Koks.getKoks().valueManager.addValue(cpsL);
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof AnimationEvent && Koks.getKoks().moduleManager.getModule(Animations.class).isToggled() && mc.thePlayer.isSprinting()) {
            AnimationEvent a = (AnimationEvent) event;

            a.setBody(0.4F, 0, 0);
            a.setLeftLegPos(0, -0.1F, 0.27F);
            a.setRightLegPos(0, -0.1F, 0.27F);
            a.setLeftArm(2F, 0, 0);
            a.setRightArm(2F, 0, 0);
            a.setHead(0.8F, 0, 0);
            a.setRightLeg(a.getRightLeg()[0] + 0.3F, a.getRightLeg()[1], a.getRightLeg()[2]);
            a.setLeftLeg(a.getLeftLeg()[0] + 0.3F, a.getLeftLeg()[1], a.getLeftLeg()[2]);

        }

        if (event instanceof EventUpdate) {
            if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindBack.isKeyDown() && canSprint()) {
                mc.thePlayer.setSprinting(true);
            } else {
                mc.thePlayer.setSprinting(false);
            }
        }
    }

    public boolean canSprint() {
        ScaffoldWalk scaffoldWalk = Koks.getKoks().moduleManager.getModule(ScaffoldWalk.class);
        if (scaffoldWalk.isToggled() && !(scaffoldWalk.sprint.isToggled()))
            return false;
        return true;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {

    }

}