package org.invictus.model.players.content.dialogue;

import org.invictus.model.players.Client;

public class DialogueHandler {

	private Client player;

	public DialogueHandler(Client player) {
		this.player = player;
	}

	/**
	 * Displays an information box with nothing but a title and four lines of text.
	 * 
	 * @param text
	 * @param text1
	 * @param text2
	 * @param text3
	 * @param title
	 */
	public void sendInformationBox(String text, String text1, String text2, String text3, String title) {
		player.getPlayerAssistant().sendString(title, 6180);
		player.getPlayerAssistant().sendString(text, 6181);
		player.getPlayerAssistant().sendString(text1, 6182);
		player.getPlayerAssistant().sendString(text2, 6183);
		player.getPlayerAssistant().sendString(text3, 6184);
		player.getPlayerAssistant().sendChatInterface(6179);
	}

	/**
	 * Displays an option box with a variable number of options.
	 * 
	 * @param options
	 */
	public void sendOption(String... options) {
		switch (options.length) {
		case 1:
			break;
		case 2:
			player.getPlayerAssistant().sendString("Select an Option", 2460);
			player.getPlayerAssistant().sendString(options[0], 2461);
			player.getPlayerAssistant().sendString(options[1], 2462);
			player.getPlayerAssistant().sendChatInterface(2459);
			break;
		case 3:
			player.getPlayerAssistant().sendString("Select an Option", 2470);
			player.getPlayerAssistant().sendString(options[0], 2471);
			player.getPlayerAssistant().sendString(options[1], 2472);
			player.getPlayerAssistant().sendString(options[2], 2473);
			player.getPlayerAssistant().sendChatInterface(2469);
			break;
		case 4:
			player.getPlayerAssistant().sendString("Select an Option", 2481);
			player.getPlayerAssistant().sendString(options[0], 2482);
			player.getPlayerAssistant().sendString(options[1], 2483);
			player.getPlayerAssistant().sendString(options[2], 2484);
			player.getPlayerAssistant().sendString(options[3], 2485);
			player.getPlayerAssistant().sendChatInterface(2480);
			break;
		case 5:
			player.getPlayerAssistant().sendString("Select an Option", 2493);
			player.getPlayerAssistant().sendString(options[0], 2494);
			player.getPlayerAssistant().sendString(options[1], 2495);
			player.getPlayerAssistant().sendString(options[2], 2496);
			player.getPlayerAssistant().sendString(options[3], 2497);
			player.getPlayerAssistant().sendString(options[4], 2498);
			player.getPlayerAssistant().sendChatInterface(2492);
			break;
		}
	}

	/*
	 * Statements
	 */

	public void sendStatement(String... statements) {
		switch(statements.length) {
		case 1:
			player.getPlayerAssistant().sendString(statements[0], 357);
			player.getPlayerAssistant().sendString("Click here to continue", 358);
			player.getPlayerAssistant().sendChatInterface(356);
			break;
		case 2:
			player.getPlayerAssistant().sendString(statements[0], 360);
			player.getPlayerAssistant().sendString(statements[1], 361);
			player.getPlayerAssistant().sendString("Click here to continue", 362);
			player.getPlayerAssistant().sendChatInterface(359);
			break;
		case 3:
			player.getPlayerAssistant().sendString(statements[0], 364);
			player.getPlayerAssistant().sendString(statements[1], 365);
			player.getPlayerAssistant().sendString(statements[2], 366);
			player.getPlayerAssistant().sendString("Click here to continue", 367);
			player.getPlayerAssistant().sendChatInterface(363);
			break;
		case 4:
			player.getPlayerAssistant().sendString(statements[0], 369);
			player.getPlayerAssistant().sendString(statements[1], 370);
			player.getPlayerAssistant().sendString(statements[2], 371);
			player.getPlayerAssistant().sendString(statements[3], 372);
			player.getPlayerAssistant().sendString("Click here to continue", 373);
			player.getPlayerAssistant().sendChatInterface(368);
			break;
		case 5:
			player.getPlayerAssistant().sendString(statements[0], 375);
			player.getPlayerAssistant().sendString(statements[1], 376);
			player.getPlayerAssistant().sendString(statements[2], 377);
			player.getPlayerAssistant().sendString(statements[3], 378);
			player.getPlayerAssistant().sendString(statements[4], 379);
			player.getPlayerAssistant().sendString("Click here to continue", 380);
			player.getPlayerAssistant().sendChatInterface(374);
			break;
		}
	}
	
	public void sendNpcChat(String npcName, int npcId, Emotion emotion, String... lines) {
		switch(lines.length) {
		case 1:
			player.getPlayerAssistant().sendInterfaceAnimation(4883, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(npcName, 4884);
			player.getPlayerAssistant().sendString(lines[0], 4885);
			player.getPlayerAssistant().sendFrame75(npcId, 4883);
			player.getPlayerAssistant().sendChatInterface(4882);
			break;
		case 2:
			player.getPlayerAssistant().sendInterfaceAnimation(4888, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(npcName, 4889);
			player.getPlayerAssistant().sendString(lines[0], 4890);
			player.getPlayerAssistant().sendString(lines[1], 4891);
			player.getPlayerAssistant().sendFrame75(npcId, 4888);
			player.getPlayerAssistant().sendChatInterface(4887);
			break;
		case 3:
			player.getPlayerAssistant().sendInterfaceAnimation(4894, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(npcName, 4895);
			player.getPlayerAssistant().sendString(lines[0], 4896);
			player.getPlayerAssistant().sendString(lines[1], 4897);
			player.getPlayerAssistant().sendString(lines[2], 4898);
			player.getPlayerAssistant().sendFrame75(npcId, 4894);
			player.getPlayerAssistant().sendChatInterface(4893);
			break;
		case 4:
			player.getPlayerAssistant().sendInterfaceAnimation(4901, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(npcName, 4902);
			player.getPlayerAssistant().sendString(lines[0], 4903);
			player.getPlayerAssistant().sendString(lines[1], 4904);
			player.getPlayerAssistant().sendString(lines[2], 4905);
			player.getPlayerAssistant().sendString(lines[3], 4906);
			player.getPlayerAssistant().sendFrame75(npcId, 4901);
			player.getPlayerAssistant().sendChatInterface(4900);
			break;
		}
	}
	
	public void sendPlayerChat(Emotion emotion, String... lines) {
		switch(lines.length) {
		case 1:
			player.getPlayerAssistant().sendInterfaceAnimation(969, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(player.playerName, 970);
			player.getPlayerAssistant().sendString(lines[0], 971);
			player.getPlayerAssistant().sendPlayerHeadOnInterface(969);
			player.getPlayerAssistant().sendChatInterface(968);
			break;
		case 2:
			player.getPlayerAssistant().sendInterfaceAnimation(974, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(player.playerName, 975);
			player.getPlayerAssistant().sendString(lines[0], 976);
			player.getPlayerAssistant().sendString(lines[1], 977);
			player.getPlayerAssistant().sendPlayerHeadOnInterface(974);
			player.getPlayerAssistant().sendChatInterface(973);
			break;
		case 3:
			player.getPlayerAssistant().sendInterfaceAnimation(980, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(player.playerName, 981);
			player.getPlayerAssistant().sendString(lines[0], 982);
			player.getPlayerAssistant().sendString(lines[1], 983);
			player.getPlayerAssistant().sendString(lines[2], 984);
			player.getPlayerAssistant().sendPlayerHeadOnInterface(980);
			player.getPlayerAssistant().sendChatInterface(979);
			break;
		case 4:
			player.getPlayerAssistant().sendInterfaceAnimation(987, emotion.getEmoteId());
			player.getPlayerAssistant().sendString(player.playerName, 988);
			player.getPlayerAssistant().sendString(lines[0], 989);
			player.getPlayerAssistant().sendString(lines[1], 990);
			player.getPlayerAssistant().sendString(lines[2], 991);
			player.getPlayerAssistant().sendString(lines[3], 992);
			player.getPlayerAssistant().sendPlayerHeadOnInterface(987);
			player.getPlayerAssistant().sendChatInterface(986);
			break;
		}
	}

	/**
	 * Sends a chatbox with an item icon and a message.
	 * 
	 * @param text
	 * @param item
	 */
	public void sendItem1(String text, int item) {
		player.getPlayerAssistant().sendString(text, 308);
		player.getPlayerAssistant().sendInterfaceItem(307, 225, item);
		player.getPlayerAssistant().sendChatInterface(306);
	}

	/**
	 * Sends a chatbox with two item icons and two messages.
	 * 
	 * @param text1
	 * @param text2
	 * @param item1
	 * @param item2
	 */
	public void sendItem2(String text1, String text2, int item1, int item2) {
		player.getPlayerAssistant().sendString(text1, 6232);
		player.getPlayerAssistant().sendString(text2, 6233);
		player.getPlayerAssistant().sendInterfaceItem(6235, 225, item1);
		player.getPlayerAssistant().sendInterfaceItem(6236, 225, item2);
		player.getPlayerAssistant().sendChatInterface(6231);
	}

	/**
	 * Sends a chatbox with three item options the player can choose to make.
	 * 
	 * @param itemId1
	 * @param itemName1
	 * @param itemId2
	 * @param itemName2
	 * @param itemId3
	 * @param itemName3
	 */
	public void makeItem3(int itemId1, String itemName1, int itemId2, String itemName2, int itemId3, String itemName3) {
		player.getPlayerAssistant().sendChatInterface(8880);
		player.getPlayerAssistant().sendInterfaceItem(8883, 190, itemId1);
		player.getPlayerAssistant().sendInterfaceItem(8884, 190, itemId2);
		player.getPlayerAssistant().sendInterfaceItem(8885, 190, itemId3);
		player.getPlayerAssistant().sendString(itemName1, 8889);
		player.getPlayerAssistant().sendString(itemName2, 8893);
		player.getPlayerAssistant().sendString(itemName3, 8897);
	}
}
