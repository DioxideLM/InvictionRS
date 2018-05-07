package org.invictus.model.players.packets.in;

import org.invictus.Server;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;
import org.invictus.util.Misc;

/**
 * Chat
 **/
public class ClanChatPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
		textSent = textSent.replaceAll("_", " ");
		Server.clanChat.handleClanChat(c, textSent);
	}
}
