package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Move Items
 **/
public class MoveItemsPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int somejunk = c.getInStream().readUnsignedWordA(); // junk
		int itemFrom = c.getInStream().readUnsignedWordA();// slot1
		int itemTo = (c.getInStream().readUnsignedWordA() - 128);// slot2
		// c.sendMessage("junk: " + somejunk);
		if (c.inTrade) {
			c.getTradeAndDuel().declineTrade();
			return;
		}
		if (c.tradeStatus == 1) {
			c.getTradeAndDuel().declineTrade();
			return;
		}
		if (c.duelStatus == 1) {
			c.getDuelArena().declineDuel();
			return;
		}
		c.getItems().moveItems(itemFrom, itemTo, somejunk);
	}
}
