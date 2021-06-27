package me.kix.uzi.api.util.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;

/**
 * The util for the spotify api.
 *
 * <p>
 * I followed the github example to a t almost.
 * </p>
 *
 * @author yandhi
 * @since 6/26/2021
 */
public enum SpotifyUtil {
    INSTANCE;

    /**
     * The client id.
     */
    private final String clientId = "48552355a9664f749b604f64d57508fb";

    /**
     * The client secret.
     */
    private final String clientSecret = "b073ff437e794cc5b116b610d5d8a80c";

    /**
     * The redirect uri.
     *
     * <p>
     * I really don't think this has to be real?
     * </p>
     */
    private final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");

    /**
     * The API itself.
     */
    private SpotifyApi spotify;

    /**
     * The auth code.
     */
    private final String code = "AQDAWPHF-FwGszoIcl8mMibeurQ51H4c0qx8JgyPaMNf4eMw_aMiyEGWtPtY2qKKG__24EVJbRVivVBT2GY4WPQnoFZuHBUVCpcZ34ZvljYvZVe_ksRluzS2jqcM7qHWVUjBADdmiu1JU4c-Rm4AuYIZZgvI0r_MYK-n6YeIQBJmuBqMagHzPg";

    /**
     * Sets up the api for user-access.
     */
    public void initAccess(String code) {
        spotify = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();

        AuthorizationCodeRequest authorizationCodeRequest = spotify.authorizationCode(this.code)
                .build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotify.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotify.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println(authorizationCodeCredentials.getAccessToken());
            System.out.println(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public SpotifyApi getSpotify() {
        return spotify;
    }
}
