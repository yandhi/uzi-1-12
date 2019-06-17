package me.kix.uzi.management.ui.click.element.elements.buttons;

import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.mouse.MouseUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.element.Element;
import me.kix.uzi.management.ui.click.element.elements.Button;
import me.kix.uzi.management.ui.click.panel.Panel;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public abstract class PluginButton extends Button {

    private final Plugin plugin;
    private final List<Element> elements = new ArrayList<>();
    private boolean extended;
    private final NahrFont font = new NahrFont("Verdana", 18);
    private int pHeight;

    public PluginButton(Panel parent, String label, int posX, int posY, int width, int height, Plugin plugin) {
        super(parent, label, posX, posY, width, height);
        this.plugin = plugin;
        this.pHeight = height;
    }

    public abstract void init();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int posX = getParent().getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + getPosY();
        Gui.drawRect(posX, posY, posX + getWidth(), posY + pHeight, 0xFF515151);
        font.drawString(getLabel(), posX + 3, posY + 3.5f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        if (extended) {
            setHeight(14 + elements.size() * 16);
            getElements().forEach(element -> element.drawScreen(mouseX, mouseY, partialTicks));
        } else {
            setHeight(14);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int posX = getParent().getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + getPosY();
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, posX, posY, posX + getWidth(), posY + pHeight)) {
            if (mouseButton == 0) {
                onToggle();
            } else if (mouseButton == 1) {
                extended = !extended;
            }
        }
        if (extended) {
            getElements().forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (extended) {
            getElements().forEach(element -> element.mouseReleased(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void onToggle() {

    }

    public List<Element> getElements() {
        return elements;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public int getpHeight() {
        return pHeight;
    }
}
