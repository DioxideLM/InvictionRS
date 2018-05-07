package org.invictus.model.players.content.minigames.duelarena;

import java.util.concurrent.CopyOnWriteArrayList;

import org.invictus.Server;
import org.invictus.model.items.GameItem;
import org.invictus.model.items.Item;
import org.invictus.model.items.ItemConfiguration;
import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.util.Misc;
import org.invictus.world.Location;

public class DuelArena {

	private Client player;

	public DuelArena(Client player) {
		this.player = player;
	}

	public CopyOnWriteArrayList<GameItem> otherStakedItems = new CopyOnWriteArrayList<GameItem>();
	public CopyOnWriteArrayList<GameItem> stakedItems = new CopyOnWriteArrayList<GameItem>();

	public void requestDuel(int id) {
		try {
			if (id == player.playerId)
				return;
			resetDuel();
			resetDuelItems();
			player.duelingWith = id;
			Client otherPlayer = (Client) PlayerHandler.players[id];
			if (otherPlayer == null) {
				return;
			}
			player.duelRequested = true;
			if (player.duelStatus == 0 && otherPlayer.duelStatus == 0 && player.duelRequested && otherPlayer.duelRequested && player.duelingWith == otherPlayer.getId() && otherPlayer.duelingWith == player.getId()) {
				if (player.goodDistance(player.getX(), player.getY(), otherPlayer.getX(), otherPlayer.getY(), 1)) {
					player.getDuelArena().openDuel();
					otherPlayer.getDuelArena().openDuel();
				} else {
					player.sendMessage("You need to get closer to your opponent to start the duel.");
				}

			} else {
				player.sendMessage("Sending duel request...");
				otherPlayer.sendMessage(player.playerName + ":duelreq:");
			}
		} catch (Exception e) {
			Misc.println("Error requesting duel.");
		}
	}

	public void openDuel() {
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			return;
		}
		player.duelStatus = 1;
		refreshduelRules();
		refreshDuelScreen();
		player.canOffer = true;
		for (int i = 0; i < player.playerEquipment.length; i++) {
			sendDuelEquipment(player.playerEquipment[i], player.playerEquipmentN[i], i);
		}
		player.getPlayerAssistant().sendString("Dueling with: " + otherPlayer.playerName + " (level-" + otherPlayer.combatLevel + ")", 6671);
		player.getPlayerAssistant().sendString("", 6684);
		player.getPlayerAssistant().sendInventoryOverlay(6575, 3321);
		player.getItems().resetItems(3322);
	}

	public void sendDuelEquipment(int itemId, int amount, int slot) {
		synchronized (player) {
			if (itemId != 0) {
				player.getOutStream().createFrameVarSizeWord(34);
				player.getOutStream().writeWord(13824);
				player.getOutStream().writeByte(slot);
				player.getOutStream().writeWord(itemId + 1);

				if (amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord(amount);
				} else {
					player.getOutStream().writeByte(amount);
				}
				player.getOutStream().endFrameVarSizeWord();
				player.flushOutStream();
			}
		}
	}

	public void refreshduelRules() {
		for (int i = 0; i < player.duelRule.length; i++) {
			player.duelRule[i] = false;
		}
		player.getPlayerAssistant().sendToggleInterfaceButton(286, 0);
		player.duelOption = 0;
	}

	public void refreshDuelScreen() {
		synchronized (player) {
			Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
			if (otherPlayer == null) {
				return;
			}
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6669);
			player.getOutStream().writeWord(stakedItems.toArray().length);
			int current = 0;
			for (GameItem item : stakedItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				if (item.id > ItemConfiguration.ITEM_AMOUNT || item.id < 0) {
					item.id = ItemConfiguration.ITEM_AMOUNT;
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1);

				current++;
			}

			if (current < 27) {
				for (int i = current; i < 28; i++) {
					player.getOutStream().writeByte(1);
					player.getOutStream().writeWordBigEndianA(-1);
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();

			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6670);
			player.getOutStream().writeWord(otherPlayer.getDuelArena().stakedItems.toArray().length);
			current = 0;
			for (GameItem item : otherPlayer.getDuelArena().stakedItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				if (item.id > ItemConfiguration.ITEM_AMOUNT || item.id < 0) {
					item.id = ItemConfiguration.ITEM_AMOUNT;
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1);
				current++;
			}

			if (current < 27) {
				for (int i = current; i < 28; i++) {
					player.getOutStream().writeByte(1);
					player.getOutStream().writeWordBigEndianA(-1);
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public boolean stakeItem(int itemID, int fromSlot, int amount) {

		for (int i : ItemConfiguration.ITEMS_TRADEABLE) {
			if (i == itemID) {
				player.sendMessage("You can't stake this item.");
				return false;
			}
		}
		if (amount <= 0)
			return false;
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			declineDuel();
			return false;
		}
		if (otherPlayer.duelStatus <= 0 || player.duelStatus <= 0) {
			declineDuel();
			otherPlayer.getDuelArena().declineDuel();
			return false;
		}
		if (!player.canOffer) {
			return false;
		}
		changeDuelStuff();
		if (!Item.itemStackable[itemID]) {
			for (int a = 0; a < amount; a++) {
				if (player.getItems().playerHasItem(itemID, 1)) {
					stakedItems.add(new GameItem(itemID, 1));
					player.getItems().deleteItem(itemID, player.getItems().getItemSlot(itemID), 1);
				}
			}
			player.getItems().resetItems(3214);
			player.getItems().resetItems(3322);
			otherPlayer.getItems().resetItems(3214);
			otherPlayer.getItems().resetItems(3322);
			refreshDuelScreen();
			otherPlayer.getDuelArena().refreshDuelScreen();
			player.getPlayerAssistant().sendString("", 6684);
			otherPlayer.getPlayerAssistant().sendString("", 6684);
		}

		if (!player.getItems().playerHasItem(itemID, amount)) {
			return false;
		}
		if (Item.itemStackable[itemID] || Item.itemIsNote[itemID]) {
			boolean found = false;
			for (GameItem item : stakedItems) {
				if (item.id == itemID) {
					found = true;
					item.amount += amount;
					player.getItems().deleteItem(itemID, fromSlot, amount);
					break;
				}
			}
			if (!found) {
				player.getItems().deleteItem(itemID, fromSlot, amount);
				stakedItems.add(new GameItem(itemID, amount));
			}
		}

		player.getItems().resetItems(3214);
		player.getItems().resetItems(3322);
		otherPlayer.getItems().resetItems(3214);
		otherPlayer.getItems().resetItems(3322);
		refreshDuelScreen();
		otherPlayer.getDuelArena().refreshDuelScreen();
		player.getPlayerAssistant().sendString("", 6684);
		otherPlayer.getPlayerAssistant().sendString("", 6684);
		return true;
	}

	public boolean fromDuel(int itemID, int fromSlot, int amount) {
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			declineDuel();
			return false;
		}
		if (otherPlayer.duelStatus <= 0 || player.duelStatus <= 0) {
			declineDuel();
			otherPlayer.getDuelArena().declineDuel();
			return false;
		}
		if (Item.itemStackable[itemID]) {
			if (player.getItems().freeSlots() - 1 < (player.duelSpaceReq)) {
				player.sendMessage("You have too many rules set to remove that item.");
				return false;
			}
		}

		if (!player.canOffer) {
			return false;
		}

		changeDuelStuff();
		boolean goodSpace = true;
		if (!Item.itemStackable[itemID]) {
			for (int a = 0; a < amount; a++) {
				for (GameItem item : stakedItems) {
					if (item.id == itemID) {
						if (!item.stackable) {
							if (player.getItems().freeSlots() - 1 < (player.duelSpaceReq)) {
								goodSpace = false;
								break;
							}
							stakedItems.remove(item);
							player.getItems().addItem(itemID, 1);
						} else {
							if (player.getItems().freeSlots() - 1 < (player.duelSpaceReq)) {
								goodSpace = false;
								break;
							}
							if (item.amount > amount) {
								item.amount -= amount;
								player.getItems().addItem(itemID, amount);
							} else {
								if (player.getItems().freeSlots() - 1 < (player.duelSpaceReq)) {
									goodSpace = false;
									break;
								}
								amount = item.amount;
								stakedItems.remove(item);
								player.getItems().addItem(itemID, amount);
							}
						}
						break;
					}
					otherPlayer.duelStatus = 1;
					player.duelStatus = 1;
					player.getItems().resetItems(3214);
					player.getItems().resetItems(3322);
					otherPlayer.getItems().resetItems(3214);
					otherPlayer.getItems().resetItems(3322);
					player.getDuelArena().refreshDuelScreen();
					otherPlayer.getDuelArena().refreshDuelScreen();
					otherPlayer.getPlayerAssistant().sendString("", 6684);
				}
			}
		}

		for (GameItem item : stakedItems) {
			if (item.id == itemID) {
				if (!item.stackable) {
				} else {
					if (item.amount > amount) {
						item.amount -= amount;
						player.getItems().addItem(itemID, amount);
					} else {
						amount = item.amount;
						stakedItems.remove(item);
						player.getItems().addItem(itemID, amount);
					}
				}
				break;
			}
		}
		otherPlayer.duelStatus = 1;
		player.duelStatus = 1;
		player.getItems().resetItems(3214);
		player.getItems().resetItems(3322);
		otherPlayer.getItems().resetItems(3214);
		otherPlayer.getItems().resetItems(3322);
		player.getDuelArena().refreshDuelScreen();
		otherPlayer.getDuelArena().refreshDuelScreen();
		otherPlayer.getPlayerAssistant().sendString("", 6684);
		if (!goodSpace) {
			player.sendMessage("You have too many rules set to remove that item.");
			return true;
		}
		return true;
	}

	public void confirmDuel() {
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			declineDuel();
			return;
		}
		String itemId = "";
		for (GameItem item : stakedItems) {
			if (Item.itemStackable[item.id] || Item.itemIsNote[item.id]) {
				itemId += player.getItems().getItemName(item.id) + " x " + Misc.format(item.amount) + "\\n";
			} else {
				itemId += player.getItems().getItemName(item.id) + "\\n";
			}
		}
		player.getPlayerAssistant().sendString(itemId, 6516);
		itemId = "";
		for (GameItem item : otherPlayer.getDuelArena().stakedItems) {
			if (Item.itemStackable[item.id] || Item.itemIsNote[item.id]) {
				itemId += player.getItems().getItemName(item.id) + " x " + Misc.format(item.amount) + "\\n";
			} else {
				itemId += player.getItems().getItemName(item.id) + "\\n";
			}
		}
		player.getPlayerAssistant().sendString(itemId, 6517);
		player.getPlayerAssistant().sendString("", 8242);
		for (int i = 8238; i <= 8253; i++) {
			player.getPlayerAssistant().sendString("", i);
		}
		player.getPlayerAssistant().sendString("Hitpoints will be restored.", 8250);
		player.getPlayerAssistant().sendString("Boosted stats will be restored.", 8238);
		if (player.duelRule[8]) {
			player.getPlayerAssistant().sendString("There will be obstacles in the arena.", 8239);
		}
		player.getPlayerAssistant().sendString("", 8240);
		player.getPlayerAssistant().sendString("", 8241);

		String[] rulesOption = { "Players cannot forfeit!", "Players cannot move.", "Players cannot use range.", "Players cannot use melee.", "Players cannot use magic.", "Players cannot drink pots.", "Players cannot eat food.", "Players cannot use prayer." };

		int lineNumber = 8242;
		for (int i = 0; i < 8; i++) {
			if (player.duelRule[i]) {
				player.getPlayerAssistant().sendString("" + rulesOption[i], lineNumber);
				lineNumber++;
			}
		}
		player.getPlayerAssistant().sendString("", 6571);
		player.getPlayerAssistant().sendInventoryOverlay(6412, 197);
		// c.getPA().showInterface(6412);
	}

	public void startDuel() {
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			duelVictory();
		}
		player.headIconHints = 2;

		if (player.duelRule[7]) {
			for (int p = 0; p < player.PRAYER.length; p++) { // reset prayer glows
				player.prayerActive[p] = false;
				player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[p], 0);
			}
			player.headIcon = -1;
			player.getPlayerAssistant().requestUpdates();
		}
		if (player.duelRule[11]) {
			player.getItems().removeItem(player.playerEquipment[0], 0);
		}
		if (player.duelRule[12]) {
			player.getItems().removeItem(player.playerEquipment[1], 1);
		}
		if (player.duelRule[13]) {
			player.getItems().removeItem(player.playerEquipment[2], 2);
		}
		if (player.duelRule[14]) {
			player.getItems().removeItem(player.playerEquipment[3], 3);
		}
		if (player.duelRule[15]) {
			player.getItems().removeItem(player.playerEquipment[4], 4);
		}
		if (player.duelRule[16]) {
			player.getItems().removeItem(player.playerEquipment[5], 5);
		}
		if (player.duelRule[17]) {
			player.getItems().removeItem(player.playerEquipment[7], 7);
		}
		if (player.duelRule[18]) {
			player.getItems().removeItem(player.playerEquipment[9], 9);
		}
		if (player.duelRule[19]) {
			player.getItems().removeItem(player.playerEquipment[10], 10);
		}
		if (player.duelRule[20]) {
			player.getItems().removeItem(player.playerEquipment[12], 12);
		}
		if (player.duelRule[21]) {
			player.getItems().removeItem(player.playerEquipment[13], 13);
		}
		player.duelStatus = 5;
		player.getPlayerAssistant().removeAllWindows();
		player.specAmount = 10;
		player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);

		if (player.duelRule[8]) {
			if (player.duelRule[1]) {
				player.getPlayerAssistant().movePlayer(player.duelTeleX, player.duelTeleY, 0);
			} else {
				player.getPlayerAssistant().movePlayer(3366 + Misc.random(12), 3246 + Misc.random(6), 0);
			}
		} else {
			if (player.duelRule[1]) {
				player.getPlayerAssistant().movePlayer(player.duelTeleX, player.duelTeleY, 0);
			} else {
				player.getPlayerAssistant().movePlayer(3335 + Misc.random(12), 3246 + Misc.random(6), 0);
			}
		}

		player.getPlayerAssistant().sendMobHintIcon(10, otherPlayer.playerId);
		player.getPlayerAssistant().showOption(3, 0, "Attack", 1);
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = player.getPlayerAssistant().getLevelForXP(player.playerXP[i]);
			player.getPlayerAssistant().refreshSkill(i);
		}
		for (GameItem item : otherPlayer.getDuelArena().stakedItems) {
			otherStakedItems.add(new GameItem(item.id, item.amount));
		}
		player.getPlayerAssistant().requestUpdates();
	}

	public void duelVictory() {
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer != null) {
			player.getPlayerAssistant().sendString("" + otherPlayer.combatLevel, 6839);
			player.getPlayerAssistant().sendString(otherPlayer.playerName, 6840);
			otherPlayer.duelStatus = 0;
		} else {
			player.getPlayerAssistant().sendString("", 6839);
			player.getPlayerAssistant().sendString("", 6840);
		}
		player.duelStatus = 6;
		player.getCombat().resetPrayers();
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = player.getPlayerAssistant().getLevelForXP(player.playerXP[i]);
			player.getPlayerAssistant().refreshSkill(i);
		}
		player.getPlayerAssistant().refreshSkill(3);
		duelRewardInterface();
		player.getPlayerAssistant().sendInterface(6733);
		player.getPlayerAssistant().movePlayer(Location.DUELING_RESPAWN[0] + (Misc.random(Location.RANDOM_DUELING_RESPAWN)), Location.DUELING_RESPAWN[1] + (Misc.random(Location.RANDOM_DUELING_RESPAWN)), 0);
		player.getPlayerAssistant().requestUpdates();
		player.getPlayerAssistant().showOption(3, 0, "Challenge", 3);
		player.getPlayerAssistant().sendMobHintIcon(10, -1);
		player.canOffer = true;
		player.duelSpaceReq = 0;
		player.duelingWith = 0;
		player.getCombat().resetPlayerAttack();
		player.duelRequested = false;
	}

	public void duelRewardInterface() {
		synchronized (player) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6822);
			player.getOutStream().writeWord(otherStakedItems.toArray().length);
			for (GameItem item : otherStakedItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				if (item.id > ItemConfiguration.ITEM_AMOUNT || item.id < 0) {
					item.id = ItemConfiguration.ITEM_AMOUNT;
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public void claimStakedItems() {
		for (GameItem item : otherStakedItems) {
			if (item.id > 0 && item.amount > 0) {
				if (Item.itemStackable[item.id]) {
					if (!player.getItems().addItem(item.id, item.amount)) {
						Server.itemHandler.createGroundItem(player, item.id, player.getX(), player.getY(), item.amount, player.getId());
					}
				} else {
					int amount = item.amount;
					for (int a = 1; a <= amount; a++) {
						if (!player.getItems().addItem(item.id, 1)) {
							Server.itemHandler.createGroundItem(player, item.id, player.getX(), player.getY(), 1, player.getId());
						}
					}
				}
			}
		}
		for (GameItem item : stakedItems) {
			if (item.id > 0 && item.amount > 0) {
				if (Item.itemStackable[item.id]) {
					if (!player.getItems().addItem(item.id, item.amount)) {
						Server.itemHandler.createGroundItem(player, item.id, player.getX(), player.getY(), item.amount, player.getId());
					}
				} else {
					int amount = item.amount;
					for (int a = 1; a <= amount; a++) {
						if (!player.getItems().addItem(item.id, 1)) {
							Server.itemHandler.createGroundItem(player, item.id, player.getX(), player.getY(), 1, player.getId());
						}
					}
				}
			}
		}
		resetDuel();
		resetDuelItems();
		player.duelStatus = 0;
	}

	public void declineDuel() {
		player.getPlayerAssistant().removeAllWindows();
		player.canOffer = true;
		player.duelStatus = 0;
		player.duelingWith = 0;
		player.duelSpaceReq = 0;
		player.duelRequested = false;
		for (GameItem item : stakedItems) {
			if (item.amount < 1)
				continue;
			if (Item.itemStackable[item.id] || Item.itemIsNote[item.id]) {
				player.getItems().addItem(item.id, item.amount);
			} else {
				player.getItems().addItem(item.id, 1);
			}
		}
		stakedItems.clear();
		for (int i = 0; i < player.duelRule.length; i++) {
			player.duelRule[i] = false;
		}
	}

	public void resetDuel() {
		player.getPlayerAssistant().showOption(3, 0, "Challenge", 3);
		player.headIconHints = 0;
		for (int i = 0; i < player.duelRule.length; i++) {
			player.duelRule[i] = false;
		}
		player.getPlayerAssistant().sendMobHintIcon(10, -1);
		player.duelStatus = 0;
		player.canOffer = true;
		player.duelSpaceReq = 0;
		player.duelingWith = 0;
		player.getPlayerAssistant().requestUpdates();
		player.getCombat().resetPlayerAttack();
		player.duelRequested = false;
	}

	public void resetDuelItems() {
		stakedItems.clear();
		otherStakedItems.clear();
	}

	public void changeDuelStuff() {
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			return;
		}
		otherPlayer.duelStatus = 1;
		player.duelStatus = 1;
		otherPlayer.getPlayerAssistant().sendString("", 6684);
		player.getPlayerAssistant().sendString("", 6684);
	}

	public void selectRule(int i) { // rules
		Client otherPlayer = (Client) PlayerHandler.players[player.duelingWith];
		if (otherPlayer == null) {
			return;
		}
		if (!player.canOffer)
			return;
		changeDuelStuff();
		otherPlayer.duelSlot = player.duelSlot;
		if (i >= 11 && player.duelSlot > -1) {
			if (player.playerEquipment[player.duelSlot] > 0) {
				if (!player.duelRule[i]) {
					player.duelSpaceReq++;
				} else {
					player.duelSpaceReq--;
				}
			}
			if (otherPlayer.playerEquipment[otherPlayer.duelSlot] > 0) {
				if (!otherPlayer.duelRule[i]) {
					otherPlayer.duelSpaceReq++;
				} else {
					otherPlayer.duelSpaceReq--;
				}
			}
		}

		if (i >= 11) {
			if (player.getItems().freeSlots() < (player.duelSpaceReq) || otherPlayer.getItems().freeSlots() < (otherPlayer.duelSpaceReq)) {
				player.sendMessage("You or your opponent don't have the required space to set this rule.");
				if (player.playerEquipment[player.duelSlot] > 0) {
					player.duelSpaceReq--;
				}
				if (otherPlayer.playerEquipment[otherPlayer.duelSlot] > 0) {
					otherPlayer.duelSpaceReq--;
				}
				return;
			}
		}

		if (!player.duelRule[i]) {
			player.duelRule[i] = true;
			player.duelOption += player.DUEL_RULE_ID[i];
		} else {
			player.duelRule[i] = false;
			player.duelOption -= player.DUEL_RULE_ID[i];
		}

		player.getPlayerAssistant().sendToggleInterfaceButton(286, player.duelOption);
		otherPlayer.duelOption = player.duelOption;
		otherPlayer.duelRule[i] = player.duelRule[i];
		otherPlayer.getPlayerAssistant().sendToggleInterfaceButton(286, otherPlayer.duelOption);

		if (player.duelRule[8]) {
			if (player.duelRule[1]) {
				player.duelTeleX = 3366 + Misc.random(12);
				otherPlayer.duelTeleX = player.duelTeleX - 1;
				player.duelTeleY = 3246 + Misc.random(6);
				otherPlayer.duelTeleY = player.duelTeleY;
			}
		} else {
			if (player.duelRule[1]) {
				player.duelTeleX = 3335 + Misc.random(12);
				otherPlayer.duelTeleX = player.duelTeleX - 1;
				player.duelTeleY = 3246 + Misc.random(6);
				otherPlayer.duelTeleY = player.duelTeleY;
			}
		}

	}

}
