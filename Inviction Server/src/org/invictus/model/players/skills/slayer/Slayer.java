package org.invictus.model.players.skills.slayer;

import org.invictus.Server;
import org.invictus.model.players.Client;
import org.invictus.util.Misc;

/**
 * Slayer.java
 * 
 * @author Sanity
 * 
 **/

public class Slayer {

	private Client player;

	public Slayer(Client player) {
		this.player = player;
	}

	public int[] lowTasks = { 1648, 1612, 117, 1265, 90, 103, 78, 119, 18, 101, 1265, 181 };
	public int[] lowReqs = { 5, 15, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	public int[] medTasks = { 1643, 1618, 941, 119, 82, 52, 1612, 117, 1265, 112, 125, 1341 };
	public int[] medReqs = { 45, 50, 1, 1, 1, 1, 15, 1, 1, 1, 1, 1 };
	public int[] highTasks = { 1624, 1610, 1613, 1615, 55, 84, 49, 1618, 941, 82, 2783, 1341 };
	public int[] highReqs = { 65, 75, 80, 85, 1, 1, 1, 50, 1, 1, 90, 1 };

	public void giveTask() {
		if (player.combatLevel < 50)
			giveTask(1);
		else if (player.combatLevel >= 50 && player.combatLevel <= 90)
			giveTask(2);
		else if (player.combatLevel > 90 && player.combatLevel <= 126)
			giveTask(3);
		else
			giveTask(2);
	}

	public void checkTask() {
		if (player.slayerTask != 0) {
			player.forcedText = "I must slay another " + player.taskAmount + " " + Server.npcHandler.getNpcListName(player.slayerTask) + ".";
		} else {
			player.forcedText = "I don't currently have a Slayer task.";	
		}
		player.forcedChatUpdateRequired = true;
		player.updateRequired = true;
	}

	public void giveTask2() {
		for (int j = 0; j < lowTasks.length; j++) {
			if (lowTasks[j] == player.slayerTask) {
				player.sendMessage("You already have an easy task... to kill " + player.taskAmount + " " + Server.npcHandler.getNpcListName(player.slayerTask) + ".");
				return;
			}
		}
		giveTask(1);
	}

	public void giveTask(int taskLevel) {
		int given = 0;
		int random = 0;
		if (taskLevel == 1) {
			random = (int) (Math.random() * (lowTasks.length - 1));
			given = lowTasks[random];
		} else if (taskLevel == 2) {
			random = (int) (Math.random() * (medTasks.length - 1));
			given = medTasks[random];
		} else if (taskLevel == 3) {
			random = (int) (Math.random() * (highTasks.length - 1));
			given = highTasks[random];
		}
		if (!canDoTask(taskLevel, random)) {
			giveTask(taskLevel);
			return;
		}
		player.slayerTask = given;
		player.taskAmount = Misc.random(15) + 15;
		player.sendMessage("You have been assigned to kill " + player.taskAmount + " " + Server.npcHandler.getNpcListName(given) + " as a slayer task.");
	}

	public boolean canDoTask(int taskLevel, int random) {
		if (taskLevel == 1) {
			return player.playerLevel[player.playerSlayer] >= lowReqs[random];
		} else if (taskLevel == 2) {
			return player.playerLevel[player.playerSlayer] >= medReqs[random];
		} else if (taskLevel == 3) {
			return player.playerLevel[player.playerSlayer] >= highReqs[random];
		}
		return false;
	}
}