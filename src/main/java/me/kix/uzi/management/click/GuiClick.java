package me.kix.uzi.management.click;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.ui.toolkit.components.container.layout.BasicLayoutStrategy;
import me.kix.uzi.api.ui.toolkit.components.frame.FrameContainerComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.click.themes.NoilTheme;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The screen for interacting with the click ui system.
 *
 * @author Kix
 * @since 6/27/2019
 */
public final class GuiClick extends GuiScreen {

    /**
     * The instance of the screen.
     */
    private static volatile GuiClick screen;

    /**
     * The collection of frames for the ui.
     */
    private final Set<FrameContainerComponent> frames = new HashSet<>();

    /**
     * The theme for the click ui.
     */
    private Theme theme = new NoilTheme();

    private GuiClick() {
        initUI();
    }

    /**
     * Initializes the click gui.
     */
    private void initUI() {
        int frameX = theme.getHorizontalPadding();
        for (Category category : Category.values()) {
            FrameContainerComponent frame = new FrameContainerComponent(category.name().toLowerCase(), theme, new Rectangle(frameX, 2, theme.getWidth(), theme.getHeight()), new BasicLayoutStrategy());

            int pluginY = frame.getRenderPosition().getY() + theme.getVerticalPadding();
            for (Plugin plugin : Uzi.INSTANCE.getPluginManager().getContents()) {
                if (plugin instanceof ToggleablePlugin && plugin.getCategory() == category) {
                    ToggleablePlugin toggleablePlugin = (ToggleablePlugin) plugin;
                    frame.getComponents().add(new PluginButtonContainerComponent(plugin.getLabel(), theme,
                            new Rectangle(frame.getRenderPosition().getX() + theme.getHorizontalPadding(), pluginY, theme.getWidth() - (theme.getHorizontalPadding() * 2), theme.getComponentHeight()), new BasicLayoutStrategy(), toggleablePlugin));
                    pluginY += theme.getComponentHeight() + theme.getVerticalPadding();
                }
            }

            frames.add(frame);
            frameX += theme.getWidth() + theme.getHorizontalPadding();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        frames.forEach(frame -> frame.drawComponent(theme.getFactory().getComponentRenderer(frame), mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        frames.forEach(frame -> frame.mousePressed(mouseX, mouseY, MouseButton.getButton(mouseButton)));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        frames.forEach(frame -> frame.mouseReleased(mouseX, mouseY, MouseButton.getButton(state)));
    }

    public static GuiClick getScreen() {
        if (screen == null) {
            screen = new GuiClick();
        }
        return screen;
    }
}
