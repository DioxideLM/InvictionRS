package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.packets.PacketType;
import org.invictus.util.Misc;

/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuffPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.inTrade) {
			if (!c.acceptedTrade) {
				Misc.println("trade reset");
				c.getTradeAndDuel().declineTrade();
			}
		}

		Client o = (Client) PlayerHandler.players[c.duelingWith];
		if (o != null) {
			if (c.duelStatus >= 1 && c.duelStatus <= 4) {
				c.getDuelArena().declineDuel();
				o.getDuelArena().declineDuel();
			}
		}

		if (c.duelStatus == 6) {
			c.getDuelArena().claimStakedItems();
		}

	}

}
