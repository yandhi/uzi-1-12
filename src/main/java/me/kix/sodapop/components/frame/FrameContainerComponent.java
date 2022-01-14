package me.kix.sodapop.components.frame;

import me.kix.sodapop.components.container.ContainerComponent;
import me.kix.sodapop.components.container.layout.LayoutStrategy;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.MouseUtil;
import me.kix.sodapop.util.Rectangle;
import me.kix.sodapop.manage.GuiManager;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * A "window"-like container component.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class FrameContainerComponent extends ContainerComponent {

    /**
     * Whether or not the frame is currently dragging.
     */
    private boolean dragging;

    /**
     * Whether or not the frame can scroll.
     */
    private boolean canScroll;

    /**
     * The position that the x and y is dropped at.
     */
    private int droppedX, droppedY;

    /**
     * The scrolling y position.
     */
    private int scrollY;

    public FrameContainerComponent(String name, GuiManager guiManager, Rectangle renderPosition, LayoutStrategy layout) {
        super(name, guiManager, renderPosition, layout);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.canScroll = getComponents().size() >= 6 && MouseUtil.mouseWithinBounds(mouseX, mouseY, getRenderPosition().getX(), getRenderPosition().getY() + getRenderPosition().getHeight(), getRenderPosition().getX() + getRenderPosition().getWidth(), getRenderPosition().getY() + getRenderPosition().getHeight() + (6 * (getGuiManager().getTheme().getComponentHeight() + getGuiManager().getTheme().getVerticalPadding())));
        updateDimensions(getGuiManager().getTheme().getWidth(), getGuiManager().getTheme().getHeight());
        if (dragging) {
            updatePosition(mouseX + droppedX, mouseY + droppedY);
        }
        getLayout().layout(this, getGuiManager().getTheme());
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            if (mouseButton == MouseButton.LEFT) {
                this.dragging = true;
                this.droppedX = getRenderPosition().getX() - mouseX;
                this.droppedY = getRenderPosition().getY() - mouseY;
            }
        }

        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            if (mouseButton == MouseButton.RIGHT) {
                setExtended(!isExtended());
            }
        }

        if (isExtended()) {
            getComponents().stream()
                    .filter(component -> MouseUtil.mouseWithinBounds(mouseX, mouseY, getRenderPosition().getX(), getRenderPosition().getY() + getRenderPosition().getHeight(), getRenderPosition().getX() + getRenderPosition().getWidth(), getRenderPosition().getY() + getRenderPosition().getHeight() + (6 * (getGuiManager().getTheme().getComponentHeight() + getGuiManager().getTheme().getVerticalPadding()))))
                    .forEach(component -> component.mousePressed(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, MouseButton mouseButton) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawComponents(int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        RenderUtil.prepareScissorBox(scaledResolution, getFunctionalPosition().getX(), getFunctionalPosition().getY() + getRenderPosition().getHeight(), getFunctionalPosition().getWidth(), 6 * (getGuiManager().getTheme().getComponentHeight() + getGuiManager().getTheme().getVerticalPadding()));
        getComponents().forEach(component -> {
            component.getRenderPosition().setY(component.getRenderPosition().getY() - scrollY);
            component.getFunctionalPosition().setY(component.getFunctionalPosition().getY() - scrollY);
            component.drawComponent(getGuiManager().getTheme().getFactory().getComponentRenderer(component), mouseX, mouseY, partialTicks);
        });
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void keyTyped(char c, int keyIndex) {
        super.keyTyped(c, keyIndex);

        if (this.isExtended()) {
            getComponents().forEach(component -> component.keyTyped(c, keyIndex));
        }
    }

    public void handleMouseInput() {
        if (canScroll) {
            if (Mouse.hasWheel()) {
                if (Mouse.getEventDWheel() > 0) {
                    if (scrollY < 0) {
                        scrollY = 0;
                        return;
                    }
                    if (scrollY > 0) {
                        scrollY -= 2;
                    }
                }

                if (Mouse.getEventDWheel() < 0) {
                    int bottom = (getFunctionalPosition().getHeight() - 7 * (getGuiManager().getTheme().getComponentHeight() + getGuiManager().getTheme().getVerticalPadding()));
                    if (scrollY > bottom) {
                        scrollY = bottom;
                        return;
                    }

                    if (scrollY < bottom - 2) {
                        scrollY += 2;
                    }
                }
            }
        }
    }

    public boolean isDragging() {
        return dragging;
    }

    public int getMaxHeight() {
        return getComponents().size() >= 6 ? 6 * (getGuiManager().getTheme().getComponentHeight() + getGuiManager().getTheme().getVerticalPadding()) : getFunctionalPosition().getHeight() - getRenderPosition().getHeight();
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }
}
