package org.invictus.model.players.packets.in;

import org.invictus.Server;
import org.invictus.model.items.ItemConfiguration;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Drop Item
 **/
public class DropItemPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		if (System.currentTimeMillis() - c.alchDelay < 1800) {
			return;
		}
		if (c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}

		boolean droppable = true;
		for (int i : ItemConfiguration.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				c.droppedItem = itemId;
				c.getItems().destroyItemInterface(itemId);
				return;
			}
		}
		if (c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if (droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			} else {
				c.sendMessage("This items cannot be dropped.");
			}
		}

	}
}
