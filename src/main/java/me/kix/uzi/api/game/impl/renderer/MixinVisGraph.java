package me.kix.uzi.api.game.impl.renderer;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.block.EventOpaqueCube;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.BitSet;

/**
 * @author Kix
 * @since 6/27/2019
 */
@Mixin(VisGraph.class)
public abstract class MixinVisGraph {

    @Shadow
    @Final
    private BitSet bitSet;

    @Shadow
    private static int getIndex(BlockPos pos) {
        return 0;
    }

    @Shadow
    private int empty;

    /**
     * @author Kix
     * @reason To allow us to modify how the game is rendered.
     */
    @Overwrite
    public void setOpaqueCube(BlockPos pos) {
        EventOpaqueCube opaqueCube = new EventOpaqueCube();
        Uzi.INSTANCE.getEventManager().dispatch(opaqueCube);
        this.bitSet.set(getIndex(pos), opaqueCube.isCancelled());
        --this.empty;
    }
}
