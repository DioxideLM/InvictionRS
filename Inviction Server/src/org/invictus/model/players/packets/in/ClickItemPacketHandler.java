package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItemPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.getInStream().readSignedWordBigEndianA();
		int itemSlot = player.getInStream().readUnsignedWordA();
		int itemId = player.getInStream().readUnsignedWordBigEndian();
		// ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (itemId != player.playerItems[itemSlot] - 1) {
			return;
		}
		player.getHerbCleaning().handleHerbCleaning(itemId, itemSlot);
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			player.getRunecrafting().fillPouch(pouch);
			return;
		}
		if (player.getFood().isFood(itemId)) {
			player.getFood().consumeFood(itemId, itemSlot);
		}
		if (player.getPotions().isPotion(itemId)) {
			player.getPotions().handlePotion(itemId, itemSlot);
		}
		if (player.getPrayer().isBone(itemId)) {
			player.getPrayer().buryBone(itemId, itemSlot);
		}
		if (player.getChampChallenge().isScroll(itemId)) {
			player.getChampChallenge().redeemScrolls(itemId);
		}
		switch (itemId) {
		case 952:
			if (player.inArea(3553, 3301, 3561, 3294)) {
				player.teleTimer = 3;
				player.newLocation = 1;
			} else if (player.inArea(3550, 3287, 3557, 3278)) {
				player.teleTimer = 3;
				player.newLocation = 2;
			} else if (player.inArea(3561, 3292, 3568, 3285)) {
				player.teleTimer = 3;
				player.newLocation = 3;
			} else if (player.inArea(3570, 3302, 3579, 3293)) {
				player.teleTimer = 3;
				player.newLocation = 4;
			} else if (player.inArea(3571, 3285, 3582, 3278)) {
				player.teleTimer = 3;
				player.newLocation = 5;
			} else if (player.inArea(3562, 3279, 3569, 3273)) {
				player.teleTimer = 3;
				player.newLocation = 6;
			}
			break;
		}
	}
}
