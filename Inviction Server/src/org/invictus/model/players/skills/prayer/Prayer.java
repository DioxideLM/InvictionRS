package org.invictus.model.players.skills.prayer;

import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Prayer extends SkillHandler {
	
	//TODO: Add co-ordinate check to ensure players don't packet spoof

	Client player;

	public Prayer(Client player) {
		this.player = player;
	}
	
	public static final int BONES = 526;
	public static final int BURNT_BONES = 528;
	public static final int BAT_BONES = 530;
	public static final int BIG_BONES = 532;
	public static final int BABYDRAGON_BONES = 534;
	public static final int DRAGON_BONES = 536;
	public static final int WOLF_BONES = 2859;
	public static final int SHAIKAHAN_BONES = 3123;
	public static final int JOGRE_BONES = 3125;
	public static final int ZOGRE_BONES = 4812;
	public static final int FAYRG_BONES = 4830;
	public static final int RAURG_BONES = 4832;
	public static final int OURG_BONES = 4834;
	public static final int DAGANNOTH_BONES = 6729;

	public double[][] boneData = { 
			{ BONES, 4.5 }, 
			{ BURNT_BONES, 4.5 },
			{ BAT_BONES, 5.3 },
			{ BIG_BONES, 15 },
			{ BABYDRAGON_BONES, 30 },
			{ DRAGON_BONES, 72 },
			{ WOLF_BONES, 4.5 },
			{ SHAIKAHAN_BONES, 25 },
			{ JOGRE_BONES, 15 },
			{ ZOGRE_BONES, 22.5 },
			{ FAYRG_BONES, 84 },
			{ RAURG_BONES, 96 },
			{ DAGANNOTH_BONES, 125 },
			{ OURG_BONES, 140 },
			};

	public void buryBone(int id, int slot) {
		if (System.currentTimeMillis() - player.buryDelay > 1500) {
			player.getItems().deleteItem(id, slot, 1);
			player.getPlayerAssistant().addSkillXP(getExp(id) * EXPERIENCE_MULTIPLIER, PRAYER);
			player.buryDelay = System.currentTimeMillis();
			player.sendMessage("You bury the bones.");
			player.startAnimation(Animation.BURY_BONES);
		}
	}

	public void bonesOnAltar(int id) {
		player.getItems().deleteItem(id, player.getItems().getItemSlot(id), 1);
		player.getPlayerAssistant().addSkillXP(getExp(id) * 4 * EXPERIENCE_MULTIPLIER, PRAYER);
		player.sendMessage("The gods are pleased with your offering.");
		player.startAnimation(Animation.BONES_ON_ALTAR[0]);
	}

	public boolean isBone(int id) {
		for (int j = 0; j < boneData.length; j++)
			if (boneData[j][0] == id)
				return true;
		return false;
	}

	public double getExp(double id) {
		for (int j = 0; j < boneData.length; j++) {
			if (boneData[j][0] == id)
				return boneData[j][1];
		}
		return 0;
	}
}