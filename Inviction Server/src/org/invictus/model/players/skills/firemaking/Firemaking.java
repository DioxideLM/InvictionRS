package org.invictus.model.players.skills.firemaking;

import org.invictus.Server;
import org.invictus.clip.region.Region;
import org.invictus.event.CycleEvent;
import org.invictus.event.CycleEventContainer;
import org.invictus.event.CycleEventHandler;
import org.invictus.model.items.ItemID;
import org.invictus.model.objects.Object;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.model.players.skills.firemaking.LogData.logData;
import org.invictus.util.Misc;

public class Firemaking extends SkillHandler {

	private Client player;

	public Firemaking(Client player) {
		this.player = player;
	}
	
	public static final int FIRE_OBJECT_ID = 2732;

	public void stopFiremaking() {
		player.startAnimation(65535);
		lastSkillingAction = System.currentTimeMillis();
		isSkilling[FIREMAKING] = false;
	}

	public void attemptFire(final int itemUsed, final int usedWith, final int x, final int y, final boolean groundObject) {
		if (!player.getItems().playerHasItem(ItemID.TINDERBOX)) {
			player.sendMessage("You need a tinderbox to light a fire.");
			return;
		}
		if (isSkilling[FIREMAKING] == true) {
			return;
		}
		for (final logData logs : logData.values()) {
			final int logId = usedWith == ItemID.TINDERBOX ? itemUsed : usedWith;
			if (logId == logs.getLogId()) {
				if (player.playerLevel[11] < logs.getLevel()) {
					player.sendMessage("You need a firemaking level of " + logs.getLevel() + " to light " + player.getItems().getItemName(logId));
					return;
				}
				if (player.inBank()) {
					player.sendMessage("You cannot light a fire in a bank.");
					return;
				}

				if (Server.objectManager.objectExists(player.absX, player.absY)) {
					player.sendMessage("You cannot light a fire here.");
					return;
				}
				isSkilling[FIREMAKING] = true;
				boolean notInstant = (System.currentTimeMillis() - lastSkillingAction) > 2500;
				int cycle = 2;
				if (notInstant) {
					player.sendMessage("You attempt to light a fire.");
					if (groundObject == false) {
						player.getItems().deleteItem(logId, player.getItems().getItemSlot(logId), 1);
						Server.itemHandler.createGroundItem(player, logId, player.absX, player.absY, 1, player.playerId);
					}
					cycle = 3 + Misc.random(6);
				} else {
					if (groundObject == false) {
						player.getItems().deleteItem(logId, player.getItems().getItemSlot(logId), 1);
					}
				}
				final boolean walk;
				if (Region.getClipping((x - 1), y, player.heightLevel, -1, 0)) {
					walk = true;
				} else {
					walk = false;
				}
				player.startAnimation(Animation.LIGHT_FIRE);
				player.getPlayerAssistant().walkTo(walk == true ? -1 : 1, 0);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

					@Override
					public void execute(CycleEventContainer container) {
						if (isSkilling[FIREMAKING] == true) {
							Server.itemHandler.removeGroundItem(player, logId, x, player.absY, false);
							new Object(FIRE_OBJECT_ID, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							player.sendMessage("The fire catches and the log beings to burn.");
							CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

					            @Override
					            public void execute(CycleEventContainer container) {
						            player.turnPlayerTo(walk == true ? (x + 1) : (x - 1), y);
						            container.stop();
					            }

					            @Override
					            public void stop() {

					            }
				            }, 2);
							container.stop();
						} else {
							return;
						}
					}

					@Override
					public void stop() {
						player.startAnimation(65535);
						player.getPlayerAssistant().addSkillXP((int) (logs.getXp()), 11);
						lastSkillingAction = System.currentTimeMillis();
						isSkilling[FIREMAKING] = false;
					}
				}, cycle);
			}
		}
	}
}