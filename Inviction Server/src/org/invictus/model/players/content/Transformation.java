package org.invictus.model.players.content;

import org.invictus.model.players.Client;

public class Transformation {

	private Client player;

	public Transformation(Client player) {
		this.player = player;
	}
	
	public void transform(int npcId) {
		player.npcTransformId = npcId;
		player.isNpc = true;
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}
	
	public void ghostTransform() {
		player.isNpc = true;
		player.playerStandIndex = 5538;
		player.playerWalkIndex = 5539;
		player.playerRunIndex = 5539;
		player.playerTurnIndex = 5539;
		player.playerTurn180Index = 5539;
		player.playerTurn90CWIndex = 5539;
		player.playerTurn90CCWIndex = 5539;
		player.npcTransformId = 103;
	}
	
	public void resetTransformation() {
		player.isNpc = false;
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
		player.playerStandIndex = 0x328;
		player.playerTurnIndex = 0x337;
		player.playerWalkIndex = 0x333;
		player.playerTurn180Index = 0x334;
		player.playerTurn90CWIndex = 0x335;
		player.playerTurn90CCWIndex = 0x336;
		player.playerRunIndex = 0x338;
	}

}
