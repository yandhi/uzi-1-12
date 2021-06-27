package me.kix.uzi.management.plugin.internal.toggleable.render;

import com.google.gson.JsonObject;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventTick;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.MathUtil;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.util.spotify.SpotifyUtil;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

/**
 * Allows the client to have spotify now-playing integration.
 *
 * @author yandhi
 * @since 6/26/2021
 */
public class Spotify extends ToggleablePlugin {

    /**
     * Loads on startup from a file.
     */
    public static String code = "";

    /**
     * The stopwatch for the plugin.
     */
    private final Timer timer = new Timer();

    public Spotify() {
        super("Spotify", Category.RENDER);
    }

    @Register
    public void onWorldToScreen(EventRender.WorldToScreen event) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        if (mc.gameSettings.showDebugInfo) return;
        SpotifyApi spotify = SpotifyUtil.INSTANCE.getSpotify();

        try {
            System.out.println(spotify.getCurrentUsersProfile().build().execute().getDisplayName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Register
    public void onTick(EventTick tick) {
        if (timer.completed(3600000)) {
            try {
                SpotifyApi spotify = SpotifyUtil.INSTANCE.getSpotify();
                spotify.setAccessToken(spotify.authorizationCodeRefresh().build().execute().getAccessToken());
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                e.printStackTrace();
            }
            timer.reset();
        }
    }

    @Override
    public void save(JsonObject destination) {
        super.save(destination);
        destination.addProperty("Code", code);
    }

    @Override
    public void load(JsonObject source) {
        code = source.get("Code").getAsString();
        super.load(source);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        SpotifyUtil.INSTANCE.initAccess(code);
    }
}
