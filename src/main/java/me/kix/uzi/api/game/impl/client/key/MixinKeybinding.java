package me.kix.uzi.api.game.impl.client.key;

import me.kix.uzi.api.game.accessors.client.key.IKeybinding;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public abstract class MixinKeybinding implements IKeybinding {

    @Override
    @Accessor
    public abstract void setPressed(boolean pressed);
}
