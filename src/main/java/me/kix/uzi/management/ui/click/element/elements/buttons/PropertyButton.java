package me.kix.uzi.management.ui.click.element.elements.buttons;

import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.math.mouse.MouseUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.element.elements.Button;
import me.kix.uzi.management.ui.click.panel.Panel;
import net.minecraft.client.gui.Gui;

public class PropertyButton extends Button {

    private final PluginButton container;
    private final Property<Boolean> property;
    private final NahrFont font = new NahrFont("Verdana", 18);

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
        Gui.drawRect(posX, posY, posX + getWidth(), posY + getHeight(), property.getValue() ? 0xFF915151 : 0xFF3A3A3A);
        font.drawString(getLabel(), posX + 3, posY + 3.5f, NahrFont.FontType.NORMAL, 0xFFFFFFFF, 0xFFFFFFFF);
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
