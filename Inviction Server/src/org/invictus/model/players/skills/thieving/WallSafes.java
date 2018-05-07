package org.invictus.model.players.skills.thieving;

import org.invictus.model.items.ItemID;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

/**
 * A class for the wall safes in the Rogue's Den.
 * 
 * @author Joe
 *
 */
public class WallSafes extends SkillHandler {

	//TODO: Add hitsplats when failing the safe crack.
	//TODO: Make failure rate dependant on the thieving level.
	//TODO: Add in a rare chance of getting rogue items from stealing.
	//TODO: Make the player face the safe when they crack it.

	private Client player;

	public WallSafes(Client player) {
		this.player = player;
	}

	/**
	 * The ID of the wall safes.
	 */
	public static final int WALL_SAFE_ID = 7236;

	/**
	 * The level requirement for cracking a safe.
	 */
	public static final int SAFE_THIEVING_REQ = 50;

	/**
	 * The method that is called when a safe is clicked.
	 */
	public void crackSafe() {
		if (!inPosition()) {
			return;
		}
		if (meetsLevelRequirement()) {
			if (System.currentTimeMillis() - player.lastThievingAttempt < 2000) {
				return;
			}
			int successRate;
			if (player.getItems().playerHasItem(ItemID.STETHOSCOPE)) {
				successRate = Misc.random(20);
			} else {
				successRate = Misc.random(10);
			}
			if (successRate == 1) {
				safeFail();
			} else {
				safeSuccess();
			}
			player.startAnimation(Animation.PICKPOCKET_ANIMATION);
			player.lastThievingAttempt = System.currentTimeMillis();
		} else {
			player.sendMessage("You need a Thieving level of " + SAFE_THIEVING_REQ + " to crack this safe.");
		}
	}

	/**
	 * A boolean to check if the player is in the co-ordinates where a safe is.
	 */
	public boolean inPosition() {
		if (player.absX == 3057 && player.absY == 4970 || player.absX == 3055 && player.absY == 4970 || player.absX == 3055 && player.absY == 4977 || player.absX == 3057 && player.absY == 4977) {
			return true;
		}
		return false;
	}

	/**
	 * A boolean to check if the player has the requirement to steal from a safe.
	 */
	public boolean meetsLevelRequirement() {
		if (player.playerLevel[THIEVING] >= SAFE_THIEVING_REQ) {
			return true;
		}
		return false;
	}

	/**
	 * A method that occurs when the player has succeeded in cracking the safe.
	 */
	public void safeSuccess() {
		player.getPlayerAssistant().addSkillXP(70 * EXPERIENCE_MULTIPLIER, THIEVING);
		giveLoot();
		player.getThieving().rogueChance();
		player.sendMessage("You successfuly crack the wall safe.");
	}

	/**
	 * A method that occurs when the player has failed the safe cracking.
	 */
	public void safeFail() {
		player.startAnimation(Animation.DAMAGED_ANIMATION);
		player.getPlayerAssistant().doDamage(5);
		player.sendMessage("You fail to crack the safe and trigger a trap!");
	}

	/**
	 * Determines which loot the player will get.
	 */
	public void giveLoot() {
		int random = Misc.random(100);
		int coinAmount = Misc.random(10000);
		if (random >= 0 && random <= 54) { // 55%
			player.getItems().addItem(ItemID.COINS, coinAmount);
			return;
		}
		if (random >= 55 && random <= 75) { // 20%
			player.getItems().addItem(ItemID.UNCUT_SAPPHIRE, 1);
			return;
		}
		if (random >= 76 && random <= 90) { //15%
			player.getItems().addItem(ItemID.UNCUT_EMERALD, 1);
			return;
		}
		if (random >= 91 && random <= 97) { //7%
			player.getItems().addItem(ItemID.UNCUT_RUBY, 1);
			return;
		}
		if (random >= 98 && random <= 100) { //3%
			player.getItems().addItem(ItemID.UNCUT_DIAMOND, 1);
			return;
		}
	}
}
