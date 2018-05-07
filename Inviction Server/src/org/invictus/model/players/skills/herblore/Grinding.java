package org.invictus.model.players.skills.herblore;

import org.invictus.event.CycleEvent;
import org.invictus.event.CycleEventContainer;
import org.invictus.event.CycleEventHandler;
import org.invictus.model.items.ItemID;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Grinding extends SkillHandler {

	private Client player;

	public Grinding(Client player) {
		this.player = player;
	}

	//TODO: Using a Pestle and Mortar on any item plays the animation

	private final static int[][] GRINDABLES = { { 237, 235 }, { 1973, 1975 }, { 5075, 6693 } };

	public void grindItem(final int itemUsed, final int usedWith, final int itemSlot) {
		if (isSkilling[HERBLORE] == true) {
			return;
		}
		player.startAnimation(Animation.GRIND_INGREDIENTS);
		final int itemId = (itemUsed == ItemID.PESTLE_AND_MORTAR ? usedWith : itemUsed);
		for (int i = 0; i < GRINDABLES.length; i++) {
			if (itemId == GRINDABLES[i][0]) {
				isSkilling[HERBLORE] = true;
				final int product = GRINDABLES[i][1];
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

					@Override
					public void execute(CycleEventContainer container) {
						if (isSkilling[HERBLORE] == true) {
							player.getItems().deleteItem(itemId, itemSlot, 1);
							player.getItems().addItem(product, 1);
							player.sendMessage("You grind the " + player.getItems().getItemName(itemId) + " into " + player.getItems().getItemName(product) + ".");
							container.stop();
						}
					}

					@Override
					public void stop() {
						isSkilling[15] = false;
					}
				}, 3);
			}
		}
	}
}