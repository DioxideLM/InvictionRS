package org.invictus.model.players.skills.thieving;

import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

public class Thieving extends SkillHandler {

	private Client player;

	public Thieving(Client player) {
		this.player = player;
	}
	public void rogueChance() {
		
	}

	public void stealFromStall(int id, int xp, int level) {
		if (System.currentTimeMillis() - lastSkillingAction < 2500)
			return;
		if (Misc.random(100) == 0) {
			return;
		}
		if (player.playerLevel[player.playerThieving] >= level) {
			if (player.getItems().addItem(id, 1)) {
				player.startAnimation(Animation.STEAL_FROM_STALL_ANIMATION);
				player.getPlayerAssistant().addSkillXP(xp * EXPERIENCE_MULTIPLIER, THIEVING);
				player.lastThievingAttempt = System.currentTimeMillis();
				player.sendMessage("You steal a " + player.getItems().getItemName(id) + ".");
				rogueChance();
			}
		} else {
			player.sendMessage("You must have a thieving level of " + level + " to thieve from this stall.");
		}
	}
}