package org.invictus.model.players.skills.runecrafting;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Runecrafting extends SkillHandler {

	//TODO: Add in Runecrafting pouches.
	//TODO: Add in the Abyss.
	//TODO: 

	private Client player;

	public Runecrafting(Client player) {
		this.player = player;
	}

	/**
	 * Rune essence ID constant.
	 */
	private static final int RUNE_ESS = 1436;

	/**
	 * Pure essence ID constant.
	 */
	private static final int PURE_ESS = 7936;

	/**
	 * An array containing the rune item numbers.
	 */
	public int[] runes = { 556, 558, 555, 557, 554, 559, 564, 562, 561, 563, 560, 565 };

	/**
	 * An array containing the object IDs of the runecrafting altars.
	 */
	public int[] altarID = { 2478, 2479, 2480, 2481, 2482, 2483, 2484, 2487, 2486, 2485, 2488, 2489 };

	/**
	 * 2D Array containing the levels required to craft the specific rune.
	 */
	public int[][] craftLevelReq = { { 556, 1 }, { 558, 2 }, { 555, 5 }, { 557, 9 }, { 554, 14 }, { 559, 20 }, { 564, 27 }, { 562, 35 }, { 561, 44 }, { 563, 54 }, { 560, 65 }, { 565, 77 } };

	/**
	 * 2D Array containing the levels that you can craft multiple runes.
	 */
	public int[][] multipleRunes = { { 11, 22, 33, 44, 55, 66, 77, 88, 99 }, { 14, 28, 42, 56, 70, 84, 98 }, { 19, 38, 57, 76, 95 }, { 26, 52, 78 }, { 35, 70 }, { 46, 92 }, { 59 }, { 74 }, { 91 }, { 100 }, { 100 }, { 100 } };

	public int[] runecraftExp = { 5, 6, 6, 7, 7, 8, 9, 9, 10, 11, 11, 11 };

	public void checkPouch(int i) {
		if (i < 0)
			return;
		player.sendMessage("This pouch has " + player.pouches[i] + " rune ess in it.");
	}

	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = player.POUCH_SIZE[i] - player.pouches[i];
		if (toAdd > player.getItems().getItemAmount(1436)) {
			toAdd = player.getItems().getItemAmount(1436);
		}
		if (toAdd > player.POUCH_SIZE[i] - player.pouches[i])
			toAdd = player.POUCH_SIZE[i] - player.pouches[i];
		if (toAdd > 0) {
			player.getItems().deleteItem(1436, toAdd);
			player.pouches[i] += toAdd;
		}
	}

	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = player.pouches[i];
		if (toAdd > player.getItems().freeSlots()) {
			toAdd = player.getItems().freeSlots();
		}
		if (toAdd > 0) {
			player.getItems().addItem(1436, toAdd);
			player.pouches[i] -= toAdd;
		}
	}

	/**
	 * Checks through all 28 item inventory slots for the specified item.
	 */
	private boolean itemInInv(int itemID, int slot, boolean checkWholeInv) {
		if (checkWholeInv) {
			for (int i = 0; i < 28; i++) {
				if (player.playerItems[i] == itemID + 1) {
					return true;
				}
			}
		} else {
			if (player.playerItems[slot] == itemID + 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Replaces essence in the inventory with the specified rune.
	 */
	private void replaceEssence(int essType, int runeID, int multiplier, int index) {
		System.out.println("multipler: " + multiplier);
		int exp = 0;
		for (int i = 0; i < 28; i++) {
			if (itemInInv(essType, i, false)) {
				player.getItems().deleteItem(essType, i, 1);
				player.getItems().addItem(runeID, 1 * multiplier);
				exp += runecraftExp[index];
			}
		}
		player.getPlayerAssistant().addSkillXP(exp * EXPERIENCE_MULTIPLIER, RUNECRAFTING);
	}

	/**
	 * Crafts the specific rune.
	 */
	public void craftRunes(int altarID) {
		int runeID = 0;

		for (int i = 0; i < this.altarID.length; i++) {
			if (altarID == this.altarID[i]) {
				runeID = runes[i];
			}
		}
		for (int i = 0; i < craftLevelReq.length; i++) {
			if (runeID == runes[i]) {
				if (player.playerLevel[20] >= craftLevelReq[i][1]) {
					if (player.getItems().playerHasItem(RUNE_ESS) || player.getItems().playerHasItem(PURE_ESS)) {
						int multiplier = 1;
						for (int j = 0; j < multipleRunes[i].length; j++) {
							if (player.playerLevel[20] >= multipleRunes[i][j]) {
								multiplier += 1;
							}
						}
						replaceEssence(RUNE_ESS, runeID, multiplier, i);
						player.startAnimation(791);
						// c.frame174(481, 0, 0); for sound
						player.gfx100(186);
						return;
					}
					player.sendMessage("You need to have essence to craft runes!");
					return;
				}
				player.sendMessage("You need a Runecrafting level of " + craftLevelReq[i][1] + " to craft this rune.");
			}
		}
	}

}