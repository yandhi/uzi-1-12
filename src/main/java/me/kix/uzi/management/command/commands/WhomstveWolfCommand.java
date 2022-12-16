package me.kix.uzi.management.command.commands;

import java.util.List;
import java.util.stream.Collectors;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.api.util.logging.Logger;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.RayTraceResult;

/**
 * Tells us the owner of the wolves around us.
 * 
 * <p>
 * Courtesy of Zeb for meming this all the time. Credits to Jordin for coming up
 * with this stupid command.
 * </p>
 * 
 * @author Kix
 * @since April 2019
 */
public class WhomstveWolfCommand extends ArgumentativeCommand implements MinecraftAccessor {

    public WhomstveWolfCommand() {
        super("WhomstveWolf", new String[] { "whoswolf", "wolfowner", "whomst", "wwolf" },
                "Tells us who's wolf that is.");
    }

    @RegisterArgument({ "nearby", "close", "range", "n", "r", "c" })
    public void nearby() {
        List<EntityWolf> nearbyWolves = mc.world.loadedEntityList.stream().filter(EntityWolf.class::isInstance)
                .map(EntityWolf.class::cast).collect(Collectors.toList());
        nearbyWolves
                .sort((o1, o2) -> Float.compare(mc.player.getDistance(o1), mc.player.getDistance(o2)));
        if (!nearbyWolves.isEmpty()) {
            EntityWolf nearest = nearbyWolves.get(0);
            Logger.printMessage(nearest.getOwner().getName() + " owns that wolf!");
        } else {
            Logger.printMessage("There are no nearby wolves.");
        }
    }

    @RegisterArgument({ "mouse", "crosshair", "xhair", "m", "ch", "x" })
    public void crosshair() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY
                && mc.objectMouseOver.entityHit instanceof EntityWolf) {
            EntityWolf rayTracedWolf = (EntityWolf) mc.objectMouseOver.entityHit;
            Logger.printMessage(rayTracedWolf.getOwner().getName() + " owns that wolf!");
        } else {
            Logger.printMessage("Your crosshair is not over any wolves.");
        }
    }
}