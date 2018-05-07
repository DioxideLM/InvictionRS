package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

public class IdlePacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		// if (!c.playerName.equalsIgnoreCase("Sanity"))
		// c.logout();
	}
}