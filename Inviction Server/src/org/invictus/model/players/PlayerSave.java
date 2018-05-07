package org.invictus.model.players;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.invictus.util.Misc;

public class PlayerSave {

	/**
	 * Loading
	 **/
	public static int loadGame(Client player, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;

		try {
			characterfile = new BufferedReader(new FileReader("./data/characters/" + playerName + ".txt"));
			File1 = true;
		} catch (FileNotFoundException fileex1) {
		}

		if (File1) {
			// new File ("./characters/"+playerName+".txt");
		} else {
			Misc.println(playerName + ": character file not found.");
			player.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(playerName + ": error loading file.");
			return 3;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
				case 1:
					if (token.equals("Password")) {
						if (playerPass.equalsIgnoreCase(token2)) {
							playerPass = token2;
						} else {
							return 3;
						}
					}
					if (token.equals("Account Creation Date")) {
						player.creationDate = token2;
					}
					if (token.equals("Player Rights")) {
						player.playerRights = Integer.parseInt(token2);
					}
					if (token.equals("Donator")) {
						player.isMember = Boolean.parseBoolean(token2);
					}
					break;
				case 2:
					if (token.equals("Height Level")) {
						player.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("X Position")) {
						player.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
					} else if (token.equals("Y Position")) {
						player.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
					} else if (token.equals("Crystal Bow Shots")) {
						player.crystalBowArrowCount = Integer.parseInt(token2);
					} else if (token.equals("Skull Timer")) {
						player.skullTimer = Integer.parseInt(token2);
					} else if (token.equals("Spellbook")) {
						player.playerMagicBook = Integer.parseInt(token2);
					} else if (token.equals("Special Attack Energy")) {
						player.specAmount = Double.parseDouble(token2);
					} else if (token.equals("Teleblock Length")) {
						player.teleBlockDelay = System.currentTimeMillis();
						player.teleBlockLength = Integer.parseInt(token2);
					} else if (token.equals("Slayer Task NPC")) {
						player.slayerTask = Integer.parseInt(token2);
					} else if (token.equals("Slayer Task Amount")) {
						player.taskAmount = Integer.parseInt(token2);
					} else if (token.equals("Auto Retaliate")) {
						player.autoRet = Boolean.parseBoolean(token2);
					} else if (token.equals("Fight Mode")) {
						player.fightMode = Integer.parseInt(token2);
					} else if (token.equals("Gnome Agility Course Laps")) {
						player.gnomeCourseLaps = Integer.parseInt(token2);
					} else if (token.equals("Debug Mode")) {
						player.debugMode = Boolean.parseBoolean(token2);
					} else if (token.equals("Invisibility")) {
						player.isInvisible = Boolean.parseBoolean(token2);
					}
					break;
				case 3:
					if (token.equals("Equipment Slot")) {
						player.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						player.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("Character Look")) {
						player.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					}
					break;
				case 5:
					if (token.equals("Skill")) {
						player.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						player.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("Inventory Slot")) {
						player.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						player.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("Bank Slot")) {
						player.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						player.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 8:
					if (token.equals("Friend")) {
						player.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					}
					break;
				case 9:
					if (token.equals("Ignore")) {
						player.ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					}
					break;
				case 10:
					if (token.equals("Pest Control Points")) {
						player.pcPoints = Integer.parseInt(token2);
					}
					if (token.equals("Mage Arena Points")) {
						player.magePoints = Integer.parseInt(token2);
					}
					if (token.equals("PK Points")) {
						player.pkPoints = Integer.parseInt(token2);
					}
					if (token.equals("Agility Points")) {
						player.agilityPoints = Integer.parseInt(token2);
					}
					break;
				case 11:
					if (token.equals("Barrows Brother Information")) {
						player.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
					}
					if (token.equals("Barrows Selected Coffin")) {
						player.randomCoffin = Integer.parseInt(token2);
					}
					if (token.equals("Barrows Kill Count")) {
						player.barrowsKillCount = Integer.parseInt(token2);
					}
					if (token.equals("Fight Caves Wave Number")) {
						player.waveId = Integer.parseInt(token2);
					}
					if (token.equals("Special Item Pickup")) {
						for (int j = 0; j < token3.length; j++) {
							player.itemPickups[j] = Integer.parseInt(token3[j]);
						}
					}
					break;
				case 12:
					if (token.equals("Quest Points")) {
						player.questPoints = Integer.parseInt(token2);
					}
					if (token.equals("Test Quest")) {
						player.testQuestProgress = Integer.parseInt(token2);
					}
					break;
				case 13:
					if (token.equals("House Owned")) {
						player.houseOwned = Boolean.parseBoolean(token2);
					}
					break;
				case 14:
					if (token.equals("Account Flagged")) {
						player.accountFlagged = Boolean.parseBoolean(token2);
					}
					if (token.equals("Account Jailed")) {
						player.jailed = Boolean.parseBoolean(token2);
					}
					if (token.equals("Account Muted")) {
						player.muted = Boolean.parseBoolean(token2);
					}
					if (token.equals("Account Banned")) {
						player.banned = Boolean.parseBoolean(token2);
					}
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {
					ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {
					ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {
					ReadMode = 3;
				} else if (line.equals("[LOOK]")) {
					ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {
					ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {
					ReadMode = 6;
				} else if (line.equals("[BANK]")) {
					ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {
					ReadMode = 8;
				} else if (line.equals("[IGNORES]")) {
					ReadMode = 9;
				} else if (line.equals("[POINTS]")) {
					ReadMode = 10;
				} else if (line.equals("[MINIGAMES]")) {
					ReadMode = 11;
				} else if (line.equals("[QUESTS]")) {
					ReadMode = 12;
				} else if (line.equals("[POH]")) {
					ReadMode = 13;
				} else if (line.equals("[PUNISHMENT AND SECURITY]")) {
					ReadMode = 14;
				} else if (line.equals("[EOF]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try

		{
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return 13;
	}

	/**
	 * Saving
	 **/
	public static boolean saveGame(Client player) {
		if (!player.saveFile || player.newPlayer || !player.saveCharacter) {
			// System.out.println("first");
			return false;
		}
		if (player.playerName == null || PlayerHandler.players[player.playerId] == null) {
			// System.out.println("second");
			return false;
		}
		player.playerName = player.playerName2;
		int tbTime = (int) (player.teleBlockDelay - System.currentTimeMillis() + player.teleBlockLength);
		if (tbTime > 300000 || tbTime < 0) {
			tbTime = 0;
		}

		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter("./data/characters/" + player.playerName + ".txt"));

			/* ACCOUNT */
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("Username = ", 0, 11);
			characterfile.write(player.playerName, 0, player.playerName.length());
			characterfile.newLine();
			characterfile.write("Password = ", 0, 11);
			characterfile.write(player.playerPass, 0, player.playerPass.length());
			characterfile.newLine();
			characterfile.write("Account Creation Date = ", 0, 24);
			characterfile.write(player.creationDate, 0, player.creationDate.length());
			characterfile.newLine();
			characterfile.write("Player Rights = ", 0, 16);
			characterfile.write(Integer.toString(player.playerRights), 0, Integer.toString(player.playerRights).length());
			characterfile.newLine();
			characterfile.write("Donator = ", 0, 10);
			characterfile.write(Boolean.toString(player.isMember), 0, Boolean.toString(player.isMember).length());
			characterfile.newLine();
			characterfile.newLine();

			/* CHARACTER */
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("X Position = ", 0, 13);
			characterfile.write(Integer.toString(player.absX), 0, Integer.toString(player.absX).length());
			characterfile.newLine();
			characterfile.write("Y Position = ", 0, 13);
			characterfile.write(Integer.toString(player.absY), 0, Integer.toString(player.absY).length());
			characterfile.newLine();
			characterfile.write("Height Level = ", 0, 15);
			characterfile.write(Integer.toString(player.heightLevel), 0, Integer.toString(player.heightLevel).length());
			characterfile.newLine();
			characterfile.write("Crystal Bow Shots = ", 0, 20);
			characterfile.write(Integer.toString(player.crystalBowArrowCount), 0, Integer.toString(player.crystalBowArrowCount).length());
			characterfile.newLine();
			characterfile.write("Skull Timer = ", 0, 14);
			characterfile.write(Integer.toString(player.skullTimer), 0, Integer.toString(player.skullTimer).length());
			characterfile.newLine();
			characterfile.write("Spellbook = ", 0, 12);
			characterfile.write(Integer.toString(player.playerMagicBook), 0, Integer.toString(player.playerMagicBook).length());
			characterfile.newLine();
			characterfile.write("Special Attack Energy = ", 0, 24);
			characterfile.write(Double.toString(player.specAmount), 0, Double.toString(player.specAmount).length());
			characterfile.newLine();
			characterfile.write("Teleblock Length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("Slayer Task NPC = ", 0, 18);
			characterfile.write(Integer.toString(player.slayerTask), 0, Integer.toString(player.slayerTask).length());
			characterfile.newLine();
			characterfile.write("Slayer Task Amount = ", 0, 21);
			characterfile.write(Integer.toString(player.taskAmount), 0, Integer.toString(player.taskAmount).length());
			characterfile.newLine();
			characterfile.write("Auto Retaliate = ", 0, 17);
			characterfile.write(Boolean.toString(player.autoRet), 0, Boolean.toString(player.autoRet).length());
			characterfile.newLine();
			characterfile.write("Fight Mode = ", 0, 13);
			characterfile.write(Integer.toString(player.fightMode), 0, Integer.toString(player.fightMode).length());
			characterfile.newLine();
			characterfile.write("Gnome Agility Course Laps = ", 0, 28);
			characterfile.write(Integer.toString(player.gnomeCourseLaps), 0, Integer.toString(player.gnomeCourseLaps).length());
			characterfile.newLine();
			characterfile.write("Debug Mode = ", 0, 13);
			characterfile.write(Boolean.toString(player.debugMode), 0, Boolean.toString(player.debugMode).length());
			characterfile.newLine();
			characterfile.write("Invisibility = ", 0, 15);
			characterfile.write(Boolean.toString(player.isInvisible), 0, Boolean.toString(player.isInvisible).length());
			characterfile.newLine();
			characterfile.write("Special Item Pickups = ", 0, 23);
			String toWrite = player.itemPickups[0] + "\t" + player.itemPickups[1] + "\t" + player.itemPickups[2] + "\t" + player.itemPickups[3] + "\t" + player.itemPickups[4];
			characterfile.write(toWrite);
			characterfile.newLine();
			characterfile.newLine();

			/* EQUIPMENT */
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < player.playerEquipment.length; i++) {
				characterfile.write("Equipment Slot = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerEquipment[i]), 0, Integer.toString(player.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerEquipmentN[i]), 0, Integer.toString(player.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* LOOK */
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < player.playerAppearance.length; i++) {
				characterfile.write("Character Look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerAppearance[i]), 0, Integer.toString(player.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* SKILLS */
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < player.playerLevel.length; i++) {
				characterfile.write("Skill = ", 0, 8);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerLevel[i]), 0, Integer.toString(player.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerXP[i]), 0, Integer.toString(player.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* ITEMS */
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] > 0) {
					characterfile.write("Inventory Slot = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(player.playerItems[i]), 0, Integer.toString(player.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(player.playerItemsN[i]), 0, Integer.toString(player.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* BANK */
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < player.bankItems.length; i++) {
				if (player.bankItems[i] > 0) {
					characterfile.write("Bank Slot = ", 0, 12);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(player.bankItems[i]), 0, Integer.toString(player.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(player.bankItemsN[i]), 0, Integer.toString(player.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* FRIENDS */
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < player.friends.length; i++) {
				if (player.friends[i] > 0) {
					characterfile.write("Friend = ", 0, 9);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + player.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* IGNORES */
			characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < player.ignores.length; i++) {
				if (player.ignores[i] > 0) {
					characterfile.write("Ignore = ", 0, 9);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Long.toString(player.ignores[i]), 0, Long.toString(player.ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* EMOTES */
			characterfile.write("[POINTS]", 0, 8);
			characterfile.newLine();
			characterfile.write("Pest Control Points = ", 0, 22);
			characterfile.write(Integer.toString(player.pcPoints), 0, Integer.toString(player.pcPoints).length());
			characterfile.newLine();
			characterfile.write("Mage Arena Points = ", 0, 20);
			characterfile.write(Integer.toString(player.magePoints), 0, Integer.toString(player.magePoints).length());
			characterfile.newLine();
			characterfile.write("PK Points = ", 0, 12);
			characterfile.write(Integer.toString(player.pkPoints), 0, Integer.toString(player.pkPoints).length());
			characterfile.newLine();
			characterfile.write("Agility Points = ", 0, 17);
			characterfile.write(Integer.toString(player.agilityPoints), 0, Integer.toString(player.agilityPoints).length());
			characterfile.newLine();
			characterfile.newLine();

			/* MINIGAMES */
			characterfile.write("[MINIGAMES]", 0, 11);
			characterfile.newLine();
			for (int b = 0; b < player.barrowsNpcs.length; b++) {
				characterfile.write("Barrows Brother Information = ", 0, 30);
				characterfile.write(Integer.toString(b), 0, Integer.toString(b).length());
				characterfile.write("	", 0, 1);
				characterfile.write(player.barrowsNpcs[b][1] <= 1 ? Integer.toString(0) : Integer.toString(player.barrowsNpcs[b][1]), 0, Integer.toString(player.barrowsNpcs[b][1]).length());
				characterfile.newLine();
			}
			characterfile.write("Barrows Selected Coffin = ", 0, 26);
			characterfile.write(Integer.toString(player.randomCoffin), 0, Integer.toString(player.randomCoffin).length());
			characterfile.newLine();
			characterfile.write("Barrows Kill Count = ", 0, 21);
			characterfile.write(Integer.toString(player.barrowsKillCount), 0, Integer.toString(player.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("Fight Caves Wave Number = ", 0, 26);
			characterfile.write(Integer.toString(player.waveId), 0, Integer.toString(player.waveId).length());
			characterfile.newLine();
			characterfile.newLine();

			/* QUESTS */
			characterfile.write("[QUESTS]", 0, 8);
			characterfile.newLine();
			characterfile.write("Quest Points = ", 0, 15);
			characterfile.write(Integer.toString(player.questPoints), 0, Integer.toString(player.questPoints).length());
			characterfile.newLine();
			characterfile.write("Test Quest = ", 0, 13);
			characterfile.write(Integer.toString(player.testQuestProgress), 0, Integer.toString(player.testQuestProgress).length());
			characterfile.newLine();
			characterfile.newLine();

			/* PLAYER OWNED HOUSING */
			characterfile.write("[POH]", 0, 5);
			characterfile.newLine();
			characterfile.write("House Owned = ", 0, 14);
			characterfile.write(Boolean.toString(player.houseOwned), 0, Boolean.toString(player.houseOwned).length());
			characterfile.newLine();
			characterfile.newLine();

			/* PUNISHMENT AND SECURITY */
			characterfile.write("[PUNISHMENT AND SECURITY]", 0, 25);
			characterfile.newLine();
			characterfile.write("Account Flagged = ", 0, 18);
			characterfile.write(Boolean.toString(player.accountFlagged), 0, Boolean.toString(player.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("Account Jailed = ", 0, 17);
			characterfile.write(Boolean.toString(player.jailed), 0, Boolean.toString(player.jailed).length());
			characterfile.newLine();
			characterfile.write("Account Muted = ", 0, 16);
			characterfile.write(Boolean.toString(player.muted), 0, Boolean.toString(player.muted).length());
			characterfile.newLine();
			characterfile.write("Account Banned = ", 0, 17);
			characterfile.write(Boolean.toString(player.banned), 0, Boolean.toString(player.banned).length());
			characterfile.newLine();
			characterfile.newLine();

			/* EOF */
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch (IOException ioexception) {
			Misc.println(player.playerName + ": error writing file.");
			return false;
		}
		return true;
	}

}