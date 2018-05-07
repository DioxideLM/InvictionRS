package org.invictus.model.players.packets;

import org.invictus.model.players.Client;

/**
 * A quiet packet to be used when a packet is not in use.
 * 
 * @author Joe
 *
 */
public class QuietPacket implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {

	}
}
