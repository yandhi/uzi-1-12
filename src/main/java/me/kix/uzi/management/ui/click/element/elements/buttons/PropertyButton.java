package me.kix.uzi.management.ui.click.element.elements.buttons;

import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.click.element.elements.Button;
import me.kix.uzi.management.ui.click.panel.Panel;

public class PropertyButton extends Button {

    private final PluginButton container;
    private final Property<Boolean> property;

    public PropertyButton(Panel parent, String label, int posX, int posY, int width, int height, PluginButton container, Property<Boolean> property) {
        super(parent, label, posX, posY, width, height);
        this.container = container;
        this.property = property;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int posX = getParent().getPosX() + container.getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + container.getPosY() + container.getpHeight() + getPosY();
        RenderUtil.border(posX + getWidth() - 30, posY, posX + getWidth(), posY + getHeight(), 1f, 0xff000000);
        RenderUtil.verticalGradientRectangle(posX + getWidth() - 30, posY, posX + getWidth() - 15, posY + getHeight(), property.getValue() ? 0xff256bb6 : 0xff222222, property.getValue() ? 0xff185ea9 : 0xff2a2a2a);
        RenderUtil.verticalGradientRectangle(posX + getWidth() - 15, posY, posX + getWidth(), posY + getHeight(), !property.getValue() ? 0xff256bb6 : 0xff222222, !property.getValue() ? 0xff185ea9 : 0xff2a2a2a);
        RenderUtil.drawTinyString(getLabel(), posX + 3, posY + 3f, 0xFFFFFFFF);
        RenderUtil.drawTinyString("On", posX + getWidth() - 22.5f - (mc.fontRenderer.getStringWidth("On") / 4f), posY + 3f, 0xffffffff);
        RenderUtil.drawTinyString("Off", posX + getWidth() - 7.5f - (mc.fontRenderer.getStringWidth("Off") / 4f), posY + 3f, 0xffffffff);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int posX = getParent().getPosX() + container.getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + container.getPosY() + container.getpHeight() + getPosY();
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, posX, posY, posX + getWidth(), posY + getHeight())) {
            if (mouseButton == 0) {
                onToggle();
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onToggle() {
        getProperty().setValue(!getProperty().getValue());
    }

    public Property<Boolean> getProperty() {
        return property;
    }
}
