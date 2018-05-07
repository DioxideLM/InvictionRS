package org.invictus.model.players.packets.in;

import org.invictus.*;
import org.invictus.model.items.ItemID;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;
import org.invictus.util.Misc;

public class ItemOnGroundItemPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.getInStream().readSignedWordBigEndian();
		int itemUsed = player.getInStream().readSignedWordA();
		int groundItem = player.getInStream().readUnsignedWord();
		int gItemY = player.getInStream().readSignedWordA();
		int itemUsedSlot = player.getInStream().readSignedWordBigEndianA();
		int gItemX = player.getInStream().readUnsignedWord();
		if (!player.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		if (!Server.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
			return;
		}

		switch (itemUsed) {

		case ItemID.TINDERBOX:
			player.getFiremaking().attemptFire(itemUsed, groundItem, gItemX, gItemY, true);
			break;

		default:
			if (player.playerRights == 3)
				Misc.println("ItemUsed " + itemUsed + " on Ground Item " + groundItem);
			break;
		}
	}

}
