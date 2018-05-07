package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.packets.PacketType;
import org.invictus.model.players.skills.SkillHandler;

/**
 * Walking packet
 **/
public class WalkingPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		if (packetType == 248 || packetType == 164) {
			player.faceUpdate(0);
			player.npcIndex = 0;
			player.playerIndex = 0;
			if (player.followId > 0 || player.followId2 > 0)
				player.getPlayerAssistant().resetFollow();
		}
		player.getPlayerAssistant().removeAllWindows();
		if (player.duelRule[1] && player.duelStatus == 5) {
			if (PlayerHandler.players[player.duelingWith] != null) {
				if (!player.goodDistance(player.getX(), player.getY(),
						PlayerHandler.players[player.duelingWith].getX(),
						PlayerHandler.players[player.duelingWith].getY(), 1)
						|| player.attackTimer == 0) {
					player.sendMessage("Walking has been disabled in this duel!");
				}
			}
			player.playerIndex = 0;
			return;
		}

		if (player.freezeTimer > 0) {
			if (PlayerHandler.players[player.playerIndex] != null) {
				if (player.goodDistance(player.getX(), player.getY(),
						PlayerHandler.players[player.playerIndex].getX(),
						PlayerHandler.players[player.playerIndex].getY(), 1)
						&& packetType != 98) {
					player.playerIndex = 0;
					return;
				}
			}
			if (packetType != 98) {
				player.sendMessage("A magical force stops you from moving.");
				player.playerIndex = 0;
			}
			return;
		}

		if (System.currentTimeMillis() - player.lastSpear < 4000) {
			player.sendMessage("You have been stunned.");
			player.playerIndex = 0;
			return;
		}
		
		if(player.getAgility().isSkilling[SkillHandler.AGILITY]) {
			return;
		}
		
		if (player.isResting) {
			player.getRest().getUp();
			return;
		}

		if (packetType == 98) {
			player.mageAllowed = true;
		}

		if ((player.duelStatus >= 1 && player.duelStatus <= 4) || player.duelStatus == 6) {
			if (player.duelStatus == 6) {
				player.getDuelArena().claimStakedItems();
			}
			return;
		}

		if (player.respawnTimer > 3) {
			return;
		}
		if (player.inTrade) {
			return;
		}
		if (packetType == 248) {
			packetSize -= 14;
		}
		player.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++player.newWalkCmdSteps > player.walkingQueueSize) {
			player.newWalkCmdSteps = 0;
			return;
		}

		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;

		int firstStepX = player.getInStream().readSignedWordBigEndianA()
				- player.getMapRegionX() * 8;
		for (int i = 1; i < player.newWalkCmdSteps; i++) {
			player.getNewWalkCmdX()[i] = player.getInStream().readSignedByte();
			player.getNewWalkCmdY()[i] = player.getInStream().readSignedByte();
		}

		int firstStepY = player.getInStream().readSignedWordBigEndian()
				- player.getMapRegionY() * 8;
		player.setNewWalkCmdIsRunning(player.getInStream().readSignedByteC() == 1);
		for (int i1 = 0; i1 < player.newWalkCmdSteps; i1++) {
			player.getNewWalkCmdX()[i1] += firstStepX;
			player.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}
