package me.kix.uzi.api.event.events.render;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

/**
 * The event called when the block model is rendered.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class EventRenderBlockModel extends EventCancellable {

    /**
     * The state of the block being rendered.
     */
    private final IBlockState state;

    /**
     * The pos of the block being rendered.
     */
    private final BlockPos pos;

    public EventRenderBlockModel(IBlockState state, BlockPos pos) {
        this.state = state;
        this.pos = pos;
    }

    public IBlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }
}
