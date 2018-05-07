package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Bank 10 Items
 **/
public class Bank10PacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int interfaceId = player.getInStream().readUnsignedWordBigEndian();
		int itemId = player.getInStream().readUnsignedWordA();
		int removeSlot = player.getInStream().readUnsignedWordA();

		switch (interfaceId) {
		
		case 1688:
			player.getOperateItem().useOperate(itemId);
			break;
			
		case 3900:
			player.getShops().buyItem(itemId, removeSlot, 5);
			break;

		case 3823:
			player.getShops().sellItem(itemId, removeSlot, 5);
			break;

		case 5064:
			if (player.inTrade) {
				player.sendMessage("You can't store items while trading!");
				return;
			}
			player.getBank().bankItem(itemId, removeSlot, 10);
			break;

		case 5382:
			player.getBank().fromBank(itemId, removeSlot, 10);
			break;

		case 3322:
			if (player.duelStatus <= 0) {
				player.getTradeAndDuel().tradeItem(itemId, removeSlot, 10);
			} else {
				player.getDuelArena().stakeItem(itemId, removeSlot, 10);
			}
			break;

		case 3415:
			if (player.duelStatus <= 0) {
				player.getTradeAndDuel().fromTrade(itemId, removeSlot, 10);
			}
			break;

		case 6669:
			player.getDuelArena().fromDuel(itemId, removeSlot, 10);
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			player.getSmithing().readInput(player.playerLevel[player.playerSmithing], Integer.toString(itemId), player, 5);
			break;
		}
	}

}
