package me.kix.uzi.api.plugin;

import net.minecraft.util.ResourceLocation;

public enum Category {

    COMBAT(new ResourceLocation("combat.png")),
    MISCELLANEOUS(new ResourceLocation("miscellaneous.png")),
    MOVEMENT(new ResourceLocation("movement.png")),
    PLAYER(new ResourceLocation("player.png")),
    RENDER(new ResourceLocation("render.png")),
    WORLD(new ResourceLocation("world.png")),
    SERVER(new ResourceLocation("server.png"));

    /**
     * The icon for the category.
     */
    private ResourceLocation icon;

    Category(ResourceLocation icon) {
        this.icon = icon;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    /**
     * Gets the icon based on the name of the category.
     *
     * @param categoryName The name of the category.
     * @return The icon for that category.
     */
    public static ResourceLocation getIcon(String categoryName) {
        for (Category category : values()) {
            if (category.name().equalsIgnoreCase(categoryName)) {
                return category.getIcon();
            }
        }

        return null;
    }

}
