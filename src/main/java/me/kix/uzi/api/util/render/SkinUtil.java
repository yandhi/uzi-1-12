package me.kix.uzi.api.util.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Helps us manage skin textures. in the client.
 *
 * @author jackson
 * @since 1/14/2022
 */
public enum SkinUtil {
    INSTANCE;

    /**
     * The downloaded skin textures.
     */
    private Map<UUID, ResourceLocation> skinTextures = new HashMap<>();

    /**
     * The steve texture.
     */
    private final ResourceLocation steve = new ResourceLocation("textures/entity/steve.png");

    /**
     * Uses the game's skin manager to download the skin texture from the provided uuid.
     *
     * @param uuid The player's identifier token.
     */
    public void downloadSkinTexture(UUID uuid) {
        Minecraft.getMinecraft().getSkinManager().loadSkinFromCache(new GameProfile(uuid, "skinskimmer")).forEach(((type, minecraftProfileTexture) -> {
            if (type == MinecraftProfileTexture.Type.SKIN) {
                skinTextures.put(uuid, Minecraft.getMinecraft().getSkinManager().loadSkin(minecraftProfileTexture, type));
            }
        }));
    }

    public Map<UUID, ResourceLocation> getSkinTextures() {
        return skinTextures;
    }

    public ResourceLocation getSteve() {
        return steve;
    }
}
