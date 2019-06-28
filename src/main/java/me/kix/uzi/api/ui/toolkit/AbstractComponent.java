package me.kix.uzi.api.ui.toolkit;

import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.theme.renderer.ComponentRenderer;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * The abstracted form of {@link Component}.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class AbstractComponent implements Component {

    /**
     * The name of the component.
     */
    private final String name;

    /**
     * The themes that handles the look and feel of components.
     */
    private final Theme theme;

    /**
     * The position for actions.
     */
    private final Rectangle functionalPosition;

    /**
     * The position for rendering.
     */
    private final Rectangle renderPosition;

    public AbstractComponent(String name, Theme theme, Rectangle renderPosition) {
        this.name = name;
        this.theme = theme;
        this.renderPosition = renderPosition;
        this.functionalPosition = new Rectangle(renderPosition.getX(), renderPosition.getY(), renderPosition.getWidth(), renderPosition.getHeight());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        updateComponent(mouseX, mouseY);
        renderer.renderComponent(this);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, MouseButton mouseButton) {
    }

    @Override
    public void updatePosition(int x, int y) {
        this.renderPosition.setX(x);
        this.renderPosition.setY(y);
        this.functionalPosition.setX(x);
        this.functionalPosition.setY(y);
    }

    @Override
    public void updateDimensions(int width, int height) {
        this.renderPosition.setWidth(width);
        this.renderPosition.setHeight(height);
        this.functionalPosition.setWidth(width);
        this.functionalPosition.setHeight(height);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Rectangle getRenderPosition() {
        return renderPosition;
    }

    @Override
    public Rectangle getFunctionalPosition() {
        return functionalPosition;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }
}
