package org.invictus.model.players.content.dialogue;

import org.invictus.model.players.Client;

public class DialogueList {

	private Client player;

	public DialogueList(Client player) {
		this.player = player;
	}

	public static final int BARROWS_TUNNEL_ENTER = 1;
	public static final int SLAYER_TASK_CONFIRMATION = 5;
	public static final int SLAYER_TASK_EASIER_TASK = 6;
	public static final int MAGE_ARENA_START = 7;
	public static final int BARROWS_RESET_AND_REPAIR_ARMOUR = 8;

	/**
	 * Handles all talking
	 * 
	 * @param dialogue
	 *            The dialogue you want to use
	 * @param npcId
	 *            The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		player.talkingNpc = npcId;
		switch (dialogue) {
		case 0:
			player.talkingNpc = -1;
			player.getPlayerAssistant().removeAllWindows();
			player.nextChat = 0;
			break;
		case 1:
			player.getDialogueHandler().sendStatement("You found a hidden tunnel! Do you want to enter it?");
			player.nextChat = 2;
			break;
		case 2:
			player.getDialogueHandler().sendOption("Yea! I'm fearless!", "No way! That looks scary!");
			player.dialogueAction = BARROWS_TUNNEL_ENTER;
			player.nextChat = 0;
			break;
		case 3:
			player.getDialogueHandler().sendNpcChat("Duradel", npcId, Emotion.DEFAULT, "Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", "Would you like a slayer task?");
			player.nextChat = 4;
			break;
		case 5:
			player.getDialogueHandler().sendNpcChat("Kolodion", npcId, Emotion.DEFAULT, "Hello adventurer...", "My name is Kolodion, the master of this mage bank.", "Would you like to play a minigame in order", "to earn points towards recieving magic related prizes?");
			player.nextChat = 6;
			break;
		case 6:
			player.getDialogueHandler().sendNpcChat("Kolodion", npcId, Emotion.DEFAULT, "The way the game works is as follows...", "You will be teleported to the wilderness,", "You must kill mages to recieve points,", "redeem points with the chamber guardian.");
			player.nextChat = 15;
			break;
		case 11:
			player.getDialogueHandler().sendNpcChat("Duradel", npcId, Emotion.DEFAULT, "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", "Would you like a slayer task?");
			player.nextChat = 12;
			break;
		case 12:
			player.getDialogueHandler().sendOption("Yes I would like a slayer task.", "No I would not like a slayer task.");
			player.dialogueAction = 5;
			break;
		case 13:
			player.getDialogueHandler().sendNpcChat("Duradel", npcId, Emotion.DEFAULT, "Hello!", "My name is Duradel and I am a master of the slayer skill.", "Would you like me to give you an easier task?");
			player.nextChat = 14;
			break;
		case 14:
			player.getDialogueHandler().sendOption("Yes I would like an easier task.", "No I would like to keep my task.");
			player.dialogueAction = 6;
			break;
		case 15:
			player.getDialogueHandler().sendOption("Yes I would like to play", "No, sounds too dangerous for me.");
			player.dialogueAction = 7;
			break;
		case 16:
			player.getDialogueHandler().sendOption("I would like to reset my barrows brothers.", "I would like to fix all my barrows");
			player.dialogueAction = 8;
			break;
		case 17:
			player.getDialogueHandler().sendOption("Air", "Mind", "Water", "Earth", "More");
			player.dialogueAction = 10;
			player.dialogueId = 17;
			player.dialogueTeleportAction = -1;
			break;
		case 18:
			player.getDialogueHandler().sendOption("Fire", "Body", "Cosmic", "Astral", "More");
			player.dialogueAction = 11;
			player.dialogueId = 18;
			player.dialogueTeleportAction = -1;
			break;
		case 19:
			player.getDialogueHandler().sendOption("Nature", "Law", "Death", "Blood", "More");
			player.dialogueAction = 12;
			player.dialogueId = 19;
			player.dialogueTeleportAction = -1;
			break;
		case 20:
			player.getDialogueHandler().sendStatement("Which champion would you like to fight?");
			player.nextChat = 21;
			break;
		case 21:
			String imp = "", goblin = "", skeleton = "", zombie = "";
			if (player.champImp == 0) {
				imp = "Imp (Locked)";
			}
			if (player.champImp == 1) {
				imp = "Imp";
			}
			if (player.champImp == 2) {
				imp = "@str@Imp";
			}
			if (player.champGoblin == 0) {
				goblin = "Goblin (Locked)";
			}
			if (player.champGoblin == 1) {
				goblin = "Goblin";
			}
			if (player.champGoblin == 2) {
				goblin = "@str@Goblin";
			}
			if (player.champSkeleton == 0) {
				skeleton = "Skeleton (Locked)";
			}
			if (player.champSkeleton == 1) {
				skeleton = "Skeleton";
			}
			if (player.champSkeleton == 2) {
				skeleton = "@str@Skeleton";
			}
			if (player.champZombie == 0) {
				zombie = "Zombie (Locked)";
			}
			if (player.champZombie == 1) {
				zombie = "Zombie";
			}
			if (player.champZombie == 2) {
				zombie = "@str@Zombie";
			}
			player.getDialogueHandler().sendOption(imp, goblin, skeleton, zombie, "--See more--");
			player.dialogueAction = 2;
			break;
		case 22:
			player.getDialogueHandler().sendOption("Giant", "Hobgoblin", "Ghoul", "Earth warrior", "--See more--");
			player.dialogueAction = 3;
			break;
		case 23:
			player.getDialogueHandler().sendOption("Jogre", "Lesser Demon", "Human", "--Go back--");
			player.dialogueAction = 4;
			break;
		case 24:
			player.getDialogueHandler().sendNpcChat("Mosolo Rei", npcId, Emotion.DEFAULT, "Hello. I don't actually have a quest for you.", "I'm simply here as a means of testing the quest system.", "I can set the 'Test Quest' progress for you.", "What shall I set it to (0-5)?");
			player.nextChat = 25;
			break;
		case 25:
			player.getDialogueHandler().sendOption("Reset Quest", "Quest Stage 1", "Quest Stage 2", "Quest Stage 3", "Complete Quest");
			player.dialogueAction = 25;
			break;
		case 26:
			player.getDialogueHandler().sendNpcChat("Mosolo Rei", npcId, Emotion.DEFAULT, "There you go...", "You are at stage " + player.testQuestProgress + " of the Test Quest.");
			player.nextChat = 0;
			break;
		}
	}

}
