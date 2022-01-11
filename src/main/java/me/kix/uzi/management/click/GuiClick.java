package me.kix.uzi.management.click;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.sodapop.components.container.layout.BasicLayoutStrategy;
import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.click.component.spinners.EnumPropertySpinnerComponent;
import me.kix.uzi.management.click.themes.*;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The screen for interacting with the click ui system.
 *
 * @author Kix
 * @since 6/27/2019
 */
public final class GuiClick extends GuiScreen implements GuiManager {

    /**
     * The instance of the screen.
     */
    private static volatile GuiClick screen;

    /**
     * The collection of frames for the ui.
     */
    private final Set<FrameContainerComponent> frames = new HashSet<>();

    /**
     * The current theme for the gui system.
     */
    private final EnumProperty<GuiClickTheme> guiClickTheme = new EnumProperty<>("Theme", GuiClickTheme.UZI);

    private GuiClick() {
        initUI();
    }

    /**
     * Initializes the click gui.
     */
    @SuppressWarnings("unchecked")
    private void initUI() {
        int frameX = guiClickTheme.getValue().theme.getHorizontalPadding();
        for (Category category : Category.values()) {
            FrameContainerComponent frame = new FrameContainerComponent(WordUtils.capitalizeFully(category.name()), this, new Rectangle(frameX, 2, guiClickTheme.getValue().theme.getWidth(), guiClickTheme.getValue().theme.getHeight()), new BasicLayoutStrategy());

            int pluginY = frame.getRenderPosition().getY() + guiClickTheme.getValue().theme.getVerticalPadding();
            for (Plugin plugin : Uzi.INSTANCE.getPluginManager().getContents()) {
                if (plugin instanceof ToggleablePlugin && plugin.getCategory() == category) {
                    ToggleablePlugin toggleablePlugin = (ToggleablePlugin) plugin;
                    PluginButtonContainerComponent pluginButton = new PluginButtonContainerComponent(((ToggleablePlugin) plugin).getDisplay(), this,
                            new Rectangle(frame.getRenderPosition().getX() + guiClickTheme.getValue().theme.getHorizontalPadding(), pluginY, guiClickTheme.getValue().theme.getWidth() - (guiClickTheme.getValue().theme.getHorizontalPadding() * 2), guiClickTheme.getValue().theme.getComponentHeight()), new BasicLayoutStrategy(), toggleablePlugin);
                    int propertyY = pluginButton.getRenderPosition().getY() + guiClickTheme.getValue().theme.getVerticalPadding();
                    for (Property property : toggleablePlugin.getProperties()) {
                        if (property instanceof NumberProperty) {
                            pluginButton.getComponents().add(new NumberPropertySliderComponent(property.getLabel(), this,
                                    new Rectangle(pluginButton.getRenderPosition().getX() + guiClickTheme.getValue().theme.getHorizontalPadding(), propertyY,
                                            pluginButton.getRenderPosition().getWidth() - (guiClickTheme.getValue().theme.getHorizontalPadding() * 2), guiClickTheme.getValue().theme.getComponentHeight()), (NumberProperty) property));
                            propertyY += guiClickTheme.getValue().theme.getComponentHeight() + guiClickTheme.getValue().theme.getVerticalPadding();
                        }
                        if (property instanceof EnumProperty) {
                            pluginButton.getComponents().add(new EnumPropertySpinnerComponent(property.getLabel(), this,
                                    new Rectangle(pluginButton.getRenderPosition().getX() + guiClickTheme.getValue().theme.getHorizontalPadding(), propertyY,
                                            pluginButton.getRenderPosition().getWidth() - (guiClickTheme.getValue().theme.getHorizontalPadding() * 2), guiClickTheme.getValue().theme.getComponentHeight()), (EnumProperty) property));
                            propertyY += guiClickTheme.getValue().theme.getComponentHeight() + guiClickTheme.getValue().theme.getVerticalPadding();
                        }
                        if (property.getValue() instanceof Boolean) {
                            pluginButton.getComponents().add(new PropertyButtonComponent(property.getLabel(), this,
                                    new Rectangle(pluginButton.getRenderPosition().getX() + guiClickTheme.getValue().theme.getHorizontalPadding(), propertyY,
                                            pluginButton.getRenderPosition().getWidth() - (guiClickTheme.getValue().theme.getHorizontalPadding() * 2), guiClickTheme.getValue().theme.getComponentHeight()), property));
                            propertyY += guiClickTheme.getValue().theme.getComponentHeight() + guiClickTheme.getValue().theme.getVerticalPadding();
                        }
                    }
                    frame.getComponents().add(pluginButton);
                    pluginY += guiClickTheme.getValue().theme.getComponentHeight() + guiClickTheme.getValue().theme.getVerticalPadding();
                }
            }

            frames.add(frame);
            frameX += guiClickTheme.getValue().theme.getWidth() + guiClickTheme.getValue().theme.getHorizontalPadding();
        }

        FrameContainerComponent themeFrame = new FrameContainerComponent("Theme", this, new Rectangle(frameX, 2, guiClickTheme.getValue().theme.getWidth(), guiClickTheme.getValue().theme.getHeight()), new BasicLayoutStrategy());
        themeFrame.getComponents().add(new EnumPropertySpinnerComponent("Theme", this,
                new Rectangle(themeFrame.getRenderPosition().getX() + guiClickTheme.getValue().theme.getHorizontalPadding(), themeFrame.getRenderPosition().getY() + 2,
                        guiClickTheme.getValue().theme.getWidth() - (guiClickTheme.getValue().theme.getHorizontalPadding() * 2), guiClickTheme.getValue().theme.getComponentHeight()), guiClickTheme));
        frames.add(themeFrame);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        frames.forEach(frame -> frame.drawComponent(guiClickTheme.getValue().theme.getFactory().getComponentRenderer(frame), mouseX, mouseY, partialTicks));
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

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        frames.forEach(FrameContainerComponent::handleMouseInput);
    }

    public static GuiClick getScreen() {
        if (screen == null) {
            screen = new GuiClick();
        }
        return screen;
    }

    @Override
    public Theme getTheme() {
        return guiClickTheme.getValue().theme;
    }

    /**
     * The themes in the gui system.
     */
    private enum GuiClickTheme {
        UZI(new UziTheme()),
        NOIL(new NoilTheme()),
        SYNC(new SyncTheme()),
        MODERNESQUE(new ModernesqueTheme()),
        SOLSTICE(new SolsticeTheme());

        /**
         * The theme that the type utilizes.
         */
        private final Theme theme;

        GuiClickTheme(Theme theme) {
            this.theme = theme;
        }
    }
}