package org.invictus.model.players.skills.construction;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Construction extends SkillHandler {

	private Client player;

	public Construction(Client player) {
		this.player = player;
	}

	public void quiet() {
		player.sendMessage("Hi");
	}

}
