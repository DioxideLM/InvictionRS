package org.invictus.model.players.skills.farming;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

/**
 * Farming.java
 * 
 * @author Sanity
 * 
 **/

public class Farming extends SkillHandler {

	private Client player;

	public Farming(Client player) {
		this.player = player;
	}

	private final int[] VALID_SEEDS = { 5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304 };
	private final int[] HERBS = { 199, 201, 203, 205, 207, 3049, 209, 211, 213, 3051, 215, 2485, 217, 219 }; /* lant, toad, snap */
	private final int[] SEED_PLANT_EXP = { 11, 14, 16, 22, 27, 34, 43, 55, 69, 88, 107, 135, 171, 200 };
	private final int[] HERB_EXPS = { 13, 15, 18, 24, 31, 39, 49, 62, 78, 99, 120, 152, 192, 225 };
	private final int[] FARMING_REQS = { 1, 14, 19, 26, 32, 38, 44, 50, 56, 62, 67, 73, 79, 85 };
	private final int PATCH_HERBS = 8143;
	private final int PATCH_WEEDS = 8389;

	public void checkItemOnObject(int itemId) {
		for (int j = 0; j < VALID_SEEDS.length; j++) {
			if (itemId == VALID_SEEDS[j]) {
				handleFarming(VALID_SEEDS[j], HERBS[j], HERB_EXPS[j], j);
				return;
			}
		}
	}

	private void handleFarming(int seedId, int herbId, int exp, int slot) {
		if (player.playerLevel[player.playerFarming] < FARMING_REQS[slot]) {
			player.sendMessage("You need a farming level of " + FARMING_REQS[slot] + " to farm this seed.");
			return;
		}
		if (player.getItems().playerHasItem(seedId, 1)) {
			player.getItems().deleteItem(seedId, player.getItems().getItemSlot(seedId), 1);
			player.getPlayerAssistant().addSkillXP(SEED_PLANT_EXP[slot] * EXPERIENCE_MULTIPLIER, FARMING);
			player.getPlayerAssistant().refreshSkill(FARMING);
			int herbAmount = Misc.random(5) + 3;
			player.farm[0] = herbId;
			player.farm[1] = herbAmount;
			updateHerbPatch();
		}
	}

	public int getExp() {
		for (int j = 0; j < HERBS.length; j++)
			if (HERBS[j] == player.farm[0])
				return HERB_EXPS[j];
		return 0;
	}

	public void updateHerbPatch() {
		if (player.farm[0] > 0 && player.farm[1] > 0) {
			// make object here
			// c.sendMessage("Make herbs...");
			player.getPlayerAssistant().object(PATCH_HERBS, 2813, 3463, -1, 10);
		} else {
			// make weed patch here
			// c.sendMessage("Make weeds...");
			player.getPlayerAssistant().object(PATCH_WEEDS, 2813, 3463, -1, 10);
		}
	}

	public void pickHerb() {
		if (player.farm[0] > 0 && player.farm[1] > 0) {
			if (player.getItems().addItem(player.farm[0], 1)) {
				// c.startAnimation(2273);
				player.getPlayerAssistant().addSkillXP(getExp() * EXPERIENCE_MULTIPLIER, FARMING);
				player.farm[1]--;
				if (player.farm[1] == 0)
					player.farm[0] = -1;
				updateHerbPatch();
				player.sendMessage("You pick a herb.");
				player.getPlayerAssistant().resetAnimation();
			}
		}
	}

}