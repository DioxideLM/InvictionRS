package org.invictus.model.players.packets.in;

import org.invictus.model.items.UseItem;
import org.invictus.model.npcs.NPCHandler;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

public class ItemOnNpcPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = NPCHandler.npcs[i].npcType;
		if (!c.getItems().playerHasItem(itemId, 1, slot)) {
			return;
		}

		UseItem.ItemonNpc(c, itemId, npcId, slot);
	}
}
