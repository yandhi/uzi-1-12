package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.management.plugin.internal.toggleable.render.Search;
import net.minecraft.block.Block;

import java.util.Optional;

/**
 * The command for search.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class SearchCommand extends ArgumentativeCommand {

    public SearchCommand() {
        super("Search", new String[]{"xray", "vision"}, "Allows the player to edit what blocks are seen.");
    }

    /**
     * Adds a block to the search.
     *
     * @param blockName The name of the block being added.
     */
    @RegisterArgument({"add", "a", "include"})
    public void addBlock(String blockName) {
        Block block = Block.getBlockFromName(blockName);

        if (block != null) {
            Optional<Plugin> foundPlugin = Uzi.INSTANCE.getPluginManager().getPlugin("Search");

            if (foundPlugin.isPresent()) {
                Search search = (Search) foundPlugin.get();

                search.getBlocks().add(block);
            }
        }
    }

    /**
     * Removes a block from the search.
     *
     * @param blockName The name of the block being removed.
     */
    @RegisterArgument({"remove", "r", "del", "delete"})
    public void removeBlock(String blockName) {
        Block block = Block.getBlockFromName(blockName);

        if (block != null) {
            Optional<Plugin> foundPlugin = Uzi.INSTANCE.getPluginManager().getPlugin("Search");

            if (foundPlugin.isPresent()) {
                Search search = (Search) foundPlugin.get();

                search.getBlocks().remove(block);
            }
        }
    }
}
