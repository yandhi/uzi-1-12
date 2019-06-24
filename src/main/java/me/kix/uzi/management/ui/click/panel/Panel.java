package me.kix.uzi.management.ui.click.panel;

import me.kix.uzi.api.util.interfaces.Labeled;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.api.util.math.mouse.MouseUtil;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.element.Element;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Panel implements Labeled, MinecraftAccessor {

    private final List<Element> elements = new ArrayList<>();
    private final String label;
    private int posX, posY, lastPosX, lastPosY, width, height;
    private boolean dragging, extended;

    protected Panel(String label, int posX, int posY, int width, int height) {
        this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.lastPosX = posX;
        this.lastPosY = posY;
    }

    public abstract void init();

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            this.posX = mouseX + this.lastPosX;
            this.posY = mouseY + this.lastPosY;
        }
        RenderUtil.border(posX, posY, posX + width, posY + height, 2f, 0xff000000);
        RenderUtil.verticalGradientRectangle(posX, posY, posX + width, posY + height, 0xFF222222, 0xFF2a2a2a);
        RenderUtil.drawTinyString(label, posX + 4.5f, posY + 4f, 0xFFFFFFFF);

        RenderUtil.border(posX + width - 10.5f, posY + 2, posX + width - 2, posY + getHeight() - 2, 1f, 0xff000000);
        RenderUtil.verticalGradientRectangle(posX + width - 10.5f, posY + 2, posX + width - 2, posY + getHeight() - 2, 0xff256bb6, 0xff185ea9);
        RenderUtil.drawTinyString(extended ? "-" : "+", posX + width - 6f - (mc.fontRenderer.getStringWidth(extended ? "-" : "+") / 4f), posY + (getHeight() / 2f) - (mc.fontRenderer.FONT_HEIGHT / 4f) + 0.5f, 0xffffffff);
        if (extended) {
            int addition = 2;
            for (Element element : getElements()) {
                element.setPosY(addition);
                addition += element.getHeight() + 2;
            }
            RenderUtil.border(posX, posY + height, posX + width, posY + height + addition, 2f, 0xff000000);
            if (!elements.isEmpty()) {
                RenderUtil.verticalGradientRectangle(posX, posY + height, posX + width, posY + height + addition, 0xff313131, 0xff2e2e2e);
            }
            elements.forEach(element -> element.drawScreen(mouseX, mouseY, partialTicks));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, posX, posY, posX + width, posY + height)) {
            if (mouseButton == 0) {
                dragging = true;
                this.lastPosX = (posX - mouseX);
                this.lastPosY = (posY - mouseY);
            } else if (mouseButton == 1) {
                extended = !extended;
            }
        }
        if (extended) {
            elements.forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
        if (extended) {
            elements.forEach(element -> element.mouseReleased(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    public List<Element> getElements() {
        return elements;
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
