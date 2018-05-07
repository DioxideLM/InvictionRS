package org.invictus.model.players.skills.agility;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Agility extends SkillHandler {

	private Client player;

	public Agility(Client player) {
		this.player = player;
	}

	public static int CLIMB_DOWN_ANIMATION = 827;
	public static int CLIMB_UP_ANIMATION = 828;
	public static int ENTER_PIPE_ANIMATION = 749;
	public static int ROPE_SWING = 751;
	public static int WALL_CLIMB = 758;
	public static int BALANCE_WALK_ANIMATION = 762;
	public static int PIPE_CRAWL_ANIMATION = 844;
	public static int EXIT_PIPE_ANIMATION = 748;

	public boolean doingAgility;

	public void agilityWalk(int walkAnimation, int x, int y) {
		player.isRunning2 = false;
		player.getPlayerAssistant().sendConfig(173, 0);
		player.playerWalkIndex = walkAnimation;
		player.getPlayerAssistant().requestUpdates();
		player.getPlayerAssistant().walkTo(x, y);
	}
	
	public void agilityRun(int runAnimation, int x, int y) {
		player.isRunning2 = true;
		player.getPlayerAssistant().sendConfig(173, 1);
		player.playerRunIndex = runAnimation;
		player.getPlayerAssistant().requestUpdates();
		player.getPlayerAssistant().walkTo(x, y);
	}

	public void resetAgilityWalk() {
		player.isRunning2 = true;
		player.getPlayerAssistant().sendConfig(173, 1);
		player.playerWalkIndex = 0x333;
		player.playerRunIndex = 0x338;
		player.getPlayerAssistant().requestUpdates();
	}

}
