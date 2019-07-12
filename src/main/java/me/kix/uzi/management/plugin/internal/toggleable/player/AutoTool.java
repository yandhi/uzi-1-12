package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.block.EventDamageBlock;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;

/**
 * Automatically chooses the most appropriate tool.
 *
 * @author Kix
 * @since 6/30/2019
 */
public class AutoTool extends ToggleablePlugin {

    public AutoTool() {
        super("AutoTool", Category.PLAYER);
        setDisplay("Auto Tool");
    }

    @Register
    public void onBlockBreak(EventDamageBlock event) {
        EntityPlayerSP player = mc.player;
        IBlockState blockState = mc.world.getBlockState(event.getPos());
        mc.player.inventory.currentItem = getStrongestItemSlot(player, blockState);
        mc.playerController.updateController();
    }

    /**
     * Calculates the best possible item for breaking said block.
     *
     * <p>
     * The items are grabbed from the hotbar.
     * </p>
     *
     * @param player     The player switching items.
     * @param blockState The block being broken.
     * @return The slot of the strongest item.
     */
    private int getStrongestItemSlot(EntityPlayerSP player, IBlockState blockState) {
        int currentBestItemSlot = player.inventory.currentItem;
        ItemStack currentBestItem = player.inventory.getStackInSlot(currentBestItemSlot);

        for (int i = 0; i <= 8; i++) {
            ItemStack indexItem = player.inventory.getStackInSlot(i);
            if (indexItem.getStrVsBlock(blockState) > currentBestItem.getStrVsBlock(blockState)) {
                currentBestItemSlot = i;
            }
        }
        return currentBestItemSlot;
    }
}
