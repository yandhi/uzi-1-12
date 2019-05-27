package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.management.plugin.internal.toggleable.render.Waypoints;

/**
 * Handles waypoints for the client.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class WaypointsCommand extends ArgumentativeCommand {

    public WaypointsCommand() {
        super("Waypoints", new String[]{"wp", "point", "way", "wayp"}, "Adds/Removes a waypoint from the client.");
    }

    @RegisterArgument({"add", "a"})
    public void addWaypoint(String label, String x, String y, String z) {
        Waypoints.addWaypoint(label, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
        Logger.printMessage(String.format("Added a waypoint named %s at (%s, %s, %s).", label, x, y, z));
    }

    @RegisterArgument({"remove", "rm", "delete", "del"})
    public void removeWaypoint(String label) {
        Waypoints.removeWaypoint(label);
        Logger.printMessage(String.format("Removed a waypoint named %s.", label));
    }
}
