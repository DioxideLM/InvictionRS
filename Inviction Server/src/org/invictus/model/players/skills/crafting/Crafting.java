package org.invictus.model.players.skills.crafting;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Crafting extends SkillHandler {

	Client player;

	public Crafting(Client player) {
		this.player = player;
	}

	public int hideType = 0, makeId = 0, amount = 0, craftType = 0, exp = 0, index = 0;

	public void resetCrafting() {
		hideType = 0;
		makeId = 0;
		amount = 0;
		player.craftingLeather = false;
		craftType = 0;
	}

	public void handleChisel(int id1, int id2) {
		if (id1 == 1755)
			cutGem(id2);
		else
			cutGem(id1);
	}

	public int[][] gems = { { 1623, 1727, 1, 50 }, { 1621, 1729, 27, 68 }, { 1619, 1725, 34, 85 }, { 1617, 1731, 43, 108 }, { 1631, 1712, 55, 138 }, { 6571, 6585, 67, 168 } };

	public void cutGem(int id) {
		for (int j = 0; j < gems.length; j++) {
			if (gems[j][0] == id) {
				if (player.playerLevel[CRAFTING] >= gems[j][2]) {
					player.getItems().deleteItem(id, player.getItems().getItemSlot(id), 1);
					player.getItems().addItem(gems[j][1], 1);
					player.getPlayerAssistant().addSkillXP(gems[j][3] * EXPERIENCE_MULTIPLIER, CRAFTING);
					break;
				}
			}
		}
	}

	public void handleCraftingClick(int clickId) {
		for (int j = 0; j < buttons.length; j++) {
			if (buttons[j][0] == clickId) {
				craftType = buttons[j][1];
				amount = buttons[j][2];
				checkRequirements();
				break;
			}
		}
	}

	public void checkRequirements() {
		for (int j = 0; j < expsAndLevels.length; j++) {
			if (expsAndLevels[j][0] == hideType) {
				if (player.playerLevel[CRAFTING] >= expsAndLevels[j][1]) {
					if (player.getItems().playerHasItem(hideType, 1)) {
						player.getPlayerAssistant().closeAllWindows();
						exp = expsAndLevels[j][2];
						index = j;
						craftHides(hideType);
					}
				} else {
					player.sendMessage("You need a crafting level of " + expsAndLevels[j][1] + " to craft this.");
				}
			}
		}
	}

	public void craftHides(int id) {
		for (int j = 0; j < amount; j++) {
			if (!player.getItems().playerHasItem(id, craftType))
				break;
			player.getItems().deleteItem(id, craftType);
			if (getItemToAdd() <= 0)
				break;
			player.getItems().addItem(getItemToAdd(), 1);
			player.getPlayerAssistant().addSkillXP(exp * EXPERIENCE_MULTIPLIER, CRAFTING);
		}
		resetCrafting();
	}

	public int getItemToAdd() {
		if (craftType == 1) {
			return vambs[index];
		} else if (craftType == 2) {
			return chaps[index];
		} else if (craftType == 3) {
			return bodys[index];
		}
		return -1;
	}

	public int[] vambs = { 1065, 2487, 2489, 2491 };
	public int[] chaps = { 1099, 2493, 2495, 2497 };
	public int[] bodys = { 1135, 2499, 2501, 2503 };

	public void handleLeather(int item1, int item2) {
		if (item1 == 1733) {
			openLeather(item2);
		} else {
			openLeather(item1);
		}
	}

	public int[][] buttons = { { 34185, 1, 1 }, { 34184, 1, 5 }, { 34183, 1, 10 }, { 34182, 1, 27 }, { 34189, 2, 1 }, { 34188, 2, 5 }, { 34187, 2, 10 }, { 34186, 2, 27 }, { 34193, 3, 1 }, { 34192, 3, 5 }, { 34191, 3, 10 }, { 34190, 3, 27 } };

	public int[][] expsAndLevels = { { 1745, 62, 57 }, { 2505, 66, 70 }, { 2507, 73, 78 }, { 2509, 79, 86 } };

	public void openLeather(int item) {
		if (item == 1745) {
			player.getPlayerAssistant().sendChatInterface(8880); // green dhide
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 1099); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 1065); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 1135); // right pic
			player.getPlayerAssistant().sendString("Vambs", 8889);
			player.getPlayerAssistant().sendString("Chaps", 8893);
			player.getPlayerAssistant().sendString("Body", 8897);
			hideType = item;
		} else if (item == 2505) {
			player.getPlayerAssistant().sendChatInterface(8880); // blue
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 2493); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 2487); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 2499); // right pic
			player.getPlayerAssistant().sendString("Vambs", 8889);
			player.getPlayerAssistant().sendString("Chaps", 8893);
			player.getPlayerAssistant().sendString("Body", 8897);
			hideType = item;
		} else if (item == 2507) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 2495); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 2489); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 2501); // right pic
			player.getPlayerAssistant().sendString("Vambs", 8889);
			player.getPlayerAssistant().sendString("Chaps", 8893);
			player.getPlayerAssistant().sendString("Body", 8897);
			hideType = item;
		} else if (item == 2509) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 2497); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 2491); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 2503); // right pic
			player.getPlayerAssistant().sendString("Vambs", 8889);
			player.getPlayerAssistant().sendString("Chaps", 8893);
			player.getPlayerAssistant().sendString("Body", 8897);
			hideType = item;
		}
		player.craftingLeather = true;
	}

}