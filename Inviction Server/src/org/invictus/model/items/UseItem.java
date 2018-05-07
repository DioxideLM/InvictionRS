package org.invictus.model.items;

import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

public class UseItem {

	public static void ItemonObject(Client player, int objectID, int objectX, int objectY, int itemId) {
		if (!player.getItems().playerHasItem(itemId, 1))
			return;
		switch (objectID) {
		case 2783:
			player.getSmithingInt().showSmithInterface(itemId);
			break;
		case 8151:
		case 8389:
			player.getFarming().checkItemOnObject(itemId);
			break;
		case 2728:
		case 12269:
			player.getCooking().itemOnObject(itemId);
			break;
		case 409:
			if (player.getPrayer().isBone(itemId)) {
				player.getPrayer().bonesOnAltar(itemId);
				player.getPlayerAssistant().stillGfx(Animation.BONES_ON_ALTAR[1], objectX, objectY, player.heightLevel, 0);
			}
			break;
		default:
			if (player.playerRights == 3) {
				Misc.println("Player At Object id: " + objectID + " with Item id: " + itemId);
			}
			player.sendMessage("Nothing interesting happens.");
			break;
		}

	}

	public static void ItemonItem(Client player, int itemUsed, int useWith, int usedWithSlot, int itemUsedSlot) {
		player.getPotionMaking().createPotion(itemUsed, useWith);
		if (itemUsed == ItemID.VIAL_OF_WATER || useWith == ItemID.VIAL_OF_WATER) {
			player.getPotionMaking().createUnfinishedPotion(itemUsed, useWith);
		}
		if (itemUsed == ItemID.PESTLE_AND_MORTAR || useWith == ItemID.PESTLE_AND_MORTAR) {
			player.getGrinding().grindItem(itemUsed, useWith, (itemUsed == 233 ? usedWithSlot : itemUsedSlot));
		}
		if (player.getItems().getItemName(itemUsed).contains("(") && player.getItems().getItemName(useWith).contains("("))
			player.getPotMixing().mixPotion2(itemUsed, useWith);
		if (itemUsed == 1733 || useWith == 1733)
			player.getCrafting().handleLeather(itemUsed, useWith);
		if (itemUsed == 1755 || useWith == 1755)
			player.getCrafting().handleChisel(itemUsed, useWith);
		if (itemUsed == 946 || useWith == 946)
			player.getFletching().handleLog(itemUsed, useWith);
		if (itemUsed == 53 || useWith == 53 || itemUsed == 52 || useWith == 52)
			player.getFletching().makeArrows(itemUsed, useWith);
		if ((itemUsed == 1540 && useWith == 11286) || (itemUsed == 11286 && useWith == 1540)) {
			if (player.playerLevel[player.playerSmithing] >= 95) {
				player.getItems().deleteItem(1540, player.getItems().getItemSlot(1540), 1);
				player.getItems().deleteItem(11286, player.getItems().getItemSlot(11286), 1);
				player.getItems().addItem(11284, 1);
				player.sendMessage("You combine the two materials to create a dragonfire shield.");
				player.getPlayerAssistant().addSkillXP(500 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerSmithing);
			} else {
				player.sendMessage("You need a smithing level of 95 to create a dragonfire shield.");
			}
		}
		if (itemUsed == 9142 && useWith == 9190 || itemUsed == 9190 && useWith == 9142) {
			if (player.playerLevel[player.playerFletching] >= 58) {
				int boltsMade = player.getItems().getItemAmount(itemUsed) > player.getItems().getItemAmount(useWith) ? player.getItems().getItemAmount(useWith) : player.getItems().getItemAmount(itemUsed);
				player.getItems().deleteItem(useWith, player.getItems().getItemSlot(useWith), boltsMade);
				player.getItems().deleteItem(itemUsed, player.getItems().getItemSlot(itemUsed), boltsMade);
				player.getItems().addItem(9241, boltsMade);
				player.getPlayerAssistant().addSkillXP(boltsMade * 6 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9191 || itemUsed == 9191 && useWith == 9143) {
			if (player.playerLevel[player.playerFletching] >= 63) {
				int boltsMade = player.getItems().getItemAmount(itemUsed) > player.getItems().getItemAmount(useWith) ? player.getItems().getItemAmount(useWith) : player.getItems().getItemAmount(itemUsed);
				player.getItems().deleteItem(useWith, player.getItems().getItemSlot(useWith), boltsMade);
				player.getItems().deleteItem(itemUsed, player.getItems().getItemSlot(itemUsed), boltsMade);
				player.getItems().addItem(9242, boltsMade);
				player.getPlayerAssistant().addSkillXP(boltsMade * 7 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9192 || itemUsed == 9192 && useWith == 9143) {
			if (player.playerLevel[player.playerFletching] >= 65) {
				int boltsMade = player.getItems().getItemAmount(itemUsed) > player.getItems().getItemAmount(useWith) ? player.getItems().getItemAmount(useWith) : player.getItems().getItemAmount(itemUsed);
				player.getItems().deleteItem(useWith, player.getItems().getItemSlot(useWith), boltsMade);
				player.getItems().deleteItem(itemUsed, player.getItems().getItemSlot(itemUsed), boltsMade);
				player.getItems().addItem(9243, boltsMade);
				player.getPlayerAssistant().addSkillXP(boltsMade * 7 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9193 || itemUsed == 9193 && useWith == 9144) {
			if (player.playerLevel[player.playerFletching] >= 71) {
				int boltsMade = player.getItems().getItemAmount(itemUsed) > player.getItems().getItemAmount(useWith) ? player.getItems().getItemAmount(useWith) : player.getItems().getItemAmount(itemUsed);
				player.getItems().deleteItem(useWith, player.getItems().getItemSlot(useWith), boltsMade);
				player.getItems().deleteItem(itemUsed, player.getItems().getItemSlot(itemUsed), boltsMade);
				player.getItems().addItem(9244, boltsMade);
				player.getPlayerAssistant().addSkillXP(boltsMade * 10 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9194 || itemUsed == 9194 && useWith == 9144) {
			if (player.playerLevel[player.playerFletching] >= 58) {
				int boltsMade = player.getItems().getItemAmount(itemUsed) > player.getItems().getItemAmount(useWith) ? player.getItems().getItemAmount(useWith) : player.getItems().getItemAmount(itemUsed);
				player.getItems().deleteItem(useWith, player.getItems().getItemSlot(useWith), boltsMade);
				player.getItems().deleteItem(itemUsed, player.getItems().getItemSlot(itemUsed), boltsMade);
				player.getItems().addItem(9245, boltsMade);
				player.getPlayerAssistant().addSkillXP(boltsMade * 13 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 1601 && useWith == 1755 || itemUsed == 1755 && useWith == 1601) {
			if (player.playerLevel[player.playerFletching] >= 63) {
				player.getItems().deleteItem(1601, player.getItems().getItemSlot(1601), 1);
				player.getItems().addItem(9192, 15);
				player.getPlayerAssistant().addSkillXP(8 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 1607 && useWith == 1755 || itemUsed == 1755 && useWith == 1607) {
			if (player.playerLevel[player.playerFletching] >= 65) {
				player.getItems().deleteItem(1607, player.getItems().getItemSlot(1607), 1);
				player.getItems().addItem(9189, 15);
				player.getPlayerAssistant().addSkillXP(8 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 1605 && useWith == 1755 || itemUsed == 1755 && useWith == 1605) {
			if (player.playerLevel[player.playerFletching] >= 71) {
				player.getItems().deleteItem(1605, player.getItems().getItemSlot(1605), 1);
				player.getItems().addItem(9190, 15);
				player.getPlayerAssistant().addSkillXP(8 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 1603 && useWith == 1755 || itemUsed == 1755 && useWith == 1603) {
			if (player.playerLevel[player.playerFletching] >= 73) {
				player.getItems().deleteItem(1603, player.getItems().getItemSlot(1603), 1);
				player.getItems().addItem(9191, 15);
				player.getPlayerAssistant().addSkillXP(8 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed == 1615 && useWith == 1755 || itemUsed == 1755 && useWith == 1615) {
			if (player.playerLevel[player.playerFletching] >= 73) {
				player.getItems().deleteItem(1615, player.getItems().getItemSlot(1615), 1);
				player.getItems().addItem(9193, 15);
				player.getPlayerAssistant().addSkillXP(8 * SkillHandler.EXPERIENCE_MULTIPLIER, player.playerFletching);
			} else {
				player.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710 && useWith <= 11714) {
			if (player.getItems().hasAllShards()) {
				player.getItems().makeBlade();
			}
		}
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366 && useWith == 2368) {
			player.getItems().deleteItem(2368, player.getItems().getItemSlot(2368), 1);
			player.getItems().deleteItem(2366, player.getItems().getItemSlot(2366), 1);
			player.getItems().addItem(1187, 1);
		}

		if (itemUsed == ItemID.TINDERBOX || useWith == ItemID.TINDERBOX) {
			player.getFiremaking().attemptFire(itemUsed, useWith, player.absX, player.absY, false);
		}

		if (player.getItems().isHilt(itemUsed) || player.getItems().isHilt(useWith)) {
			int hilt = player.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = player.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				player.getItems().makeGodsword(hilt);
			}
		}

		switch (itemUsed) {

		default:
			if (player.playerRights == 3)
				Misc.println("Player used Item id: " + itemUsed + " with Item id: " + useWith);
			break;
		}
	}

	public static void ItemonNpc(Client player, int itemId, int npcId, int slot) {
		switch (itemId) {

		default:
			if (player.playerRights == 3)
				Misc.println("Player used Item id: " + itemId + " with Npc id: " + npcId + " With Slot : " + slot);
			break;
		}

	}

}
