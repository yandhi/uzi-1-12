package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.multiplayer.PlayerController;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.management.event.block.EventDamageBlock;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.block.Block;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Speedmine extends ToggleablePlugin {

    private final Property<Boolean> effect = new Property<>("Effect", true);

    public Speedmine() {
        super("SpeedMine", Category.PLAYER);
        setDisplay("Speed Mine");
        setColor(0xFF96E6B1);
        getProperties().add(effect);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (effect.getValue())
            mc.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, Integer.MAX_VALUE, 0));
        ((PlayerController) mc.playerController).setBlockHitDelay(0);
    }

    @Register
    public void onDamageBlock(EventDamageBlock event) {
        mc.player.swingArm(EnumHand.MAIN_HAND);
        final PlayerController controller = (PlayerController) mc.playerController;
        controller.setCurBlockDamageMP(controller.getCurBlockDamageMP() +
                getBlock(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ())
                        .getPlayerRelativeBlockHardness(getBlock(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ())
                                .getDefaultState(), mc.player, mc.world, event.getPos()) * 0.186f);
    }

    private Block getBlock(double posX, double posY, double posZ) {
        posX = MathHelper.floor(posX);
        posY = MathHelper.floor(posY);
        posZ = MathHelper.floor(posZ);
        return this.mc.world.getChunkFromBlockCoords(new BlockPos(posX, posY, posZ))
                .getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.removePotionEffect(MobEffects.HASTE);
    }
}
