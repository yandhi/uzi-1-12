package me.kix.uzi.api.util.logging;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import net.minecraft.util.text.TextComponentString;

public class Logger implements MinecraftAccessor {

    public static void printCommandReturn(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
    }

    public static void printMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(String.format("\247c[%s]\247f %s", Uzi.INSTANCE.getLabel(), message)));
    }

    public static void ircMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(String.format("\2476[IRC]\247f %s", message)));
    }

}
