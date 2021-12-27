package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.key.EventKeyPressed;
import me.kix.uzi.api.event.events.render.EventRenderChatLine;
import me.kix.uzi.api.event.events.render.EventRenderTextBox;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.GuiHud;
import me.kix.uzi.management.ui.tab.TabGui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

/**
 * The hud for the client.
 * <p>
 * Revised the hell out of this shit on 11/25/2021. ~ freshman thanksgiving break yo and im high as balls.
 *
 * @author jackson
 * @since idk like 2017 lol.
 */
public class Overlay extends ToggleablePlugin {

    /**
     * Tab gui!
     */
    private final Property<Boolean> tabGui = new Property<>("Tabgui", false);

    /**
     * Fancy chat!
     */
    private final Property<Boolean> chat = new Property<>("Chat", true);

    /**
     * The font renderer for the chat.
     */
    private final NahrFont font = new NahrFont("Consolas", 16);

    private TabGui tab;
    private final GuiHud hud = new GuiHud();

    public Overlay() {
        super("Overlay", Category.RENDER);
        setHidden(true);
        getProperties().add(tabGui);
        getProperties().add(chat);
    }

    @Register
    public void onWorldToScreen(EventRender.WorldToScreen event) {
        if (!mc.gameSettings.showDebugInfo) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            if (tabGui.getValue()) {
                if (tab == null) {
                    tab = TabGui.INSTANCE;
                    tab.setup();
                }
            }
            hud.render(tabGui.getValue(), tab);
            GlStateManager.popMatrix();
        }
    }

    @Register
    public void onRenderChatLine(EventRenderChatLine renderChatLine) {
        if (chat.getValue()) {
            renderChatLine.setCancelled(true);
            RenderUtil.drawRect(2, renderChatLine.getHeight() - 9, renderChatLine.getWidth() + 4, renderChatLine.getHeight(), Color.BLACK.getRGB());
            GlStateManager.enableBlend();
            font.drawStringWithShadow(font.stripUnsupported(renderChatLine.getMessage()), 4f, (float) (renderChatLine.getHeight() - 6), Color.WHITE.getRGB());
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }
    }

    @Register
    public void onRenderTextBoxText(EventRenderTextBox.Text text) {
        if (chat.getValue()) {
            text.setCancelled(true);
            text.setCallbackReturn((int) font.getStringWidth(text.getText()) + 4);
            if (!text.getText().equalsIgnoreCase("_")) {
                RenderUtil.drawRect(text.getX(), text.getY() - 1, text.getX() + font.getStringWidth(text.getText()) + 4, text.getY() + font.getStringHeight(text.getText()) - 1, Color.BLACK.getRGB());
            }
            font.drawStringWithShadow(text.getText(), text.getX() + 2, text.getY() + 3, Color.WHITE.getRGB());
        }
    }

    @Register
    public void onRenderTextBoxRectangle(EventRenderTextBox.Rectangle rectangle) {
        if (chat.getValue()) {
            rectangle.setCancelled(true);
        }
    }

    @Register
    public void onKeyPress(EventKeyPressed pressed) {
        if (tabGui.getValue()) {
            if (tab != null) {
                tab.handleKeys(pressed.getKey());
            }
        }
    }
}