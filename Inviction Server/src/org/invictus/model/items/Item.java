package org.invictus.model.items;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.invictus.Server;

public class Item {

	public static boolean playerCape(int itemId) {
		String[] data = { "cloak", "cape", "accumulator", "Cape", "Ava's", "Grain" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerBoots(int itemId) {
		String[] data = { "Shoes", "shoes", "boots", "Boots", "Flippers", "Chicken feet" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerGloves(int itemId) {
		String[] data = { "Gloves", "gloves", "brace", "glove", "Glove", "gauntlets", "Gauntlets", "v'brace", "vamb", "Crabclaw hook", "Pirate's hook", "Crab claw" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerShield(int itemId) {
		String[] data = { "bug lantern", "kiteshield", "satchel", "book", "Kiteshield", "shield", "Shield", "Kite", "kite", "defender", "spirit sheild", "spirit_sheild", "Chicken" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i]) && itemId != 11020 && itemId != 11021 && itemId != 11022) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerAmulet(int itemId) {
		String[] data = { "Witchwood", "amulet", "Amulet", "necklace", "Necklace", "Pendant", "pendant", "Symbol", "symbol", "scarf" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerArrows(int itemId) {
		String[] data = { "Arrows", "arrows", "Arrow", "arrow", "Bolts", "bolts", "rack", "Rack", "Bolt rack", "grapple" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerRings(int itemId) {
		String[] data = { "ring", "rings", "Ring", "Rings", };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerHats(int itemId) {
		String[] data = { "crown", "wreath", "boater", "cowl", "peg", "coif", "helm", "coif", "mask", "hat", "headband", "hood", "disguise", "cavalier", "tiara", "Saradomin full", "helmet", "Hat", "ears", "partyhat", "helm(t)", "helm(g)", "beret", "facemask", "sallet", "hat(g)", "hat(t)", "bandana", "Helm", "Coif", "bandanna", "head", "Jack lantern mask", "Earmuffs", "wig", "cap", "reaper hood", "Gnome goggles" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		switch (itemId) {
		case 2940:
			item1 = true;
			break;
		}
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerLegs(int itemId) {
		String[] data = { "tassets", "chaps", "bottoms", "gown", "trousers", "platelegs", "robe", "plateskirt", "legs", "leggings", "Pantaloons", "shorts", "Skirt", "skirt", "cuisse", "Trousers", "Shorts", };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if ((item.endsWith(data[i]) || item.contains(data[i])) && (!item.contains("top") && (!item.contains("Wizard robe") && (!item.contains("robe (g)") && (!item.contains("robe (t)") && itemId != 21468 && itemId != 21463))))) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerBody(int itemId) {
		String[] data = { "Battle-mage robe", "Trickster robe", "Varrock armour", "body", "top", "Priest gown", "apron", "shirt", "platebody", "robetop", "body(g)", "body(t)", "Chicken wings", "Wizard robe (g)", "Wizard robe (t)", "body", "brassard", "blouse", "tunic", "leathertop", "Saradomin plate", "chainbody", "Wizard robe", "hauberk", "Shirt", "torso", "chestplate", "wings", "Zamorak d'hide", "Saradomin d'hide", "Guthix dragonhide" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	private static String[] fullbody = { "Third-age druidic robe top", "Battle-mage robe", "Vanguard body", "Trickster robe", "Pernix body", "Varrock armour", "top", "chestplate", "shirt", "platebody", "Ahrims robetop", "Karils leathertop", "brassard", "Robe top", "robetop", "Saradomin plate", "platebody (t)", "platebody (g)", "chestplate", "Wizard robe", "Chicken wings", "torso", "hauberk", "Dragon chainbody", "Vesta's chainbody", "Zamorak d'hide", "Saradomin d'hide", "Guthix dragonhide" };

	private static String[] fullhat = { "med helm", "coif", "Dharok's helm", "hood", "Initiate helm", "Chicken head", "Coif", "Skeleton mask", "Helm of neitiznot", "Armadyl helmet", "Berserker helm", "Void ranger helm", "Archer helm", "Farseer helm", "Warrior helm", "Void", "Coif", "Void melee helm", "cowl", "Snakeskin body", "Mitre", "mitre", "Spiny helmet", "Saradomin full", "Void mage helm", "Reindeer hat" };

	private static String[] fullmask = { "full helm (t)", "full helm (g)", "Vanguard helm", "Trickster helm", "full helm (or)", "full helm (sp)", "Full slayer helm", "Slayer helm", "full helm", "bandanna", "skeletal", "mask", "Verac's helm", "Guthan's helm", "Karil's coif", "mask", "Torag's helm", "sallet", "Saradomin helm", "reaper hood", };

	public static boolean isFullBody(int itemId) {
		String weapon = getItemName(itemId);
		if (weapon == null) {
			return false;
		}
		for (int i = 0; i < fullbody.length; i++) {
			if (weapon.endsWith(fullbody[i]) || weapon.contains(fullbody[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFullHelm(int itemId) {
		String weapon = getItemName(itemId);
		if (weapon == null) {
			return false;
		}
		for (int i = 0; i < fullhat.length; i++) {
			if (weapon.endsWith(fullhat[i]) && itemId != 2631) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFullMask(int itemId) {
		String weapon = getItemName(itemId);
		if (weapon == null) {
			return false;
		}
		for (int i = 0; i < fullmask.length; i++) {
			if ((weapon.endsWith(fullmask[i]) && itemId != 11280 && itemId != 2631 && itemId != 4164 && itemId != 11278 && itemId != 9925 && itemId != 11282 && itemId != 11282 && itemId != 11277 && itemId != 8921) || itemId == 9096) {
				return true;
			}
		}
		return false;
	}

	public static String getItemName(int id) {
		for (int j = 0; j < Server.itemHandler.ItemList.length; j++) {
			if (Server.itemHandler.ItemList[j] != null) {
				if (Server.itemHandler.ItemList[j].itemId == id) {
					return Server.itemHandler.ItemList[j].itemName;
				}
			}
		}
		return null;
	}

	public static boolean[] itemStackable = new boolean[ItemConfiguration.ITEM_AMOUNT];
	public static boolean[] itemIsNote = new boolean[ItemConfiguration.ITEM_AMOUNT];
	public static int[] targetSlots = new int[ItemConfiguration.ITEM_AMOUNT];
	static {
		int counter = 0;
		int c;

		try {
			FileInputStream dataIn = new FileInputStream(new File("./data/item_data/stackable.dat"));
			while ((c = dataIn.read()) != -1) {
				if (c == 0) {
					itemStackable[counter] = false;
				} else {
					itemStackable[counter] = true;
				}
				counter++;
				itemStackable[13883] = true;
				itemStackable[13879] = true;
				itemStackable[7478] = true;
				itemStackable[15263] = true;
			}
			dataIn.close();
		} catch (IOException e) {
			System.out.println("Critical error while loading stackabledata! Trace:");
			e.printStackTrace();
		}

		counter = 0;

		try {
			FileInputStream dataIn = new FileInputStream(new File("./data/item_data/notes.dat"));
			while ((c = dataIn.read()) != -1) {
				if (c == 0) {
					itemIsNote[counter] = true;
				} else {
					itemIsNote[counter] = false;
				}
				counter++;
			}
			dataIn.close();
		} catch (IOException e) {
			System.out.println("Critical error while loading notedata! Trace:");
			e.printStackTrace();
		}

		counter = 0;
		try {
			FileInputStream dataIn = new FileInputStream(new File("./data/item_data/equipment.dat"));
			while ((c = dataIn.read()) != -1) {
				targetSlots[counter++] = c;
			}
			dataIn.close();
		} catch (IOException e) {
			System.out.println("Critical error while loading notedata! Trace:");
			e.printStackTrace();
		}
	}

}