package org.invictus.model.players.content;

import org.invictus.model.players.Client;

public class Resting {

	Client player;

	public Resting(Client player) {
		this.player = player;
	}

	public static final int REST_BEGIN_ANIM = 11786;
	public static final int REST_PROCESS_ANIM = 11787;
	public static final int REST_END_ANIM = 11788;
	public static final int REST_BUTTON_ID = 153;

	public void sitDown() {
		if (player.isResting) {
			getUp();
			return;
		}
		if (!player.isResting) {
			player.isResting = true;
			player.getPlayerAssistant().sendConfig(1900, 1);
			player.startAnimation(11786);
			player.sendMessage("You sit down and take a rest.");
			player.setAppearanceUpdateRequired(true);
			player.playerStandIndex = 11787;
		}
	}

	public void getUp() {
		player.isResting = false;
		player.getPlayerAssistant().sendConfig(1900, 0);
		player.setAppearanceUpdateRequired(true);
		player.getPlayerAssistant().resetAnimation();
		player.startAnimation(11788);
		player.sendMessage("You stand up.");
	}

}