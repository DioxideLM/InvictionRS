package org.invictus.model.players;

import org.invictus.world.Location;

/**
 * A class for handling the various forms of teleportation found throughout the game.
 * 
 * @author Joe
 *
 */

public class Teleports {

	private Client player;

	public Teleports(Client player) {
		this.player = player;
	}

	public static final int HOME_TELE_BUTTON_MODERN = 75010;
	public static final int HOME_TELE_BUTTON_ANCIENT = 84237;
	public static final int MONSTER_TELE_BUTTON_MODERN = 4140;
	public static final int MONSTER_TELE_BUTTON_ANCIENT = 50235;
	public static final int MINIGAME_TELE_BUTTON_MODERN = 4143;
	public static final int MINIGAME_TELE_BUTTON_ANCIENT = 50245;
	public static final int BOSS_TELE_BUTTON_MODERN = 4146;
	public static final int BOSS_TELE_BUTTON_ANCIENT = 50253;
	public static final int SKILL_TELE_BUTTON_MODERN = 4150; // old 6004
	public static final int SKILL_TELE_BUTTON_ANCIENT = 51005; // old 51013
	public static final int CITY_TELE_BUTTON_MODERN = 6004;
	public static final int CITY_TELE_BUTTON_ANCIENT = 51013;
	public static final int PK_TELE_BUTTON_MODERN = 6005; // old 4150
	public static final int PK_TELE_BUTTON_ANCIENT = 51023; // old 51005
	public static final int DONATOR_AREA_TELEPORT_BUTTON_MODERN = 29031;
	public static final int DONATOR_AREA_TELEPORT_BUTTON_ANCIENT = 51031;
	public static final int APE_ATOLL_TELEPORT_BUTTON_MODERN = 72038;
	public static final int APE_ATOLL_TELEPORT_BUTTON_ANCIENT = 51039;
	public static final int HOUSE_TELEPORT_BUTTON_MODERN = 75008;

	/**
	 * Constants for the Teleport Dialogue Action ID's.
	 */
	public static final int MONSTER_TELEPORTS = 1;
	public static final int MINIGAME_TELEPORTS = 2;
	public static final int BOSS_TELEPORTS = 3;
	public static final int SKILL_TELEPORTS = 4;
	public static final int CITY_TELEPORTS = 5;
	public static final int PK_TELEPORTS = 6;
	public static final int AGILITY_TELEPORTS = 7;

	public void locationTeleport(int[] coords, String teleportType, boolean wildernessTeleport) {
		startTeleport(coords[0], coords[1], coords[2], "modern", false);
	}

	public void startTeleport(int x, int y, int height, String teleportType, boolean wildernessTeleport) {
		if (player.duelStatus == 5) {
			player.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (player.inWild() && player.wildLevel > 20 && !wildernessTeleport) {
			player.sendMessage("You can't teleport above level 20 in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!player.isDead && player.teleTimer == 0 && player.respawnTimer == -6) {
			if (player.playerIndex > 0 || player.npcIndex > 0)
				player.getCombat().resetPlayerAttack();
			player.stopMovement();
			player.getPlayerAssistant().removeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			player.teleHeight = height;
			switch (teleportType) {
			case "modern":
				player.startAnimation(714);
				player.teleEndAnimation = 715;
				player.teleGfx = 308;
				player.teleTimer = 11;
				break;
			case "ancient":
				player.startAnimation(1979);
				player.teleEndAnimation = 0;
				player.teleGfx = 0;
				player.teleTimer = 9;
				player.gfx0(392);
				break;
			case "lunar":
				break;
			case "donator":
				break;
			}
		}
	}

	public void processTeleport() {
		player.teleportToX = player.teleX;
		player.teleportToY = player.teleY;
		player.heightLevel = player.teleHeight;
		if (player.teleEndAnimation > 0) {
			player.startAnimation(player.teleEndAnimation);
		}
	}

	public void displayMonsterTeleports() {
		player.getDialogueHandler().sendOption("Rock Crabs", "Taverly Dungeon", "Slayer Tower", "Brimhaven Dungeon", "Crash Island");
		player.dialogueTeleportAction = MONSTER_TELEPORTS;
	}

	public void displayMinigameTeleports() {
		player.getDialogueHandler().sendOption("Barrows", "Pest Control", "Fight Caves", "Duel Arena", "Barbarian Assault");
		player.dialogueTeleportAction = MINIGAME_TELEPORTS;
	}

	public void displayBossTeleports() {
		player.getDialogueHandler().sendOption("God Wars Dungeon", "Dagannoth Kings", "Kalphite Queen", "King Black Dragon", "Chaos Elemental");
		player.dialogueTeleportAction = BOSS_TELEPORTS;
	}

	public void displaySkillTeleports() {
		player.getDialogueHandler().sendOption("Agility", "Mining / Smithing", "Fishing / Cooking", "Woodcutting", "Farming");
		player.dialogueTeleportAction = SKILL_TELEPORTS;
	}

	public void displayAgilityTeleports() {
		player.getDialogueHandler().sendOption("Gnome Agility Course", "Barbarian Agility Course", "Wilderness Agility Course");
		player.dialogueTeleportAction = AGILITY_TELEPORTS;
	}

	public void displayCityTeleports() {
		player.getDialogueHandler().sendOption("Varrock", "Lumbridge", "Falador", "Camelot", "Ardougne");
		player.dialogueTeleportAction = CITY_TELEPORTS;
	}

	public void displayPKTeleports() {
		player.getDialogueHandler().sendOption("Varrock Wild", "Graveyard", "Level 44 Obelisk", "Greater Demons", "Ardougne Lever");
		player.dialogueTeleportAction = PK_TELEPORTS;
	}

	public void homeTeleport() {
		String type = player.playerMagicBook == 0 ? "modern" : "ancient";
		locationTeleport(Location.EDGEVILLE, type, false);
	}

	public void teleportToHouse() {
		if (player.houseOwned) {
			startTeleport(1884, 5106, 0, "modern", false);
		} else {
			player.sendMessage("You do not currently own a house. To buy one, speak to the Estate Agent in Edgeville.");
		}
	}

	public void teleportToMemberArea() {
		if (player.isMember) {
			locationTeleport(Location.MEMBER_AREA, "Modern", false);
		} else {
			player.sendMessage("You need to be a Member to use this teleport!");
		}
	}

}
