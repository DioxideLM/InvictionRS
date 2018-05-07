package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

public class ChangeRegionPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getPlayerAssistant().removeObjects();
		// Server.objectManager.loadObjects(c);
	}

}
