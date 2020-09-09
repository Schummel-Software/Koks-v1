package koks.modules.impl.player;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.Sys;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:53
 */
public class OldAutoArmor extends Module {

    public BooleanValue<Boolean> openedInventory = new BooleanValue<>("Opened Inventory", true, this);
    public NumberValue<Long> startDelayValue = new NumberValue<>("Delay Start", 100L, 500L, 0L, this);
    public NumberValue<Long> equipDelay = new NumberValue<>("Equip Delay", 90L, 125L, 150L, 0L, this);
    public final RandomUtil randomUtil = new RandomUtil();
    public final TimeUtil timeUtilOpening = new TimeUtil();
    public final TimeUtil timeUtil = new TimeUtil();

    public boolean done;

    public OldAutoArmor() {
        super("AutoArmor", Category.PLAYER);

        addValue(openedInventory);
        addValue(startDelayValue);
        addValue(equipDelay);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MotionEvent) {
            if (((MotionEvent) event).getType() == MotionEvent.Type.PRE) {
                setModuleInfo(equipDelay.getMinDefaultValue() + ", " + equipDelay.getDefaultValue());
                if (mc.currentScreen instanceof GuiInventory) {
                    if (!timeUtilOpening.hasReached(startDelayValue.getDefaultValue())) {
                        timeUtil.reset();
                        return;
                    }
                } else {
                    timeUtilOpening.reset();
                    if (openedInventory.isToggled())
                        return;
                }

                for (int i = 1; i < 5; i++) {
                    if (mc.thePlayer.inventoryContainer.getSlot(4 + i).getHasStack()) {
                        ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(4 + i).getStack();
                        if (isBestArmor(itemStack, i)) {
                            continue;
                        } else {
                            if (timeUtil.hasReached(randomUtil.randomLong(equipDelay.getMinDefaultValue(), equipDelay.getDefaultValue()))) {
                                drop(4 + i);
                            }
                        }

                    }
                    for (int j = 9; j < 45; j++) {
                        if (mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                            if (getDamageReduceAmount(itemStack) > 0.0F && isBestArmor(itemStack, i)) {
                                if (timeUtil.hasReached(randomUtil.randomLong(equipDelay.getMinDefaultValue(), equipDelay.getDefaultValue()))) {
                                    shiftClick(j);
                                    timeUtil.reset();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public float getDamageReduceAmount(ItemStack itemStack) {
        float damageReduceAmount = 0.0F;
        if (itemStack.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = ((ItemArmor) itemStack.getItem());
            damageReduceAmount = itemArmor.damageReduceAmount;
            if (itemStack.getEnchantmentTagList() != null) {
                damageReduceAmount += 0.5F;
            }
        }
        return damageReduceAmount;
    }

    public boolean isBestArmor(ItemStack itemStack, int type) {
        float damageReduceAmount = getDamageReduceAmount(itemStack);
        String armorType = "";
        if (type == 1)
            armorType = "helmet";
        if (type == 2)
            armorType = "chestplate";
        if (type == 3)
            armorType = "leggings";
        if (type == 4)
            armorType = "boots";
        if (!(itemStack.getItem().getUnlocalizedName().contains(armorType)))
            return false;
        for (int i = 5; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack itemStack1 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getDamageReduceAmount(itemStack1) > damageReduceAmount && itemStack1.getItem().getUnlocalizedName().contains(armorType)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void shiftClick(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
    }

    public void drop(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }

}
