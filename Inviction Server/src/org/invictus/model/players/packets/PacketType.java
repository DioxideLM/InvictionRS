package org.invictus.model.players.packets;

import org.invictus.model.players.Client;

public interface PacketType {
	public void processPacket(Client c, int packetType, int packetSize);
}
