package org.invictus.model.players.skills.fletching;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class Fletching extends SkillHandler {

	Client player;

	public Fletching(Client player) {
		this.player = player;
	}

	int[][] arrows = { { 52, 314, 53, 15, 1 }, { 53, 39, 882, 40, 1 }, { 53, 40, 884, 58, 15 }, { 53, 41, 886, 95, 30 }, { 53, 42, 888, 132, 45 }, { 53, 43, 890, 170, 60 }, { 53, 44, 892, 207, 75 } };

	public void makeArrows(int item1, int item2) {
		for (int j = 0; j < arrows.length; j++) {
			if ((item1 == arrows[j][0] && item2 == arrows[j][1]) || (item2 == arrows[j][0] && item1 == arrows[j][1])) {
				if (player.getItems().playerHasItem(item1, 15) && player.getItems().playerHasItem(item2, 15)) {
					if (player.playerLevel[player.playerFletching] >= arrows[j][4]) {
						player.getItems().deleteItem(item1, player.getItems().getItemSlot(item1), 15);
						player.getItems().deleteItem(item2, player.getItems().getItemSlot(item2), 15);
						player.getItems().addItem(arrows[j][2], 15);
						player.getPlayerAssistant().addSkillXP(arrows[j][3] * EXPERIENCE_MULTIPLIER, FLETCHING);
					} else {
						player.sendMessage("You need a fletching level of " + arrows[j][4] + " to fletch this.");
					}
				} else {
					player.sendMessage("You must have 15 of each supply to do this.");
				}
			}
		}
	}

	public boolean fletching = false;
	int fletchType = 0, amount = 0, log = 0;

	public void handleFletchingClick(int clickId) {
		for (int j = 0; j < buttons.length; j++) {
			if (buttons[j][0] == clickId) {
				fletchType = buttons[j][1];
				amount = buttons[j][2];
				for (int i = 0; i < logType.length; i++)
					if (log == logType[i]) {
						fletchBow(i);
						break;
					}
				break;
			}
		}
	}

	public void fletchBow(int index) {
		int toAdd = getItemToAdd(index);
		int amountToAdd = getAmountToAdd(toAdd);
		for (int j = 0; j < amount; j++) {
			if (player.getItems().playerHasItem(logType[index], 1)) {
				if (player.playerLevel[player.playerFletching] >= reqs[index] || fletchType == 3) {
					player.getItems().deleteItem(logType[index], player.getItems().getItemSlot(logType[index]), 1);
					player.getItems().addItem(toAdd, amountToAdd);
					player.getPlayerAssistant().addSkillXP(getExp(index) * EXPERIENCE_MULTIPLIER, FLETCHING);
				} else {
					player.sendMessage("You need a fletching level of " + reqs[index] + " to fletch this item.");
					break;
				}
			} else {
				break;
			}
		}
	}

	public int getExp(int index) {
		if (fletchType == 3)
			return 5;
		else if (fletchType == 1)
			return exps[index];
		else
			return exps[index] + 8;

	}

	public int getItemToAdd(int index) {
		if (fletchType == 3)
			return shaft;
		else if (fletchType == 1)
			return shortbows[index];
		else if (fletchType == 2)
			return longbows[index];
		return 0;
	}

	public int getAmountToAdd(int id) {
		if (id == 52)
			return 15;
		else
			return 1;
	}

	public int shaft = 52;
	public int[] logType = { 1511, 1521, 1519, 1517, 1515, 1513 };
	public int[] shortbows = { 841, 843, 849, 853, 857, 861 };
	public int[] longbows = { 839, 845, 847, 851, 855, 859 };
	public int[] exps = { 5, 16, 33, 50, 67, 83 };
	public int[] reqs = { 5, 20, 35, 50, 65, 80 };

	public void handleLog(int item1, int item2) {
		if (item1 == 946) {
			openFletching(item2);
		} else {
			openFletching(item1);
		}
	}

	public int[][] buttons = { { 34185, 1, 1 }, { 34184, 1, 5 }, { 34183, 1, 10 }, { 34182, 1, 27 }, { 34189, 2, 1 }, { 34188, 2, 5 }, { 34187, 2, 10 }, { 34186, 2, 27 }, { 34193, 3, 1 }, { 34193, 3, 5 }, { 34193, 3, 10 }, { 34193, 3, 27 } };

	public void openFletching(int item) {
		if (item == 1511) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 839); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 841); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 52); // right pic
			player.getPlayerAssistant().sendString("Shortbow", 8889);
			player.getPlayerAssistant().sendString("Longbow", 8893);
			player.getPlayerAssistant().sendString("Arrow Shafts", 8897);
			log = item;
		} else if (item == 1521) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 845); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 843); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 52); // right pic
			player.getPlayerAssistant().sendString("Oak Shortbow", 8889);
			player.getPlayerAssistant().sendString("Oak Longbow", 8893);
			player.getPlayerAssistant().sendString("Arrow Shafts", 8897);
			log = item;
		} else if (item == 1519) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 847); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 849); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 52); // right pic
			player.getPlayerAssistant().sendString("Willow Shortbow", 8889);
			player.getPlayerAssistant().sendString("Willow Longbow", 8893);
			player.getPlayerAssistant().sendString("Arrow Shafts", 8897);
			log = item;
		} else if (item == 1517) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 851); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 853); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 52); // right pic
			player.getPlayerAssistant().sendString("Maple Shortbow", 8889);
			player.getPlayerAssistant().sendString("Maple Longbow", 8893);
			player.getPlayerAssistant().sendString("Arrow Shafts", 8897);
			log = item;
		} else if (item == 1515) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 855); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 857); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 52); // right pic
			player.getPlayerAssistant().sendString("Yew Shortbow", 8889);
			player.getPlayerAssistant().sendString("Yew Longbow", 8893);
			player.getPlayerAssistant().sendString("Arrow Shafts", 8897);
			log = item;
		} else if (item == 1513) {
			player.getPlayerAssistant().sendChatInterface(8880);
			player.getPlayerAssistant().sendString("What would you like to make?", 8879);
			player.getPlayerAssistant().sendInterfaceItem(8884, 250, 859); // middle
			player.getPlayerAssistant().sendInterfaceItem(8883, 250, 861); // left picture
			player.getPlayerAssistant().sendInterfaceItem(8885, 250, 52); // right pic
			player.getPlayerAssistant().sendString("Magic Shortbow", 8889);
			player.getPlayerAssistant().sendString("Magic Longbow", 8893);
			player.getPlayerAssistant().sendString("Arrow Shafts", 8897);
			log = item;
		}
		fletching = true;
	}
}