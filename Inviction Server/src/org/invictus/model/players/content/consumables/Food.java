package org.invictus.model.players.content.consumables;

import java.util.HashMap;

import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;

/**
 * @author Sanity
 */

public class Food {

	private Client player;

	public Food(Client player) {
		this.player = player;
	}

	public static enum FoodData {
		MANTA(391, 22, "Manta Ray"), 
		SHARK(385, 20, "Shark"), 
		LOBSTER(379, 12, "Lobster"), 
		TROUT(333, 7, "Trout"), 
		SALMON(329, 9, "Salmon"), 
		SWORDFISH(373, 14, "Swordfish"), 
		TUNA(361, 10, "Tuna"), 
		MONKFISH(7946, 16, "Monkfish"), 
		SEA_TURTLE(397, 22, "Sea Turtle"), 
		TUNA_POTATO(7060, 22, "Tuna Potato");

		private int id;
		private int heal;
		private String name;

		private FoodData(int id, int heal, String name) {
			this.id = id;
			this.heal = heal;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public int getHeal() {
			return heal;
		}

		public String getName() {
			return name;
		}

		public static HashMap<Integer, FoodData> food = new HashMap<Integer, FoodData>();

		public static FoodData forId(int id) {
			return food.get(id);
		}

		static {
			for (FoodData f : FoodData.values())
				food.put(f.getId(), f);
		}
	}

	public void consumeFood(int id, int slot) {
		if (player.duelRule[6]) {
			player.sendMessage("You may not eat in this duel.");
			return;
		}
		if (System.currentTimeMillis() - player.foodDelay >= 1500 && player.playerLevel[3] > 0) {
			player.getCombat().resetPlayerAttack();
			player.attackTimer += 2;
			player.startAnimation(Animation.EAT_FOOD);
			player.getItems().deleteItem(id, slot, 1);
			FoodData f = FoodData.food.get(id);
			if (player.playerLevel[3] < player.getLevelForXP(player.playerXP[3])) {
				player.playerLevel[3] += f.getHeal();
				if (player.playerLevel[3] > player.getLevelForXP(player.playerXP[3]))
					player.playerLevel[3] = player.getLevelForXP(player.playerXP[3]);
			}
			player.foodDelay = System.currentTimeMillis();
			player.getPlayerAssistant().refreshSkill(3);
			player.sendMessage("You eat the " + f.getName() + ".");
		}
	}

	public boolean isFood(int id) {
		return FoodData.food.containsKey(id);
	}

}