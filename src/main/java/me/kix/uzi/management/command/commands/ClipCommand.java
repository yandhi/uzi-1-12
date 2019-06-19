package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.Command;
import net.minecraft.client.Minecraft;

/**
 * Allows the player to vclip.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class ClipCommand extends Command {

    public ClipCommand() {
        super("Clip", new String[]{"vclip", "vc", "cli", "cl"}, "Allows the player to vertically clip.");
    }

    @Override
    public void execute(String args) {
        String[] splitArgs = args.split(" ");

        if (args.length() >= 2) {
            int distance = Integer.parseInt(splitArgs[1]);
            Minecraft.getMinecraft().player.setPositionAndUpdate(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + distance, Minecraft.getMinecraft().player.posZ);
        }
    }
}
