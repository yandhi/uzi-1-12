package me.kix.uzi.api.plugin.service;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.AbstractPlugin;

/**
 * A plugin that is always running!
 *
 * @author jackson
 * @since 1/11/2022
 */
public class Service extends AbstractPlugin {

    public Service(String label, Category category) {
        super(label, category);
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        Uzi.INSTANCE.getEventManager().register(this);
    }
}
