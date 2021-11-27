package me.kix.uzi.management.ui.tab.item.impl.focus;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.impl.FocusTabComponent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that is a spinner.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class SpinnerTabComponent extends FocusTabComponent {

    /**
     * The property that this item handles.
     */
    private final EnumProperty property;

    public SpinnerTabComponent(GuiManager guiManager, Rectangle renderPosition, EnumProperty property) {
        super(property.getLabel(), guiManager, renderPosition);
        this.property = property;
    }

    @Override
    public void handleKeys(int keyCode) {
        super.handleKeys(keyCode);

        if (isFocused()) {
            if (keyCode == Keyboard.KEY_UP) {
                property.increment();
            }
            if (keyCode == Keyboard.KEY_DOWN) {
                property.decrement();
            }
        }
    }

    /**
     * @return The raw label and value together for width purposes.
     */
    public String getRaw() {
        return getName() + " : " + property.getFixedValue();
    }

}
