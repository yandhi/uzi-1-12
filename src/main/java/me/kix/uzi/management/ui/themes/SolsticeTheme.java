package me.kix.uzi.management.ui.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.ui.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.ui.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.ui.click.component.spinners.EnumPropertySpinnerComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.CoordinatesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.ToggleablesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.WatermarkComponent;
import me.kix.uzi.management.ui.tab.item.impl.FolderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.PropertyButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.ToggleablePluginButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.folders.ToggleablePluginFolderTabComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

/**
 * The uzi version of the noil styled ui.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class SolsticeTheme extends AbstractTheme {

    /**
     * The font for title panels.
     */
    private final NahrFont titleFont = new NahrFont("Selawik", 12);

    /**
     * The font renderer for the UI.
     */
    private final NahrFont font = new NahrFont("Selawik", 16);

    /**
     * The current accent color of the theme.
     */
    private final Color color = new Color(0x2ECC71);

    /**
     * The settings icon.
     */
    private final ResourceLocation settingsIcon = new ResourceLocation("settings.png");

    public SolsticeTheme() {
        super("Solstice", 115, 14, 15, 0, 2);
    }

    @Override
    public void initTheme() {
        getComponentRenderers().add(new FrameComponentRenderer());
        getComponentRenderers().add(new PluginButtonComponentRenderer());
        getComponentRenderers().add(new PropertyButtonComponentRenderer());
        getComponentRenderers().add(new EnumPropertySpinnerComponentRenderer());
        getComponentRenderers().add(new NumberPropertySliderComponentRenderer());
        getComponentRenderers().add(new WatermarkComponentRenderer());
        getComponentRenderers().add(new ToggleablesBlockComponentRenderer());
        getComponentRenderers().add(new CoordinatesBlockComponentRenderer());
        getComponentRenderers().add(new FolderTabComponentRenderer());
        getComponentRenderers().add(new ToggleablePluginFolderTabComponentRenderer());
        getComponentRenderers().add(new SpinnerTabComponentRenderer());
        getComponentRenderers().add(new SliderTabComponentRenderer());
        getComponentRenderers().add(new ToggleablePluginButtonTabComponentRenderer());
        getComponentRenderers().add(new PropertyButtonTabComponentRenderer());
    }

    /**
     * Renders blocks of strings :).
     */
    private class WatermarkComponentRenderer extends AbstractComponentRenderer<WatermarkComponent> {

        @Override
        public void renderComponent(WatermarkComponent component) {
            font.drawStringWithShadow("Solstice", component.getRenderPosition().getX(), component.getRenderPosition().getY() + 2, Color.WHITE.getRGB());
            titleFont.drawStringWithShadow("1.12.2", component.getRenderPosition().getX() + font.getStringWidth("Solstice") + 1, component.getRenderPosition().getY() + 2, Color.RED.getRGB());
        }
    }

    /**
     * Renders the toggleables.
     */
    private class ToggleablesBlockComponentRenderer extends AbstractComponentRenderer<ToggleablesBlockComponent> {

        @Override
        public void renderComponent(ToggleablesBlockComponent component) {
            List<ToggleablePlugin> plugins = component.getToggleables();
            plugins.sort((o1, o2) -> Float.compare(font.getStringWidth(o2.getDisplay()), font.getStringWidth(o1.getDisplay())));

            int y = component.getRenderPosition().getY() + 2;

            for (ToggleablePlugin plugin : plugins) {
                font.drawStringWithShadow(plugin.getDisplay(), component.getRenderPosition().getX() - font.getStringWidth(plugin.getDisplay()) - 2, y, plugin.getColor());
                y += font.getStringHeight(plugin.getDisplay()) - 1;
            }

            /* for good measure. */
            component.getRenderPosition().setHeight(y);
        }
    }

    /**
     * Renders the coordinates
     */
    private class CoordinatesBlockComponentRenderer extends AbstractComponentRenderer<CoordinatesBlockComponent> {

        @Override
        public void renderComponent(CoordinatesBlockComponent component) {
            long x = Math.round(Minecraft.getMinecraft().player.posX);
            long y = Math.round(Minecraft.getMinecraft().player.posY);
            long z = Math.round(Minecraft.getMinecraft().player.posZ);
            String coords = String.format("%s, %s, %s", x, y, z);
            String overworld = String.format("%s, %s, %s", x * 8, y * 8, z * 8);

            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            if (component.getRenderPosition().getY() == scaledResolution.getScaledHeight() - 20) {
                if (Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
                    component.getRenderPosition().setY(component.getRenderPosition().getY() - 16);
                }
            }

            /* If player is in the nether */
            if (Minecraft.getMinecraft().player.dimension == -1) {
                font.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.RED.darker().darker().getRGB());
                font.drawStringWithShadow(overworld, component.getRenderPosition().getX() + font.getStringWidth(coords) + 2, component.getRenderPosition().getY(), Color.GRAY.getRGB());
            } else {
                font.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.GRAY.getRGB());
            }
        }
    }

    /**
     * Tabgui folders.
     */
    private class FolderTabComponentRenderer extends AbstractComponentRenderer<FolderTabComponent> {
        @Override
        public void renderComponent(FolderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? (component.isOpen() ? 0xFF476C9F : 0xFF4A96CC) : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2.5f, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());
        }
    }

    private class ToggleablePluginFolderTabComponentRenderer extends AbstractComponentRenderer<ToggleablePluginFolderTabComponent> {
        @Override
        public void renderComponent(ToggleablePluginFolderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? (component.isOpen() ? 0xFF476C9F : 0xFF4A96CC) : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());
            font.drawStringWithShadow("...", component.getRenderPosition().getX() +
                            component.getRenderPosition().getWidth() - font.getStringWidth("...") - 2f,
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight() - font.getStringHeight("...") + 3f, Color.LIGHT_GRAY.getRGB());
        }
    }

    /**
     * Tabgui focus components.
     */
    private class SpinnerTabComponentRenderer extends AbstractComponentRenderer<SpinnerTabComponent> {
        @Override
        public void renderComponent(SpinnerTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? 0xFF4A96CC : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getRaw(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
        }
    }

    private class SliderTabComponentRenderer extends AbstractComponentRenderer<SliderTabComponent> {
        @Override
        public void renderComponent(SliderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    Color.BLACK.getRGB());
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getSliderLength(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    component.isHovered() ? 0xFF4A96CC : Color.BLACK.getRGB());
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
            font.drawStringWithShadow(component.getProperty().getValue().toString(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - font.getStringWidth(component.getProperty().getValue().toString()) - 2, (component.getRenderPosition().getY()
                            + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f,
                    0xFFDEDEDE);
        }
    }

    /**
     * Tabgui buttons.
     */
    private class ToggleablePluginButtonTabComponentRenderer extends AbstractComponentRenderer<ToggleablePluginButtonTabComponent> {
        @Override
        public void renderComponent(ToggleablePluginButtonTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? 0xFF4A96CC : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), 0xFF476C9F);
            }
        }
    }

    private class PropertyButtonTabComponentRenderer extends AbstractComponentRenderer<PropertyButtonTabComponent> {
        @Override
        public void renderComponent(PropertyButtonTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? 0xFF4A96CC : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), 0xFF476C9F);
            }
        }
    }


    /**
     * The component renderer for the frame.
     */
    private class FrameComponentRenderer extends AbstractComponentRenderer<FrameContainerComponent> {
        @Override
        public void renderComponent(FrameContainerComponent component) {
            Rectangle position = component.getRenderPosition();

            int frameHeight = position.getHeight();
            if (component.isExtended()) {
                frameHeight += component.getMaxHeight() + 2;
            }

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + frameHeight, 0xFF34495e);
            RenderUtil.drawRect(position.getX() + position.getWidth() - 14, position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.isExtended() ? 0xFF2ECC71 : 0xFF2C3E50);
            titleFont.drawString(component.getName(), position.getX() + 4, position.getY() + 6f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, Color.BLACK.getRGB());
            if(!component.getName().equalsIgnoreCase("theme")) {
                float x = position.getX() + position.getWidth() - 28;
                int width = 11;
                float y = (position.getY()) + 1.5f;
                int height = 11;
                GlStateManager.pushMatrix();
                Minecraft.getMinecraft().getTextureManager().bindTexture(Category.getIcon(component.getName()));
                GlStateManager.enableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder renderer = tessellator.getBuffer();
                renderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
                renderer.pos(x + width, y, 0F).tex(1, 0).endVertex();
                renderer.pos(x, y, 0F).tex(0, 0).endVertex();
                renderer.pos(x, y + height, 0F).tex(0, 1).endVertex();
                renderer.pos(x, y + height, 0F).tex(0, 1).endVertex();
                renderer.pos(x + width, y + height, 0F).tex(1, 1).endVertex();
                renderer.pos(x + width, y, 0F).tex(1, 0).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
        }
    }

    /**
     * The component renderer for plugin buttons.
     */
    private class PluginButtonComponentRenderer extends AbstractComponentRenderer<PluginButtonContainerComponent> {

        @Override
        public void renderComponent(PluginButtonContainerComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getPlugin().isEnabled() ? 0xFF2ECC71 : 0xFF2C3E50);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
            if (!component.getComponents().isEmpty()) {
                float x = (position.getX() + position.getWidth() - 14);
                int width = 14;
                float y = (position.getY());
                int height = 14;
                GlStateManager.pushMatrix();
                Minecraft.getMinecraft().getTextureManager().bindTexture(settingsIcon);
                GlStateManager.enableTexture2D();
                if (component.isExtended()) {
                    RenderUtil.color(0xFF2ECC71);
                }
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder renderer = tessellator.getBuffer();
                renderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
                renderer.pos(x + width, y, 0F).tex(1, 0).endVertex();
                renderer.pos(x, y, 0F).tex(0, 0).endVertex();
                renderer.pos(x, y + height, 0F).tex(0, 1).endVertex();
                renderer.pos(x, y + height, 0F).tex(0, 1).endVertex();
                renderer.pos(x + width, y + height, 0F).tex(1, 1).endVertex();
                renderer.pos(x + width, y, 0F).tex(1, 0).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
        }
    }

    /**
     * The component renderer for property buttons.
     */
    private class PropertyButtonComponentRenderer extends AbstractComponentRenderer<PropertyButtonComponent> {
        @Override
        public void renderComponent(PropertyButtonComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getProperty().getValue() ? 0xFF2ECC71 : 0xFF2C3E50);
            RenderUtil.drawRect(position.getX() + 14, position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight() - 1, 0xFF2C3E50);
            titleFont.drawString(component.getName(), position.getX() + 16, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }

    /**
     * The component renderer for property spinners.
     */
    private class EnumPropertySpinnerComponentRenderer extends AbstractComponentRenderer<EnumPropertySpinnerComponent> {
        @Override
        public void renderComponent(EnumPropertySpinnerComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF2C3E50);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
            titleFont.drawString(component.getProperty().getFixedValue(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getFixedValue()) - 2,
                    position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }

    /**
     * The component renderer for property sliders.
     */
    private class NumberPropertySliderComponentRenderer extends AbstractComponentRenderer<NumberPropertySliderComponent> {
        @Override
        public void renderComponent(NumberPropertySliderComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF2C3E50);
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), 0xFF2ECC71);
            RenderUtil.drawRect(position.getX() + component.getLength() - 2, position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), color.darker().getRGB());
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
            titleFont.drawString(component.getProperty().getValue().toString(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getValue().toString()) - 2,
                    position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }
}