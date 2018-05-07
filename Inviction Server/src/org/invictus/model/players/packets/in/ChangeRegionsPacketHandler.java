package org.invictus.model.players.packets.in;

import org.invictus.Server;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Change Regions
 */
public class ChangeRegionsPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		// Server.objectHandler.updateObjects(c);
		Server.itemHandler.reloadItems(c);
		Server.objectManager.loadObjects(c);
		c.getPlayerAssistant().castleWarsObjects();

		c.saveFile = true;

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPlayerAssistant().requestUpdates();
		}

	}

}
