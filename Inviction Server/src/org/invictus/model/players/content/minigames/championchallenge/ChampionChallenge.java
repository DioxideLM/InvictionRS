package org.invictus.model.players.content.minigames.championchallenge;

import org.invictus.Server;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.world.ObjectManager;

/**
 * Handles the Champion's Challenge minigame underneath the Champion's Guild.
 * 
 * @author Joe
 *
 */

//TODO: Make it so the player can only have one Challenge scroll at any given time.
//TODO: Figure out how to distribute the scrolls.

public class ChampionChallenge {

	Client player;

	public ChampionChallenge(Client player) {
		this.player = player;
	}

	public static final int EARTH_WARRIOR_SCROLL_ID = 6798;
	public static final int GHOUL_SCROLL_ID = 6799;
	public static final int GIANT_SCROLL_ID = 6800;
	public static final int GOBLIN_SCROLL_ID = 6801;
	public static final int HOBGOBLIN_SCROLL_ID = 6802;
	public static final int IMP_SCROLL_ID = 6803;
	public static final int JOGRE_SCROLL_ID = 6804;
	public static final int LESSER_DEMON_SCROLL_ID = 6805;
	public static final int SKELETON_SCROLL_ID = 6806;
	public static final int ZOMBIE_SCROLL_ID = 6807;
	public static final int HUMAN_SCROLL_ID = 6808;

	public static final int ARENA_GATES = 10553;
	public static final int CHAMPION_STATUE_LADDER = 10554;
	public static final int CHAMPION_STATUE_CLOSED = 10556;
	public static final int CHAMPION_STATUE_OPEN = 10557;

	public static final int CHAMPION_GUILD_TRAPDOOR_CLOSED = 10558;
	public static final int CHAMPION_GUILD_TRAPDOOR_OPEN = 10559;

	public static final int CHAMPION_DUNGEON_EXIT_LADDER = 10560;

	public static final int CHAMPION_DEFEATED_INTERFACE_ID = 15831;

	public static final int IMP = 3062;
	public static final int GOBLIN = 3060;
	public static final int SKELETON = 3065;
	public static final int ZOMBIE = 3066;
	public static final int GIANT = 3058;
	public static final int HOBGOBLIN = 3061;
	public static final int GHOUL = 3059;
	public static final int EARTH_WARRIOR = 3057;
	public static final int JOGRE = 3063;
	public static final int LESSER_DEMON = 3064;
	public static final int HUMAN = 3067;

	public static final int scrollData[] = { 6798, 6799, 6800, 6801, 6802, 6803, 6804, 6805, 6806, 6807, 6808 };

	public boolean isScroll(int id) {
		for (int i = 0; i < scrollData.length; i++)
			if (scrollData[i] == id)
				return true;
		return false;
	}

	public void openTrapdoor() {
		player.getPlayerAssistant().checkObjectSpawn(CHAMPION_GUILD_TRAPDOOR_OPEN, 3190, 3355, 0, ObjectManager.GROUND_OBJECT_2);
		player.sendMessage("You open the trapdoor.");
	}

	public void closeTrapdoor() {
		player.getPlayerAssistant().checkObjectSpawn(CHAMPION_GUILD_TRAPDOOR_CLOSED, 3190, 3355, 0, ObjectManager.GROUND_OBJECT_2);
		player.sendMessage("You close the trapdoor.");
	}

	public void enterChampionArea() {
		player.getPlayerAssistant().movePlayer(3189, 9758, 0);
		player.startAnimation(Animation.CLIMB_DOWN_LADDER);
		player.sendMessage("You climb down the trapdoor.");
	}

	public void exitChampionArea() {
		player.getPlayerAssistant().movePlayer(3191, 3355, 0);
		player.startAnimation(Animation.CLIMB_UP_LADDER);
		player.sendMessage("You climb up the ladder.");
	}

	public void redeemScrolls(int scrollId) {
		switch (scrollId) {
		case EARTH_WARRIOR_SCROLL_ID:
			if (player.champEarthWarrior == 0) {
				player.getItems().deleteItem2(EARTH_WARRIOR_SCROLL_ID, 1);
				player.champEarthWarrior = 1;
				player.sendMessage("You can now fight the Earth Warrior Champion.");
				return;
			}
			if (player.champEarthWarrior == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champEarthWarrior == 2) {
				player.sendMessage("I have already defeated the Earth Warrior Champion.");
			}
			break;
		case GHOUL_SCROLL_ID:
			if (player.champGhoul == 0) {
				player.getItems().deleteItem2(GHOUL_SCROLL_ID, 1);
				player.champGhoul = 1;
				player.sendMessage("You can now fight the Ghoul Champion.");
				return;
			}
			if (player.champGhoul == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champGhoul == 2) {
				player.sendMessage("I have already defeated the Ghoul Champion.");
			}
			break;
		case GIANT_SCROLL_ID:
			if (player.champGiant == 0) {
				player.getItems().deleteItem2(GIANT_SCROLL_ID, 1);
				player.champGiant = 1;
				player.sendMessage("You can now fight the Giant Champion.");
				return;
			}
			if (player.champGiant == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champGiant == 2) {
				player.sendMessage("I have already defeated the Giant Champion.");
			}
			break;
		case GOBLIN_SCROLL_ID:
			if (player.champGoblin == 0) {
				player.getItems().deleteItem2(GOBLIN_SCROLL_ID, 1);
				player.champGoblin = 1;
				player.sendMessage("You can now fight the Goblin Champion.");
				return;
			}
			if (player.champGoblin == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champGoblin == 2) {
				player.sendMessage("I have already defeated the Goblin Champion.");
			}
			break;
		case HOBGOBLIN_SCROLL_ID:
			if (player.champHobgoblin == 0) {
				player.getItems().deleteItem2(HOBGOBLIN_SCROLL_ID, 1);
				player.champHobgoblin = 1;
				player.sendMessage("You can now fight the Hobgoblin Champion.");
				return;
			}
			if (player.champHobgoblin == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champHobgoblin == 2) {
				player.sendMessage("I have already defeated the Hobgoblin Champion.");
			}
			break;
		case IMP_SCROLL_ID:
			if (player.champImp == 0) {
				player.getItems().deleteItem2(IMP_SCROLL_ID, 1);
				player.champImp = 1;
				player.sendMessage("You can now fight the Imp Champion.");
				return;
			}
			if (player.champImp == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champImp == 2) {
				player.sendMessage("I have already defeated the Imp Champion.");
			}
			break;
		case JOGRE_SCROLL_ID:
			if (player.champJogre == 0) {
				player.getItems().deleteItem2(JOGRE_SCROLL_ID, 1);
				player.champJogre = 1;
				player.sendMessage("You can now fight the Jogre Champion.");
				return;
			}
			if (player.champJogre == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champJogre == 2) {
				player.sendMessage("I have already defeated the Jogre Champion.");
			}
			break;
		case LESSER_DEMON_SCROLL_ID:
			if (player.champLesserDemon == 0) {
				player.getItems().deleteItem2(LESSER_DEMON_SCROLL_ID, 1);
				player.champLesserDemon = 1;
				player.sendMessage("You can now fight the Lesser Demon Champion.");
				return;
			}
			if (player.champLesserDemon == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champLesserDemon == 2) {
				player.sendMessage("I have already defeated the Lesser Demon Champion.");
			}
			break;
		case SKELETON_SCROLL_ID:
			if (player.champSkeleton == 0) {
				player.getItems().deleteItem2(SKELETON_SCROLL_ID, 1);
				player.champSkeleton = 1;
				player.sendMessage("You can now fight the Skeleton Champion.");
				return;
			}
			if (player.champSkeleton == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champSkeleton == 2) {
				player.sendMessage("I have already defeated the Skeleton Champion.");
			}
			break;
		case ZOMBIE_SCROLL_ID:
			if (player.champZombie == 0) {
				player.getItems().deleteItem2(ZOMBIE_SCROLL_ID, 1);
				player.champZombie = 1;
				player.sendMessage("You can now fight the Zombie Champion.");
				return;
			}
			if (player.champZombie == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champZombie == 2) {
				player.sendMessage("I have already defeated the Zombie Champion.");
			}
			break;
		case HUMAN_SCROLL_ID:
			if (player.champHuman == 0) {
				player.getItems().deleteItem2(HUMAN_SCROLL_ID, 1);
				player.champHuman = 1;
				player.sendMessage("You can now fight the Human Champion.");
				return;
			}
			if (player.champHuman == 1) {
				player.sendMessage("I have already accepted this invitation.");
			}
			if (player.champHuman == 2) {
				player.sendMessage("I have already defeated the Human Champion.");
			}
			break;
		}
	}

	public void initiateChallenge() {
		player.getDialogueList().sendDialogues(20, 0);
	}

	public void loadFight(int champFightLoaded) {
		player.getPlayerAssistant().closeAllWindows();
		player.champFightLoaded = champFightLoaded;
		player.sendMessage("The champion is now ready.");
		championStatue();
	}

	public void enterArena() {
		player.getPlayerAssistant().movePlayer(3182, 9758, player.playerId *4);
		player.startAnimation(Animation.CLIMB_DOWN_LADDER);
	}

	public void passGate() {
		if (player.absX == 3181 && player.absY == 9758) {
			player.getPlayerAssistant().movePlayer(3182, 9758, 0);
		}
		if (player.absX == 3182 && player.absY == 9758) {
			player.getPlayerAssistant().movePlayer(3181, 9758, 0);
		}
	}

	public void startFight(int champFightLoaded) {
		switch (champFightLoaded) {
		case 1:
			Server.npcHandler.spawnNpc(player, IMP, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 2:
			Server.npcHandler.spawnNpc(player, GOBLIN, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 3:
			Server.npcHandler.spawnNpc(player, SKELETON, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 4:
			Server.npcHandler.spawnNpc(player, ZOMBIE, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 5:
			Server.npcHandler.spawnNpc(player, GIANT, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 6:
			Server.npcHandler.spawnNpc(player, HOBGOBLIN, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 7:
			Server.npcHandler.spawnNpc(player, GHOUL, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 8:
			Server.npcHandler.spawnNpc(player, EARTH_WARRIOR, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 9:
			Server.npcHandler.spawnNpc(player, JOGRE, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 10:
			Server.npcHandler.spawnNpc(player, LESSER_DEMON, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		case 11:
			Server.npcHandler.spawnNpc(player, HUMAN, 3171, 9758, 0, 0, 120, 7, 70, 70, false, false);
			break;
		}
		player.getPlayerAssistant().movePlayer(3181, 9758, 0);
	}

	public void leaveFight() {
		player.getPlayerAssistant().movePlayer(3185, 9758, 0);
		player.champFightLoaded = 0;
		championStatue();
		player.startAnimation(Animation.CLIMB_UP_LADDER);
	}

	public void championStatue() {
		if (player.champFightLoaded != 0) {
			player.getPlayerAssistant().checkObjectSpawn(CHAMPION_STATUE_OPEN, 3184, 9758, 1, 10);
		} else {
			player.getPlayerAssistant().checkObjectSpawn(CHAMPION_STATUE_CLOSED, 3184, 9758, 1, 10);
		}
	}

	public void chooseImp() {
		if (player.champImp == 0) {
			player.getDialogueHandler().sendStatement("You haven't unlocked this champion yet!");
		}
		if (player.champImp == 1) {
			loadFight(1);
		}
		if (player.champImp == 2) {
			player.getDialogueHandler().sendStatement("You've already beaten the Imp champion!");
		}
	}

	public void chooseGoblin() {
		if (player.champGoblin == 0) {
			player.getDialogueHandler().sendStatement("You haven't unlocked this champion yet!");
		}
		if (player.champGoblin == 1) {
			loadFight(2);
		}
		if (player.champGoblin == 2) {
			player.getDialogueHandler().sendStatement("You've already beaten the Goblin champion!");
		}
	}

	public void chooseSkeleton() {
		if (player.champSkeleton == 0) {
			player.getDialogueHandler().sendStatement("You haven't unlocked this champion yet!");
		}
		if (player.champSkeleton == 1) {
			loadFight(3);
		}
		if (player.champSkeleton == 2) {
			player.getDialogueHandler().sendStatement("You've already beaten the Skeleton champion!");
		}
	}

	public void chooseZombie() {
		if (player.champZombie == 0) {
			player.getDialogueHandler().sendStatement("You haven't unlocked this champion yet!");
		}
		if (player.champZombie == 1) {
			loadFight(4);
		}
		if (player.champZombie == 2) {
			player.getDialogueHandler().sendStatement("You've already beaten the Zombie champion!");
		}
	}

}
