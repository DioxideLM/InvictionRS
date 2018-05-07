package org.invictus.model.items;

import org.invictus.Server;
import org.invictus.model.npcs.NPCHandler;
import org.invictus.model.players.Banking;
import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.util.Misc;

public class ItemAssistant {

	private Client player;

	public ItemAssistant(Client player) {
		this.player = player;
	}

	/**
	 * Items
	 **/

	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 }, { 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 }, { 4720, 4896 }, { 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 }, { 4734, 4938 }, { 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 }, { 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 }, { 4745, 4956 }, { 4747, 4926 }, { 4749, 4968 }, { 4751, 4794 }, { 4753, 4980 }, { 4755, 4986 }, { 4757, 4992 }, { 4759, 4998 } };

	public void resetItems(int WriteFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(WriteFrame);
			player.getOutStream().writeWord(player.playerItems.length);
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItemsN[i] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(player.playerItemsN[i]);
				} else {
					player.getOutStream().writeByte(player.playerItemsN[i]);
				}
				player.getOutStream().writeWordBigEndianA(player.playerItems[i]);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public int getItemCount(int itemID) {
		int count = 0;
		for (int j = 0; j < player.playerItems.length; j++) {
			if (player.playerItems[j] == itemID + 1) {
				count += player.playerItemsN[j];
			}
		}
		return count;
	}

	public void writeBonus() {
		int offset = 0;
		String send = "";
		for (int i = 0; i < player.playerBonus.length; i++) {
			if (player.playerBonus[i] >= 0) {
				send = BONUS_NAMES[i] + ": +" + player.playerBonus[i];
			} else {
				send = BONUS_NAMES[i] + ": -" + java.lang.Math.abs(player.playerBonus[i]);
			}

			if (i == 10) {
				offset = 1;
			}
			player.getPlayerAssistant().sendString(send, (1675 + i + offset));
		}

	}

	public void sendItemsKept() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6963);
			player.getOutStream().writeWord(player.itemKeptId.length);
			for (int i = 0; i < player.itemKeptId.length; i++) {
				if (player.playerItemsN[i] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(1);
				} else {
					player.getOutStream().writeByte(1);
				}
				if (player.itemKeptId[i] > 0) {
					player.getOutStream().writeWordBigEndianA(player.itemKeptId[i] + 1);
				} else {
					player.getOutStream().writeWordBigEndianA(0);
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	/**
	 * Item kept on death
	 **/

	public void keepItem(int keepItem, boolean deleteItem) {
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] - 1 > 0) {
				int inventoryItemValue = player.getShops().getItemShopValue(player.playerItems[i] - 1);
				if (inventoryItemValue > value && (!player.invSlot[i])) {
					value = inventoryItemValue;
					item = player.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;
				}
			}
		}
		for (int i1 = 0; i1 < player.playerEquipment.length; i1++) {
			if (player.playerEquipment[i1] > 0) {
				int equipmentItemValue = player.getShops().getItemShopValue(player.playerEquipment[i1]);
				if (equipmentItemValue > value && (!player.equipSlot[i1])) {
					value = equipmentItemValue;
					item = player.playerEquipment[i1];
					slotId = i1;
					itemInInventory = false;
				}
			}
		}
		if (itemInInventory) {
			player.invSlot[slotId] = true;
			if (deleteItem) {
				deleteItem(player.playerItems[slotId] - 1, getItemSlot(player.playerItems[slotId] - 1), 1);
			}
		} else {
			player.equipSlot[slotId] = true;
			if (deleteItem) {
				deleteEquipment(item, slotId);
			}
		}
		player.itemKeptId[keepItem] = item;
	}

	/**
	 * Reset items kept on death
	 **/

	public void resetKeepItems() {
		for (int i = 0; i < player.itemKeptId.length; i++) {
			player.itemKeptId[i] = -1;
		}
		for (int i1 = 0; i1 < player.invSlot.length; i1++) {
			player.invSlot[i1] = false;
		}
		for (int i2 = 0; i2 < player.equipSlot.length; i2++) {
			player.equipSlot[i2] = false;
		}
	}

	/**
	 * Shows the interface for destroying undroppable items.
	 * 
	 * @param itemId
	 */

	public void destroyItemInterface(int itemId) {
		itemId = player.droppedItem;
		String itemName = player.getItems().getItemName(player.droppedItem);
		String[][] info = { { "Are you sure you want to drop this item?", "14174" }, { "Yes.", "14175" }, { "No.", "14176" }, { "", "14177" }, { "This item is valuable, you will not", "14182" }, { "get it back once clicked Yes.", "14183" }, { itemName, "14184" } };
		player.getPlayerAssistant().sendUpdateSingleItem(itemId, 0, 14171, 1);
		for (int i = 0; i < info.length; i++) {
			player.getPlayerAssistant().sendString(info[i][0], Integer.parseInt(info[i][1]));
			player.getPlayerAssistant().sendChatInterface(14170);
		}
	}

	/**
	 * A method for completely removing destroyable items from the game.
	 * 
	 * @param itemId
	 */

	public void destroyItem(int itemId) {
		itemId = player.droppedItem;
		String itemName = player.getItems().getItemName(itemId);
		player.getItems().deleteItem(itemId, player.getItems().getItemSlot(itemId), player.playerItemsN[player.getItems().getItemSlot(itemId)]);
		player.sendMessage("Your " + itemName + " vanishes as you drop it on the ground.");
		player.getPlayerAssistant().removeAllWindows();
	}

	/**
	 * delete all items
	 **/

	public void deleteAllItems() {
		for (int i1 = 0; i1 < player.playerEquipment.length; i1++) {
			deleteEquipment(player.playerEquipment[i1], i1);
		}
		for (int i = 0; i < player.playerItems.length; i++) {
			deleteItem(player.playerItems[i] - 1, getItemSlot(player.playerItems[i] - 1), player.playerItemsN[i]);
		}
	}

	/**
	 * Drop all items for your killer
	 **/

	public void dropAllItems() {
		Client o = (Client) PlayerHandler.players[player.killerId];

		for (int i = 0; i < player.playerItems.length; i++) {
			if (o != null) {
				if (tradeable(player.playerItems[i] - 1)) {
					Server.itemHandler.createGroundItem(o, player.playerItems[i] - 1, player.getX(), player.getY(), player.playerItemsN[i], player.killerId);
				} else {
					if (specialCase(player.playerItems[i] - 1))
						Server.itemHandler.createGroundItem(o, 995, player.getX(), player.getY(), getUntradePrice(player.playerItems[i] - 1), player.killerId);
					Server.itemHandler.createGroundItem(player, player.playerItems[i] - 1, player.getX(), player.getY(), player.playerItemsN[i], player.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(player, player.playerItems[i] - 1, player.getX(), player.getY(), player.playerItemsN[i], player.playerId);
			}
		}
		for (int e = 0; e < player.playerEquipment.length; e++) {
			if (o != null) {
				if (tradeable(player.playerEquipment[e])) {
					Server.itemHandler.createGroundItem(o, player.playerEquipment[e], player.getX(), player.getY(), player.playerEquipmentN[e], player.killerId);
				} else {
					if (specialCase(player.playerEquipment[e]))
						Server.itemHandler.createGroundItem(o, 995, player.getX(), player.getY(), getUntradePrice(player.playerEquipment[e]), player.killerId);
					Server.itemHandler.createGroundItem(player, player.playerEquipment[e], player.getX(), player.getY(), player.playerEquipmentN[e], player.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(player, player.playerEquipment[e], player.getX(), player.getY(), player.playerEquipmentN[e], player.playerId);
			}
		}
		if (o != null) {
			Server.itemHandler.createGroundItem(o, 526, player.getX(), player.getY(), 1, player.killerId);
		}
	}

	public int getUntradePrice(int item) {
		switch (item) {
		case 2518:
		case 2524:
		case 2526:
			return 100000;
		case 2520:
		case 2522:
			return 150000;
		}
		return 0;
	}

	public boolean specialCase(int itemId) {
		switch (itemId) {
		case 2518:
		case 2520:
		case 2522:
		case 2524:
		case 2526:
			return true;
		}
		return false;
	}

	public void handleSpecialPickup(int itemId) {
		player.sendMessage("My " + getItemName(itemId) + " has been recovered. I should talk to the void knights to get it back.");
		player.getItems().specialItemPickup(itemId);
	}

	/**
	 * Deletes an item when you attempt to pick it up, and increases the value of said item.
	 * 
	 * @param itemId
	 */

	public void specialItemPickup(int itemId) {
		switch (itemId) {
		case 2518:
			player.itemPickups[0]++;
			break;
		case 2520:
			player.itemPickups[1]++;
			break;
		case 2522:
			player.itemPickups[2]++;
			break;
		case 2524:
			player.itemPickups[3]++;
			break;
		case 2526:
			player.itemPickups[4]++;
			break;
		}
	}

	public boolean tradeable(int itemId) {
		for (int j = 0; j < ItemConfiguration.ITEMS_TRADEABLE.length; j++) {
			if (itemId == ItemConfiguration.ITEMS_TRADEABLE[j])
				return false;
		}
		return true;
	}

	/**
	 * Add Item
	 **/
	public boolean addItem(int item, int amount) {
		if (amount < 1) {
			amount = 1;
		}
		if (item <= 0) {
			return false;
		}
		if ((((freeSlots() >= 1) || playerHasItem(item, 1)) && Item.itemStackable[item]) || ((freeSlots() > 0) && !Item.itemStackable[item])) {
			for (int i = 0; i < player.playerItems.length; i++) {
				if ((player.playerItems[i] == (item + 1)) && Item.itemStackable[item] && (player.playerItems[i] > 0)) {
					player.playerItems[i] = (item + 1);
					if (((player.playerItemsN[i] + amount) < Integer.MAX_VALUE) && ((player.playerItemsN[i] + amount) > -1)) {
						player.playerItemsN[i] += amount;
					} else {
						player.playerItemsN[i] = Integer.MAX_VALUE;
					}
					if (player.getOutStream() != null && player != null) {
						player.getOutStream().createFrameVarSizeWord(34);
						player.getOutStream().writeWord(3214);
						player.getOutStream().writeByte(i);
						player.getOutStream().writeWord(player.playerItems[i]);
						if (player.playerItemsN[i] > 254) {
							player.getOutStream().writeByte(255);
							player.getOutStream().writeDWord(player.playerItemsN[i]);
						} else {
							player.getOutStream().writeByte(player.playerItemsN[i]);
						}
						player.getOutStream().endFrameVarSizeWord();
						player.flushOutStream();
					}
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] <= 0) {
					player.playerItems[i] = item + 1;
					if ((amount < Integer.MAX_VALUE) && (amount > -1)) {
						player.playerItemsN[i] = 1;
						if (amount > 1) {
							player.getItems().addItem(item, amount - 1);
							return true;
						}
					} else {
						player.playerItemsN[i] = Integer.MAX_VALUE;
					}
					resetItems(3214);
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			resetItems(3214);
			player.sendMessage("Not enough space in your inventory.");
			return false;
		}
	}

	public String itemType(int item) {
		if (Item.playerCape(item)) {
			return "cape";
		}
		if (Item.playerBoots(item)) {
			return "boots";
		}
		if (Item.playerGloves(item)) {
			return "gloves";
		}
		if (Item.playerShield(item)) {
			return "shield";
		}
		if (Item.playerAmulet(item)) {
			return "amulet";
		}
		if (Item.playerArrows(item)) {
			return "arrows";
		}
		if (Item.playerRings(item)) {
			return "ring";
		}
		if (Item.playerHats(item)) {
			return "hat";
		}
		if (Item.playerLegs(item)) {
			return "legs";
		}
		if (Item.playerBody(item)) {
			return "body";
		}
		return "weapon";
	}

	/**
	 * Bonuses
	 **/

	public final String[] BONUS_NAMES = { "Stab", "Slash", "Crush", "Magic", "Range", "Stab", "Slash", "Crush", "Magic", "Range", "Strength", "Prayer" };

	public void resetBonus() {
		for (int i = 0; i < player.playerBonus.length; i++) {
			player.playerBonus[i] = 0;
		}
	}

	public void getBonus() {
		for (int i = 0; i < player.playerEquipment.length; i++) {
			if (player.playerEquipment[i] > -1) {
				for (int j = 0; j < ItemConfiguration.ITEM_AMOUNT; j++) {
					if (Server.itemHandler.ItemList[j] != null) {
						if (Server.itemHandler.ItemList[j].itemId == player.playerEquipment[i]) {
							for (int k = 0; k < player.playerBonus.length; k++) {
								player.playerBonus[k] += Server.itemHandler.ItemList[j].Bonuses[k];
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Wear Item
	 **/

	public void sendWeapon(int Weapon, String WeaponName) {
		String WeaponName2 = WeaponName.replaceAll("Bronze", "");
		WeaponName2 = WeaponName2.replaceAll("Iron", "");
		WeaponName2 = WeaponName2.replaceAll("Steel", "");
		WeaponName2 = WeaponName2.replaceAll("Black", "");
		WeaponName2 = WeaponName2.replaceAll("Mithril", "");
		WeaponName2 = WeaponName2.replaceAll("Adamant", "");
		WeaponName2 = WeaponName2.replaceAll("Rune", "");
		WeaponName2 = WeaponName2.replaceAll("Granite", "");
		WeaponName2 = WeaponName2.replaceAll("Dragon", "");
		WeaponName2 = WeaponName2.replaceAll("Drag", "");
		WeaponName2 = WeaponName2.replaceAll("Crystal", "");
		WeaponName2 = WeaponName2.trim();
		if (WeaponName.equals("Unarmed")) {
			player.setSidebarInterface(0, 5855); // punch, kick, block
			player.getPlayerAssistant().sendString(WeaponName, 5857);
		} else if (WeaponName.endsWith("whip")) {
			player.setSidebarInterface(0, 12290); // flick, lash, deflect
			player.getPlayerAssistant().sendInterfaceItem(12291, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 12293);
		} else if (WeaponName.endsWith("bow") || WeaponName.endsWith("10") || WeaponName.endsWith("full") || WeaponName.startsWith("seercull")) {
			player.setSidebarInterface(0, 1764); // accurate, rapid, longrange
			player.getPlayerAssistant().sendInterfaceItem(1765, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 1767);
		} else if (WeaponName.startsWith("Staff") || WeaponName.endsWith("staff") || WeaponName.endsWith("wand")) {
			player.setSidebarInterface(0, 328); // spike, impale, smash, block
			player.getPlayerAssistant().sendInterfaceItem(329, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 331);
		} else if (WeaponName2.startsWith("dart") || WeaponName2.startsWith("knife") || WeaponName2.startsWith("javelin") || WeaponName.equalsIgnoreCase("toktz-xil-ul")) {
			player.setSidebarInterface(0, 4446); // accurate, rapid, longrange
			player.getPlayerAssistant().sendInterfaceItem(4447, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 4449);
		} else if (WeaponName2.startsWith("dagger") || WeaponName2.contains("sword")) {
			player.setSidebarInterface(0, 2276); // stab, lunge, slash, block
			player.getPlayerAssistant().sendInterfaceItem(2277, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 2279);
		} else if (WeaponName2.startsWith("pickaxe")) {
			player.setSidebarInterface(0, 5570); // spike, impale, smash, block
			player.getPlayerAssistant().sendInterfaceItem(5571, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 5573);
		} else if (WeaponName2.startsWith("axe") || WeaponName2.startsWith("battleaxe")) {
			player.setSidebarInterface(0, 1698); // chop, hack, smash, block
			player.getPlayerAssistant().sendInterfaceItem(1699, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 1701);
		} else if (WeaponName2.startsWith("halberd")) {
			player.setSidebarInterface(0, 8460); // jab, swipe, fend
			player.getPlayerAssistant().sendInterfaceItem(8461, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 8463);
		} else if (WeaponName2.startsWith("Scythe")) {
			player.setSidebarInterface(0, 8460); // jab, swipe, fend
			player.getPlayerAssistant().sendInterfaceItem(8461, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 8463);
		} else if (WeaponName2.startsWith("spear")) {
			player.setSidebarInterface(0, 4679); // lunge, swipe, pound, block
			player.getPlayerAssistant().sendInterfaceItem(4680, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 4682);
		} else if (WeaponName2.toLowerCase().contains("mace")) {
			player.setSidebarInterface(0, 3796);
			player.getPlayerAssistant().sendInterfaceItem(3797, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 3799);

		} else if (player.playerEquipment[player.playerWeapon] == 4153) {
			player.setSidebarInterface(0, 425); // war hamer equip.
			player.getPlayerAssistant().sendInterfaceItem(426, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 428);
		} else {
			player.setSidebarInterface(0, 2423); // chop, slash, lunge, block
			player.getPlayerAssistant().sendInterfaceItem(2424, 200, Weapon);
			player.getPlayerAssistant().sendString(WeaponName, 2426);
		}

	}

	/**
	 * Weapon Requirements
	 **/

	public void getRequirements(String itemName, int itemId) {
		player.attackLevelReq = player.defenceLevelReq = player.strengthLevelReq = player.rangeLevelReq = player.magicLevelReq = 0;
		if (itemName.contains("mystic") || itemName.contains("nchanted")) {
			if (itemName.contains("staff")) {
				player.magicLevelReq = 20;
				player.attackLevelReq = 40;
			} else {
				player.magicLevelReq = 20;
				player.defenceLevelReq = 20;
			}
		}
		if (itemName.contains("infinity")) {
			player.magicLevelReq = 50;
			player.defenceLevelReq = 25;
		}
		if (itemName.contains("splitbark")) {
			player.magicLevelReq = 40;
			player.defenceLevelReq = 40;
		}
		if (itemName.contains("Green")) {
			if (itemName.contains("hide")) {
				player.rangeLevelReq = 40;
				if (itemName.contains("body"))
					player.defenceLevelReq = 40;
				return;
			}
		}
		if (itemName.contains("Blue")) {
			if (itemName.contains("hide")) {
				player.rangeLevelReq = 50;
				if (itemName.contains("body"))
					player.defenceLevelReq = 40;
				return;
			}
		}
		if (itemName.contains("Red")) {
			if (itemName.contains("hide")) {
				player.rangeLevelReq = 60;
				if (itemName.contains("body"))
					player.defenceLevelReq = 40;
				return;
			}
		}
		if (itemName.contains("Black")) {
			if (itemName.contains("hide")) {
				player.rangeLevelReq = 70;
				if (itemName.contains("body"))
					player.defenceLevelReq = 40;
				return;
			}
		}
		if (itemName.contains("bronze")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("iron")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("steel")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 5;
			}
			return;
		}
		if (itemName.contains("black")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe") && !itemName.contains("vamb") && !itemName.contains("chap")) {
				player.attackLevelReq = player.defenceLevelReq = 10;
			}
			return;
		}
		if (itemName.contains("mithril")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 20;
			}
			return;
		}
		if (itemName.contains("adamant")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 30;
			}
			return;
		}
		if (itemName.contains("rune")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin") && !itemName.contains("thrownaxe") && !itemName.contains("'bow")) {
				player.attackLevelReq = player.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("dragon")) {
			if (!itemName.contains("nti-") && !itemName.contains("fire")) {
				player.attackLevelReq = player.defenceLevelReq = 60;
				return;
			}
		}
		if (itemName.contains("crystal")) {
			if (itemName.contains("shield")) {
				player.defenceLevelReq = 70;
			} else {
				player.rangeLevelReq = 70;
			}
			return;
		}
		if (itemName.contains("ahrim")) {
			if (itemName.contains("staff")) {
				player.magicLevelReq = 70;
				player.attackLevelReq = 70;
			} else {
				player.magicLevelReq = 70;
				player.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("karil")) {
			if (itemName.contains("crossbow")) {
				player.rangeLevelReq = 70;
			} else {
				player.rangeLevelReq = 70;
				player.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("godsword")) {
			player.attackLevelReq = 75;
		}
		if (itemName.contains("3rd age") && !itemName.contains("amulet")) {
			player.defenceLevelReq = 60;
		}
		if (itemName.contains("Initiate")) {
			player.defenceLevelReq = 20;
		}
		if (itemName.contains("verac") || itemName.contains("guthan") || itemName.contains("dharok") || itemName.contains("torag")) {

			if (itemName.contains("hammers")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else if (itemName.contains("axe")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else if (itemName.contains("warspear")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else if (itemName.contains("flail")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else {
				player.defenceLevelReq = 70;
			}
		}

		switch (itemId) {
		case 8839:
		case 8840:
		case 8842:
		case 11663:
		case 11664:
		case 11665:
			player.attackLevelReq = 42;
			player.rangeLevelReq = 42;
			player.strengthLevelReq = 42;
			player.magicLevelReq = 42;
			player.defenceLevelReq = 42;
			return;
		case 10551:
		case 2503:
		case 2501:
		case 2499:
		case 1135:
			player.defenceLevelReq = 40;
			return;
		case 11235:
		case 6522:
			player.rangeLevelReq = 60;
			break;
		case 6524:
			player.defenceLevelReq = 60;
			break;
		case 11284:
			player.defenceLevelReq = 75;
			return;
		case 6889:
		case 6914:
			player.magicLevelReq = 60;
			break;
		case 861:
			player.rangeLevelReq = 50;
			break;
		case 10828:
			player.defenceLevelReq = 55;
			break;
		case 11724:
		case 11726:
		case 11728:
			player.defenceLevelReq = 65;
			break;
		case 3751:
		case 3749:
		case 3755:
			player.defenceLevelReq = 40;
			break;

		case 7462:
		case 7461:
			player.defenceLevelReq = 40;
			break;
		case 8846:
			player.defenceLevelReq = 5;
			break;
		case 8847:
			player.defenceLevelReq = 10;
			break;
		case 8848:
			player.defenceLevelReq = 20;
			break;
		case 8849:
			player.defenceLevelReq = 30;
			break;
		case 8850:
			player.defenceLevelReq = 40;
			break;

		case 7460:
			player.defenceLevelReq = 40;
			break;

		case 837:
			player.rangeLevelReq = 61;
			break;

		case 4151: // if you don't want to use names
			player.attackLevelReq = 70;
			return;

		case 6724: // seercull
			player.rangeLevelReq = 60; // idk if that is correct
			return;
		case 4153:
			player.attackLevelReq = 50;
			player.strengthLevelReq = 50;
			return;
		}
	}

	/**
	 * two handed weapon check
	 **/
	public boolean is2handed(String itemName, int itemId) {
		if (itemName.contains("ahrim") || itemName.contains("karil") || itemName.contains("verac") || itemName.contains("guthan") || itemName.contains("dharok") || itemName.contains("torag")) {
			return true;
		}
		if (itemName.contains("longbow") || itemName.contains("shortbow") || itemName.contains("ark bow")) {
			return true;
		}
		if (itemName.contains("crystal")) {
			return true;
		}
		if (itemName.contains("godsword") || itemName.contains("aradomin sword") || itemName.contains("2h") || itemName.contains("spear")) {
			return true;
		}
		switch (itemId) {
		case 6724: // seercull
		case 11730:
		case 4153:
		case 6528:
		case 14484:
			return true;
		}
		return false;
	}

	/**
	 * Weapons special bar, adds the spec bars to weapons that require them and removes the spec bars from weapons which don't require them
	 **/

	public void addSpecialBar(int weapon) {
		switch (weapon) {

		case 15486: // staff of light
			player.getPlayerAssistant().sendHiddenInterface(0, 7599);
			specialAmount(weapon, player.specAmount, 7611);
			break;

		case 4151: // whip
			player.getPlayerAssistant().sendHiddenInterface(0, 12323);
			specialAmount(weapon, player.specAmount, 12335);
			break;

		case 859: // magic bows
		case 861:
		case 11235:
			player.getPlayerAssistant().sendHiddenInterface(0, 7549);
			specialAmount(weapon, player.specAmount, 7561);
			break;

		case 4587: // dscimmy
			player.getPlayerAssistant().sendHiddenInterface(0, 7599);
			specialAmount(weapon, player.specAmount, 7611);
			break;

		case 3204: // d hally
			player.getPlayerAssistant().sendHiddenInterface(0, 8493);
			specialAmount(weapon, player.specAmount, 8505);
			break;

		case 1377: // d battleaxe
			player.getPlayerAssistant().sendHiddenInterface(0, 7499);
			specialAmount(weapon, player.specAmount, 7511);
			break;

		case 4153: // gmaul
			player.getPlayerAssistant().sendHiddenInterface(0, 7474);
			specialAmount(weapon, player.specAmount, 7486);
			break;

		case 1249: // dspear
			player.getPlayerAssistant().sendHiddenInterface(0, 7674);
			specialAmount(weapon, player.specAmount, 7686);
			break;

		case 1215:// dragon dagger
		case 1231:
		case 5680:
		case 5698:
		case 1305: // dragon long
		case 11694:
		case 11698:
		case 11700:
		case 11730:
		case 11696:
			player.getPlayerAssistant().sendHiddenInterface(0, 7574);
			specialAmount(weapon, player.specAmount, 7586);
			break;

		case 1434: // dragon mace
			player.getPlayerAssistant().sendHiddenInterface(0, 7624);
			specialAmount(weapon, player.specAmount, 7636);
			break;

		default:
			player.getPlayerAssistant().sendHiddenInterface(1, 7624); // mace interface
			player.getPlayerAssistant().sendHiddenInterface(1, 7474); // hammer, gmaul
			player.getPlayerAssistant().sendHiddenInterface(1, 7499); // axe
			player.getPlayerAssistant().sendHiddenInterface(1, 7549); // bow interface
			player.getPlayerAssistant().sendHiddenInterface(1, 7574); // sword interface
			player.getPlayerAssistant().sendHiddenInterface(1, 7599); // scimmy sword interface, for most
			// swords
			player.getPlayerAssistant().sendHiddenInterface(1, 8493);
			player.getPlayerAssistant().sendHiddenInterface(1, 12323); // whip interface
			break;
		}
	}

	/**
	 * Specials bar filling amount
	 **/

	public void specialAmount(int weapon, double specAmount, int barId) {
		player.specBarId = barId;
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 10 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 9 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 8 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 7 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 6 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 5 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 4 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 3 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 2 ? 500 : 0, 0, (--barId));
		player.getPlayerAssistant().sendInterfaceOffset(specAmount >= 1 ? 500 : 0, 0, (--barId));
		updateSpecialBar();
		sendWeapon(weapon, getItemName(weapon));
	}

	/**
	 * Special attack text and what to highlight or blackout
	 **/

	public void updateSpecialBar() {
		if (player.usingSpecial) {
			player.getPlayerAssistant().sendString("" + (player.specAmount >= 2 ? "@yel@S P" : "@bla@S P") + "" + (player.specAmount >= 3 ? "@yel@ E" : "@bla@ E") + "" + (player.specAmount >= 4 ? "@yel@ C I" : "@bla@ C I") + "" + (player.specAmount >= 5 ? "@yel@ A L" : "@bla@ A L") + "" + (player.specAmount >= 6 ? "@yel@  A" : "@bla@  A") + "" + (player.specAmount >= 7 ? "@yel@ T T" : "@bla@ T T") + "" + (player.specAmount >= 8 ? "@yel@ A" : "@bla@ A") + "" + (player.specAmount >= 9 ? "@yel@ C" : "@bla@ C") + "" + (player.specAmount >= 10 ? "@yel@ K" : "@bla@ K"), player.specBarId);
		} else {
			player.getPlayerAssistant().sendString("@bla@S P E C I A L  A T T A C K", player.specBarId);
		}
	}

	/**
	 * Wear Item
	 **/

	public boolean wearItem(int wearID, int slot) {
		if (!player.getItems().playerHasItem(wearID, 1, slot)) {
			// add a method here for logging cheaters(If you want)
			return false;
		}
		int targetSlot = 0;
		boolean canWearItem = true;
		if (player.playerItems[slot] == (wearID + 1)) {
			getRequirements(getItemName(wearID).toLowerCase(), wearID);
			targetSlot = Item.targetSlots[wearID];

			switch (itemType(wearID)) {
			case "hat":
				targetSlot = 0;
				break;
			case "cape":
				targetSlot = 1;
				break;
			case "amulet":
				targetSlot = 2;
				break;
			case "body":
				targetSlot = 4;
				break;
			case "shield":
				targetSlot = 5;
				break;
			case "legs":
				targetSlot = 7;
				break;
			case "gloves":
				targetSlot = 9;
				break;
			case "boots":
				targetSlot = 10;
				break;
			case "ring":
				targetSlot = 12;
				break;
			case "arrows":
				targetSlot = 13;
				break;
			default:
				targetSlot = 3;
				break;
			}

			if (player.duelRule[11] && targetSlot == 0) {
				player.sendMessage("Wearing hats has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[12] && targetSlot == 1) {
				player.sendMessage("Wearing capes has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[13] && targetSlot == 2) {
				player.sendMessage("Wearing amulets has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[14] && targetSlot == 3) {
				player.sendMessage("Wielding weapons has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[15] && targetSlot == 4) {
				player.sendMessage("Wearing bodies has been disabled in this duel!");
				return false;
			}
			if ((player.duelRule[16] && targetSlot == 5) || (player.duelRule[16] && is2handed(getItemName(wearID).toLowerCase(), wearID))) {
				player.sendMessage("Wearing shield has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[17] && targetSlot == 7) {
				player.sendMessage("Wearing legs has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[18] && targetSlot == 9) {
				player.sendMessage("Wearing gloves has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[19] && targetSlot == 10) {
				player.sendMessage("Wearing boots has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[20] && targetSlot == 12) {
				player.sendMessage("Wearing rings has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[21] && targetSlot == 13) {
				player.sendMessage("Wearing arrows has been disabled in this duel!");
				return false;
			}

			if (targetSlot == 10 || targetSlot == 7 || targetSlot == 5 || targetSlot == 4 || targetSlot == 0 || targetSlot == 9 || targetSlot == 10) {
				if (player.defenceLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[1]) < player.defenceLevelReq) {
						player.sendMessage("You need a defence level of " + player.defenceLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				if (player.rangeLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[4]) < player.rangeLevelReq) {
						player.sendMessage("You need a range level of " + player.rangeLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				if (player.magicLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[6]) < player.magicLevelReq) {
						player.sendMessage("You need a magic level of " + player.magicLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
			}
			if (targetSlot == 3) {
				if (player.attackLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[0]) < player.attackLevelReq) {
						player.sendMessage("You need an attack level of " + player.attackLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
				if (player.rangeLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[4]) < player.rangeLevelReq) {
						player.sendMessage("You need a range level of " + player.rangeLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
				if (player.magicLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[6]) < player.magicLevelReq) {
						player.sendMessage("You need a magic level of " + player.magicLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
			}

			if (!canWearItem) {
				return false;
			}

			int wearAmount = player.playerItemsN[slot];
			if (wearAmount < 1) {
				return false;
			}

			if (targetSlot == player.playerWeapon) {
				player.autocasting = false;
				player.autocastId = 0;
				player.getPlayerAssistant().sendConfig(108, 0);
			}

			if (slot >= 0 && wearID >= 0) {
				int toEquip = player.playerItems[slot];
				int toEquipN = player.playerItemsN[slot];
				int toRemove = player.playerEquipment[targetSlot];
				int toRemoveN = player.playerEquipmentN[targetSlot];
				if (toEquip == toRemove + 1 && Item.itemStackable[toRemove]) {
					deleteItem(toRemove, getItemSlot(toRemove), toEquipN);
					player.playerEquipmentN[targetSlot] += toEquipN;
				} else if (targetSlot != 5 && targetSlot != 3) {
					player.playerItems[slot] = toRemove + 1;
					player.playerItemsN[slot] = toRemoveN;
					player.playerEquipment[targetSlot] = toEquip - 1;
					player.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == 5) {
					boolean wearing2h = is2handed(getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase(), player.playerEquipment[player.playerWeapon]);
					if (wearing2h) {
						toRemove = player.playerEquipment[player.playerWeapon];
						toRemoveN = player.playerEquipmentN[player.playerWeapon];
						player.playerEquipment[player.playerWeapon] = -1;
						player.playerEquipmentN[player.playerWeapon] = 0;
						updateSlot(player.playerWeapon);
					}
					player.playerItems[slot] = toRemove + 1;
					player.playerItemsN[slot] = toRemoveN;
					player.playerEquipment[targetSlot] = toEquip - 1;
					player.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == 3) {
					boolean is2h = is2handed(getItemName(wearID).toLowerCase(), wearID);
					boolean wearingShield = player.playerEquipment[player.playerShield] > 0;
					boolean wearingWeapon = player.playerEquipment[player.playerWeapon] > 0;
					if (is2h) {
						if (wearingShield && wearingWeapon) {
							if (freeSlots() > 0) {
								player.playerItems[slot] = toRemove + 1;
								player.playerItemsN[slot] = toRemoveN;
								player.playerEquipment[targetSlot] = toEquip - 1;
								player.playerEquipmentN[targetSlot] = toEquipN;
								removeItem(player.playerEquipment[player.playerShield], player.playerShield);
							} else {
								player.sendMessage("You do not have enough inventory space to do this.");
								return false;
							}
						} else if (wearingShield && !wearingWeapon) {
							player.playerItems[slot] = player.playerEquipment[player.playerShield] + 1;
							player.playerItemsN[slot] = player.playerEquipmentN[player.playerShield];
							player.playerEquipment[targetSlot] = toEquip - 1;
							player.playerEquipmentN[targetSlot] = toEquipN;
							player.playerEquipment[player.playerShield] = -1;
							player.playerEquipmentN[player.playerShield] = 0;
							updateSlot(player.playerShield);
						} else {
							player.playerItems[slot] = toRemove + 1;
							player.playerItemsN[slot] = toRemoveN;
							player.playerEquipment[targetSlot] = toEquip - 1;
							player.playerEquipmentN[targetSlot] = toEquipN;
						}
					} else {
						player.playerItems[slot] = toRemove + 1;
						player.playerItemsN[slot] = toRemoveN;
						player.playerEquipment[targetSlot] = toEquip - 1;
						player.playerEquipmentN[targetSlot] = toEquipN;
					}
				}
				resetItems(3214);
			}
			if (targetSlot == 3) {
				player.usingSpecial = false;
				addSpecialBar(wearID);
			}
			if (player.getOutStream() != null && player != null) {
				player.getOutStream().createFrameVarSizeWord(34);
				player.getOutStream().writeWord(1688);
				player.getOutStream().writeByte(targetSlot);
				player.getOutStream().writeWord(wearID + 1);

				if (player.playerEquipmentN[targetSlot] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord(player.playerEquipmentN[targetSlot]);
				} else {
					player.getOutStream().writeByte(player.playerEquipmentN[targetSlot]);
				}

				player.getOutStream().endFrameVarSizeWord();
				player.flushOutStream();
			}
			sendWeapon(player.playerEquipment[player.playerWeapon], getItemName(player.playerEquipment[player.playerWeapon]));
			resetBonus();
			getBonus();
			writeBonus();
			player.getCombat().getPlayerAnimIndex(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.getPlayerAssistant().requestUpdates();
			return true;
		} else {
			return false;
		}
	}

	public void wearItem(int wearID, int wearAmount, int targetSlot) {
		synchronized (player) {
			if (player.getOutStream() != null && player != null) {
				player.getOutStream().createFrameVarSizeWord(34);
				player.getOutStream().writeWord(1688);
				player.getOutStream().writeByte(targetSlot);
				player.getOutStream().writeWord(wearID + 1);

				if (wearAmount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord(wearAmount);
				} else {
					player.getOutStream().writeByte(wearAmount);
				}
				player.getOutStream().endFrameVarSizeWord();
				player.flushOutStream();
				player.playerEquipment[targetSlot] = wearID;
				player.playerEquipmentN[targetSlot] = wearAmount;
				player.getItems().sendWeapon(player.playerEquipment[player.playerWeapon], player.getItems().getItemName(player.playerEquipment[player.playerWeapon]));
				player.getItems().resetBonus();
				player.getItems().getBonus();
				player.getItems().writeBonus();
				player.getCombat().getPlayerAnimIndex(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
				player.updateRequired = true;
				player.setAppearanceUpdateRequired(true);
			}
		}
	}

	public void updateSlot(int slot) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(slot);
			player.getOutStream().writeWord(player.playerEquipment[slot] + 1);
			if (player.playerEquipmentN[slot] > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(player.playerEquipmentN[slot]);
			} else {
				player.getOutStream().writeByte(player.playerEquipmentN[slot]);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}

	}

	/**
	 * Remove Item
	 **/
	public void removeItem(int wearID, int slot) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			if (player.playerEquipment[slot] > -1) {
				if (addItem(player.playerEquipment[slot], player.playerEquipmentN[slot])) {
					player.playerEquipment[slot] = -1;
					player.playerEquipmentN[slot] = 0;
					sendWeapon(player.playerEquipment[player.playerWeapon], getItemName(player.playerEquipment[player.playerWeapon]));
					resetBonus();
					getBonus();
					writeBonus();
					player.getCombat().getPlayerAnimIndex(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.getOutStream().createFrame(34);
					player.getOutStream().writeWord(6);
					player.getOutStream().writeWord(1688);
					player.getOutStream().writeByte(slot);
					player.getOutStream().writeWord(0);
					player.getOutStream().writeByte(0);
					player.flushOutStream();
					player.updateRequired = true;
					player.setAppearanceUpdateRequired(true);
				}
			}
		}
	}

	/**
	 * BANK
	 */

	public void rearrangeBank() {
		int totalItems = 0;
		int highestSlot = 0;
		for (int i = 0; i < Banking.BANK_SIZE; i++) {
			if (player.bankItems[i] != 0) {
				totalItems++;
				if (highestSlot <= i) {
					highestSlot = i;
				}
			}
		}

		for (int i = 0; i <= highestSlot; i++) {
			if (player.bankItems[i] == 0) {
				boolean stop = false;

				for (int k = i; k <= highestSlot; k++) {
					if (player.bankItems[k] != 0 && !stop) {
						int spots = k - i;
						for (int j = k; j <= highestSlot; j++) {
							player.bankItems[j - spots] = player.bankItems[j];
							player.bankItemsN[j - spots] = player.bankItemsN[j];
							stop = true;
							player.bankItems[j] = 0;
							player.bankItemsN[j] = 0;
						}
					}
				}
			}
		}

		int totalItemsAfter = 0;
		for (int i = 0; i < Banking.BANK_SIZE; i++) {
			if (player.bankItems[i] != 0) {
				totalItemsAfter++;
			}
		}

		if (totalItems != totalItemsAfter)
			player.disconnected = true;
	}

	public void itemOnInterface(int id, int amount) {
		// synchronized(c) {
		player.getOutStream().createFrameVarSizeWord(53);
		player.getOutStream().writeWord(2274);
		player.getOutStream().writeWord(1);
		if (amount > 254) {
			player.getOutStream().writeByte(255);
			player.getOutStream().writeDWord_v2(amount);
		} else {
			player.getOutStream().writeByte(amount);
		}
		player.getOutStream().writeWordBigEndianA(id);
		player.getOutStream().endFrameVarSizeWord();
		player.flushOutStream();
	}

	public void resetBank() {
		synchronized (player) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(5382); // bank
			player.getOutStream().writeWord(Banking.BANK_SIZE);
			for (int i = 0; i < Banking.BANK_SIZE; i++) {
				if (player.bankItemsN[i] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(player.bankItemsN[i]);
				} else {
					player.getOutStream().writeByte(player.bankItemsN[i]);
				}
				if (player.bankItemsN[i] < 1) {
					player.bankItems[i] = 0;
				}
				if (player.bankItems[i] > ItemConfiguration.ITEM_AMOUNT || player.bankItems[i] < 0) {
					player.bankItems[i] = ItemConfiguration.ITEM_AMOUNT;
				}
				player.getOutStream().writeWordBigEndianA(player.bankItems[i]);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public void resetTempItems() {
		// synchronized(c) {
		int itemCount = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] > -1) {
				itemCount = i;
			}
		}
		player.getOutStream().createFrameVarSizeWord(53);
		player.getOutStream().writeWord(5064);
		player.getOutStream().writeWord(itemCount + 1);
		for (int i = 0; i < itemCount + 1; i++) {
			if (player.playerItemsN[i] > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord_v2(player.playerItemsN[i]);
			} else {
				player.getOutStream().writeByte(player.playerItemsN[i]);
			}
			if (player.playerItems[i] > ItemConfiguration.ITEM_AMOUNT || player.playerItems[i] < 0) {
				player.playerItems[i] = ItemConfiguration.ITEM_AMOUNT;
			}
			player.getOutStream().writeWordBigEndianA(player.playerItems[i]);
		}
		player.getOutStream().endFrameVarSizeWord();
		player.flushOutStream();
		// }
	}

	public int itemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] == itemID) {
				tempAmount += player.playerItemsN[i];
			}
		}
		return tempAmount;
	}

	public boolean isStackable(int itemID) {
		return Item.itemStackable[itemID];
	}

	/**
	 * Update Equip tab
	 **/

	public void setEquipment(int wearID, int amount, int targetSlot) {
		// synchronized(c) {
		player.getOutStream().createFrameVarSizeWord(34);
		player.getOutStream().writeWord(1688);
		player.getOutStream().writeByte(targetSlot);
		player.getOutStream().writeWord(wearID + 1);
		if (amount > 254) {
			player.getOutStream().writeByte(255);
			player.getOutStream().writeDWord(amount);
		} else {
			player.getOutStream().writeByte(amount);
		}
		player.getOutStream().endFrameVarSizeWord();
		player.flushOutStream();
		player.playerEquipment[targetSlot] = wearID;
		player.playerEquipmentN[targetSlot] = amount;
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	/**
	 * Move Items
	 **/

	public void moveItems(int from, int to, int moveWindow) {
		if (moveWindow == 3724) {
			int tempI;
			int tempN;
			tempI = player.playerItems[from];
			tempN = player.playerItemsN[from];

			player.playerItems[from] = player.playerItems[to];
			player.playerItemsN[from] = player.playerItemsN[to];
			player.playerItems[to] = tempI;
			player.playerItemsN[to] = tempN;
		}

		if (moveWindow == 34453 && from >= 0 && to >= 0 && from < Banking.BANK_SIZE && to < Banking.BANK_SIZE && to < Banking.BANK_SIZE) {
			int tempI;
			int tempN;
			tempI = player.bankItems[from];
			tempN = player.bankItemsN[from];

			player.bankItems[from] = player.bankItems[to];
			player.bankItemsN[from] = player.bankItemsN[to];
			player.bankItems[to] = tempI;
			player.bankItemsN[to] = tempN;
		}

		if (moveWindow == 34453) {
			resetBank();
		}
		if (moveWindow == 18579) {
			int tempI;
			int tempN;
			tempI = player.playerItems[from];
			tempN = player.playerItemsN[from];

			player.playerItems[from] = player.playerItems[to];
			player.playerItemsN[from] = player.playerItemsN[to];
			player.playerItems[to] = tempI;
			player.playerItemsN[to] = tempN;
			resetItems(3214);
		}
		resetTempItems();
		if (moveWindow == 3724) {
			resetItems(3214);
		}

	}

	/**
	 * delete Item
	 **/

	public void deleteEquipment(int i, int j) {
		// synchronized(c) {
		if (PlayerHandler.players[player.playerId] == null) {
			return;
		}
		if (i < 0) {
			return;
		}

		player.playerEquipment[j] = -1;
		player.playerEquipmentN[j] = player.playerEquipmentN[j] - 1;
		player.getOutStream().createFrame(34);
		player.getOutStream().writeWord(6);
		player.getOutStream().writeWord(1688);
		player.getOutStream().writeByte(j);
		player.getOutStream().writeWord(0);
		player.getOutStream().writeByte(0);
		getBonus();
		if (j == player.playerWeapon) {
			sendWeapon(-1, "Unarmed");
		}
		resetBonus();
		getBonus();
		writeBonus();
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	public void deleteItem(int id, int amount) {
		if (id <= 0)
			return;
		for (int j = 0; j < player.playerItems.length; j++) {
			if (amount <= 0)
				break;
			if (player.playerItems[j] == id + 1) {
				player.playerItems[j] = 0;
				player.playerItemsN[j] = 0;
				amount--;
			}
		}
		resetItems(3214);
	}

	public void deleteItem(int id, int slot, int amount) {
		if (id <= 0 || slot < 0) {
			return;
		}
		if (player.playerItems[slot] == (id + 1)) {
			if (player.playerItemsN[slot] > amount) {
				player.playerItemsN[slot] -= amount;
			} else {
				player.playerItemsN[slot] = 0;
				player.playerItems[slot] = 0;
			}
			resetItems(3214);
		}
	}

	public void deleteItem2(int id, int amount) {
		int am = amount;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (am == 0) {
				break;
			}
			if (player.playerItems[i] == (id + 1)) {
				if (player.playerItemsN[i] > amount) {
					player.playerItemsN[i] -= amount;
					break;
				} else {
					player.playerItems[i] = 0;
					player.playerItemsN[i] = 0;
					am--;
				}
			}
		}
		resetItems(3214);
	}

	/**
	 * Delete Arrows
	 **/
	public void deleteArrow() {
		// synchronized(c) {
		if (player.playerEquipment[player.playerCape] == 10499 && Misc.random(5) != 1 && player.playerEquipment[player.playerArrows] != 4740)
			return;
		if (player.playerEquipmentN[player.playerArrows] == 1) {
			player.getItems().deleteEquipment(player.playerEquipment[player.playerArrows], player.playerArrows);
		}
		if (player.playerEquipmentN[player.playerArrows] != 0) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(player.playerArrows);
			player.getOutStream().writeWord(player.playerEquipment[player.playerArrows] + 1);
			if (player.playerEquipmentN[player.playerArrows] - 1 > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(player.playerEquipmentN[player.playerArrows] - 1);
			} else {
				player.getOutStream().writeByte(player.playerEquipmentN[player.playerArrows] - 1);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
			player.playerEquipmentN[player.playerArrows] -= 1;
		}
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	public void deleteEquipment() {
		// synchronized(c) {
		if (player.playerEquipmentN[player.playerWeapon] == 1) {
			player.getItems().deleteEquipment(player.playerEquipment[player.playerWeapon], player.playerWeapon);
		}
		if (player.playerEquipmentN[player.playerWeapon] != 0) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(player.playerWeapon);
			player.getOutStream().writeWord(player.playerEquipment[player.playerWeapon] + 1);
			if (player.playerEquipmentN[player.playerWeapon] - 1 > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(player.playerEquipmentN[player.playerWeapon] - 1);
			} else {
				player.getOutStream().writeByte(player.playerEquipmentN[player.playerWeapon] - 1);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
			player.playerEquipmentN[player.playerWeapon] -= 1;
		}
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	/**
	 * Dropping Arrows
	 **/

	public void dropArrowNpc() {
		if (player.playerEquipment[player.playerCape] == 10499)
			return;
		int enemyX = NPCHandler.npcs[player.oldNpcIndex].getX();
		int enemyY = NPCHandler.npcs[player.oldNpcIndex].getY();
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(player.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX, enemyY, 1, player.getId());
			} else if (Server.itemHandler.itemAmount(player.rangeItemUsed, enemyX, enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(player.rangeItemUsed, enemyX, enemyY);
				Server.itemHandler.removeGroundItem(player, player.rangeItemUsed, enemyX, enemyY, false);
				Server.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX, enemyY, amount + 1, player.getId());
			}
		}
	}

	public void dropArrowPlayer() {
		int enemyX = PlayerHandler.players[player.oldPlayerIndex].getX();
		int enemyY = PlayerHandler.players[player.oldPlayerIndex].getY();
		if (player.playerEquipment[player.playerCape] == 10499)
			return;
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(player.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX, enemyY, 1, player.getId());
			} else if (Server.itemHandler.itemAmount(player.rangeItemUsed, enemyX, enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(player.rangeItemUsed, enemyX, enemyY);
				Server.itemHandler.removeGroundItem(player, player.rangeItemUsed, enemyX, enemyY, false);
				Server.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX, enemyY, amount + 1, player.getId());
			}
		}
	}

	public void removeAllItems() {
		for (int i = 0; i < player.playerItems.length; i++) {
			player.playerItems[i] = 0;
		}
		for (int i = 0; i < player.playerItemsN.length; i++) {
			player.playerItemsN[i] = 0;
		}
		resetItems(3214);
	}

	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int findItem(int id, int[] items, int[] amounts) {
		for (int i = 0; i < player.playerItems.length; i++) {
			if (((items[i] - 1) == id) && (amounts[i] > 0)) {
				return i;
			}
		}
		return -1;
	}

	public String getItemName(int ItemID) {
		for (int i = 0; i < ItemConfiguration.ITEM_AMOUNT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					return Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		return "Unarmed";
	}

	public int getItemId(String itemName) {
		for (int i = 0; i < ItemConfiguration.ITEM_AMOUNT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName.equalsIgnoreCase(itemName)) {
					return Server.itemHandler.ItemList[i].itemId;
				}
			}
		}
		return -1;
	}

	public int getItemSlot(int ItemID) {
		for (int i = 0; i < player.playerItems.length; i++) {
			if ((player.playerItems[i] - 1) == ItemID) {
				return i;
			}
		}
		return -1;
	}

	public int getItemAmount(int ItemID) {
		int itemCount = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if ((player.playerItems[i] - 1) == ItemID) {
				itemCount += player.playerItemsN[i];
			}
		}
		return itemCount;
	}

	public boolean playerHasItem(int itemID, int amt, int slot) {
		itemID++;
		int found = 0;
		if (player.playerItems[slot] == (itemID)) {
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] == itemID) {
					if (player.playerItemsN[i] >= amt) {
						return true;
					} else {
						found++;
					}
				}
			}
			if (found >= amt) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean playerHasItem(int itemID) {
		itemID++;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] == itemID)
				return true;
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] == itemID) {
				if (player.playerItemsN[i] >= amt) {
					return true;
				} else {
					found++;
				}
			}
		}
		if (found >= amt) {
			return true;
		}
		return false;
	}

	public int getUnnotedItem(int ItemID) {
		int NewID = ItemID - 1;
		String NotedName = "";
		for (int i = 0; i < ItemConfiguration.ITEM_AMOUNT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					NotedName = Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		for (int i = 0; i < ItemConfiguration.ITEM_AMOUNT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName == NotedName) {
					if (Server.itemHandler.ItemList[i].itemDescription.startsWith("Swap this note at any bank for a") == false) {
						NewID = Server.itemHandler.ItemList[i].itemId;
						break;
					}
				}
			}
		}
		return NewID;
	}

	/**
	 * Drop Item
	 **/

	public void createGroundItem(int itemID, int itemX, int itemY, int itemAmount) {
		player.getOutStream().createFrame(85);
		player.getOutStream().writeByteC((itemY - 8 * player.mapRegionY));
		player.getOutStream().writeByteC((itemX - 8 * player.mapRegionX));
		player.getOutStream().createFrame(44);
		player.getOutStream().writeWordBigEndianA(itemID);
		player.getOutStream().writeWord(itemAmount);
		player.getOutStream().writeByte(0);
		player.flushOutStream();
	}

	/**
	 * Pickup Item
	 **/

	public void removeGroundItem(int itemID, int itemX, int itemY, int Amount) {
		if (player == null) {
			return;
		}
		player.getOutStream().createFrame(85);
		player.getOutStream().writeByteC((itemY - 8 * player.mapRegionY));
		player.getOutStream().writeByteC((itemX - 8 * player.mapRegionX));
		player.getOutStream().createFrame(156);
		player.getOutStream().writeByteS(0);
		player.getOutStream().writeWord(itemID);
		player.flushOutStream();
	}

	public boolean ownsCape() {
		if (player.getItems().playerHasItem(2412, 1) || player.getItems().playerHasItem(2413, 1) || player.getItems().playerHasItem(2414, 1))
			return true;
		for (int j = 0; j < Banking.BANK_SIZE; j++) {
			if (player.bankItems[j] == 2412 || player.bankItems[j] == 2413 || player.bankItems[j] == 2414)
				return true;
		}
		if (player.playerEquipment[player.playerCape] == 2413 || player.playerEquipment[player.playerCape] == 2414 || player.playerEquipment[player.playerCape] == 2415)
			return true;
		return false;
	}

	public boolean hasAllShards() {
		return playerHasItem(11712, 1) && playerHasItem(11712, 1) && playerHasItem(11714, 1);
	}

	public void makeBlade() {
		deleteItem(11710, 1);
		deleteItem(11712, 1);
		deleteItem(11714, 1);
		addItem(11690, 1);
		player.sendMessage("You combine the shards to make a blade.");
	}

	public void makeGodsword(int i) {
		if (playerHasItem(11690) && playerHasItem(i)) {
			deleteItem(11690, 1);
			deleteItem(i, 1);
			addItem(i - 8, 1);
			player.sendMessage("You combine the hilt and the blade to make a godsword.");
		}
	}

	public boolean isHilt(int i) {
		return i >= 11702 && i <= 11708 && i % 2 == 0;
	}

}