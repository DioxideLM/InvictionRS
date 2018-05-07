package org.invictus.model.players.commands;

import org.invictus.Configuration;
import org.invictus.Connection;
import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;

/**
 * A set of commands for Moderators and above to use.
 * 
 * @author Joe
 *
 */

public class ModeratorCommands {

	Client player;

	public ModeratorCommands(Client player) {
		this.player = player;
	}

	public void moderatorCommandList(String commandString) {
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		switch (command) {

		/**
		 * Isolates the player in an area where they cannot do anything.
		 */
		case "jail":
			try {
				String playerJailed = commandString.substring(5);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerJailed)) {
							Client otherPlayer = (Client) PlayerHandler.players[i];
							otherPlayer.teleportToX = 3191;
							otherPlayer.teleportToY = 9827;
							otherPlayer.heightLevel = 0;
							otherPlayer.jailed = true;
							player.sendMessage("You have Jailed " + otherPlayer.playerName + ".");
							otherPlayer.sendMessage("You have been Jailed.");
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Removes the player from isolation.
		 */
		case "unjail":
			try {
				String playerUnjailed = commandString.substring(7);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerUnjailed)) {
							Client otherPlayer = (Client) PlayerHandler.players[i];
							otherPlayer.teleportToX = 2605;
							otherPlayer.teleportToY = 3093;
							otherPlayer.heightLevel = 0;
							otherPlayer.jailed = false;
							player.sendMessage("You have Unjailed " + otherPlayer.playerName + ".");
							otherPlayer.sendMessage("You have been Unjailed.");
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Teleports to the player's current location.
		 */
		case "teleto":
			try {
				String playerTeleportingTo = commandString.substring(7);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerTeleportingTo)) {
							Client otherPlayer = (Client) PlayerHandler.players[i];
							player.getPlayerAssistant().movePlayer(otherPlayer.absX, otherPlayer.absY, otherPlayer.heightLevel);
							player.sendMessage("You have teleported to " + playerTeleportingTo);
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Teleports the player to your current location.
		 */
		case "teletome":
			try {
				String playerTeleported = commandString.substring(9);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerTeleported)) {
							Client otherPlayer = (Client) PlayerHandler.players[i];
							otherPlayer.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel);
							otherPlayer.sendMessage("You have been teleported to " + player.playerName);
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Bans the player's account.
		 */
		case "ban":
			try {
				String playerBanned = commandString.substring(4);
				Connection.addNameToBanList(playerBanned);
				Connection.addNameToFile(playerBanned);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerBanned)) {
							PlayerHandler.players[i].disconnected = true;
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Unbans the player's account.
		 */
		case "unban":
			try {
				String playerUnbanned = commandString.substring(6);
				Connection.removeNameFromBanList(playerUnbanned);
				player.sendMessage(playerUnbanned + " has been unbanned.");
			} catch (Exception e) {
			}
			break;

		/**
		 * Prevents the player's account from being able to communicate via chat.
		 */
		case "mute":
			try {
				String playerMuted = commandString.substring(5);
				Connection.addNameToMuteList(playerMuted);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerMuted)) {
							Client otherPlayer = (Client) PlayerHandler.players[i];
							otherPlayer.sendMessage("You have been muted by: " + player.playerName);
							otherPlayer.muted = true;
							break;
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Allows a muted player's account to be able to communicate freely again.
		 */
		case "unmute":
			try {
				String playerUnmuted = commandString.substring(7);
				Connection.unMuteUser(playerUnmuted);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerUnmuted)) {
							Client otherPlayer = (Client) PlayerHandler.players[i];
							otherPlayer.sendMessage("You have been unmuted by " + player.playerName);
							otherPlayer.muted = false;
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Prevents all of the player's accounts from being able to communicate via chat via their IP-Address.
		 */
		case "ipmute":
			try {
				String playerIpMuted = commandString.substring(7);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerIpMuted)) {
							Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
							player.sendMessage("You have IP Muted the user: " + PlayerHandler.players[i].playerName);
							Client otherPlayer = (Client) PlayerHandler.players[i];
							otherPlayer.sendMessage("You have been muted by: " + player.playerName);
							break;
						}
					}
				}
			} catch (Exception e) {
			}
			break;

		/**
		 * Allows a previously IP-Muted user to be able to communicate again.
		 */
		case "unipmute":
			try {
				String playerUnIpMuted = commandString.substring(9);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerUnIpMuted)) {
							Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
							player.sendMessage("You have Un Ip-Muted the user: " + PlayerHandler.players[i].playerName);
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
