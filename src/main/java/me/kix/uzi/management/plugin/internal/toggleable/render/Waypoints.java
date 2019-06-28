package me.kix.uzi.management.plugin.internal.toggleable.render;

import com.google.gson.JsonObject;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adds waypoints on the map.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Waypoints extends ToggleablePlugin {

    /**
     * All of our Waypoints that are currently being rendered.
     */
    private static final List<Waypoint> waypoints = new ArrayList<>();

    public Waypoints() {
        super("Waypoints", Category.RENDER);
        setHidden(true);
    }

    @Override
    public void load(JsonObject source) {
        super.load(source);
        JsonObject points = source.getAsJsonObject("Points");
        points.entrySet().forEach(point -> {
            JsonObject manifest = point.getValue().getAsJsonObject();
            waypoints.add(new Waypoint(point.getKey(),
                    manifest.get("Server").getAsString(),
                    manifest.get("Dimension").getAsInt(),
                    manifest.get("X").getAsDouble(),
                    manifest.get("Y").getAsDouble(),
                    manifest.get("Z").getAsDouble()));
        });
    }

    @Override
    public void save(JsonObject destination) {
        super.save(destination);
        JsonObject points = new JsonObject();
        waypoints.forEach(point -> {
            JsonObject manifest = new JsonObject();
            manifest.addProperty("Server", point.getServer());
            manifest.addProperty("Dimension", point.getDimension());
            manifest.addProperty("X", point.getX());
            manifest.addProperty("Y", point.getY());
            manifest.addProperty("Z", point.getZ());
            points.add(point.getLabel(), manifest);
        });
        destination.add("Points", points);
    }

    @Register
    public void onRenderHand(EventRender.Hand event) {
        RenderManager renderManager = mc.getRenderManager();
        waypoints.forEach(waypoint -> {
            float partialTicks = event.getPartialTicks();
            String server = mc.getCurrentServerData() == null ? "SinglePlayer" : mc.getCurrentServerData().serverIP;
            if (mc.player.dimension == waypoint.dimension && server.equals(waypoint.getServer())) {
                draw(mc, waypoint, waypoint.getX() - renderManager.viewerPosX, waypoint.getY() - renderManager.viewerPosY, waypoint.getZ() - renderManager.viewerPosZ);
            }
        });
    }

    /**
     * Draw's a tag above a position.
     * Duplicated from {@link Nametags}
     *
     * @param minecraft The game's instance.
     * @param x         The x offset.
     * @param y         The y offset.
     * @param z         The z offset.
     */
    private void draw(Minecraft minecraft, Waypoint waypoint, double x, double y, double z) {
        double dist = minecraft.player.getDistance(waypoint.getX(), waypoint.getY(), waypoint.getZ());
        // Far renderer distance.
        double away = minecraft.gameSettings.renderDistanceChunks * 12.8D;
        double sqrt = Math.sqrt(x * x + z * z + y * y);
        double newDist;
        if (sqrt > away) {
            newDist = away / sqrt;
            dist *= newDist;
            x *= newDist;
            y *= newDist;
            z *= newDist;
        }
        double distScaled = dist / 3 <= 2 ? 2 : dist / 3;
        // Adjust the distance to scale towards the world.
        double distScaledWrld = 0.016666668 * distScaled;
        // Make it adjust based on sneak state.
        double height = 2 + (distScaled / 6) + 0.325f;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(x, y + height, z);
        GL11.glNormal3f(0f, 1f, 0f);
        // Adjust based on third person for our waypoints.
        if (minecraft.gameSettings.thirdPersonView == 2) {
            // Added a parenthesis here to remove a code duplication warning.
            GlStateManager.rotate((-minecraft.getRenderManager().playerViewY), 0f, 1f, 0f);
            GlStateManager.rotate(minecraft.getRenderManager().playerViewX, -1f, 0f, 0f);
        } else {
            GlStateManager.rotate(-minecraft.getRenderManager().playerViewY, 0f, 1f, 0f);
            GlStateManager.rotate(minecraft.getRenderManager().playerViewX, 1f, 0f, 0f);
        }
        GlStateManager.scale(-distScaledWrld, -distScaledWrld, distScaledWrld);
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        String distance = "\2477" + Math.round(minecraft.player.getDistance(waypoint.getX(), waypoint.getY(), waypoint.getZ())) + "\247f";
        Gui.drawRect(-(minecraft.fontRenderer.getStringWidth(waypoint.getLabel() + " [" + distance + "]") / 2) - 2,
                -2, (minecraft.fontRenderer.getStringWidth(waypoint.getLabel() + " [" + distance + "]") / 2) + 2, 9, new Color(0, 0, 0, 50).getRGB());
        minecraft.fontRenderer.drawStringWithShadow(waypoint.getLabel() + " [" + distance + "]", -(minecraft.fontRenderer.getStringWidth(waypoint.getLabel() + " [" + distance + "]") / 2f) + 0.5f, -0.5f, 0xFFFFFFFF);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    /**
     * Allows us to draw a certain waypoint.
     *
     * @param label The waypoint's label.
     * @param x     The waypoint's pos x.
     * @param y     The waypoint's pos y.
     * @param z     The waypoint's pos z.
     */
    public static void addWaypoint(String label, double x, double y, double z) {
        waypoints.add(new Waypoint(label, mc.getCurrentServerData() == null ? "SinglePlayer" : mc.getCurrentServerData().serverIP,
                mc.player.dimension, x, y, z));
    }

    /**
     * Stops a waypoint from drawing.
     *
     * @param label The waypoint's name.
     */
    public static void removeWaypoint(String label) {
        if (getWaypoint(label).isPresent()) {
            waypoints.remove(getWaypoint(label).get());
        }
    }

    /**
     * Gives us a waypoint.
     *
     * @param label The name of the waypoint being searched for.
     * @return An instance of the found waypoint.
     */
    public static Optional<Waypoint> getWaypoint(String label) {
        return waypoints.stream()
                .filter(waypoint -> waypoint.getLabel().equalsIgnoreCase(label))
                .findFirst();
    }

    /**
     * Basic vector class to store our WayPoints.
     */
    private static class Waypoint {

        /**
         * The label of the WayPoint.
         */
        private final String label;

        /**
         * The server the WayPoint is on.
         */
        private final String server;

        /**
         * The dimension of the WayPoint.
         */
        private final int dimension;

        /**
         * The position of the WayPoint.
         */
        private final double x, y, z;

        public Waypoint(String label, String server, int dimension, double x, double y, double z) {
            this.label = label;
            this.server = server;
            this.dimension = dimension;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String getLabel() {
            return label;
        }

        public String getServer() {
            return server;
        }

        public int getDimension() {
            return dimension;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }
}
