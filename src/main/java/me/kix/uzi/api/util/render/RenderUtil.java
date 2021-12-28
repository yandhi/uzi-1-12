package me.kix.uzi.api.util.render;

import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.awt.*;

public class RenderUtil implements MinecraftAccessor {

    private static final Frustum viewFrustum = new Frustum();

    public static boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    /**
     * This is from render global
     */
    public static void renderFilledBox(AxisAlignedBB p_189696_0_, float p_189696_1_, float p_189696_2_, float p_189696_3_, float p_189696_4_) {
        GL11.glPushMatrix();
        enable3D();
        renderFilledBox(p_189696_0_.minX, p_189696_0_.minY, p_189696_0_.minZ, p_189696_0_.maxX, p_189696_0_.maxY, p_189696_0_.maxZ, p_189696_1_, p_189696_2_, p_189696_3_, p_189696_4_);
        disable3D();
        GL11.glPopMatrix();
    }


    /**
     * This is from render global
     */
    public static void renderFilledBox(double p_189695_0_, double p_189695_2_, double p_189695_4_, double p_189695_6_, double p_189695_8_, double p_189695_10_, float p_189695_12_, float p_189695_13_, float p_189695_14_, float p_189695_15_) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        addChainedFilledBoxVertices(bufferbuilder, p_189695_0_, p_189695_2_, p_189695_4_, p_189695_6_, p_189695_8_, p_189695_10_, p_189695_12_, p_189695_13_, p_189695_14_, p_189695_15_);
        tessellator.draw();
    }

    /**
     * This is from render global
     */
    public static void addChainedFilledBoxVertices(BufferBuilder builder, double p_189693_1_, double p_189693_3_, double p_189693_5_, double p_189693_7_, double p_189693_9_, double p_189693_11_, float red, float green, float blue, float alpha) {
        builder.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        builder.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
    }

    public static void drawTinyString(String str, float x, float y, int color) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        mc.fontRenderer.drawStringWithShadow(str, x * 2, y * 2, color);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedString(String str, int x, int y, int color) {
        mc.fontRenderer.drawString(str, x + 1, y, 0);
        mc.fontRenderer.drawString(str, x - 1, y, 0);
        mc.fontRenderer.drawString(str, x, y + 1, 0);
        mc.fontRenderer.drawString(str, x, y - 1, 0);
        mc.fontRenderer.drawString(str, x, y, color);
    }

    public static void drawBlendedShadowedString(String str, int x, int y, Color color1, Color color2) {
        int charX = x;
        for (int i = 0; i < str.toCharArray().length; i++) {
            mc.fontRenderer.drawStringWithShadow(Character.toString(str.toCharArray()[i]), charX, y, mixColors(color1, color2, (double) (i * str.toCharArray().length) / 100).getRGB());
            charX += mc.fontRenderer.getCharWidth(str.toCharArray()[i]);
        }
    }

    /**
     * @author Camden Rodriguez
     */
    public static void prepareScissorBox(ScaledResolution sr, float x, float y, float width, float height) {
        float x2 = x + width;
        float y2 = y + height;
        int factor = sr.getScaleFactor();
        GL11.glScissor((int) (x * factor), (int) ((sr.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    /**
     * https://stackoverflow.com/questions/17544157/generate-n-colors-between-two-colors
     */
    public static Color mixColors(Color color1, Color color2, double percent) {
        double inverse_percent = 1.0 - percent;
        int redPart = (int) (color1.getRed() * percent + color2.getRed() * inverse_percent);
        int greenPart = (int) (color1.getGreen() * percent + color2.getGreen() * inverse_percent);
        int bluePart = (int) (color1.getBlue() * percent + color2.getBlue() * inverse_percent);
        return new Color(redPart, greenPart, bluePart);
    }

    /**
     * @author Halalaboos
     */
    public static int genVbo() {
        int id = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        return id;
    }

    /**
     * Taught to me by Alerithe.
     *
     * @author Kix
     */
    public static boolean isInViewFrustrum(AxisAlignedBB aabb) {
        Entity currentViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        viewFrustum.setPosition(currentViewEntity.posX, currentViewEntity.posY, currentViewEntity.posZ);
        return viewFrustum.isBoundingBoxInFrustum(aabb);
    }

    /**
     * Sets up drawing for 2D.
     */
    public static void enable2D() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
    }

    /**
     * Ends the drawing for 2D
     */
    public static void disable2D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
    }

    /**
     * Sets up drawing for 3D.
     */
    public static void enable3D() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
    }

    /**
     * Ends the drawing for 3D.
     */
    public static void disable3D() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * Colors the GL matrix.
     *
     * @param color The color that you want to paint the matrix.
     */
    public static void color(int color) {
        GL11.glColor4f((color >> 16 & 0xFF) / 255f,
                (color >> 8 & 0xFF) / 255f,
                (color & 0xFF) / 255f,
                (color >> 24 & 0xFF) / 255f);
    }

    /**
     * Draws us a border and applies color and width to it.
     *
     * @param x     The top left position.
     * @param y     The top position.
     * @param x1    The top right position.
     * @param y1    The bottom position
     * @param width The line width.
     * @param color The line color.
     */
    public static void border(float x, float y, float x1, float y1, float width, int color) {
        enable2D();
        GL11.glLineWidth(width);
        color(color);
        border(x, y, x1, y1);
        disable2D();
    }

    /**
     * Draws us a border around the given points.
     * This is essentially the same thing as a rectangle but instead of GL_QUADS we are
     * using GL_LINES.
     *
     * @param x  The top left position.
     * @param y  The top position.
     * @param x1 The top right position.
     * @param y1 The bottom position
     */
    public static void border(float x, float y, float x1, float y1) {
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
    }

    /**
     * Draws a horizontal gradient rectangle.
     *
     * @param x      The top left position.
     * @param y      The top position.
     * @param x1     The top right position.
     * @param y1     The bottom position.
     * @param color1 The first color for the gradient.
     * @param color2 The second color for the gradient.
     */
    public static void horizontalGradientRectangle(float x, float y, float x1, float y1, int color1, int color2) {
        enable2D();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_QUADS);
        color(color1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        color(color2);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glEnd();
        GL11.glPopMatrix();
        disable2D();
        GL11.glShadeModel(GL11.GL_FLAT);
    }

    /**
     * Draws a vertical gradient rectangle.
     *
     * @param x      The top left position.
     * @param y      The top position.
     * @param x1     The top right position.
     * @param y1     The bottom position.
     * @param color1 The first color for the gradient.
     * @param color2 The second color for the gradient.
     */
    public static void verticalGradientRectangle(float x, float y, float x1, float y1, int color1, int color2) {
        enable2D();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_QUADS);
        color(color1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        color(color2);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glPopMatrix();
        disable2D();
        GL11.glShadeModel(GL11.GL_FLAT);
    }

    /**
     * Draws an outline around a bounding box with setup and coloring as well as width.
     *
     * @param bb    The bounding box that will be outlined.
     * @param width The width of the outline.
     * @param color The color of the outline.
     */
    public static void bb(AxisAlignedBB bb, float width, int color) {
        enable3D();
        GL11.glLineWidth(width);
        color(color);
        drawFilledBox(bb, new Color(color));
        drawOutlinedBox(bb);
        disable3D();
    }

    /**
     * Draws an outline around a bounding box with setup and coloring as well as width.
     *
     * @param bb    The bounding box that will be outlined.
     * @param width The width of the outline.
     * @param color The color of the outline.
     */
    public static void bb(AxisAlignedBB bb, float width, Color color) {
        enable3D();
        GL11.glLineWidth(width);
        color(color.getRGB());
        drawFilledBox(bb, color);
        drawOutlinedBox(bb);
        disable3D();
    }

    /**
     * From old Uzi.
     * All GL parameters are pre-compiled because I had to grab this from jd-gui due to me losing the src to old Uzi.
     *
     * @author Kix
     */
    public static void drawTracerLine(double[] pos, float[] c, float width, ScaledResolution res) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glColor4f(c[0], c[1], c[2], c[3]);
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(res.getScaledWidth() / 2, res.getScaledHeight() / 2, 0);
            GL11.glVertex3d(pos[0] + ((pos[2] - pos[0]) / 2), pos[1] + ((pos[3] - pos[1]) / 2), pos[2]);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos).getBoundingBox(Minecraft.getMinecraft().world, pos).offset(pos);
    }

    /**
     * Draws a filled box.
     *
     * @param bb The bounding box being drawn.
     */
    public static void drawFilledBox(AxisAlignedBB bb, Color color) {
        float red = (color.getRGB() >> 16 & 0xFF) / 255f;
        float green = (color.getRGB() >> 8 & 0xFF) / 255f;
        float blue = (color.getRGB() & 0xFF) / 255f;
        float alpha = (color.getRGB() >> 24 & 0xFF) / 255f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }

    public static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        }
        GL11.glEnd();
    }

    /**
     * Straight from the Gui class.
     *
     * @author Kix
     */
    public static void drawRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double) left, (double) bottom, 0.0D).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, 0.0D).endVertex();
        bufferbuilder.pos((double) right, (double) top, 0.0D).endVertex();
        bufferbuilder.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }


    /**
     * Used for making hollox boxes in newer versions.
     *
     * @author Kix
     */
    public static void drawHorizontalLine(float x, float y, float x1, float thickness, int color) {
        drawRect(x, y, x1, y + thickness, color);
    }

    /**
     * Used for making hollox boxes in newer versions.
     *
     * @author Kix
     */
    public static void drawVerticalLine(float x, float y, float y1, float thickness, int color) {
        drawRect(x, y, x + thickness, y1, color);
    }

    /**
     * Made for any version, prevents changes in buffering like what happened between 1.8-1.12
     *
     * @author Kix
     */
    public static void drawHollowBox(float x, float y, float x1, float y1, float thickness, int color) {
        /* Top */
        drawHorizontalLine(x, y, x1, thickness, color);
        /* Bottom */
        drawHorizontalLine(x, y1, x1, thickness, color);
        /* Left */
        drawVerticalLine(x, y, y1, thickness, color);
        /* Right */
        drawVerticalLine(x1 - thickness, y, y1, thickness, color);
    }

}
