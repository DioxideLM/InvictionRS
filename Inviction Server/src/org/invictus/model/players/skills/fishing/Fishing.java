package org.invictus.model.players.skills.fishing;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

/**
 * Fishing.java
 * 
 * @author Sanity
 * 
 **/

public class Fishing extends SkillHandler {

	private Client player;

	public Fishing(Client player) {
		this.player = player;
	}
	
	private int fishType;
	private int exp;
	private int req;

	private final int SALMON_EXP = 70;
	private final int SWORD_EXP = 100;
	private final int SALMON_ID = 331;
	private final int SWORD_ID = 371;
	public boolean fishing = false;

	private final int[] REQS = { 1, 20, 40, 35, 62, 76, 81 };
	private final int[] FISH_TYPES = { 317, 335, 359, 359, 7944, 383, 389 };
	private final int[] EXP = { 10, 50, 80, 90, 120, 110, 46 };
	private int equipmentType;

	public void setupFishing(int fishType) {
		if (player.getItems().playerHasItem(getEquipment(fishType))) {
			if (player.playerLevel[player.playerFishing] >= req) {
				int slot = getSlot(fishType);
				if (slot > -1) {
					this.req = REQS[slot];
					this.fishType = FISH_TYPES[slot];
					this.setEquipmentType(getEquipment(fishType));
					this.exp = EXP[slot];
					player.fishing = true;
					player.fishTimer = 3 + Misc.random(2);
				}
			} else {
				player.sendMessage("You need a fishing level of " + req + " to fish here.");
				resetFishing();
			}
		} else {
			player.sendMessage("You do not have the correct equipment to use this fishing spot.");
			resetFishing();
		}
	}

	public void catchFish() {
		if (player.getItems().playerHasItem(getEquipment(fishType))) {
			if (player.playerLevel[player.playerFishing] >= req) {
				if (player.getItems().freeSlots() > 0) {
					if (canFishOther(fishType)) {
						player.getItems().addItem(otherFishId(fishType), 1);
						player.getPlayerAssistant().addSkillXP(otherFishXP(fishType), FISHING);
					} else {
						player.getItems().addItem(fishType, 1);
						player.getPlayerAssistant().addSkillXP(exp * EXPERIENCE_MULTIPLIER, FISHING);
					}
					player.sendMessage("You catch a fish.");
					player.fishTimer = 2 + Misc.random(2);
				}
			} else {
				player.sendMessage("You need a fishing level of " + req + " to fish here.");
				resetFishing();
			}
		} else {
			player.sendMessage("You do not have the correct equipment to use this fishing spot.");
			resetFishing();
		}
	}

	private int getSlot(int fishType) {
		for (int j = 0; j < REQS.length; j++)
			if (FISH_TYPES[j] == fishType)
				return j;
		return -1;
	}

	private int getEquipment(int fish) {
		if (fish == 317) // shrimp
			return 303;
		if (fish == 335) // trout + salmon
			return 309;
		if (fish == 337) // lobs
			return 301;
		if (fish == 361)// tuna
			return 311;
		if (fish == 7944)// monks
			return 303;
		if (fish == 383)// sharks
			return 311;
		if (fish == 389)// mantas
			return 303;
		return -1;
	}

	private boolean canFishOther(int fishType) {
		if (fishType == 335 && player.playerLevel[player.playerFishing] >= 30)
			return true;
		if (fishType == 361 && player.playerLevel[player.playerFishing] >= 50)
			return true;
		return false;
	}

	private int otherFishId(int fishType) {
		if (fishType == 335)
			return SALMON_ID;
		else if (fishType == 361)
			return SWORD_ID;
		return -1;
	}

	private int otherFishXP(int fishType) {
		if (fishType == 335)
			return SALMON_EXP;
		else if (fishType == 361)
			return SWORD_EXP;
		return 0;
	}

	public void resetFishing() {
		this.exp = 0;
		this.fishType = -1;
		this.setEquipmentType(-1);
		this.req = 0;
		player.fishTimer = -1;
		player.fishing = false;
	}

	public int getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(int equipmentType) {
		this.equipmentType = equipmentType;
	}
}