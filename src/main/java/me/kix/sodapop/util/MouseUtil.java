package me.kix.sodapop.util;

/**
 * Handles mouse movement.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class MouseUtil {

    /**
     * Tells whether or not the mouse is within the given boundaries.
     *
     * @param mouseX The x position of the mouse.
     * @param mouseY The y position of the mouse.
     * @param x      The x boundary.
     * @param y      The y boundary.
     * @param x1     The second x boundary.
     * @param y1     The second y boundary.
     * @return Whether or not the mouse is in the given boundaries.
     */
    public static boolean mouseWithinBounds(int mouseX, int mouseY, float x, float y, float x1, float y1) {
        return (mouseX >= x && mouseX <= x1) && (mouseY >= y && mouseY <= y1);
    }

    /**
     * Tells whether or not the mouse is within the given rectangle's position.
     *
     * @param mouseX    The x position of the mouse.
     * @param mouseY    The y position of the mouse.
     * @param rectangle The rectangle of boundaries for the mouse to be confined to.
     * @return Whether or not the mouse is in the given rectangle's position.
     */
    public static boolean mouseWithinRectangle(int mouseX, int mouseY, Rectangle rectangle) {
        return mouseWithinBounds(mouseX, mouseY, rectangle.getX(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
    }
}
