package org.invictus.model.players.content.minigames.fightcaves;

import org.invictus.Server;
import org.invictus.model.players.Client;

public class FightCavesMisc {

	private Client player;

	public FightCavesMisc(Client player) {
		this.player = player;
	}
	
	public static final int CAVE_EXIT_ID = 9357;
	public static final int CAVE_ENTRY_ID = 1;

	public void resetTzhaar() {
		player.waveId = -1;
		player.tzhaarToKill = -1;
		player.tzhaarKilled = -1;
		player.getPlayerAssistant().movePlayer(2438, 5168, 0);
	}

	public void enterCaves() {
		player.getPlayerAssistant().movePlayer(2413, 5117, player.playerId * 4);
		player.waveId = 0;
		player.tzhaarToKill = -1;
		player.tzhaarKilled = -1;
		Server.fightCaves.spawnNextWave(player);
	}

}
