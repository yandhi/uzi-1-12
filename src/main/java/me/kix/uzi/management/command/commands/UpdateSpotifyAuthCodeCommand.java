package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.management.plugin.internal.toggleable.render.Spotify;

/**
 * Updates the spotify authorization code.
 *
 * @author yandhi
 * @since 6/26/2021
 */
public class UpdateSpotifyAuthCodeCommand extends ArgumentativeCommand {

    public UpdateSpotifyAuthCodeCommand() {
        super("UpdateSpotifyAuthCode", new String[]{"usac", "authcode", "codeupdate", "code"}, "Updates the spotify plugin's auth code");
    }

    @RegisterArgument({"set", "s", "up", "update"})
    public void set(String code) {
        Spotify.code = code;
    }
}
