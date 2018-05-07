package org.invictus.model.players.skills.magic;

import org.invictus.model.items.ItemID;
import org.invictus.model.players.Client;

public class LunarSpellbook {

	private Client c;

	public LunarSpellbook(Client c) {
		this.c = c;
	}
	
	public static final int VENGEANCE_BUTTON_ID = 118098;

	public void castVengeance() {
		if (!c.getItems().playerHasItem(ItemID.ASTRAL_RUNE, 4) || !c.getItems().playerHasItem(ItemID.EARTH_RUNE, 10) || !c.getItems().playerHasItem(ItemID.DEATH_RUNE, 2)) {
			c.sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		if (System.currentTimeMillis() - c.lastCast < 30000) {
			c.sendMessage("You can only cast vengeance every 30 seconds.");
			return;
		}
		if (c.vengOn) {
			c.sendMessage("You already have vengeance casted.");
			return;
		}
		c.startAnimation(905);
		c.gfx100(666);
		c.getItems().deleteItem2(9075, 4);
		c.getItems().deleteItem2(557, 10);
		c.getItems().deleteItem2(560, 2);
		c.getPlayerAssistant().addSkillXP(112, 6);
		c.getPlayerAssistant().refreshSkill(6);
		c.vengOn = true;
		c.lastCast = System.currentTimeMillis();
	}

}
