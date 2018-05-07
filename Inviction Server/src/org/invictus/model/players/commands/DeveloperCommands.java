package org.invictus.model.players.commands;

import org.invictus.Configuration;
import org.invictus.Server;
import org.invictus.model.items.ItemConfiguration;
import org.invictus.model.players.Client;
import org.invictus.model.players.Player;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.PlayerSave;
import org.invictus.model.players.content.dialogue.Emotion;
import org.invictus.world.ObjectManager;

/**
 * A class containing all the commands usable by developers of the server.
 * 
 * @author Joe
 *
 */
public class DeveloperCommands {

	Client player;

	public DeveloperCommands(Client player) {
		this.player = player;
	}

	public void developerCommandList(String commandString) {
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		switch (command) {

		case "text":
			player.getDialogueHandler().makeItem3(11694, "Armadyl Godsword", 6570, "Fire Cape", 6585, "Amulet of Fury");
			break;

		case "twoitem":
			player.getDialogueHandler().sendItem2("Nigger", "Nagger", 4151, 13740);
			break;

		case "start":
			player.getDialogueHandler().sendInformationBox("Hello there! Welcome to our server.", "If you need help, don't be afraid to ask.", "We hope you enjouy your time.", "Have a lovely day!", "Welcome to Invictus");
			break;

		case "statement":
			player.getDialogueHandler().sendStatement("1", "2", "3", "4", "5");
			break;

		case "dialogue":
			player.getDialogueHandler().sendNpcChat("Nigger", 1, Emotion.SAD, "Hello faggot", "My name is Bob", "I'm a nigger.", "You better hope you are one too.");
			break;

		case "playerdial":
			player.getDialogueHandler().sendPlayerChat(Emotion.DELIGHTED_EVIL, "I love cum.", "in my bum", "very much", "yipee");
			break;

		case "ghost":
			player.updateRequired = true;
			player.isInvisible = !player.isInvisible;
			if (player.isInvisible) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel);
				player.getTransformation().ghostTransform();
				player.sendMessage("You are now invisible to other players. Note: Other owners can see you.");
			} else {
				player.getTransformation().resetTransformation();
				player.sendMessage("You are now visible.");
			}
			break;

		case "pnpc":
			try {
				int npc = Integer.parseInt(args[1]);
				player.getTransformation().transform(npc);
			} catch (Exception Ex) {
				player.sendMessage("Invalid syntax.");
			}
			break;

		case "unpc":
			player.getTransformation().resetTransformation();
			break;

		case "debug":
			player.debugMode = !player.debugMode;
			player.sendMessage("Debug Mode: " + player.debugMode);
			break;

		case "damage":
			try {
				int damage = Integer.parseInt(args[1]);
				player.getPlayerAssistant().doDamage(damage);
			} catch (Exception Ex) {
				player.sendMessage("Invalid syntax.");
			}
			break;
		case "bank":
			player.getBank().openUpBank();
			break;
		case "goup":
			if (player.heightLevel != 3) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel + 1);
				player.sendMessage("Height level: " + player.heightLevel);
			} else {
				player.sendMessage("You can't go any higher!");
			}
			break;
		case "godown":
			if (player.heightLevel != 0) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel - 1);
				player.sendMessage("Height level: " + player.heightLevel);
			} else {
				player.sendMessage("You can't go any lower!");
			}
			break;
		case "questpoints":
			try {
				int questPoints = Integer.parseInt(args[1]);
				player.questPoints = questPoints;
				player.getQuestHandler().updateQuestTabText();
				player.sendMessage("Quest Points: " + player.questPoints);
			} catch (Exception Ex) {
				player.sendMessage("Invalid syntax.");
			}
			break;
		case "testquest":
			try {
				int questStage = Integer.parseInt(args[1]);
				player.testQuestProgress = questStage;
				player.getTestQuest().updateQuestTab();
				player.sendMessage("Test quest status: " + player.testQuestProgress);
			} catch (Exception Ex) {
				player.sendMessage("Invalid syntax.");
			}
			break;
		case "instance":
			player.sendMessage("Player ID: " + player.playerId);
			player.sendMessage("Height level: " + player.playerInstance);
			break;
		case "champ":
			player.champImp = 2;
			break;
		case "max":
		case "master":
			for (int i = 0; i < 24; i++) {
				player.playerXP[i] = player.getPlayerAssistant().getXPForLevel(99) + 5;
				player.playerLevel[i] = player.getPlayerAssistant().getLevelForXP(player.playerXP[i]);
				player.getPlayerAssistant().refreshSkill(i);
				player.getPlayerAssistant().levelUp(i);
			}
			player.sendMessage("Skills now maxed.");
			break;
		case "empty":
			for (int i = 0; i < player.playerItems.length; i++) {
				player.getItems().deleteItem(player.playerItems[i] - 1, player.getItems().getItemSlot(player.playerItems[i] - 1), player.playerItemsN[i]);
			}
			player.sendMessage("Inventory emptied.");
			break;
		case "unequip":
			for (int i1 = 0; i1 < player.playerEquipment.length; i1++) {
				player.getItems().deleteEquipment(player.playerEquipment[i1], i1);
			}
			player.sendMessage("Equipment cleared.");
			break;
		case "clear":
			player.getItems().deleteAllItems();
			player.sendMessage("Equipment and inventory cleared.");
			break;
		case "saveall":
			for (Player player : PlayerHandler.players) {
				if (player != null) {
					Client c = (Client) player;
					if (PlayerSave.saveGame(c)) {
						c.sendMessage("Your character has been saved.");
					}
				}
			}
			break;
		case "reloadshops":
			Server.shopHandler = new org.invictus.model.shops.ShopHandler();
			Server.shopHandler.loadShops("shops.cfg");
			player.sendMessage("You have reloaded the shops.");
			break;
		case "unlockemotes":
			player.goblinBow = true;
			player.goblinSalute = true;
			player.glassWall = true;
			player.glassBox = true;
			player.lean = true;
			player.climbRope = true;
			player.idea = true;
			player.stomp = true;
			player.flap = true;
			player.slapHead = true;
			player.zombieDance = true;
			player.zombieWalk = true;
			player.zombieHand = true;
			player.scared = true;
			player.bunnyHop = true;
			player.skillcape = true;
			player.snowmanDance = true;
			player.airGuitar = true;
			player.safetyFirst = true;
			player.getEmotes().displayEmotes();
			player.sendMessage("You've unlocked all the emotes.");
			break;
		case "lunars":
			player.playerMagicBook = 2;
			player.setSidebarInterface(6, 29999);
			player.sendMessage("Lunar spells enabled.");
			break;
		case "dumptext":
			for (int i = 0; i < 20000; i++) {
				player.getPlayerAssistant().sendString("" + i + "", i);
			}
			break;
		case "update":
			PlayerHandler.updateSeconds = 120;
			PlayerHandler.updateAnnounced = false;
			PlayerHandler.updateRunning = true;
			PlayerHandler.updateStartTime = System.currentTimeMillis();
			break;
		case "pos":
		case "mypos":
			player.sendMessage("Your current co-ordinates are: [" + player.absX + ", " + player.absY + ", " + player.heightLevel + "]");
			System.out.println("Your current co-ordinates are: (" + player.absX + ", " + player.absY + ", " + player.heightLevel + ")");
			break;
		case "item":
			try {
				int itemId = Integer.parseInt(args[1]);
				if (itemId >= ItemConfiguration.ITEM_AMOUNT) {
					player.sendMessage("Error: Invalid item ID.");
				}
				if (args.length == 2) {
					player.getItems().addItem(itemId, 1);
					player.sendMessage("You have spawned a " + player.getItems().getItemName(itemId));
				}
				if (args.length == 3) {
					int itemAmount = Integer.parseInt(args[2]);
					player.getItems().addItem(itemId, itemAmount);
					if (itemAmount == 1) {
						player.sendMessage("You have spawned a " + player.getItems().getItemName(itemId));
					} else {
						player.sendMessage("You have spawned " + itemAmount + " " + player.getItems().getItemName(itemId) + "s");
					}
				}
			} catch (Exception ex) {
				player.sendMessage("Use the command as ::item [ID], or ::item [ID] [AMOUNT].");
			}
			break;
		case "config":
			int id = Integer.parseInt(args[1]);
			int status = Integer.parseInt(args[2]);
			player.getPlayerAssistant().sendConfig(id, status);
			break;
		case "setlevel":
			try {
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				if (level > 99) {
					level = 99;
				} else if (level < 0) {
					level = 1;
				}
				player.playerXP[skill] = player.getPlayerAssistant().getXPForLevel(level) + 5;
				player.playerLevel[skill] = player.getPlayerAssistant().getLevelForXP(player.playerXP[skill]);
				player.getPlayerAssistant().refreshSkill(skill);
				player.getPlayerAssistant().levelUp(skill);
			} catch (Exception e) {
			}
			break;
		case "interface":
		case "int":
			player.getPlayerAssistant().sendInterface(Integer.parseInt(args[1]));
			break;
		case "gfx":
			player.gfx0(Integer.parseInt(args[1]));
			break;
		case "anim":
			player.startAnimation(Integer.parseInt(args[1]));
			player.getPlayerAssistant().requestUpdates();
			break;
		case "animgfx":
			try {
				player.gfx0(Integer.parseInt(args[1]));
				player.startAnimation(Integer.parseInt(args[2]));
			} catch (Exception d) {
				player.sendMessage("Wrong Syntax! Use as -->dualG gfx anim");
			}
			break;
		case "object":
		case "obj":
			player.getPlayerAssistant().object(Integer.parseInt(args[1]), player.absX, player.absY, 0, ObjectManager.DEFAULT_OBJECT);
			break;
		case "dungeon":
			player.getPlayerAssistant().movePlayer(player.absX, player.absY + 6400, 0);
			player.sendMessage("Jumping +6400 Y coords.");
			break;
		case "headicon":
			player.sendMessage("new headicon = " + Integer.parseInt(args[1]));
			player.headIcon = Integer.parseInt(args[1]);
			player.getPlayerAssistant().requestUpdates();
			break;
		case "spec":
			player.specAmount = (Integer.parseInt(args[1]));
			player.getItems().updateSpecialBar();
			break;
		case "tele":
			if (args.length > 3)
				player.getPlayerAssistant().movePlayer(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			else if (args.length == 3)
				player.getPlayerAssistant().movePlayer(Integer.parseInt(args[1]), Integer.parseInt(args[2]), player.heightLevel);
			break;
		case "npc": //TODO: FIX
			try {
				int npcId = Integer.parseInt(args[1]);
				if (npcId >= 0 && npcId <= Configuration.NPC_AMOUNT) {
					Server.npcHandler.spawnNpc(player, npcId, player.absX, player.absY, player.heightLevel, 0, 120, 7, 70, 70, false, false);
					player.sendMessage("You spawn an NPC.");
				} else {
					player.sendMessage("Error: No such NPC.");
				}
			} catch (Exception e) {
				System.out.println("Error: Invalid syntax, use as ::npc [NPC ID]");
			}
			break;
		}
		if (command.startsWith("announcement"))

		{
			String text = command.substring(13);
			player.getPlayerAssistant().sendGlobalMessage(text);
		}
		if (command.startsWith("whois")) {
			try {
				String playerName = command.substring(6);
				for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerName)) {
							player.sendMessage("IP: " + PlayerHandler.players[i].connectedFrom);
						}
					}
				}
			} catch (Exception e) {
				player.sendMessage("Player Must Be Offline.");
			}
		}
	}
}
