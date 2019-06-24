package me.kix.uzi.management.ui.click;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.management.plugin.internal.Overlay;
import me.kix.uzi.management.ui.click.element.elements.Slider;
import me.kix.uzi.management.ui.click.element.elements.buttons.PluginButton;
import me.kix.uzi.management.ui.click.element.elements.buttons.PropertyButton;
import me.kix.uzi.management.ui.click.element.elements.buttons.ToggleablePluginButton;
import me.kix.uzi.management.ui.click.panel.Panel;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.*;

public class GuiClick extends GuiScreen {

    private final List<Panel> panels = new ArrayList<>();

    public void init() {
        int posX = 2;
        for (Category category : Category.values()) {
            panels.add(new Panel(WordUtils.capitalizeFully(category.name()), posX, 2, 90, 12) {
                @Override
                public void init() {
                    for (Plugin plugin : Uzi.INSTANCE.getPluginManager().getContents()) {
                        if (plugin instanceof ToggleablePlugin && plugin.getCategory() == category) {
                            getElements().add(new ToggleablePluginButton(this, plugin.getLabel(), 2, 2, 86, 10, (ToggleablePlugin) plugin) {
                                @Override
                                public void init() {
                                    int posY = 2;
                                    for (Property property : plugin.getProperties()) {
                                        if (property.getValue() instanceof Boolean) {
                                            getElements().add(new PropertyButton(getParent(), property.getLabel(), 2, posY, 82, 10, this, (Property<Boolean>) property));
                                            posY += 12;
                                        }
                                        if (property instanceof NumberProperty) {
                                            getElements().add(new Slider(getParent(), property.getLabel(), 2, posY, 82, 10, this, (NumberProperty) property));
                                            posY += 12;
                                        }
                                    }
                                }
                            });
                        }
                    }
                    getElements().stream().filter(element -> element instanceof PluginButton).forEach(element -> ((PluginButton) element).init());
                }
            });
            posX += 92;
        }
        panels.forEach(Panel::init);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        panels.forEach(panel -> panel.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        panels.forEach(panel -> panel.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        panels.forEach(panel -> panel.mouseReleased(mouseX, mouseY, state));
    }
}
