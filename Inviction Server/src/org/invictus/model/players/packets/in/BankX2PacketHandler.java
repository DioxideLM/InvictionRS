package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Bank X Items
 **/
public class BankX2PacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int Xamount = c.getInStream().readDWord();
		if (Xamount == 0)
			Xamount = 1;
		switch (c.xInterfaceId) {
		case 5064:
			if (c.inTrade) {
				c.sendMessage("You can't store items while trading!");
				return;
			}
			c.getBank().bankItem(c.playerItems[c.xRemoveSlot], c.xRemoveSlot, Xamount);
			break;

		case 5382:
			c.getBank().fromBank(c.bankItems[c.xRemoveSlot], c.xRemoveSlot, Xamount);
			break;

		case 3322:
			if (c.duelStatus <= 0) {
				c.getTradeAndDuel().tradeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
			} else {
				c.getDuelArena().stakeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
			}
			break;

		case 3415:
			if (c.duelStatus <= 0) {
				c.getTradeAndDuel().fromTrade(c.xRemoveId, c.xRemoveSlot, Xamount);
			}
			break;

		case 6669:
			c.getDuelArena().fromDuel(c.xRemoveId, c.xRemoveSlot, Xamount);
			break;
		}
	}
}