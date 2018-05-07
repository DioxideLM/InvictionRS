package org.invictus.model.players.content.dialogue;

import org.invictus.model.players.Client;
import org.invictus.model.players.Teleports;
import org.invictus.model.players.content.quests.testquest.TestQuest;
import org.invictus.world.Location;

/**
 * A class for easily handling the buttons found in the dialogue interfaces.
 * 
 * @author Thoby
 * @author Joe
 *
 */

public class DialogueButtons {

	private Client player;

	public DialogueButtons(Client player) {
		this.player = player;
	}

	public static final int OPTIONS_2_1 = 9157;
	public static final int OPTIONS_2_2 = 9158;
	public static final int OPTIONS_3_1 = 9167;
	public static final int OPTIONS_3_2 = 9168;
	public static final int OPTIONS_3_3 = 9169;
	public static final int OPTIONS_4_1 = 9178;
	public static final int OPTIONS_4_2 = 9179;
	public static final int OPTIONS_4_3 = 9180;
	public static final int OPTIONS_4_4 = 9181;
	public static final int OPTIONS_5_1 = 9190;
	public static final int OPTIONS_5_2 = 9191;
	public static final int OPTIONS_5_3 = 9192;
	public static final int OPTIONS_5_4 = 9193;
	public static final int OPTIONS_5_5 = 9194;

	public void click(int actionbutton) {
		boolean firstOption = false;
		boolean secondOption = false;
		boolean thirdOption = false;
		boolean fourthOption = false;
		boolean fifthOption = false;

		switch (actionbutton) {
		case OPTIONS_5_1:
		case OPTIONS_4_1:
		case OPTIONS_3_1:
		case OPTIONS_2_1:
			firstOption = true;
			break;

		case OPTIONS_5_2:
		case OPTIONS_4_2:
		case OPTIONS_3_2:
		case OPTIONS_2_2:
			secondOption = true;
			break;

		case OPTIONS_5_3:
		case OPTIONS_4_3:
		case OPTIONS_3_3:
			thirdOption = true;
			break;

		case OPTIONS_5_4:
		case OPTIONS_4_4:
			fourthOption = true;
			break;

		case OPTIONS_5_5:
			fifthOption = true;
			break;
		}

		switch (player.dialogueTeleportAction) {

		case Teleports.MONSTER_TELEPORTS:
			if (firstOption) {
				player.getTeleports().locationTeleport(Location.RELLEKKA_ROCK_CRABS, "Modern", false);
			}
			if (secondOption) {
				player.getTeleports().locationTeleport(Location.TAVERLY_DUNGEON, "Modern", false);
			}
			if (thirdOption) {
				player.getTeleports().locationTeleport(Location.SLAYER_TOWER, "Modern", false);
			}
			if (fourthOption) {
				player.getTeleports().locationTeleport(Location.BRIMHAVEN_DUNGEON, "Modern", false);
			}
			if (fifthOption) {
				player.getTeleports().locationTeleport(Location.CRASH_ISLAND, "Modern", false);
			}
			break;

		case Teleports.MINIGAME_TELEPORTS:
			if (firstOption) {
				player.getTeleports().locationTeleport(Location.BARROWS, "Modern", false);
			}
			if (secondOption) {
				player.getTeleports().locationTeleport(Location.PEST_CONTROL, "Modern", false);
			}
			if (thirdOption) {
				player.getTeleports().locationTeleport(Location.TZHAAR_DUNGEON, "Modern", false);
			}
			if (fourthOption) {
				player.getTeleports().locationTeleport(Location.DUEL_ARENA, "Modern", false);
			}
			if (fifthOption) {
				player.getTeleports().locationTeleport(Location.BARBARIAN_ASSAULT_LOBBY, "Modern", false);
			}
			break;

		case Teleports.BOSS_TELEPORTS:
			if (firstOption) {
				player.getTeleports().locationTeleport(Location.GOD_WARS_DUNGEON_ENTRANCE, "Modern", false);
			}
			if (secondOption) {
				player.getTeleports().locationTeleport(Location.WATERBIRTH_DUNGEON, "Modern", false);
			}
			if (thirdOption) {
				player.getTeleports().locationTeleport(Location.KALPHITE_DUNGEON_ENTRANCE, "Modern", false);
			}
			if (fourthOption) {
				player.getTeleports().locationTeleport(Location.KING_BLACK_DRAGON_LAIR_GATE, "Modern", false);
			}
			if (fifthOption) {
				player.getTeleports().locationTeleport(Location.ROGUES_CASTLE, "Modern", false);
			}
			break;

		case Teleports.SKILL_TELEPORTS:
			if (firstOption) {
				player.getTeleports().displayAgilityTeleports();
			}
			if (secondOption) {
				player.getTeleports().locationTeleport(Location.FALADOR_MINE, "Modern", false);
			}
			if (thirdOption) {
				player.getTeleports().locationTeleport(Location.FISHING_GUILD, "Modern", false);
			}
			if (fourthOption) {
				player.getTeleports().locationTeleport(Location.CAMELOT, "Modern", false);
			}
			if (fifthOption) {
				player.getTeleports().locationTeleport(Location.CATHERBY, "Modern", false);
			}
			break;

		case Teleports.AGILITY_TELEPORTS:
			if (firstOption) {
				player.getTeleports().locationTeleport(Location.GNOME_AGILITY_COURSE, "Modern", false);
			}
			if (secondOption) {
				player.getTeleports().locationTeleport(Location.BARBARIAN_AGILITY_COURSE, "Modern", false);
			}
			if (thirdOption) {
				player.getTeleports().locationTeleport(Location.WILDERNESS_AGILITY_COURSE, "Modern", false);
			}
			break;

		case Teleports.CITY_TELEPORTS:
			if (firstOption) {
				player.getTeleports().locationTeleport(Location.VARROCK, "Modern", false);
			}
			if (secondOption) {
				player.getTeleports().locationTeleport(Location.LUMBRIDGE, "Modern", false);
			}
			if (thirdOption) {
				player.getTeleports().locationTeleport(Location.FALADOR, "Modern", false);
			}
			if (fourthOption) {
				player.getTeleports().locationTeleport(Location.CAMELOT, "Modern", false);
			}
			if (fifthOption) {
				player.getTeleports().locationTeleport(Location.ARDOUGNE, "Modern", false);
			}
			break;

		case Teleports.PK_TELEPORTS:
			break;
		}
	}
}
