package org.invictus.model.players.skills.woodcutting;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

/**
 * @Author Sanity
 */

public class Woodcutting extends SkillHandler {

	Client player;

	public Woodcutting(Client player) {
		this.player = player;
	}

	private final int VALID_AXE[] = { 1351, 1349, 1353, 1361, 1355, 1357, 1359, 6739 };
	private final int[] AXE_REQS = { 1, 1, 6, 6, 21, 31, 41, 61 };
	private int logType;
	private int exp;
	private int axeType;
	private final int EMOTE = 875;

	public void startWoodcutting(int logType, int levelReq, int exp) {
		if (goodAxe() > 0) {
			player.turnPlayerTo(player.objectX, player.objectY);
			if (player.playerLevel[player.playerWoodcutting] >= levelReq) {
				this.logType = logType;
				this.exp = exp;
				this.setAxeType(goodAxe());
				player.wcTimer = getWcTimer();
				player.startAnimation(EMOTE);
			} else {
				player.getPlayerAssistant().resetVariables();
				player.startAnimation(65535);
				player.sendMessage("You need a woodcutting level of " + levelReq + " to cut this tree.");
			}
		} else {
			player.startAnimation(65535);
			player.sendMessage("You need an axe to cut this tree.");
			player.getPlayerAssistant().resetVariables();
		}
	}

	public void resetWoodcut() {
		this.logType = -1;
		this.exp = -1;
		this.setAxeType(-1);
		player.wcTimer = -1;
	}

	public void cutWood() {
		if (player.getItems().addItem(logType, 1)) {
			player.startAnimation(EMOTE);
			player.sendMessage("You get some logs.");
			player.getPlayerAssistant().addSkillXP(exp * EXPERIENCE_MULTIPLIER, WOODCUTTING);
			player.getPlayerAssistant().refreshSkill(player.playerWoodcutting);
			player.wcTimer = getWcTimer();
		} else {
			player.getPlayerAssistant().resetVariables();
		}
	}

	public int goodAxe() {
		for (int j = VALID_AXE.length - 1; j >= 0; j--) {
			if (player.playerEquipment[player.playerWeapon] == VALID_AXE[j]) {
				if (player.playerLevel[player.playerWoodcutting] >= AXE_REQS[j])
					return VALID_AXE[j];
			}
		}
		for (int i = 0; i < player.playerItems.length; i++) {
			for (int j = VALID_AXE.length - 1; j >= 0; j--) {
				if (player.playerItems[i] == VALID_AXE[j] + 1) {
					if (player.playerLevel[player.playerWoodcutting] >= AXE_REQS[j])
						return VALID_AXE[j];
				}
			}
		}
		return -1;
	}

	public int getWcTimer() {
		int time = Misc.random(5);
		return time;
	}

	public int getAxeType() {
		return axeType;
	}

	public void setAxeType(int axeType) {
		this.axeType = axeType;
	}

}