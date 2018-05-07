package org.invictus.model.players.content.minigames.barrows;

import org.invictus.Server;
import org.invictus.model.players.Client;
import org.invictus.util.Misc;
import org.invictus.world.Location;

public class Barrows {

	private Client player;

	public Barrows(Client player) {
		this.player = player;
	}

	public static final int[][] COFFIN_DATA = { { 6823, 2030 }, { 6772, 2029 }, { 6822, 2028 }, { 6773, 2027 }, { 6771, 2026 }, { 6821, 2025 } };

	public static int BARROWS_ITEMS[] = { 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759 };
	public static int RUNES[] = { 4740, 558, 560, 565 };
	
	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = player.getItems().getItemAmount(995);
		for (int j = 0; j < player.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < player.getItems().brokenBarrows.length; i++) {
				if (player.playerItems[j] - 1 == player.getItems().brokenBarrows[i][1]) {
					if (totalCost + 80000 > cashAmount) {
						breakOut = true;
						player.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 80000;
					}
					player.playerItems[j] = player.getItems().brokenBarrows[i][0] + 1;
				}
			}
			if (breakOut)
				break;
		}
		if (totalCost > 0)
			player.getItems().deleteItem(995, player.getItems().getItemSlot(995), totalCost);
	}

	public void resetBarrows() {
		player.barrowsNpcs[0][1] = 0;
		player.barrowsNpcs[1][1] = 0;
		player.barrowsNpcs[2][1] = 0;
		player.barrowsNpcs[3][1] = 0;
		player.barrowsNpcs[4][1] = 0;
		player.barrowsNpcs[5][1] = 0;
		player.barrowsKillCount = 0;
		player.randomCoffin = Misc.random(3) + 1;
	}

	public int randomBarrows() {
		return BARROWS_ITEMS[(int) (Math.random() * BARROWS_ITEMS.length)];
	}

	public int randomRunes() {
		return RUNES[(int) (Math.random() * RUNES.length)];
	}

	/**
	 * Picking the random coffin
	 **/
	public static int getRandomCoffin() {
		return Misc.random(COFFIN_DATA.length - 1);
	}

	/**
	 * Selects the coffin and shows the interface if coffin id matches random coffin
	 **/
	public static boolean selectCoffin(Client c, int coffinId) {
		if (c.randomCoffin == 0) {
			c.randomCoffin = getRandomCoffin();
		}
		if (COFFIN_DATA[c.randomCoffin][0] == coffinId) {
			c.getDialogueList().sendDialogues(1, -1);
			return true;
		}
		return false;
	}

	public void openChest() {
		if (player.barrowsKillCount < 5) {
			player.sendMessage("You haven't yet slain all of the Barrows Brothers.");
		}
		if (player.barrowsKillCount == 5 && player.barrowsNpcs[player.randomCoffin][1] == 1) {
			player.sendMessage("I have already summoned the last Barrows Brother. I must kill him before looting the chest.");
		}
		if (player.barrowsNpcs[player.randomCoffin][1] == 0 && player.barrowsKillCount >= 5) {
			Server.npcHandler.spawnNpc(player, player.barrowsNpcs[player.randomCoffin][0], 3551, 9694 - 1, 0, 0, 120, 30, 200, 200, true, true);
			player.barrowsNpcs[player.randomCoffin][1] = 1;
		}
		if ((player.barrowsKillCount > 5 || player.barrowsNpcs[player.randomCoffin][1] == 2) && player.getItems().freeSlots() >= 2) {
			resetBarrows();
			player.getItems().addItem(randomRunes(), Misc.random(150) + 100);
			if (Misc.random(2) == 1)
				player.getItems().addItem(randomBarrows(), 1);
			player.getItems().addItem(randomRunes(), Misc.random(150) + 100);
			player.getTeleports().locationTeleport(Location.BARROWS, "modern", false);
		} else if (player.barrowsKillCount > 5 && player.getItems().freeSlots() <= 1) {
			player.sendMessage("You need at least 2 inventory slot opened.");
		}
	}

}