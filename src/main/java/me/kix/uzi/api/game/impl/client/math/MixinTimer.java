package me.kix.uzi.api.game.impl.client.math;

import me.kix.uzi.api.game.accessors.client.math.GameTimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Kix
 * @since 7/19/2019
 */
@Mixin(Timer.class)
public abstract class MixinTimer implements GameTimer {

    @Shadow
    public int elapsedTicks;

    @Override
    @Accessor
    public abstract void setTickLength(float tickLength);

    @Override
    public void setTimerSpeed(int speed) {
        this.elapsedTicks = speed;
    }
}
