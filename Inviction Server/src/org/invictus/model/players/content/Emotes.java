package org.invictus.model.players.content;

import org.invictus.model.players.Client;

public class Emotes {

	private Client player;

	public Emotes(Client player) {
		this.player = player;
	}

	/**
	 * A method for changing the emote sprite depending on whether or not a player has unlocked the emote.
	 */
	public void displayEmotes() {
		player.getPlayerAssistant().sendConfig(744, 1); // blow kiss
		if (player.goblinBow) {
			player.getPlayerAssistant().sendConfig(745, 1);
		} else {
			player.getPlayerAssistant().sendConfig(745, 0);
		}
		if (player.goblinSalute) {
			player.getPlayerAssistant().sendConfig(746, 1);
		} else {
			player.getPlayerAssistant().sendConfig(746, 0);
		}
		if (player.glassBox) {
			player.getPlayerAssistant().sendConfig(747, 1);
		} else {
			player.getPlayerAssistant().sendConfig(747, 0);
		}
		if (player.climbRope) {
			player.getPlayerAssistant().sendConfig(748, 1);
		} else {
			player.getPlayerAssistant().sendConfig(748, 0);
		}
		if (player.lean) {
			player.getPlayerAssistant().sendConfig(749, 1);
		} else {
			player.getPlayerAssistant().sendConfig(749, 0);
		}
		if (player.glassWall) {
			player.getPlayerAssistant().sendConfig(750, 1);
		} else {
			player.getPlayerAssistant().sendConfig(750, 0);
		}
		if (player.idea) {
			player.getPlayerAssistant().sendConfig(751, 1);
		} else {
			player.getPlayerAssistant().sendConfig(751, 0);
		}
		if (player.stomp) {
			player.getPlayerAssistant().sendConfig(752, 1);
		} else {
			player.getPlayerAssistant().sendConfig(752, 0);
		}
		if (player.flap) {
			player.getPlayerAssistant().sendConfig(753, 1);
		} else {
			player.getPlayerAssistant().sendConfig(753, 0);
		}
		if (player.slapHead) {
			player.getPlayerAssistant().sendConfig(754, 1);
		} else {
			player.getPlayerAssistant().sendConfig(754, 0);
		}
		if (player.zombieWalk) {
			player.getPlayerAssistant().sendConfig(755, 1);
		} else {
			player.getPlayerAssistant().sendConfig(755, 0);
		}
		if (player.zombieDance) {
			player.getPlayerAssistant().sendConfig(756, 1);
		} else {
			player.getPlayerAssistant().sendConfig(756, 0);
		}
		if (player.zombieHand) {
			player.getPlayerAssistant().sendConfig(757, 1);
		} else {
			player.getPlayerAssistant().sendConfig(757, 0);
		}
		if (player.scared) {
			player.getPlayerAssistant().sendConfig(758, 1);
		} else {
			player.getPlayerAssistant().sendConfig(758, 0);
		}
		if (player.bunnyHop) {
			player.getPlayerAssistant().sendConfig(759, 1);
		} else {
			player.getPlayerAssistant().sendConfig(759, 0);
		}
		if (player.skillcape) {
			player.getPlayerAssistant().sendConfig(760, 1);
		} else {
			player.getPlayerAssistant().sendConfig(760, 0);
		}
		if (player.snowmanDance) {
			player.getPlayerAssistant().sendConfig(761, 1);
		} else {
			player.getPlayerAssistant().sendConfig(761, 0);
		}
		if (player.airGuitar) {
			player.getPlayerAssistant().sendConfig(762, 1);
		} else {
			player.getPlayerAssistant().sendConfig(762, 0);
		}
		if (player.airGuitar) {
			player.getPlayerAssistant().sendConfig(763, 1);
		} else {
			player.getPlayerAssistant().sendConfig(763, 0);
		}
	}

	/**
	 * An array of data for the emotes.
	 * 
	 * Button ID, Animation ID, GFX ID
	 */
	public static final int[][] emoteData = { { 168, 855, 0 }, // Yes
	        { 169, 856, 0 }, // No
	        { 164, 858, 0 }, // Bow
	        { 167, 859, 0 }, // Angry 
	        { 162, 857, 0 }, // Think
	        { 163, 863, 0 }, // Wave
	        { 52058, 2113, 0 }, // Shrug
	        { 171, 862, 0 }, // Cheer
	        { 165, 864, 0 }, // Beckon
	        { 170, 861, 0 }, // Laugh
	        { 52054, 2109, 0 }, // Jump For Joy
	        { 52056, 2111, 0 }, // Yawn
	        { 166, 866, 0 }, // Dance
	        { 52051, 2106, 0 }, // Jig
	        { 52052, 2107, 0 }, // Twirl
	        { 52053, 2108, 0 }, // Headbang
	        { 161, 860, 0 }, // Cry
	        { 43092, 0x558, 0 }, // Blow Kiss
	        { 52050, 2105, 0 }, // Panic
	        { 52055, 2110, 0 }, // Raspberry
	        { 172, 865, 0 }, // Clap
	        { 52057, 2112, 0 }, // Salute
	        { 52071, 0x84F, 0 }, // Goblin Bow
	        { 52072, 0x850, 0 }, // Goblin Salute
	        { 2155, 0x46B, 0 }, // Glass Box
	        { 25103, 0x46A, 0 }, // Climb Rope
	        { 25106, 0x469, 0 }, // Lean
	        { 2154, 0x468, 0 }, // Glass Wall
	        { 88060, 4276, 0 }, // Idea
	        { 88061, 4278, 0 }, // Stomp
	        { 88062, 4280, 0 }, // Flap
	        { 88063, 4275, 0 }, // Slap Head
	        { 72032, 3544, 0 }, // Zombie Walk
	        { 72033, 3543, 0 }, // Zombie Dance
	        { 88065, 7272, 1244 }, // Zombie Hand
	        { 59062, 2836, 0 }, // Scared
	        { 72254, 6111, 0 }, // Bunny Hop
	        { 89048, 7531, 0 }, //  Snowman Dance
	        { 89049, 2414, 1537 }, // Air Guitar 
	        { 89050, 8770, 1553 } // Safety First
	};

	/**
	 * A method for handling the skillcape emotes.
	 */
	public void handleSkillcapeEmote() {

	}

	/**
	 * A method for doing the corresponding emotes when a button is pressed.
	 * 
	 * @param actionButtonId
	 */
	public void doEmote(int actionButtonId) {
		switch (actionButtonId) {
		case 52071:
			if (!player.goblinBow) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 52072:
			if (!player.goblinSalute) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 2155:
			if (!player.glassBox) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 25103:
			if (!player.climbRope) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 25106:
			if (!player.lean) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 2154:
			if (!player.glassWall) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 88060:
			if (!player.idea) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 88061:
			if (!player.stomp) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 88062:
			if (!player.flap) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 88063:
			if (!player.slapHead) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 72032:
			if (!player.zombieWalk) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 72033:
			if (!player.zombieDance) {
				player.getDialogueHandler().sendStatement("@dre@This emote has not been unlocked.");
				return;
			}
			break;
		case 88065:
			if (!player.zombieHand) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by completing a Halloween event.");
				return;
			}
			break;
		case 59062:
			if (!player.scared) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by completing a Halloween event.");
				return;
			}
			break;
		case 72254:
			if (!player.bunnyHop) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by completing an Easter event.");
				return;
			}
			break;
		case 74108:
			if (!player.skillcape) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by equipping a cape of accomplishment.");
				return;
			} else {
				handleSkillcapeEmote();
			}
			break;
		case 89048:
			if (!player.snowmanDance) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by completing a Christmas event.");
				return;
			}
			break;
		case 89049:
			if (!player.airGuitar) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by unlocking all of the music tracks.");
				return;
			}
			break;
		case 89050:
			if (!player.safetyFirst) {
				player.getDialogueHandler().sendStatement("@dre@You can unlock this emote by setting a bank PIN.");
				return;
			}
			break;
		}
		for (int i = 0; i < emoteData.length; i++) {
			if (emoteData[i][0] == actionButtonId) {
				player.startAnimation(emoteData[i][1]);
				if (emoteData[i][2] != 0) {
					player.gfx0(emoteData[i][2]);
				}
			}
		}
	}
}
