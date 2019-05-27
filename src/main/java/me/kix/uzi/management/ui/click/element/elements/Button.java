package me.kix.uzi.management.ui.click.element.elements;

import me.kix.uzi.management.ui.click.element.Element;
import me.kix.uzi.management.ui.click.panel.Panel;

public abstract class Button extends Element {

    public Button(Panel parent, String label, int posX, int posY, int width, int height) {
        super(parent, label, posX, posY, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    public abstract void onToggle();

}
