package org.invictus.model.players.packets.in;

import org.invictus.Server;
import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;
import org.invictus.model.players.skills.SkillHandler;

/**
 * Pickup Item
 **/
public class PickupItemPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.pItemY = player.getInStream().readSignedWordBigEndian();
		player.pItemId = player.getInStream().readUnsignedWord();
		player.pItemX = player.getInStream().readSignedWordBigEndian();
		if (Math.abs(player.getX() - player.pItemX) > 25 || Math.abs(player.getY() - player.pItemY) > 25) {
			player.resetWalkingQueue();
			return;
		}
		if (SkillHandler.isSkilling[11] == true) {
			player.getFiremaking().stopFiremaking();
		}
		player.getCombat().resetPlayerAttack();
		if (player.getX() == player.pItemX && player.getY() == player.pItemY) {
			Server.itemHandler.removeGroundItem(player, player.pItemId, player.pItemX, player.pItemY, true);
		} else {
			player.walkingToItem = true;
		}

	}

}
