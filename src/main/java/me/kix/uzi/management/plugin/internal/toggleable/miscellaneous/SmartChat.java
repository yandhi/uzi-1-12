package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentString;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Translates non-english chat messages.
 *
 * <p>
 * This is for third-world servers.
 * </p>
 *
 * @author Kix & Brennan & Alerithe
 * Created in 06 2019.
 */
public class SmartChat extends ToggleablePlugin {

    /**
     * Our Yandex API Key.
     */
    private static final String API_KEY = "trnsl.1.1.20190619T035103Z.ce2ec667a9042ac3.4f694b585a5fa74e5bf08860a7c69994e8f8c7fe";

    /**
     * The common splitters for chat and player.
     */
    public static String splitter = ":";

    public SmartChat() {
        super("SmartChat", Category.MISCELLANEOUS);
        setDisplay("Smart Chat");
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketChat) {
            SPacketChat packetChat = (SPacketChat) read.getPacket();
            String messageHeader = "";
            String extractedMessage = StringUtils.stripControlCodes(packetChat.getChatComponent().getFormattedText());
            String[] splitMessage = extractedMessage.split(splitter);
            if (splitMessage.length >= 1) {
                messageHeader = splitMessage[0];
                extractedMessage = StringUtils.stripControlCodes(splitMessage[1]);
            }
            String language = getLanguage(extractedMessage);

            if (!language.equalsIgnoreCase("en")) {
                String translatedText = getTranslatedText(extractedMessage, "en");
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(messageHeader + ":" + translatedText));
            }
        }
    }

    /**
     * Gives us the translated text.
     *
     * @param text     The text being translated.
     * @param language The language of the text being translated.
     * @return The translated text.
     * @author bhopped & kix & alerithe
     */
    private String getTranslatedText(String text, String language) {
        try {
            URL url = new URL(String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s", API_KEY, URLEncoder.encode(text), language));
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonArray array = element.getAsJsonObject().get("text").getAsJsonArray();
            return array.get(0).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Translation Failed";
    }

    /**
     * Tells us the language of the text.
     *
     * @param text The text being checked.
     * @return The language type.
     * @author bhopped & kix & alerithe
     */
    private String getLanguage(String text) {
        try {
            URL url = new URL(String.format("https://translate.yandex.net/api/v1.5/tr.json/detect?key=%s&text=%s", API_KEY, URLEncoder.encode(text)));
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject object = element.getAsJsonObject();

            if (object.has("lang")) {
                return object.get("lang").getAsString();
            } else {
                return "Not a Language";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "none";
    }

}
