package me.kix.uzi.api.keybind.task.tasks;

import me.kix.uzi.api.keybind.task.KeybindTaskStrategy;
import net.minecraft.client.Minecraft;

/**
 * Sends a message on a keybind.
 *
 * @author Kix
 * @since 7/19/2019
 */
public class SendMessageKeybindTaskStrategy implements KeybindTaskStrategy {

    /**
     * The message to send.
     */
    private final String message;

    public SendMessageKeybindTaskStrategy(String message) {
        this.message = message;
    }

    @Override
    public void executeTask() {
        Minecraft.getMinecraft().player.sendChatMessage(message);
    }

    @Override
    public String getStrategy() {
        return "Message";
    }

    public String getMessage() {
        return message;
    }
}
