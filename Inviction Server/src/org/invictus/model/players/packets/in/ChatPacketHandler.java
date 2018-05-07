package org.invictus.model.players.packets.in;

import org.invictus.Connection;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Chat
 **/
public class ChatPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.setChatTextEffects(player.getInStream().readUnsignedByteS());
		player.setChatTextColor(player.getInStream().readUnsignedByteS());
		player.setChatTextSize((byte) (player.packetSize - 2));
		player.inStream.readBytes_reverseA(player.getChatText(), player.getChatTextSize(), 0);
		if (!Connection.isMuted(player))
			player.setChatTextUpdateRequired(true);
	}
}
