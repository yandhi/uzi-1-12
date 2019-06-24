package me.kix.uzi.api.util.math.claim;

import net.minecraft.util.math.BlockPos;

/**
 * A "vector" of sorts for claims.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Claim {

    /**
     * The positions for the claim.
     */
    private BlockPos startPos, endPos;

    public Claim(BlockPos startPos, BlockPos endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public BlockPos getStartPos() {
        return startPos;
    }

    public void setStartPos(BlockPos startPos) {
        this.startPos = startPos;
    }

    public BlockPos getEndPos() {
        return endPos;
    }

    public void setEndPos(BlockPos endPos) {
        this.endPos = endPos;
    }
}
