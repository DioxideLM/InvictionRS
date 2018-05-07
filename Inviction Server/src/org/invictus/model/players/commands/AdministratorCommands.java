package org.invictus.model.players.commands;

import org.invictus.Configuration;
import org.invictus.Connection;
import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;

/**
 * A set of commands for Administrators and above to use.
 * 
 * @author Joe
 *
 */

public class AdministratorCommands {

	Client player;

	public AdministratorCommands(Client player) {
		this.player = player;
	}

	public void administratorCommandList(String commandString) {
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		switch (command) {

		/**
		 * Prevents all of the player's accounts from being able to play via their IP-Address.
		 */
		case "ipban":
			try {
				String playerIpBanned = commandString.substring(6);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerIpBanned)) {
							if (PlayerHandler.players[i].playerRights == 0) {
								Connection.addIpToBanList(PlayerHandler.players[i].connectedFrom);
								Connection.addIpToFile(PlayerHandler.players[i].connectedFrom);
								player.sendMessage("You have IP banned the user: " + PlayerHandler.players[i].playerName + " with the host: " + PlayerHandler.players[i].connectedFrom);
								PlayerHandler.players[i].disconnected = true;
							} else {
								player.sendMessage("You cannot ipban a moderator!");
							}
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Allows a previously IP-Banned user to be able to play again.
		 */
		case "unipban":
			try {
				String playerUnIpBanned = commandString.substring(9);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerUnIpBanned)) {
							Connection.unIPBanUser(PlayerHandler.players[i].connectedFrom);
							player.sendMessage("You have Un Ip-Banned the user: " + PlayerHandler.players[i].playerName);
							break;
						}
					}
				}
			} catch (Exception e) {
			}
			break;
		}
	}
}