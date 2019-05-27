package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

/**
 * @author jackson
 * @since 8/29/18
 */
public class ServerCommand extends ArgumentativeCommand {

	public ServerCommand() {
		super("Server", new String[]{"ser", "s"}, "Allows us to see info about the server.");
	}

	@RegisterArgument({"brand", "servertype", "type"})
	public void serverBrand() {
		ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
		if (serverData != null) {
			Logger.printMessage("Brand: " + serverData.gameVersion);
		} else {
			Logger.printMessage("Brand: Vanilla");
		}
	}

	@RegisterArgument({"ip", "getip"})
	public void serverIP() {
		ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
		if (serverData != null) {
			Logger.printMessage("IP: " + serverData.serverIP);
		} else {
			Logger.printMessage("IP: localhost");
		}
	}

	@RegisterArgument({"motd", "message"})
	public void motd() {
		ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
		if (serverData != null) {
			Logger.printMessage("MOTD: " + serverData.serverMOTD);
		} else {
			Logger.printMessage("MOTD: Minecraft Client");
		}
	}

	@RegisterArgument({"protocol"})
	public void protocol() {
		ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
		if (serverData != null) {
			Logger.printMessage("Protocol: " + serverData.version);
		} else {
			Logger.printMessage("Protocol: 1.12.2");
		}
	}

	@RegisterArgument({"ping", "p"})
	public void ping() {
		ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
		if (serverData != null) {
			Logger.printMessage("Ping: " + serverData.pingToServer);
		} else {
			Logger.printMessage("Ping: 0");
		}
	}

}
