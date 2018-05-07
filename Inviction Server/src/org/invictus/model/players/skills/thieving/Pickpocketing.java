package org.invictus.model.players.skills.thieving;

import org.invictus.model.items.ItemID;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

public class Pickpocketing extends SkillHandler {

	private Client player;

	public Pickpocketing(Client player) {
		this.player = player;
	}
	
	//TODO: Add in more NPC's with fully fleshed out loot tables
	//TODO: Add in a random chance of getting Rogue armour when pickpocketing/stealing from stalls (higher lvl content = greater chance)
	

	public static final int MAN = 1;
	public static final int GUARD = 9;
	public static final int AL_KHARID_WARRIOR = 18;
	public static final int PALADIN = 20;
	public static final int HERO = 21;
	public static final int KNIGHT_OF_ARDOUGNE = 26;

	// npc, level, exp, coin amount
	public int[][] npcThieving = 
		{ 
			{ MAN, 1, 8, 200, 1 }, 
			{ AL_KHARID_WARRIOR, 25, 26, 500, 1 }, 
			{ GUARD, 40, 47, 1000, 2 }, 
			{ KNIGHT_OF_ARDOUGNE, 55, 85, 1500, 3 }, 
			{ PALADIN, 70, 152, 2000, 4 }, 
			{ HERO, 80, 273, 3000, 5 } 
		};

	public void stealFromNPC(int npcId) {
		if (System.currentTimeMillis() - lastSkillingAction < 2000)
			return;
		for (int j = 0; j < npcThieving.length; j++) {
			if (npcThieving[j][0] == npcId) {
				if (player.playerLevel[THIEVING] >= npcThieving[j][1]) {
					if (Misc.random(player.playerLevel[THIEVING] + 2 - npcThieving[j][1]) != 1) {
						player.getPlayerAssistant().addSkillXP(npcThieving[j][2] * EXPERIENCE_MULTIPLIER, THIEVING);
						player.getItems().addItem(ItemID.COINS, npcThieving[j][3]);
						player.startAnimation(Animation.PICKPOCKET_ANIMATION);
						lastSkillingAction = System.currentTimeMillis();
						player.sendMessage("You thieve some money...");
						player.getThieving().rogueChance();
						break;
					} else {
						player.setHitDiff(npcThieving[j][4]);
						player.setHitUpdateRequired(true);
						player.playerLevel[HITPOINTS] -= npcThieving[j][4];
						player.getPlayerAssistant().refreshSkill(HITPOINTS);
						lastSkillingAction = System.currentTimeMillis() + 2000;
						player.sendMessage("You fail to thieve the NPC.");
						break;
					}
				} else {
					player.sendMessage("You need a thieving level of " + npcThieving[j][1] + " to thieve from this NPC.");
				}
			}
		}
	}

}
