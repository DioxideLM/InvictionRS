package org.invictus.model.players.packets.in;

import org.invictus.model.items.UseItem;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

public class ItemOnItemPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int usedWithSlot = player.getInStream().readUnsignedWord();
		int itemUsedSlot = player.getInStream().readUnsignedWordA();
		int useWith = player.playerItems[usedWithSlot] - 1;
		int itemUsed = player.playerItems[itemUsedSlot] - 1;
		if (!player.getItems().playerHasItem(useWith, 1, usedWithSlot)
				|| !player.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		UseItem.ItemonItem(player, itemUsed, useWith, usedWithSlot, itemUsedSlot);
	}

}
