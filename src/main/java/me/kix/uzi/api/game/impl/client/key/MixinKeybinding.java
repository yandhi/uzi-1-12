package me.kix.uzi.api.game.impl.client.key;

import me.kix.uzi.api.game.accessors.client.key.GameKeybinding;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public abstract class MixinKeybinding implements GameKeybinding {

    @Override
    @Accessor
    public abstract void setPressed(boolean pressed);
}
