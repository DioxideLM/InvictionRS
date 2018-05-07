package org.invictus.model.players.packets.in;

import org.invictus.Configuration;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Trading
 */
public class TradingPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPlayerAssistant().resetFollow();
		if (c.disconnected) {
			c.tradeStatus = 0;
		}
		if (c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}

		if (c.playerRights == 2 && !Configuration.ADMINISTRATOR_TRADING) {
			c.sendMessage("Trading as an admin has been disabled.");
			return;
		}
		if (tradeId != c.playerId)
			c.getTradeAndDuel().requestTrade(tradeId);
	}

}
