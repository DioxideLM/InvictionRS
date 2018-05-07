package org.invictus.model.players;

import org.invictus.Server;
import org.invictus.model.objects.Object;
import org.invictus.model.players.content.minigames.championchallenge.ChampionChallenge;
import org.invictus.model.players.content.minigames.fightcaves.FightCavesMisc;
import org.invictus.model.players.content.quests.testquest.TestQuest;
import org.invictus.model.players.skills.agility.BarbarianAgilityCourse;
import org.invictus.model.players.skills.agility.GnomeAgilityCourse;
import org.invictus.model.players.skills.thieving.Pickpocketing;
import org.invictus.model.players.skills.thieving.WallSafes;
import org.invictus.util.Misc;
import org.invictus.util.ScriptManager;
import org.invictus.world.Location;

public class ActionHandler {

	private Client player;

	public ActionHandler(Client player) {
		this.player = player;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		player.clickObjectType = 0;
		player.turnPlayerTo(obX, obY);
		// c.sendMessage("Object type: " + objectType);
		switch (objectType) {
		
		case WallSafes.WALL_SAFE_ID:
			player.getWallSafes().crackSafe();
			break;

		case ChampionChallenge.CHAMPION_GUILD_TRAPDOOR_CLOSED:
			player.getChampChallenge().openTrapdoor();
			break;

		case ChampionChallenge.CHAMPION_GUILD_TRAPDOOR_OPEN:
			player.getChampChallenge().enterChampionArea();
			break;

		case ChampionChallenge.CHAMPION_DUNGEON_EXIT_LADDER:
			player.getChampChallenge().exitChampionArea();
			break;

		case ChampionChallenge.CHAMPION_STATUE_CLOSED:
			player.getChampChallenge().initiateChallenge();
			break;

		case ChampionChallenge.CHAMPION_STATUE_OPEN:
			player.getChampChallenge().enterArena();
			break;

		case ChampionChallenge.CHAMPION_STATUE_LADDER:
			player.getChampChallenge().leaveFight();
			break;

		case ChampionChallenge.ARENA_GATES:
			player.getChampChallenge().passGate();
			break;

		case 1765:
			player.getPlayerAssistant().movePlayer(3067, 10256, 0);
			break;
		case 2882:
		case 2883:
			if (player.objectX == 3268) {
				if (player.absX < player.objectX) {
					player.getPlayerAssistant().walkTo(1, 0);
				} else {
					player.getPlayerAssistant().walkTo(-1, 0);
				}
			}
			break;
		case 272:
			player.getPlayerAssistant().movePlayer(player.absX, player.absY, 1);
			break;

		case 273:
			player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
			break;
		case 245:
			player.getPlayerAssistant().movePlayer(player.absX, player.absY + 2, 2);
			break;
		case 246:
			player.getPlayerAssistant().movePlayer(player.absX, player.absY - 2, 1);
			break;
		case 1766:
			player.getPlayerAssistant().movePlayer(3016, 3849, 0);
			break;
		case 6552:
			if (player.playerMagicBook == 0) {
				player.playerMagicBook = 1;
				player.setSidebarInterface(6, 12855);
				player.sendMessage("An ancient wisdomin fills your mind.");
				player.getPlayerAssistant().resetAutocast();
			} else {
				player.setSidebarInterface(6, 1151); // modern
				player.playerMagicBook = 0;
				player.sendMessage("You feel a drain on your memory.");
				player.autocastId = -1;
				player.getPlayerAssistant().resetAutocast();
			}
			break;

		case 410:
			if (player.playerMagicBook == 0) {
				player.playerMagicBook = 2;
				player.setSidebarInterface(6, 29999);
				player.sendMessage("Lunar wisdom fills your mind.");
				player.getPlayerAssistant().resetAutocast();
			} else {
				player.setSidebarInterface(6, 1151); // modern
				player.playerMagicBook = 0;
				player.sendMessage("You feel a drain on your memory.");
				player.autocastId = -1;
				player.getPlayerAssistant().resetAutocast();
			}
			break;

		case 1816:
			player.getTeleports().locationTeleport(Location.KING_BLACK_DRAGON_LAIR, "modern", true);
			break;
		case 1817:
			player.getTeleports().locationTeleport(Location.KING_BLACK_DRAGON_LAIR_ENTRANCE_LEVER, "modern", false);
			break;
		case 1814:
			// ardy lever
			player.getTeleports().locationTeleport(Location.DESERTED_KEEP, "modern", false);
			break;

		case 9356:
			// c.getPA().enterCaves();
			player.sendMessage("Temporarily removed due to bugs.");
			break;
		case 1733:
			player.getPlayerAssistant().movePlayer(player.absX, player.absY + 6393, 0);
			break;

		case 1734:
			player.getPlayerAssistant().movePlayer(player.absX, player.absY - 6396, 0);
			break;

		case FightCavesMisc.CAVE_EXIT_ID:
			player.getFightCavesMisc().resetTzhaar();
			break;

		case 8959:
			if (player.getX() == 2490 && (player.getY() == 10146 || player.getY() == 10148)) {
				if (player.getPlayerAssistant().checkForPlayer(2490, player.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, player.objectX, player.objectY, player.heightLevel, 1, 10, 8959, 15);
				}
			}
			break;

		case 2213:
		case 14367:
		case 11758:
		case 3193:
			player.getBank().openUpBank();
			break;

		case 10177:
			player.getPlayerAssistant().movePlayer(1890, 4407, 0);
			break;
		case 10230:
			player.getPlayerAssistant().movePlayer(2900, 4449, 0);
			break;
		case 10229:
			player.getPlayerAssistant().movePlayer(1912, 4367, 0);
			break;
		case 2623:
			if (player.absX >= player.objectX)
				player.getPlayerAssistant().walkTo(-1, 0);
			else
				player.getPlayerAssistant().walkTo(1, 0);
			break;
		// pc boat
		case 14315:
			player.getPlayerAssistant().movePlayer(2661, 2639, 0);
			break;
		case 14314:
			player.getPlayerAssistant().movePlayer(2657, 2639, 0);
			break;

		case 1596:
		case 1597:
			if (player.getY() >= player.objectY)
				player.getPlayerAssistant().walkTo(0, -1);
			else
				player.getPlayerAssistant().walkTo(0, 1);
			break;

		case 14235:
		case 14233:
			if (player.objectX == 2670)
				if (player.absX <= 2670)
					player.absX = 2671;
				else
					player.absX = 2670;
			if (player.objectX == 2643)
				if (player.absX >= 2643)
					player.absX = 2642;
				else
					player.absX = 2643;
			if (player.absX <= 2585)
				player.absY += 1;
			else
				player.absY -= 1;
			player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
			break;

		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			player.sendMessage("This will be added soon. TODO: Choose which Obelisk");
			break;
		case 4387:
			// Server.castleWars.joinWait(c,1);
			break;

		case 4388:
			// Server.castleWars.joinWait(c,2);
			break;

		case 4408:
			// Server.castleWars.joinWait(c,3);
			break;

		case 9369:
			if (player.getY() > 5175)
				player.getPlayerAssistant().movePlayer(2399, 5175, 0);
			else
				player.getPlayerAssistant().movePlayer(2399, 5177, 0);
			break;

		case 9368:
			if (player.getY() < 5169) {
				Server.fightPits.removePlayerFromPits(player.playerId);
				player.getPlayerAssistant().movePlayer(2399, 5169, 0);
			}
			break;
		case 4411:
		case 4415:
		case 4417:
		case 4418:
		case 4419:
		case 4420:
		case 4469:
		case 4470:
		case 4911:
		case 4912:
		case 1747:
		case 1757:
			// Server.castleWars.handleObjects(c, objectType, obX, obY);
			break;

		case GnomeAgilityCourse.LOG:
		case GnomeAgilityCourse.FIRST_NET:
		case GnomeAgilityCourse.FIRST_BRANCH:
		case GnomeAgilityCourse.TIGHTROPE:
		case GnomeAgilityCourse.SECOND_BRANCH_NORTH:
		case GnomeAgilityCourse.SECOND_BRANCH_WEST:
		case GnomeAgilityCourse.SECOND_NET:
		case GnomeAgilityCourse.PIPE_EAST:
		case GnomeAgilityCourse.PIPE_WEST:
			player.getGnomeAgilityCourse().agilityCourse(objectType);
			break;
			
		case BarbarianAgilityCourse.ROPESWING:
		case BarbarianAgilityCourse.LOG:
		case BarbarianAgilityCourse.NET:
		case BarbarianAgilityCourse.BALANCING_LEDGE:
		case BarbarianAgilityCourse.LADDER:
		case BarbarianAgilityCourse.CRUMBLING_WALL:
			player.getBarbarianAgilityCourse().agilityCourse(objectType);
			break;

		/*
		 * Barrows Chest
		 */
		case 10284:
			player.getBarrows().openChest();
			break;
		/*
		 * Doors
		 */
		case 6749:
			if (obX == 3562 && obY == 9678) {
				player.getPlayerAssistant().object(3562, 9678, 6749, -3, 0);
				player.getPlayerAssistant().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9677) {
				player.getPlayerAssistant().object(3558, 9677, 6749, -1, 0);
				player.getPlayerAssistant().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if (obX == 3558 && obY == 9677) {
				player.getPlayerAssistant().object(3562, 9678, 6749, -3, 0);
				player.getPlayerAssistant().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9678) {
				player.getPlayerAssistant().object(3558, 9677, 6749, -1, 0);
				player.getPlayerAssistant().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if (obX == 3551 && obY == 9684) {
				player.sendMessage("You cant open this door..");
			}
			break;
		case 6746:
			if (obX == 3552 && obY == 9684) {
				player.sendMessage("You cant open this door..");
			}
			break;
		case 6748:
			if (obX == 3545 && obY == 9678) {
				player.getPlayerAssistant().object(3545, 9678, 6748, -3, 0);
				player.getPlayerAssistant().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9677) {
				player.getPlayerAssistant().object(3541, 9677, 6748, -1, 0);
				player.getPlayerAssistant().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if (obX == 3545 && obY == 9677) {
				player.getPlayerAssistant().object(3545, 9678, 6748, -3, 0);
				player.getPlayerAssistant().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9678) {
				player.getPlayerAssistant().object(3541, 9677, 6748, -1, 0);
				player.getPlayerAssistant().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if (obX == 3534 && obY == 9684) {
				player.getPlayerAssistant().object(3534, 9684, 6726, -4, 0);
				player.getPlayerAssistant().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3535 && obY == 9688) {
				player.getPlayerAssistant().object(3535, 9688, 6726, -2, 0);
				player.getPlayerAssistant().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if (obX == 3535 && obY == 9684) {
				player.getPlayerAssistant().object(3534, 9684, 6726, -4, 0);
				player.getPlayerAssistant().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3534 && obY == 9688) {
				player.getPlayerAssistant().object(3535, 9688, 6726, -2, 0);
				player.getPlayerAssistant().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if (obX == 3545 && obY == 9695) {
				player.getPlayerAssistant().object(3545, 9694, 6724, -1, 0);
				player.getPlayerAssistant().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9694) {
				player.getPlayerAssistant().object(3541, 9694, 6724, -1, 0);
				player.getPlayerAssistant().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if (obX == 3545 && obY == 9694) {
				player.getPlayerAssistant().object(3545, 9694, 6724, -1, 0);
				player.getPlayerAssistant().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9695) {
				player.getPlayerAssistant().object(3541, 9694, 6724, -1, 0);
				player.getPlayerAssistant().object(3541, 9695, 6743, -3, 0);
			}
			break;
		/*
		 * Cofins
		 */
		case 6707: // verac
			player.getPlayerAssistant().movePlayer(3556, 3298, 0);
			break;

		case 6823:
			if (org.invictus.model.players.content.minigames.barrows.Barrows.selectCoffin(player, objectType)) {
				return;
			}
			if (player.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(player, 2030, player.getX(), player.getY() - 1, -1, 0, 120, 25, 200, 200, true, true);
				player.barrowsNpcs[0][1] = 1;
			} else {
				player.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // torag
			player.getPlayerAssistant().movePlayer(3553, 3283, 0);
			break;

		case 6772:
			if (org.invictus.model.players.content.minigames.barrows.Barrows.selectCoffin(player, objectType)) {
				return;
			}
			if (player.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(player, 2029, player.getX() + 1, player.getY(), -1, 0, 120, 20, 200, 200, true, true);
				player.barrowsNpcs[1][1] = 1;
			} else {
				player.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6705: // karil stairs
			player.getPlayerAssistant().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if (org.invictus.model.players.content.minigames.barrows.Barrows.selectCoffin(player, objectType)) {
				return;
			}
			if (player.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(player, 2028, player.getX(), player.getY() - 1, -1, 0, 90, 17, 200, 200, true, true);
				player.barrowsNpcs[2][1] = 1;
			} else {
				player.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6704: // guthan stairs
			player.getPlayerAssistant().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if (org.invictus.model.players.content.minigames.barrows.Barrows.selectCoffin(player, objectType)) {
				return;
			}
			if (player.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(player, 2027, player.getX(), player.getY() - 1, -1, 0, 120, 23, 200, 200, true, true);
				player.barrowsNpcs[3][1] = 1;
			} else {
				player.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6703: // dharok stairs
			player.getPlayerAssistant().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if (org.invictus.model.players.content.minigames.barrows.Barrows.selectCoffin(player, objectType)) {
				return;
			}
			if (player.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(player, 2026, player.getX(), player.getY() - 1, -1, 0, 120, 45, 250, 250, true, true);
				player.barrowsNpcs[4][1] = 1;
			} else {
				player.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6702: // ahrim stairs
			player.getPlayerAssistant().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if (org.invictus.model.players.content.minigames.barrows.Barrows.selectCoffin(player, objectType)) {
				return;
			}
			if (player.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(player, 2025, player.getX(), player.getY() - 1, -1, 0, 90, 19, 200, 200, true, true);
				player.barrowsNpcs[5][1] = 1;
			} else {
				player.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 1276:
		case 1278:// trees
			player.woodcut[0] = 1511;
			player.woodcut[1] = 1;
			player.woodcut[2] = 25;
			player.getWoodcutting().startWoodcutting(player.woodcut[0], player.woodcut[1], player.woodcut[2]);
			break;

		case 1281: // oak
			player.woodcut[0] = 1521;
			player.woodcut[1] = 15;
			player.woodcut[2] = 37;
			player.getWoodcutting().startWoodcutting(player.woodcut[0], player.woodcut[1], player.woodcut[2]);
			break;

		case 1308: // willow
			player.woodcut[0] = 1519;
			player.woodcut[1] = 30;
			player.woodcut[2] = 68;
			player.getWoodcutting().startWoodcutting(player.woodcut[0], player.woodcut[1], player.woodcut[2]);
			break;

		case 1307: // maple
			player.woodcut[0] = 1517;
			player.woodcut[1] = 45;
			player.woodcut[2] = 100;
			player.getWoodcutting().startWoodcutting(player.woodcut[0], player.woodcut[1], player.woodcut[2]);
			break;

		case 1309: // yew
			player.woodcut[0] = 1515;
			player.woodcut[1] = 60;
			player.woodcut[2] = 175;
			player.getWoodcutting().startWoodcutting(player.woodcut[0], player.woodcut[1], player.woodcut[2]);
			break;

		case 1306: // yew
			player.woodcut[0] = 1513;
			player.woodcut[1] = 75;
			player.woodcut[2] = 250;
			player.getWoodcutting().startWoodcutting(player.woodcut[0], player.woodcut[1], player.woodcut[2]);
			break;

		case 2090:// copper
		case 2091:
			player.mining[0] = 436;
			player.mining[1] = 1;
			player.mining[2] = 18;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 2094:// tin
			player.mining[0] = 438;
			player.mining[1] = 1;
			player.mining[2] = 18;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 145856:
		case 2092:
		case 2093: // iron
			player.mining[0] = 440;
			player.mining[1] = 15;
			player.mining[2] = 35;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 14850:
		case 14851:
		case 14852:
		case 2096:
		case 2097: // coal
			player.mining[0] = 453;
			player.mining[1] = 30;
			player.mining[2] = 50;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 2098:
		case 2099:
			player.mining[0] = 444;
			player.mining[1] = 40;
			player.mining[2] = 65;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 2102:
		case 2103:
		case 14853:
		case 14854:
		case 14855: // mith ore
			player.mining[0] = 447;
			player.mining[1] = 55;
			player.mining[2] = 80;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 2105:
		case 14862: // addy ore
			player.mining[0] = 449;
			player.mining[1] = 70;
			player.mining[2] = 95;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 14859:
		case 14860: // rune ore
			player.mining[0] = 451;
			player.mining[1] = 85;
			player.mining[2] = 125;
			player.getMining().startMining(player.mining[0], player.mining[1], player.mining[2]);
			break;

		case 8143:
			if (player.farm[0] > 0 && player.farm[1] > 0) {
				player.getFarming().pickHerb();
			}
			break;

		// DOORS
		case 1516:
		case 1519:
			if (player.objectY == 9698) {
				if (player.absY >= player.objectY)
					player.getPlayerAssistant().walkTo(0, -1);
				else
					player.getPlayerAssistant().walkTo(0, 1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, player.objectX, player.objectY, 0);
			break;

		case 9319:
			if (player.heightLevel == 0)
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 1);
			else if (player.heightLevel == 1)
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 2);
			break;

		case 9320:
			if (player.heightLevel == 1)
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
			else if (player.heightLevel == 2)
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 1);
			break;

		case 4496:
		case 4494:
			if (player.heightLevel == 2) {
				player.getPlayerAssistant().movePlayer(player.absX - 5, player.absY, 1);
			} else if (player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(player.absX + 5, player.absY, 0);
			}
			break;

		case 4493:
			if (player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(player.absX - 5, player.absY, 1);
			} else if (player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(player.absX + 5, player.absY, 2);
			}
			break;

		case 4495:
			if (player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(player.absX + 5, player.absY, 2);
			}
			break;

		case 5126:
			if (player.absY == 3554)
				player.getPlayerAssistant().walkTo(0, 1);
			else
				player.getPlayerAssistant().walkTo(0, -1);
			break;

		case 1755:
			if (player.objectX == 2884 && player.objectY == 9797)
				player.getPlayerAssistant().movePlayer(player.absX, player.absY - 6400, 0);
			break;
		case 1759:
			if (player.objectX == 2884 && player.objectY == 3397)
				player.getPlayerAssistant().movePlayer(player.absX, player.absY + 6400, 0);
			break;
		case 409:
			if (player.playerLevel[5] < player.getPlayerAssistant().getLevelForXP(player.playerXP[5])) {
				player.startAnimation(645);
				player.playerLevel[5] = player.getPlayerAssistant().getLevelForXP(player.playerXP[5]);
				player.sendMessage("You recharge your prayer points.");
				player.getPlayerAssistant().refreshSkill(5);
			} else {
				player.sendMessage("You already have full prayer points.");
			}
			break;
		case 2873:
			if (!player.getItems().ownsCape()) {
				player.startAnimation(645);
				player.sendMessage("Saradomin blesses you with a cape.");
				player.getItems().addItem(2412, 1);
			}
			break;
		case 2875:
			if (!player.getItems().ownsCape()) {
				player.startAnimation(645);
				player.sendMessage("Guthix blesses you with a cape.");
				player.getItems().addItem(2413, 1);
			}
			break;
		case 2874:
			if (!player.getItems().ownsCape()) {
				player.startAnimation(645);
				player.sendMessage("Zamorak blesses you with a cape.");
				player.getItems().addItem(2414, 1);
			}
			break;
		case 2879:
			player.getPlayerAssistant().movePlayer(2538, 4716, 0);
			break;
		case 2878:
			player.getPlayerAssistant().movePlayer(2509, 4689, 0);
			break;
		case 5960:
			player.getTeleports().locationTeleport(Location.MAGE_ARENA_BANK_ENTRY_LEVER, "modern", true);
			break;

		case 1815:
			player.getTeleports().locationTeleport(Location.EDGEVILLE, "modern", true);
			break;

		case 9706:
			player.getTeleports().locationTeleport(Location.MAGE_ARENA_LEVER_EXIT, "modern", true);
			break;
		case 9707:
			player.getTeleports().locationTeleport(Location.MAGE_ARENA_LEVER_ENTRY, "modern", true);
			break;

		case 5959:
			player.getTeleports().locationTeleport(Location.MAGE_ARENA_BANK, "modern", true);
			break;

		case 2558:
			player.sendMessage("This door is locked.");
			break;

		case 9294:
			if (player.absX < player.objectX) {
				player.getPlayerAssistant().movePlayer(player.objectX + 1, player.absY, 0);
			} else if (player.absX > player.objectX) {
				player.getPlayerAssistant().movePlayer(player.objectX - 1, player.absY, 0);
			}
			break;

		case 9293:
			if (player.absX < player.objectX) {
				player.getPlayerAssistant().movePlayer(2892, 9799, 0);
			} else {
				player.getPlayerAssistant().movePlayer(2886, 9799, 0);
			}
			break;
		case 10529:
		case 10527:
			if (player.absY <= player.objectY)
				player.getPlayerAssistant().walkTo(0, 1);
			else
				player.getPlayerAssistant().walkTo(0, -1);
			break;
		case 3044:
			player.getSmithing().sendSmelting();
			break;
		case 733:
			player.startAnimation(451);
			if (player.objectX == 3158 && player.objectY == 3951) {
				new Object(734, player.objectX, player.objectY, player.heightLevel, 1, 10, 733, 50);
			} else {
				new Object(734, player.objectX, player.objectY, player.heightLevel, 0, 10, 733, 50);
			}
			break;

		default:
			ScriptManager.callFunc("objectClick1_" + objectType, player, objectType, obX, obY);
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		player.clickObjectType = 0;
		// c.sendMessage("Object type: " + objectType);
		switch (objectType) {

		case ChampionChallenge.CHAMPION_GUILD_TRAPDOOR_OPEN:
			player.getChampChallenge().closeTrapdoor();
			break;

		case 11666:
		case 3044:
			player.getSmithing().sendSmelting();
			break;
		case 2213:
		case 14367:
		case 11758:
			player.getBank().openUpBank();
			break;
		case 6163:
			player.getThieving().stealFromStall(1897, 10, 1);
			break;
		case 6165:
			player.getThieving().stealFromStall(950, 30, 25);
			break;
		case 6166:
			player.getThieving().stealFromStall(1635, 60, 50);
			break;
		case 6164:
			player.getThieving().stealFromStall(7650, 100, 75);
			break;
		case 6162:
			player.getThieving().stealFromStall(1613, 170, 90);
			break;
		case 2558:
			if (System.currentTimeMillis() - player.lastLockPick < 3000 || player.freezeTimer > 0)
				break;
			if (player.getItems().playerHasItem(1523, 1)) {
				player.lastLockPick = System.currentTimeMillis();
				if (Misc.random(10) <= 3) {
					player.sendMessage("You fail to pick the lock.");
					break;
				}
				if (player.objectX == 3044 && player.objectY == 3956) {
					if (player.absX == 3045) {
						player.getPlayerAssistant().walkTo2(-1, 0);
					} else if (player.absX == 3044) {
						player.getPlayerAssistant().walkTo2(1, 0);
					}

				} else if (player.objectX == 3038 && player.objectY == 3956) {
					if (player.absX == 3037) {
						player.getPlayerAssistant().walkTo2(1, 0);
					} else if (player.absX == 3038) {
						player.getPlayerAssistant().walkTo2(-1, 0);
					}
				} else if (player.objectX == 3041 && player.objectY == 3959) {
					if (player.absY == 3960) {
						player.getPlayerAssistant().walkTo2(0, -1);
					} else if (player.absY == 3959) {
						player.getPlayerAssistant().walkTo2(0, 1);
					}
				}
			} else {
				player.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
		default:
			ScriptManager.callFunc("objectClick2_" + objectType, player, objectType, obX, obY);
			break;
		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		player.clickObjectType = 0;
		player.sendMessage("Object type: " + objectType);
		switch (objectType) {
		default:
			ScriptManager.callFunc("objectClick3_" + objectType, player, objectType, obX, obY);
			break;
		}
	}

	public void firstClickNpc(int npcId) {
		player.clickNpcType = 0;
		player.npcClickIndex = 0;
		switch (npcId) {
		
		case TestQuest.MOSOL_REI:
			player.getDialogueList().sendDialogues(24, npcId);
			break;
			
		case 706:
			player.getDialogueList().sendDialogues(9, npcId);
			break;

		case 2258:
			player.getDialogueList().sendDialogues(17, npcId);
			break;

		case 1599:
			if (player.slayerTask <= 0) {
				player.getDialogueList().sendDialogues(11, npcId);
			} else {
				player.getDialogueList().sendDialogues(13, npcId);
			}
			break;

		case 1304:
			player.getDialogueHandler().sendOption("1", "2", "3", "4", "5");
			player.dialogueTeleportAction = 1;
			break;

		case 528:
			player.getShops().openShop(1);
			break;

		case 461:
			player.getShops().openShop(2);
			break;

		case 683:
			player.getShops().openShop(3);
			break;

		case 586:
			player.getShops().openShop(4);
			break;

		case 555:
			player.getShops().openShop(6);
			break;

		case 519:
			player.getShops().openShop(8);
			break;

		case 1700:
			player.getShops().openShop(19);
			break;

		case 251:
			player.getShops().openShop(60);
			break;

		case 1282:
			player.getShops().openShop(7);
			break;

		case 1152:
			player.getDialogueList().sendDialogues(16, npcId);
			break;

		case 494:
			player.getBank().openUpBank();
			break;

		case 2566:
			player.getShops().openSkillCape();
			break;

		case 3789:
			player.sendMessage((new StringBuilder()).append("You currently have ").append(player.pcPoints).append(" pest control points.").toString());
			break;

		case 3788:
			player.getShops().openVoid();
			break;

		case 905:
			player.getDialogueList().sendDialogues(5, npcId);
			break;

		case 460:
			player.getDialogueList().sendDialogues(3, npcId);
			break;

		case 462:
			player.getDialogueList().sendDialogues(7, npcId);
			break;

		case 316:
			player.getFishing().setupFishing(317);
			break;

		case 334:
			player.getFishing().setupFishing(389);
			break;

		case 324:
			player.getFishing().setupFishing(359);
			break;

		case 314:
			player.getFishing().setupFishing(335);
			break;

		case 326:
			player.getFishing().setupFishing(7944);
			break;

		case 522:
		case 523:
			player.getShops().openShop(1);
			break;

		case 599:
			player.getPlayerAssistant().sendInterface(3559);
			player.canChangeAppearance = true;
			break;

		case 904:
			player.sendMessage((new StringBuilder()).append("You have ").append(player.magePoints).append(" points.").toString());
			break;
		}
	}

	public void secondClickNpc(int i) {
		player.clickNpcType = 0;
		player.npcClickIndex = 0;
		switch (i) {
		case 1282:
			player.getShops().openShop(7);
			break;

		case 334:
			player.getFishing().setupFishing(383);
			break;

		case 3788:
			player.getShops().openVoid();
			break;

		case 494:
			player.getBank().openUpBank();
			break;

		case 324:
			player.getFishing().setupFishing(359);
			break;

		case 904:
			player.getShops().openShop(17);
			break;

		case 522:
		case 523:
			player.getShops().openShop(1);
			break;

		case 541:
			player.getShops().openShop(5);
			break;

		case 461:
			player.getShops().openShop(2);
			break;

		case 683:
			player.getShops().openShop(3);
			break;

		case 549:
			player.getShops().openShop(4);
			break;

		case 2538:
			player.getShops().openShop(6);
			break;

		case 519:
			player.getShops().openShop(8);
			break;

		case 3789:
			player.getShops().openShop(18);
			break;

		case Pickpocketing.MAN:
		case Pickpocketing.GUARD:
		case Pickpocketing.AL_KHARID_WARRIOR:
		case Pickpocketing.KNIGHT_OF_ARDOUGNE:
		case Pickpocketing.PALADIN:
		case Pickpocketing.HERO:
			player.getPickpocketing().stealFromNPC(i);
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		player.clickNpcType = 0;
		player.npcClickIndex = 0;
		switch (npcType) {
		default:
			ScriptManager.callFunc("npcClick3_" + npcType, player, npcType);
			if (player.playerRights == 3)
				Misc.println("Third Click NPC : " + npcType);
			break;

		}
	}

}