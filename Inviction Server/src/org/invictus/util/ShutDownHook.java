package org.invictus.util;

import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;

public class ShutDownHook extends Thread {

	@Override
	public void run() {
		System.out.println("Shutdown thread run.");
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				org.invictus.model.players.PlayerSave.saveGame(c);
			}
		}
		System.out.println("Shutting down...");
	}

}