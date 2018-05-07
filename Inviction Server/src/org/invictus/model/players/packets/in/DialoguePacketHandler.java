package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;

/**
 * Dialogue
 **/
public class DialoguePacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {

		if (player.nextChat > 0) {
			player.getDialogueList().sendDialogues(player.nextChat, player.talkingNpc);
		} else {
			player.getDialogueList().sendDialogues(0, -1);
		}

	}

}
