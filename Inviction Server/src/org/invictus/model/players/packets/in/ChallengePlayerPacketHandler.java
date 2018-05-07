package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.packets.PacketType;

/**
 * Challenge Player
 **/
public class ChallengePlayerPacketHandler implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch (packetType) {
		case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
			if (PlayerHandler.players[answerPlayer] == null) {
				return;
			}

			if (c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return;
			}

			c.getDuelArena().requestDuel(answerPlayer);
			break;
		}
	}
}
