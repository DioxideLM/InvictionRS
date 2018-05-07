package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Magic on items
 **/
public class MagicOnItemsPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int slot = c.getInStream().readSignedWord();
		int itemId = c.getInStream().readSignedWordA();
		c.getInStream().readSignedWord();
		int spellId = c.getInStream().readSignedWordA();

		c.usingMagic = true;
		c.getPlayerAssistant().magicOnItems(slot, itemId, spellId);
		c.usingMagic = false;

	}

}
