package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 00:18
 */
public class InventoryManager extends Module {

    public BooleanValue<Boolean> openedInventory = new BooleanValue<>("Opened Inventory", true, this);
    public NumberValue<Long> startDelay = new NumberValue<>("Start Delay", 100L, 500L, 0L, this);
    public NumberValue<Long> throwDelay = new NumberValue<>("Throw Delay", 90L, 125L, 150L, 0L, this);
    private final RandomUtil randomUtil = new RandomUtil();
    private final TimeUtil startTimer = new TimeUtil();
    private final TimeUtil throwTimer = new TimeUtil();

    public InventoryManager() {
        super("InventoryManager", Category.PLAYER);

        addValue(openedInventory);
        addValue(startDelay);
        addValue(throwDelay);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(throwDelay.getMinDefaultValue() + ", " + throwDelay.getDefaultValue() + (openedInventory.isToggled() ? ", Opened Inventory" : ""));
            if (mc.currentScreen instanceof GuiInventory) {
                if (!startTimer.hasReached(startDelay.getDefaultValue())) {
                    throwTimer.reset();
                    return;
                }
            } else {
                startTimer.reset();
                if (openedInventory.isToggled())
                    return;
            }

            for (int i = 9; i < 45; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                    if (throwTimer.hasReached(randomUtil.randomLong(throwDelay.getMinDefaultValue(), throwDelay.getDefaultValue()))) {
                        if (is.getItem() instanceof ItemSword && is == bestSword() && mc.thePlayer.inventoryContainer.getInventory().contains(bestSword()) && mc.thePlayer.inventoryContainer.getSlot(36).getStack() != is) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 2, mc.thePlayer);
                        } else if (is.getItem() instanceof ItemBow && is == bestBow() && mc.thePlayer.inventoryContainer.getInventory().contains(bestBow()) && mc.thePlayer.inventoryContainer.getSlot(37).getStack() != is) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 1, 2, mc.thePlayer);
                        } else if (isBadStack(is)) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 1, 4, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean isBadStack(ItemStack is) {
        if (is.getItem() instanceof ItemSword && is != bestSword())
            return true;
        if (is.getItem() instanceof ItemBow && is != bestBow())
            return true;
        if (is.getUnlocalizedName().equals("mushroom") || is.getUnlocalizedName().equals("furnace") || is.getUnlocalizedName().equals("feather"))
            return true;
        return false;
    }

    public ItemStack bestSword() {
        ItemStack bestSword = null;
        float itemDamage = -1;

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword) {
                    float swordDamage = getSwordDamage(is);
                    if (swordDamage >= itemDamage) {
                        itemDamage = getSwordDamage(is);
                        bestSword = is;
                    }
                }
            }
        }

        return bestSword;
    }

    public float getSwordDamage(ItemStack itemStack) {
        Item is = itemStack.getItem();
        float damage = (((ItemSword) is).getDamageVsEntity());
        //damage -= ((ItemSword) is).getToolMaterialName().equals("GOLD") ? 0.01 : 0;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.50F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.10F;
        damage += (itemStack.getMaxDamage() - itemStack.getItemDamage()) * 0.0000001F;
        return damage;
    }

    public ItemStack bestBow() {
        ItemStack bestBow = null;
        float itemDamage = -1;

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemBow) {
                    float bowDamage = getBowDamage(is);
                    if (bowDamage >= itemDamage) {
                        itemDamage = getBowDamage(is);
                        bestBow = is;
                    }
                }
            }
        }

        return bestBow;
    }

    public float getBowDamage(ItemStack itemStack) {
        float damage = 5;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack) * 1.25F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 0.75F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) * 0.50F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.10F;
        damage += itemStack.getMaxDamage() - itemStack.getItemDamage() * 0.001F;
        return damage;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}