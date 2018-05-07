package org.invictus.model.players;

import org.invictus.model.items.Item;

/**
 * A class for handling banking on the server.
 * 
 * @author Joe
 *
 */

public class Banking {

	Client player;

	public Banking(Client player) {
		this.player = player;
	}

	public static final int BANK_SIZE = 352;
	public static final int DEPOSIT_ALL_BANK_BUTTON_ID = 89223;
	public static final int ENABLE_NOTE_BUTTON_ID = 21010;
	public static final int DISABLE_NOTE_BUTTON_ID = 21011;
	public static final int BANK_INTERFACE_ID = 23000; // 5292 (old)
	public static int[] bankBooths = { 2213, 14367, 11758, 3193, 26972 };

	/**
	 * Open bank
	 **/
	public void openUpBank() {
		if (player.getOutStream() != null && player != null) {
			player.getItems().resetItems(5064);
			player.getItems().rearrangeBank();
			player.getItems().resetBank();
			player.getItems().resetTempItems();
			player.getOutStream().createFrame(248);
			player.getOutStream().writeWordA(BANK_INTERFACE_ID);
			player.getOutStream().writeWord(5063);
			player.flushOutStream();
		}
	}

	public int getTotalCount(int itemID) {
		int count = 0;
		for (int j = 0; j < player.playerItems.length; j++) {
			if (Item.itemIsNote[itemID + 1]) {
				if (itemID + 2 == player.playerItems[j])
					count += player.playerItemsN[j];
			}
			if (!Item.itemIsNote[itemID + 1]) {
				if (itemID + 1 == player.playerItems[j]) {
					count += player.playerItemsN[j];
				}
			}
		}
		for (int j = 0; j < player.bankItems.length; j++) {
			if (player.bankItems[j] == itemID + 1) {
				count += player.bankItemsN[j];
			}
		}
		return count;
	}

	public boolean bankItem(int itemID, int fromSlot, int amount) {
		if (player.inTrade) {
			player.sendMessage("You can't store items while trading!");
			return false;
		}
		if (player.playerItemsN[fromSlot] <= 0) {
			return false;
		}
		if (!Item.itemIsNote[player.playerItems[fromSlot] - 1]) {
			if (player.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[player.playerItems[fromSlot] - 1] || player.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < BANK_SIZE; i++) {
					if (player.bankItems[i] == player.playerItems[fromSlot]) {
						if (player.playerItemsN[fromSlot] < amount)
							amount = player.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = BANK_SIZE + 1;
						}
					}
					player.bankItems[toBankSlot] = player.playerItems[fromSlot];
					if (player.playerItemsN[fromSlot] < amount) {
						amount = player.playerItemsN[fromSlot];
					}
					if ((player.bankItemsN[toBankSlot] + amount) <= Integer.MAX_VALUE && (player.bankItemsN[toBankSlot] + amount) > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						player.sendMessage("Bank full!");
						return false;
					}
					player.getItems().deleteItem((player.playerItems[fromSlot] - 1), fromSlot, amount);
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else if (alreadyInBank) {
					if ((player.bankItemsN[toBankSlot] + amount) <= Integer.MAX_VALUE && (player.bankItemsN[toBankSlot] + amount) > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						player.sendMessage("Bank full!");
						return false;
					}
					player.getItems().deleteItem((player.playerItems[fromSlot] - 1), fromSlot, amount);
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else {
					player.sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = player.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < BANK_SIZE; i++) {
					if (player.bankItems[i] == player.playerItems[fromSlot]) {
						alreadyInBank = true;
						toBankSlot = i;
						i = BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if ((player.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItems[toBankSlot] = player.playerItems[firstPossibleSlot];
							player.bankItemsN[toBankSlot] += 1;
							player.getItems().deleteItem((player.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if ((player.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItemsN[toBankSlot] += 1;
							player.getItems().deleteItem((player.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else {
					player.sendMessage("Bank full!");
					return false;
				}
			}
		} else if (Item.itemIsNote[player.playerItems[fromSlot] - 1] && !Item.itemIsNote[player.playerItems[fromSlot] - 2]) {
			if (player.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[player.playerItems[fromSlot] - 1] || player.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < BANK_SIZE; i++) {
					if (player.bankItems[i] == (player.playerItems[fromSlot] - 1)) {
						if (player.playerItemsN[fromSlot] < amount)
							amount = player.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = BANK_SIZE + 1;
						}
					}
					player.bankItems[toBankSlot] = (player.playerItems[fromSlot] - 1);
					if (player.playerItemsN[fromSlot] < amount) {
						amount = player.playerItemsN[fromSlot];
					}
					if ((player.bankItemsN[toBankSlot] + amount) <= Integer.MAX_VALUE && (player.bankItemsN[toBankSlot] + amount) > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					player.getItems().deleteItem((player.playerItems[fromSlot] - 1), fromSlot, amount);
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else if (alreadyInBank) {
					if ((player.bankItemsN[toBankSlot] + amount) <= Integer.MAX_VALUE && (player.bankItemsN[toBankSlot] + amount) > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					player.getItems().deleteItem((player.playerItems[fromSlot] - 1), fromSlot, amount);
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else {
					player.sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = player.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < BANK_SIZE; i++) {
					if (player.bankItems[i] == (player.playerItems[fromSlot] - 1)) {
						alreadyInBank = true;
						toBankSlot = i;
						i = BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if ((player.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItems[toBankSlot] = (player.playerItems[firstPossibleSlot] - 1);
							player.bankItemsN[toBankSlot] += 1;
							player.getItems().deleteItem((player.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if ((player.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItemsN[toBankSlot] += 1;
							player.getItems().deleteItem((player.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					player.getItems().resetTempItems();
					player.getItems().resetBank();
					return true;
				} else {
					player.sendMessage("Bank full!");
					return false;
				}
			}
		} else {
			player.sendMessage("Item not supported " + (player.playerItems[fromSlot] - 1));
			return false;
		}
	}

	public int freeBankSlots() {
		int freeS = 0;
		for (int i = 0; i < BANK_SIZE; i++) {
			if (player.bankItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public void fromBank(int itemID, int fromSlot, int amount) {
		if (amount > 0) {
			if (player.bankItems[fromSlot] > 0) {
				if (!player.takeAsNote) {
					if (Item.itemStackable[player.bankItems[fromSlot] - 1]) {
						if (player.bankItemsN[fromSlot] > amount) {
							if (player.getItems().addItem((player.bankItems[fromSlot] - 1), amount)) {
								player.bankItemsN[fromSlot] -= amount;
								player.getItems().resetBank();
								player.getItems().resetItems(5064);
							}
						} else {
							if (player.getItems().addItem((player.bankItems[fromSlot] - 1), player.bankItemsN[fromSlot])) {
								player.bankItems[fromSlot] = 0;
								player.bankItemsN[fromSlot] = 0;
								player.getItems().resetBank();
								player.getItems().resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (player.bankItemsN[fromSlot] > 0) {
								if (player.getItems().addItem((player.bankItems[fromSlot] - 1), 1)) {
									player.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						player.getItems().resetBank();
						player.getItems().resetItems(5064);
					}
				} else if (player.takeAsNote && Item.itemIsNote[player.bankItems[fromSlot]]) {
					if (player.bankItemsN[fromSlot] > amount) {
						if (player.getItems().addItem(player.bankItems[fromSlot], amount)) {
							player.bankItemsN[fromSlot] -= amount;
							player.getItems().resetBank();
							player.getItems().resetItems(5064);
						}
					} else {
						if (player.getItems().addItem(player.bankItems[fromSlot], player.bankItemsN[fromSlot])) {
							player.bankItems[fromSlot] = 0;
							player.bankItemsN[fromSlot] = 0;
							player.getItems().resetBank();
							player.getItems().resetItems(5064);
						}
					}
				} else {
					player.sendMessage("This item can't be withdrawn as a note.");
					if (Item.itemStackable[player.bankItems[fromSlot] - 1]) {
						if (player.bankItemsN[fromSlot] > amount) {
							if (player.getItems().addItem((player.bankItems[fromSlot] - 1), amount)) {
								player.bankItemsN[fromSlot] -= amount;
								player.getItems().resetBank();
								player.getItems().resetItems(5064);
							}
						} else {
							if (player.getItems().addItem((player.bankItems[fromSlot] - 1), player.bankItemsN[fromSlot])) {
								player.bankItems[fromSlot] = 0;
								player.bankItemsN[fromSlot] = 0;
								player.getItems().resetBank();
								player.getItems().resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (player.bankItemsN[fromSlot] > 0) {
								if (player.getItems().addItem((player.bankItems[fromSlot] - 1), 1)) {
									player.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						player.getItems().resetBank();
						player.getItems().resetItems(5064);
					}
				}
			}
		}
	}

	public void depositAll() {
		for (int i = 0; i < player.playerItems.length; i++) {
			player.getBank().bankItem(player.playerItems[i] - 1, player.getItems().getItemSlot(player.playerItems[i] - 1), player.playerItemsN[i]);
		}
	}

}
