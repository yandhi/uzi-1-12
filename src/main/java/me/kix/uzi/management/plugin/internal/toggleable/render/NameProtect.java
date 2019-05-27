package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.management.event.render.EventRenderString;

/**
 * @author Kix
 * @since 5/25/2018
 */
public class NameProtect extends ToggleablePlugin {

    private final Property<String> name = new Property<>("Name", "Chuck Knoblock");

    public NameProtect() {
        super("NameProtect", Category.RENDER);
        setDisplay("Name Protect");
        setHidden(true);
        getProperties().add(name);
    }

    @Register
    public void onRenderString(EventRenderString event) {
        if (event.getText().contains(mc.getSession().getProfile().getName()))
            event.setText(event.getText().replaceAll(mc.getSession().getProfile().getName(), "\2475" + name.getValue() + "\247r"));
    }

}
