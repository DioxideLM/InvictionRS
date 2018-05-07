package org.invictus.model.players;

import java.util.concurrent.CopyOnWriteArrayList;

import org.invictus.model.items.GameItem;
import org.invictus.model.items.Item;
import org.invictus.model.items.ItemConfiguration;
import org.invictus.util.Misc;

public class Trading {

	private Client player;

	public Trading(Client player) {
		this.player = player;
	}

	public CopyOnWriteArrayList<GameItem> offeredItems = new CopyOnWriteArrayList<GameItem>();

	public void requestTrade(int id) {
		try {
			Client otherPlayer = (Client) PlayerHandler.players[id];
			if (id == player.playerId)
				return;
			player.tradeWith = id;
			if (!player.inTrade && otherPlayer.tradeRequested && otherPlayer.tradeWith == player.playerId) {
				player.getTradeAndDuel().openTrade();
				otherPlayer.getTradeAndDuel().openTrade();
			} else if (!player.inTrade) {

				player.tradeRequested = true;
				player.sendMessage("Sending trade request...");
				otherPlayer.sendMessage(player.playerName + ":tradereq:");
			}
		} catch (Exception e) {
			Misc.println("Error requesting trade.");
		}
	}

	public void openTrade() {
		Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];

		if (otherPlayer == null) {
			return;
		}
		player.inTrade = true;
		player.canOffer = true;
		player.tradeStatus = 1;
		player.tradeRequested = false;
		player.getItems().resetItems(3322);
		resetTItems(3415);
		resetOTItems(3416);
		String out = otherPlayer.playerName;

		if (otherPlayer.playerRights == 1) {
			out = "@cr1@" + out;
		} else if (otherPlayer.playerRights == 2) {
			out = "@cr2@" + out;
		}
		player.getPlayerAssistant().sendString("Trading with: " + otherPlayer.playerName + " who has @gre@" + otherPlayer.getItems().freeSlots() + " free slots", 3417);
		player.getPlayerAssistant().sendString("", 3431);
		player.getPlayerAssistant().sendString("Are you sure you want to make this trade?", 3535);
		player.getPlayerAssistant().sendInventoryOverlay(3323, 3321);
	}

	public void resetTItems(int WriteFrame) {
		synchronized (player) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(WriteFrame);
			int len = offeredItems.toArray().length;
			int current = 0;
			player.getOutStream().writeWord(len);
			for (GameItem item : offeredItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
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

	public boolean fromTrade(int itemID, int fromSlot, int amount) {
		Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];
		if (otherPlayer == null) {
			return false;
		}
		try {
			if (!player.inTrade || !player.canOffer) {
				declineTrade();
				return false;
			}
			player.tradeConfirmed = false;
			otherPlayer.tradeConfirmed = false;
			if (!Item.itemStackable[itemID]) {
				for (int a = 0; a < amount; a++) {
					for (GameItem item : offeredItems) {
						if (item.id == itemID) {
							if (!item.stackable) {
								offeredItems.remove(item);
								player.getItems().addItem(itemID, 1);
								otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
							} else {
								if (item.amount > amount) {
									item.amount -= amount;
									player.getItems().addItem(itemID, amount);
									otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
								} else {
									amount = item.amount;
									offeredItems.remove(item);
									player.getItems().addItem(itemID, amount);
									otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
								}
							}
							break;
						}
						otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
						player.tradeConfirmed = false;
						otherPlayer.tradeConfirmed = false;
						player.getItems().resetItems(3322);
						resetTItems(3415);
						otherPlayer.getTradeAndDuel().resetOTItems(3416);
						player.getPlayerAssistant().sendString("", 3431);
						otherPlayer.getPlayerAssistant().sendString("", 3431);
					}
				}
			}
			for (GameItem item : offeredItems) {
				if (item.id == itemID) {
					if (!item.stackable) {
					} else {
						if (item.amount > amount) {
							item.amount -= amount;
							player.getItems().addItem(itemID, amount);
							otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
						} else {
							amount = item.amount;
							offeredItems.remove(item);
							player.getItems().addItem(itemID, amount);
							otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
						}
					}
					break;
				}
			}

			otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
			player.tradeConfirmed = false;
			otherPlayer.tradeConfirmed = false;
			player.getItems().resetItems(3322);
			resetTItems(3415);
			otherPlayer.getTradeAndDuel().resetOTItems(3416);
			player.getPlayerAssistant().sendString("", 3431);
			otherPlayer.getPlayerAssistant().sendString("", 3431);
		} catch (Exception e) {
		}
		return true;
	}

	public boolean tradeItem(int itemID, int fromSlot, int amount) {
		Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];
		if (otherPlayer == null) {
			return false;
		}

		for (int i : ItemConfiguration.ITEMS_TRADEABLE) {
			if (i == itemID) {
				player.sendMessage("You can't trade this item.");
				return false;
			}
		}
		if (!((player.playerItems[fromSlot] == itemID + 1) && (player.playerItemsN[fromSlot] >= amount))) {
			player.sendMessage("You don't have that amount!");
			return false;
		}
		player.tradeConfirmed = false;
		otherPlayer.tradeConfirmed = false;
		if (!Item.itemStackable[itemID] && !Item.itemIsNote[itemID]) {
			for (int a = 0; a < amount && a < 28; a++) {
				if (player.getItems().playerHasItem(itemID, 1)) {
					offeredItems.add(new GameItem(itemID, 1));
					player.getItems().deleteItem(itemID, player.getItems().getItemSlot(itemID), 1);
					otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
				}
			}
			otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
			player.getItems().resetItems(3322);
			resetTItems(3415);
			otherPlayer.getTradeAndDuel().resetOTItems(3416);
			player.getPlayerAssistant().sendString("", 3431);
			otherPlayer.getPlayerAssistant().sendString("", 3431);
		}
		if (player.getItems().getItemCount(itemID) < amount) {
			amount = player.getItems().getItemCount(itemID);
			if (amount == 0)
				return false;
		}
		if (!player.inTrade || !player.canOffer) {
			declineTrade();
			return false;
		}

		if (Item.itemStackable[itemID] || Item.itemIsNote[itemID]) {
			boolean inTrade = false;
			for (GameItem item : offeredItems) {
				if (item.id == itemID) {
					inTrade = true;
					item.amount += amount;
					player.getItems().deleteItem2(itemID, amount);
					otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
					break;
				}
			}

			if (!inTrade) {
				offeredItems.add(new GameItem(itemID, amount));
				player.getItems().deleteItem2(itemID, amount);
				otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
			}
		}
		otherPlayer.getPlayerAssistant().sendString("Trading with: " + player.playerName + " who has @gre@" + player.getItems().freeSlots() + " free slots", 3417);
		player.getItems().resetItems(3322);
		resetTItems(3415);
		otherPlayer.getTradeAndDuel().resetOTItems(3416);
		player.getPlayerAssistant().sendString("", 3431);
		otherPlayer.getPlayerAssistant().sendString("", 3431);
		return true;
	}

	public void resetTrade() {
		offeredItems.clear();
		player.inTrade = false;
		player.tradeWith = 0;
		player.canOffer = true;
		player.tradeConfirmed = false;
		player.tradeConfirmed2 = false;
		player.acceptedTrade = false;
		player.getPlayerAssistant().removeAllWindows();
		player.tradeResetNeeded = false;
		player.getPlayerAssistant().sendString("Are you sure you want to make this trade?", 3535);
	}

	public void declineTrade() {
		player.tradeStatus = 0;
		declineTrade(true);
	}

	public void declineTrade(boolean tellOther) {
		player.getPlayerAssistant().removeAllWindows();
		Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];
		if (otherPlayer == null) {
			return;
		}

		if (tellOther) {
			otherPlayer.getTradeAndDuel().declineTrade(false);
			otherPlayer.getTradeAndDuel().player.getPlayerAssistant().removeAllWindows();
		}

		for (GameItem item : offeredItems) {
			if (item.amount < 1) {
				continue;
			}
			if (item.stackable) {
				player.getItems().addItem(item.id, item.amount);
			} else {
				for (int i = 0; i < item.amount; i++) {
					player.getItems().addItem(item.id, 1);
				}
			}
		}
		player.canOffer = true;
		player.tradeConfirmed = false;
		player.tradeConfirmed2 = false;
		offeredItems.clear();
		player.inTrade = false;
		player.tradeWith = 0;
	}

	public void resetOTItems(int WriteFrame) {
		synchronized (player) {
			Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];
			if (otherPlayer == null) {
				return;
			}
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(WriteFrame);
			int len = otherPlayer.getTradeAndDuel().offeredItems.toArray().length;
			int current = 0;
			player.getOutStream().writeWord(len);
			for (GameItem item : otherPlayer.getTradeAndDuel().offeredItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255); // item's stack count. if
					// over 254, write byte
					// 255
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1); // item id
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

	public void confirmScreen() {
		Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];
		if (otherPlayer == null) {
			return;
		}
		player.canOffer = false;
		player.getItems().resetItems(3214);
		String SendTrade = "Absolutely nothing!";
		String SendAmount = "";
		int Count = 0;
		for (GameItem item : offeredItems) {
			if (item.id > 0) {
				if (item.amount >= 1000 && item.amount < 1000000) {
					SendAmount = "@cya@" + (item.amount / 1000) + "K @whi@(" + Misc.format(item.amount) + ")";
				} else if (item.amount >= 1000000) {
					SendAmount = "@gre@" + (item.amount / 1000000) + " million @whi@(" + Misc.format(item.amount) + ")";
				} else {
					SendAmount = "" + Misc.format(item.amount);
				}

				if (Count == 0) {
					SendTrade = player.getItems().getItemName(item.id);
				} else {
					SendTrade = SendTrade + "\\n" + player.getItems().getItemName(item.id);
				}

				if (item.stackable) {
					SendTrade = SendTrade + " x " + SendAmount;
				}
				Count++;
			}
		}

		player.getPlayerAssistant().sendString(SendTrade, 3557);
		SendTrade = "Absolutely nothing!";
		SendAmount = "";
		Count = 0;

		for (GameItem item : otherPlayer.getTradeAndDuel().offeredItems) {
			if (item.id > 0) {
				if (item.amount >= 1000 && item.amount < 1000000) {
					SendAmount = "@cya@" + (item.amount / 1000) + "K @whi@(" + Misc.format(item.amount) + ")";
				} else if (item.amount >= 1000000) {
					SendAmount = "@gre@" + (item.amount / 1000000) + " million @whi@(" + Misc.format(item.amount) + ")";
				} else {
					SendAmount = "" + Misc.format(item.amount);
				}

				if (Count == 0) {
					SendTrade = player.getItems().getItemName(item.id);
				} else {
					SendTrade = SendTrade + "\\n" + player.getItems().getItemName(item.id);
				}
				if (item.stackable) {
					SendTrade = SendTrade + " x " + SendAmount;
				}
				Count++;
			}
		}
		player.getPlayerAssistant().sendString(SendTrade, 3558);
		// TODO: find out what 197 does eee 3213
		player.getPlayerAssistant().sendInventoryOverlay(3443, 197);
	}

	public void giveItems() {
		Client otherPlayer = (Client) PlayerHandler.players[player.tradeWith];
		if (otherPlayer == null) {
			return;
		}
		try {
			for (GameItem item : otherPlayer.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					player.getItems().addItem(item.id, item.amount);
				}
			}

			player.getPlayerAssistant().removeAllWindows();
			player.tradeResetNeeded = true;
		} catch (Exception e) {
		}
	}
}