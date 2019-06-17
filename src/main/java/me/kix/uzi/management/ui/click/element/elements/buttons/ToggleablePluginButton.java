package me.kix.uzi.management.ui.click.element.elements.buttons;

import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.mouse.MouseUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.element.Element;
import me.kix.uzi.management.ui.click.element.elements.Button;
import me.kix.uzi.management.ui.click.panel.Panel;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public abstract class ToggleablePluginButton extends PluginButton {

    private final NahrFont font = new NahrFont("Verdana", 18);

    public ToggleablePluginButton(Panel parent, String label, int posX, int posY, int width, int height, ToggleablePlugin plugin) {
        super(parent, label, posX, posY, width, height, plugin);
    }

    public abstract void init();

    @Override
    public void onToggle() {
        ((ToggleablePlugin) getPlugin()).toggle();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int posX = getParent().getPosX() + getPosX();
        int posY = getParent().getPosY() + getParent().getHeight() + getPosY();
        Gui.drawRect(posX, posY, posX + getWidth(), posY + getpHeight(), ((ToggleablePlugin) getPlugin()).isEnabled() ? 0xFFc16e6e : 0xFF515151);
        font.drawString(getLabel(), posX + 3, posY + 3.5f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        if (isExtended()) {
            setHeight(14 + getElements().size() * 16);
            getElements().forEach(element -> element.drawScreen(mouseX, mouseY, partialTicks));
        } else {
            setHeight(14);
        }
    }

}
