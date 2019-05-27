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
    private final NahrFont font = new NahrFont("Verdana", 18);

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
        Gui.drawRect(posX, posY, posX + getWidth(), posY + getHeight(), 0xFF3A3A3A);
        RenderUtil.drawRect(posX, posY, posX + length, posY + getHeight(), 0xFF915151);
        font.drawString(getLabel() + ": " + property.getValue(), posX + 3, posY + 3.5f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        if(dragging){
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
