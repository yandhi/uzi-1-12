package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GodItemCommand extends ArgumentativeCommand {

    public GodItemCommand() {
        super("GodItem", new String[]{"gitem", "gi", "itemgod", "igod", "godlikeitem", "creategoditem", "enhanceitemwithgodenchants"}, "Allows the user to create/enhance items with maxed out enchantments.");
    }

    @RegisterArgument({"create", "cre", "invent", "magicallyAppear"})
    public void create(String name, String color) {
        ItemStack hammer = new ItemStack(Items.DIAMOND_AXE);
        if (hammer.getItem() != Items.AIR) {
            Enchantment.REGISTRY.forEach(enchantment -> {
                hammer.addEnchantment(enchantment, 127);
            });
            hammer.setStackDisplayName("\247" + color + name);
        }
        Minecraft.getMinecraft().player.addItemStackToInventory(hammer);
        Logger.printMessage("The hammer must be in the hands of greatness.");
    }

    @RegisterArgument({"enhance", "enchant", "god", "maxout"})
    public void enhance(String name, String color) {
        ItemStack currentItem = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
        if (currentItem.getItem() != Items.AIR) {
            Enchantment.REGISTRY.forEach(enchantment -> {
                currentItem.addEnchantment(enchantment, 127);
            });
            currentItem.setStackDisplayName("\247" + color + name);
        }
        Logger.printMessage("One must not abuse this power.");
    }

}
