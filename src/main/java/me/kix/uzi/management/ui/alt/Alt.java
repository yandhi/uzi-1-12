package me.kix.uzi.management.ui.alt;

import java.util.UUID;

/**
 * An account.
 *
 * @author jackson
 * @since idk, revised 1/14/2022
 */
public class Alt {

    /**
     * The account username.
     */
    private String username;

    /**
     * The account uuid.
     */
    private UUID uuid;

    /**
     * The account email.
     */
    private final String email;

    /**
     * The account password.
     */
    private final String password;

    public Alt(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Alt(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
