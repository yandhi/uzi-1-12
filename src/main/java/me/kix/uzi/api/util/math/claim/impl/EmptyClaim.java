package me.kix.uzi.api.util.math.claim.impl;

import me.kix.uzi.api.util.math.claim.Claim;
import net.minecraft.util.math.BlockPos;

/**
 * An empty form of {@link me.kix.uzi.api.util.math.claim.Claim}.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class EmptyClaim extends Claim {

    public EmptyClaim() {
        super(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0));
    }
}
