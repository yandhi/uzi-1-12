package me.kix.uzi.management.ui.click.element.elements.buttons;

import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.mouse.MouseUtil;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.element.Element;
import me.kix.uzi.management.ui.click.element.elements.Button;
import me.kix.uzi.management.ui.click.panel.Panel;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public abstract class ToggleablePluginButton extends PluginButton {

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

        RenderUtil.border(posX, posY, posX + getWidth(), posY + getpHeight(), 1f, 0xFF000000);
        RenderUtil.verticalGradientRectangle(posX, posY, posX + getWidth(), posY + getpHeight(), ((ToggleablePlugin) getPlugin()).isEnabled() ? 0xff256bb6 : 0xff222222,
                ((ToggleablePlugin) getPlugin()).isEnabled() ? 0xff185ea9 : 0xff2a2a2a);
        RenderUtil.drawTinyString(getLabel(), posX + ((getWidth() / 2) - (mc.fontRenderer.getStringWidth(getLabel()) / 4)), posY + 5f, 0xFFFFFFFF);
        if (isExtended()) {
            setHeight(14 + getElements().size() * 16);
            getElements().forEach(element -> element.drawScreen(mouseX, mouseY, partialTicks));
        } else {
            setHeight(14);
        }
    }

}
