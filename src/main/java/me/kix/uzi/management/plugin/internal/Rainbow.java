package me.kix.uzi.management.plugin.internal;

import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.service.Service;
import me.kix.uzi.api.util.render.RainbowUtil;

/**
 * Initializes the rainbow service for the client.
 *
 * @author jackson
 * @since 1/11/2022
 */
public class Rainbow extends Service {

    public Rainbow() {
        super("Rainbow", Category.RENDER);
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        RainbowUtil.INSTANCE.registerRainbowListener();
    }
}
