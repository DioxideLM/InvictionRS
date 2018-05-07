package org.invictus.model.players.packets.in;

import org.invictus.model.items.GameItem;
import org.invictus.model.items.Item;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Bank All Items
 **/
public class BankAllPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int removeSlot = c.getInStream().readUnsignedWordA();
		int interfaceId = c.getInStream().readUnsignedWord();
		int removeId = c.getInStream().readUnsignedWordA();

		switch (interfaceId) {
		case 3900:
			c.getShops().buyItem(removeId, removeSlot, 10);
			break;

		case 3823:
			c.getShops().sellItem(removeId, removeSlot, 10);
			break;

		case 5064:
			if (c.inTrade) {
				c.sendMessage("You can't store items while trading!");
				return;
			}
			if (Item.itemStackable[removeId]) {
				c.getBank().bankItem(c.playerItems[removeSlot], removeSlot, c.playerItemsN[removeSlot]);
			} else {
				c.getBank().bankItem(c.playerItems[removeSlot], removeSlot, c.getItems().itemAmount(c.playerItems[removeSlot]));
			}
			break;

		case 5382:
			c.getBank().fromBank(c.bankItems[removeSlot], removeSlot, c.bankItemsN[removeSlot]);
			break;

		case 3322:
			if (c.duelStatus <= 0) {
				if (Item.itemStackable[removeId]) {
					c.getTradeAndDuel().tradeItem(removeId, removeSlot, c.playerItemsN[removeSlot]);
				} else {
					c.getTradeAndDuel().tradeItem(removeId, removeSlot, 28);
				}
			} else {
				if (Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
					c.getDuelArena().stakeItem(removeId, removeSlot, c.playerItemsN[removeSlot]);
				} else {
					c.getDuelArena().stakeItem(removeId, removeSlot, 28);
				}
			}
			break;

		case 3415:
			if (c.duelStatus <= 0) {
				if (Item.itemStackable[removeId]) {
					for (GameItem item : c.getTradeAndDuel().offeredItems) {
						if (item.id == removeId) {
							c.getTradeAndDuel().fromTrade(removeId, removeSlot, c.getTradeAndDuel().offeredItems.get(removeSlot).amount);
						}
					}
				} else {
					for (GameItem item : c.getTradeAndDuel().offeredItems) {
						if (item.id == removeId) {
							c.getTradeAndDuel().fromTrade(removeId, removeSlot, 28);
						}
					}
				}
			}
			break;

		case 6669:
			if (Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
				for (GameItem item : c.getDuelArena().stakedItems) {
					if (item.id == removeId) {
						c.getDuelArena().fromDuel(removeId, removeSlot, c.getDuelArena().stakedItems.get(removeSlot).amount);
					}
				}

			} else {
				c.getDuelArena().fromDuel(removeId, removeSlot, 28);
			}
			break;

		}
	}

}
