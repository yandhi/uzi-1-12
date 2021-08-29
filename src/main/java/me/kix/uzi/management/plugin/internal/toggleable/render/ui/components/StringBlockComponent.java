package me.kix.uzi.management.plugin.internal.toggleable.render.ui.components;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;

/**
 * An implementation of {@link me.kix.sodapop.Component} for String blocks.
 *
 * @author yandhi
 * @since 8/29/2021
 */
public class StringBlockComponent extends AbstractComponent {

    public StringBlockComponent(String text, GuiManager guiManager, Rectangle renderPosition) {
        super(text, guiManager, renderPosition);
    }
}
