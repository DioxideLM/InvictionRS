package org.invictus.model.players.commands;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.in.CommandPacketHandler;

/**
 * A set of commands for Members to use.
 * 
 * @author Joe
 *
 */
public class MemberCommands extends CommandPacketHandler {

	Client player;

	public MemberCommands(Client player) {
		this.player = player;
	}

	public void memberCommandList(String commandString) {
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		switch (command) {
		case "membertest":
			player.sendMessage("Hello, World! You're a member!");
			break;
		}
	}
}
