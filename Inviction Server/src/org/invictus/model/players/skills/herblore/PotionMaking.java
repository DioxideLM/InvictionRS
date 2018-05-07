package org.invictus.model.players.skills.herblore;

import org.invictus.event.CycleEvent;
import org.invictus.event.CycleEventContainer;
import org.invictus.event.CycleEventHandler;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;

public class PotionMaking extends HerbData {

	private Client player;

	public PotionMaking(Client player) {
		this.player = player;
	}

	public void createUnfinishedPotion(final int itemUsed, final int usedWith) {
		if (isSkilling[15] == true) {
			return;
		}
		for (int i = 0; i < unfinishedPotions.length; i++) {
			final int itemId = (itemUsed == 227 ? usedWith : itemUsed);
			if (itemId == unfinishedPotions[i][1]) {
				if (player.playerLevel[15] < unfinishedPotions[i][2]) {
					player.sendMessage("You need an herblore level of " + unfinishedPotions[i][2] + " to make this potion.");
					return;
				}
				final int product = unfinishedPotions[i][0];
				handlePotionMaking(itemId, 227, product, 0, true);
			}
		}
	}

	public void createPotion(final int itemUsed, final int usedWith) {
		if (isSkilling[15] == true) {
			return;
		}
		for (int i = 0; i < finishedPotions.length; i++) {
			final int primary = (usedWith == finishedPotions[i][1] ? usedWith : itemUsed);
			if (primary == finishedPotions[i][1]) {
				if (player.playerLevel[15] < finishedPotions[i][3]) {
					player.sendMessage("You need an herblore level of " + finishedPotions[i][3] + " to make this potion.");
					return;
				}
				final int product = finishedPotions[i][0];
				final int secondary = finishedPotions[i][2];
				handlePotionMaking(primary, secondary, product, finishedPotions[i][4], false);
			}
		}
	}

	private void handlePotionMaking(final int primary, final int secondary, final int product, final int xp, boolean unfinished) {
		isSkilling[HERBLORE] = true;
		player.stopPlayerSkill = true;
		player.startAnimation(Animation.MAKE_POTION);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (isSkilling[HERBLORE] == true) {
					if (player.getItems().playerHasItem(primary) && player.getItems().playerHasItem(secondary)) {
						player.getItems().deleteItem(primary, 1);
						player.getItems().deleteItem(secondary, 1);
						player.getItems().addItem(product, 1);
						player.getPlayerAssistant().addSkillXP(xp, HERBLORE);
						player.startAnimation(363);
					} else {
						container.stop();
					}
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
				isSkilling[15] = false;
			}
		}, (unfinished ? 1 : 2));
	}
}
