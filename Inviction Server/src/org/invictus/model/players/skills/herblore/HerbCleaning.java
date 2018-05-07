package org.invictus.model.players.skills.herblore;

import org.invictus.model.players.Client;

public class HerbCleaning extends HerbData {

	private Client player;

	public HerbCleaning(Client player) {
		this.player = player;
	}

	public void handleHerbCleaning(final int itemId, final int itemSlot) {
		for (int i = 0; i < grimyHerbs.length; i++) {
			if (itemId == grimyHerbs[i][0]) {
				if (player.playerLevel[HERBLORE] < grimyHerbs[i][2]) {
					player.sendMessage("You need an herblore level of " + grimyHerbs[i][2] + " to clean this herb.");
					return;
				}
				player.getItems().deleteItem(itemId, itemSlot, 1);
				player.getItems().addItem(grimyHerbs[i][1], 1);
				player.getPlayerAssistant().addSkillXP(grimyHerbs[i][3], HERBLORE);
			}
		}
	}

}
