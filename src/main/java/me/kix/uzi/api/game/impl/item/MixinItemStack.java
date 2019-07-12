package me.kix.uzi.api.game.impl.item;

import me.kix.uzi.api.game.accessors.item.Stack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The mixin for item stacks.
 *
 * @author Kix
 * @since 7/8/2019
 */
@Mixin(ItemStack.class)
public class MixinItemStack implements Stack {

    /**
     * The true durability of the item stack.
     */
    private int trueDurability;

    @Inject(method = "<init>(Lnet/minecraft/item/Item;II)V", at = @At("RETURN"))
    private void damageConstructor(Item itemIn, int amount, int meta, CallbackInfo ci) {
        trueDurability = meta;
    }

    @Override
    public int getTrueDurability() {
        return trueDurability;
    }
}
