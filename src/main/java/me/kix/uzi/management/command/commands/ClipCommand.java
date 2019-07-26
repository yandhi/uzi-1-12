package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;

/**
 * Allows the player to vclip.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class ClipCommand extends ArgumentativeCommand {

    public ClipCommand() {
        super("Clip", new String[]{"vclip", "vc", "cli", "cl"}, "Allows the player to vertically clip.");
    }

    /**
     * Vertically clips the player.
     *
     * @param distanceToParse The distance the player will move.
     */
    @RegisterArgument({"vertical", "v", "up", "down"})
    public void vertical(String distanceToParse) {
        double distance = Double.parseDouble(distanceToParse);

        if (distance != 0) {
            if (Minecraft.getMinecraft().player.getRidingEntity() != null) {
                Minecraft.getMinecraft().player.getRidingEntity().setPosition(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + distance, Minecraft.getMinecraft().player.posZ);
            } else {
                Minecraft.getMinecraft().player.setPosition(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + distance, Minecraft.getMinecraft().player.posZ);
            }
        }
    }

    /**
     * Horizontally clips the player.
     *
     * @param distanceToParse The distance that they player will move.
     */
    @RegisterArgument({"horizontal", "h", "left", "right", "forward", "backward"})
    public void horizontal(String distanceToParse) {
        double distance = Double.parseDouble(distanceToParse);

        if (distance != 0) {
            final Vec3d directionVec = MathUtil.direction(Minecraft.getMinecraft().player.rotationYaw);

            if (Minecraft.getMinecraft().player.getRidingEntity() != null) {
                Minecraft.getMinecraft().player.getRidingEntity().setPosition(Minecraft.getMinecraft().player.posX + directionVec.x * distance, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ + directionVec.z * distance);
            } else {
                Minecraft.getMinecraft().player.setPosition(Minecraft.getMinecraft().player.posX + directionVec.x * distance, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ + directionVec.z * distance);
            }
        }
    }
}
