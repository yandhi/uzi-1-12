package me.kix.uzi.management.ui.click.element.elements;

import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.mouse.MouseUtil;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.element.Element;
import me.kix.uzi.management.ui.click.element.elements.buttons.PluginButton;
import me.kix.uzi.management.ui.click.panel.Panel;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

public class Slider extends Element {

    private final PluginButton container;
    private final NumberProperty property;
    private boolean dragging;

    public Slider(Panel parent, String label, int posX, int posY, int width, int height, PluginButton container, NumberProperty property) {
        super(parent, label, posX, posY, width, height);
        this.container = container;
        this.property = property;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int posX = getParent().getPosX() + container.getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + container.getPosY() + container.getpHeight() + getPosY();
        float length = MathHelper.floor((((Number) property.getValue()).floatValue() - property.getMinimum().floatValue()) / (property.getMaximum().floatValue() - property.getMinimum().floatValue()) * getWidth());
        RenderUtil.border(posX, posY, posX + getWidth(), posY + getHeight(), 1f, 0xff000000);
        RenderUtil.verticalGradientRectangle(posX, posY, posX + getWidth(), posY + getHeight(), 0xff222222, 0xff2a2a2a);
        RenderUtil.verticalGradientRectangle(posX, posY, posX + length, posY + getHeight(), 0xff256bb6, 0xff185ea9);
        RenderUtil.drawTinyString(getLabel() + ": " + property.getValue(), posX + 3, posY + 5f, 0xFFFFFFFF);
        if (dragging) {
            property.setValue(((mouseX - posX) * (property.getMaximum().floatValue() - property.getMinimum().floatValue()) / getWidth() + property.getMinimum().floatValue()));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int posX = getParent().getPosX() + container.getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + container.getPosY() + container.getpHeight() + getPosY();
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, posX, posY, posX + getWidth(), posY + getHeight())) {
            if (mouseButton == 0) {
                dragging = true;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            dragging = false;
        }
    }
}
