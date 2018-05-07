package org.invictus.model.players.commands;

import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.packets.in.CommandPacketHandler;

/**
 * A set of commands for all players to use.
 * 
 * @author Joe
 *
 */
public class PlayerCommands extends CommandPacketHandler {

	Client player;

	public PlayerCommands(Client player) {
		this.player = player;
	}

	public void playerCommands(String commandString) {
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		switch (command) {
			
		case "skull":
			player.sendMessage("You are now skulled.");
			player.isSkulled = true;
			player.skullTimer = 1200;
			player.headIconPk = 0;
			break;
			
		case "vote":
			player.sendMessage("To-do: Add vote redirection.");
			break;
			
		case "donate":
			player.sendMessage("To-do: Add donate redirection.");
			break;
			
		case "forums":
			player.sendMessage("To-do: Add forums redirection.");
			break;
			
		case "commands":
			player.sendMessage("To-do: Add command list.");
			break;
			
		case "staff":
			player.sendMessage("To-do: Add staff list.");
			break;
			
		case "players":
			if (PlayerHandler.getPlayerCount() <= 1) {
				player.sendMessage("@blu@There is currently @dre@1@blu@ player online.");
			} else {
				player.sendMessage("@blu@There are currently @dre@" + PlayerHandler.getPlayerCount() + "@blu@ players online.");
			}
			break;
		}
	}
}
