package me.kix.uzi.api.event.events.misc;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraftforge.fml.client.ExtendedServerListData;

/**
 * Called when the server recieves the handshake info from the client.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class EventServerHandshake extends EventCancellable {

    /**
     * The server data.
     *
     * <p>
     * This is immutable so we have to create a new instance of it later down the road.
     * </p>
     */
    private ExtendedServerListData serverListData;

    public EventServerHandshake(ExtendedServerListData serverListData) {
        this.serverListData = serverListData;
    }

    public ExtendedServerListData getServerListData() {
        return serverListData;
    }

    public void setServerListData(ExtendedServerListData serverListData) {
        this.serverListData = serverListData;
    }
}
