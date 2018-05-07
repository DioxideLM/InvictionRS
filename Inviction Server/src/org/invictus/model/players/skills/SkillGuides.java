package org.invictus.model.players.skills;

import org.invictus.model.players.Client;

public class SkillGuides {

	private Client player;

	public SkillGuides(Client player) {
		this.player = player;
	}
	
	public static final int MENU_INTERFACE_ID = 8714;

	public static int[][] guideData = { 
			{ 33206, SkillHandler.ATTACK },
			{ 33209, SkillHandler.STRENGTH},
			{ 33212, SkillHandler.DEFENCE},
			{ 33215, SkillHandler.RANGED},
			{ 33218, SkillHandler.PRAYER},
			{ 33221, SkillHandler.MAGIC},
			{ 33224, SkillHandler.RUNECRAFTING},
			{ 73113, SkillHandler.CONSTRUCTION},
			{ 33207, SkillHandler.HITPOINTS},
			{ 33210, SkillHandler.AGILITY},
			{ 33213, SkillHandler.HERBLORE},
			{ 33216, SkillHandler.THIEVING},
			{ 33219, SkillHandler.CRAFTING},
			{ 33222, SkillHandler.FLETCHING},
			{ 47130, SkillHandler.SLAYER},
			{ 73141, SkillHandler.HUNTER},
			{ 33208, SkillHandler.MINING},
			{ 33211, SkillHandler.SMITHING},
			{ 33214, SkillHandler.FISHING},
			{ 33217, SkillHandler.COOKING},
			{ 33220, SkillHandler.FIREMAKING},
			{ 33223, SkillHandler.WOODCUTTING},
			{ 54104, SkillHandler.FARMING } 
			};

	public void openGuide(int actionButtonId) {
		for (int i = 0; i < guideData.length; i++) {
			if (guideData[i][0] == actionButtonId) {
				player.sendMessage("Skill Guide: " + i);
			}
		}
	}
}