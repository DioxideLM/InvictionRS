package org.invictus.model.items;

import org.invictus.model.players.Client;

/**
 * A class for handling the operating of items from the equipment interface.
 * 
 * @author Joe
 *
 */
public class OperateItem {

	private Client player;

	public OperateItem(Client player) {
		this.player = player;
	}

	public void useOperate(int itemId) {
		switch (itemId) {
		case 1712:
		case 1710:
		case 1708:
		case 1706:
			player.getPlayerAssistant().handleGlory(itemId);
			break;
		case 11283:
		case 11284:
			if (player.playerIndex > 0) {
				player.getCombat().handleDfs();
			} else if (player.npcIndex > 0) {
				player.getCombat().handleDfsNPC();
			}
			break;
		}
	}

}
