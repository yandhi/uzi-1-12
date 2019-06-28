package me.kix.uzi.api.game.impl.forge;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.misc.EventServerHandshake;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kix
 * @since 6/27/2019
 */
@Mixin(FMLHandshakeMessage.ModList.class)
public class MixinModList {

    @Shadow
    private Map<String, String> modTags;

    /**
     * @author Kix
     */
    @Overwrite
    public String modListAsString() {
        EventServerHandshake serverHandshake = new EventServerHandshake();
        Uzi.INSTANCE.getEventManager().dispatch(serverHandshake);
        return serverHandshake.isCancelled() ? "" : Joiner.on(',').withKeyValueSeparator("@").join(modTags);
    }

    /**
     * @author Kix
     */
    @Overwrite
    public int modListSize() {
        EventServerHandshake serverHandshake = new EventServerHandshake();
        Uzi.INSTANCE.getEventManager().dispatch(serverHandshake);
        return serverHandshake.isCancelled() ? 0 : modTags.size();
    }

    /**
     * @author Kix
     */
    @Overwrite
    public Map<String, String> modList() {
        EventServerHandshake serverHandshake = new EventServerHandshake();
        Uzi.INSTANCE.getEventManager().dispatch(serverHandshake);
        return serverHandshake.isCancelled() ? new HashMap<>() : modTags;
    }

}
