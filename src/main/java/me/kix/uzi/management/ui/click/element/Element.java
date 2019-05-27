package me.kix.uzi.management.ui.click.element;

import me.kix.uzi.api.util.interfaces.Labeled;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.management.ui.click.panel.Panel;

public abstract class Element implements Labeled, MinecraftAccessor {

    private final Panel parent;
    private final String label;
    private int posX, posY, width, height;

    public Element(Panel parent, String label, int posX, int posY, int width, int height) {
        this.parent = parent;
        this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract void drawScreen(int mouseX, int mouseY, float partialTicks);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    public Panel getParent() {
        return parent;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
