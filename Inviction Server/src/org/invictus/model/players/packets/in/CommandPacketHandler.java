package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

public class CommandPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		String commandString = player.getInStream().readString();
		if (player.isMember) {
			player.getDonatorCommands().memberCommandList(commandString);
		}
		if (player.playerRights >= 1) {
			player.getModCommands().moderatorCommandList(commandString);
		}
		if (player.playerRights >= 2) {
			player.getAdminCommands().administratorCommandList(commandString);
		}
		if (player.playerRights >= 3) {
			player.getDevCommands().developerCommandList(commandString);
		}
		player.getPlayerCommands().playerCommands(commandString);
	}
}