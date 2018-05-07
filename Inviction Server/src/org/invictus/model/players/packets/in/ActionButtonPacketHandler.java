package org.invictus.model.players.packets.in;

import org.invictus.Server;
import org.invictus.model.items.GameItem;
import org.invictus.model.items.ItemConfiguration;
import org.invictus.model.players.Banking;
import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.PlayerSettings;
import org.invictus.model.players.Teleports;
import org.invictus.model.players.content.Resting;
import org.invictus.model.players.content.quests.testquest.TestQuest;
import org.invictus.model.players.packets.PacketType;
import org.invictus.model.players.skills.magic.LunarSpellbook;
import org.invictus.util.Misc;

public class ActionButtonPacketHandler implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(player.getInStream().buffer, 0, packetSize);
		if (player.isDead) {
			return;
		}
		if (player.playerRights == 3 && player.debugMode) {
			player.sendMessage("Button: @dre@" + actionButtonId);
			System.out.println("Button: " + actionButtonId);
		}
		player.getEmotes().doEmote(actionButtonId);
		player.getSkillGuides().openGuide(actionButtonId);
		switch (actionButtonId) {

		case PlayerSettings.LOGOUT_BUTTON_ID:
			player.logout();
			break;

		/**
		 * Bank
		 */

		case Banking.DEPOSIT_ALL_BANK_BUTTON_ID:
			player.getBank().depositAll();
			break;

		case Banking.ENABLE_NOTE_BUTTON_ID:
			player.takeAsNote = true;
			break;

		case Banking.DISABLE_NOTE_BUTTON_ID:
			player.takeAsNote = false;
			break;

		/**
		 * Player Settings
		 */
		case PlayerSettings.AUTO_RETALIATE_BUTTON_ID:
			player.getPlayerSettings().toggleAutoRetaliate();
			break;

		case PlayerSettings.RUN_BUTTON_ID:
		case PlayerSettings.RUN_BUTTON_ORB_ID:
			player.getPlayerSettings().toggleRunStatus();
			break;

		case PlayerSettings.MOUSE_BUTTONS_BUTTON_ID:
			player.getPlayerSettings().toggleMouseButtons();
			break;

		case PlayerSettings.SPLIT_PRIVATE_CHAT_BUTTON_ID:
			player.getPlayerSettings().toggleSplitPrivateChat();
			break;

		case PlayerSettings.CHAT_EFFECTS_BUTTON_ID:
			player.getPlayerSettings().toggleChatEffects();
			break;
		case PlayerSettings.ACCEPT_AID_BUTTON_ID:
			player.getPlayerSettings().toggleAcceptAid();
			break;

		case PlayerSettings.AREA_VOLUME_BUTTON_1:
			player.getPlayerSettings().changeAreaVolume(1);
			break;

		case PlayerSettings.AREA_VOLUME_BUTTON_2:
			player.getPlayerSettings().changeAreaVolume(2);
			break;

		case PlayerSettings.AREA_VOLUME_BUTTON_3:
			player.getPlayerSettings().changeAreaVolume(3);
			break;

		case PlayerSettings.AREA_VOLUME_BUTTON_4:
			player.getPlayerSettings().changeAreaVolume(4);
			break;

		case PlayerSettings.AREA_VOLUME_BUTTON_5:
			player.getPlayerSettings().changeAreaVolume(5);
			break;

		case PlayerSettings.BRIGHTNESS_BUTTON_1:
			player.getPlayerSettings().changeBrightness(1);
			break;
		case PlayerSettings.BRIGHTNESS_BUTTON_2:
			player.getPlayerSettings().changeBrightness(2);
			break;

		case PlayerSettings.BRIGHTNESS_BUTTON_3:
			player.getPlayerSettings().changeBrightness(3);
			break;

		case PlayerSettings.BRIGHTNESS_BUTTON_4:
			player.getPlayerSettings().changeBrightness(4);
			break;

		/**
		 * Spellbook Teleports
		 */
		case Teleports.HOME_TELE_BUTTON_MODERN:
		case Teleports.HOME_TELE_BUTTON_ANCIENT:
			player.getTeleports().homeTeleport();
			break;

		case Teleports.MONSTER_TELE_BUTTON_ANCIENT:
		case Teleports.MONSTER_TELE_BUTTON_MODERN:
			player.getTeleports().displayMonsterTeleports();
			break;

		case Teleports.MINIGAME_TELE_BUTTON_MODERN:
		case Teleports.MINIGAME_TELE_BUTTON_ANCIENT:
			player.getTeleports().displayMinigameTeleports();
			break;

		case Teleports.BOSS_TELE_BUTTON_MODERN:
		case Teleports.BOSS_TELE_BUTTON_ANCIENT:
			player.getTeleports().displayBossTeleports();
			break;

		case Teleports.SKILL_TELE_BUTTON_MODERN:
		case Teleports.SKILL_TELE_BUTTON_ANCIENT:
			player.getTeleports().displaySkillTeleports();
			break;

		case Teleports.CITY_TELE_BUTTON_MODERN:
		case Teleports.CITY_TELE_BUTTON_ANCIENT:
			player.getTeleports().displayCityTeleports();
			break;

		case Teleports.PK_TELE_BUTTON_MODERN:
		case Teleports.PK_TELE_BUTTON_ANCIENT:
			player.getTeleports().displayPKTeleports();
			break;

		case Teleports.DONATOR_AREA_TELEPORT_BUTTON_MODERN:
		case Teleports.DONATOR_AREA_TELEPORT_BUTTON_ANCIENT:
			player.getTeleports().teleportToMemberArea();
			break;

		case Teleports.APE_ATOLL_TELEPORT_BUTTON_MODERN:
		case Teleports.APE_ATOLL_TELEPORT_BUTTON_ANCIENT:
			player.sendMessage("This teleport hasn't been added yet.");
			break;

		case Teleports.HOUSE_TELEPORT_BUTTON_MODERN:
			player.getTeleports().teleportToHouse();
			break;

		/** Quests **/

		case TestQuest.QUEST_TAB_BUTTON:
			player.getTestQuest().questJournal();
			player.sendMessage("Test Quest Progress: Step " + player.testQuestProgress + " of " + TestQuest.ENDING_STAGE);
			break;

		case ItemConfiguration.NO_DESTROY_ITEM_BUTTON:
			player.getPlayerAssistant().removeAllWindows();
			player.droppedItem = -1;
			break;

		case ItemConfiguration.YES_DESTROY_ITEM_BUTTON:
			player.getItems().destroyItem(player.droppedItem);
			player.droppedItem = -1;
			break;

		case Resting.REST_BUTTON_ID:
			player.getRest().sitDown();
			break;

		case 9190:
		case 9191:
		case 9192:
		case 9193:
		case 9194:
		case 9178:
		case 9179:
		case 9180:
		case 9181:
		case 9167:
		case 9168:
		case 9169:
		case 9157:
		case 9158:
			player.getDialogueButtons().click(actionButtonId);
			break;

		case LunarSpellbook.VENGEANCE_BUTTON_ID:
			player.getLunars().castVengeance();
			break;

		case 71074:
			if (player.clanId >= 0) {
				if (Server.clanChat.clans[player.clanId].owner.equalsIgnoreCase(player.playerName)) {
					Server.clanChat.sendLootShareMessage(player.clanId, "Lootshare has been toggled to " + (!Server.clanChat.clans[player.clanId].lootshare ? "on" : "off") + " by the clan leader.");
					Server.clanChat.clans[player.clanId].lootshare = !Server.clanChat.clans[player.clanId].lootshare;
				} else
					player.sendMessage("Only the owner of the clan has the power to do that.");
			}
			break;

		case 34185:
		case 34184:
		case 34183:
		case 34182:
		case 34189:
		case 34188:
		case 34187:
		case 34186:
		case 34193:
		case 34192:
		case 34191:
		case 34190:
			if (player.craftingLeather)
				player.getCrafting().handleCraftingClick(actionButtonId);
			if (player.getFletching().fletching)
				player.getFletching().handleFletchingClick(actionButtonId);
			break;

		case 15147:
			if (player.smeltInterface) {
				player.smeltType = 2349;
				player.smeltAmount = 1;
				player.getSmithing().startSmelting(player.smeltType);
			}
			break;

		case 15151:
			if (player.smeltInterface) {
				player.smeltType = 2351;
				player.smeltAmount = 1;
				player.getSmithing().startSmelting(player.smeltType);
			}
			break;

		case 15159:
			if (player.smeltInterface) {
				player.smeltType = 2353;
				player.smeltAmount = 1;
				player.getSmithing().startSmelting(player.smeltType);
			}
			break;

		case 29017:
			if (player.smeltInterface) {
				player.smeltType = 2359;
				player.smeltAmount = 1;
				player.getSmithing().startSmelting(player.smeltType);
			}
			break;

		case 29022:
			if (player.smeltInterface) {
				player.smeltType = 2361;
				player.smeltAmount = 1;
				player.getSmithing().startSmelting(player.smeltType);
			}
			break;

		case 29026:
			if (player.smeltInterface) {
				player.smeltType = 2363;
				player.smeltAmount = 1;
				player.getSmithing().startSmelting(player.smeltType);
			}
			break;
		case 58253:
			// c.getPA().showInterface(15106);
			player.getItems().writeBonus();
			break;

		case 59004:
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 70212:
			if (player.clanId > -1)
				Server.clanChat.leaveClan(player.playerId, player.clanId);
			else
				player.sendMessage("You are not in a clan.");
			break;

		case 62137:
			if (player.clanId >= 0) {
				player.sendMessage("You are already in a clan.");
				break;
			}
			if (player.getOutStream() != null) {
				player.getOutStream().createFrame(187);
				player.flushOutStream();
			}
			break;

		case 1093:
		case 1094:
		case 1097:
			if (player.autocastId > 0) {
				player.getPlayerAssistant().resetAutocast();
			} else {
				if (player.playerMagicBook == 1) {
					if (player.playerEquipment[player.playerWeapon] == 4675)
						player.setSidebarInterface(0, 1689);
					else
						player.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (player.playerMagicBook == 0) {
					if (player.playerEquipment[player.playerWeapon] == 4170) {
						player.setSidebarInterface(0, 12050);
					} else {
						player.setSidebarInterface(0, 1829);
					}
				}

			}
			break;

		/** Specials **/
		case 29188:
			player.specBarId = 7636; // the special attack text - sendframe126(S P E
			// C I A L A T T A C K, c.specBarId);
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		case 29163:
			player.specBarId = 7611;
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		case 33033:
			player.specBarId = 8505;
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		case 29038:
			player.specBarId = 7486;
			/*
			 * if (c.specAmount >= 5) { c.attackTimer = 0;
			 * c.getCombat().attackPlayer(c.playerIndex); c.usingSpecial = true;
			 * c.specAmount -= 5; }
			 */
			player.getCombat().handleGmaulPlayer();
			player.getItems().updateSpecialBar();
			break;

		case 29063:
			if (player.getCombat().checkSpecAmount(player.playerEquipment[player.playerWeapon])) {
				player.gfx0(246);
				player.forcedChat("Raarrrrrgggggghhhhhhh!");
				player.startAnimation(1056);
				player.playerLevel[2] = player.getLevelForXP(player.playerXP[2]) + (player.getLevelForXP(player.playerXP[2]) * 15 / 100);
				player.getPlayerAssistant().refreshSkill(2);
				player.getItems().updateSpecialBar();
			} else {
				player.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;

		case 48023:
			player.specBarId = 12335;
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		case 29138:
			player.specBarId = 7586;
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		case 29113:
			player.specBarId = 7561;
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		case 29238:
			player.specBarId = 7686;
			player.usingSpecial = !player.usingSpecial;
			player.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
		case 26040:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			player.duelSlot = -1;
			player.getDuelArena().selectRule(10);
			break;

		case 53245: // no helm
			player.duelSlot = 0;
			player.getDuelArena().selectRule(11);
			break;

		case 53246: // no cape
			player.duelSlot = 1;
			player.getDuelArena().selectRule(12);
			break;

		case 53247: // no ammy
			player.duelSlot = 2;
			player.getDuelArena().selectRule(13);
			break;

		case 53249: // no weapon.
			player.duelSlot = 3;
			player.getDuelArena().selectRule(14);
			break;

		case 53250: // no body
			player.duelSlot = 4;
			player.getDuelArena().selectRule(15);
			break;

		case 53251: // no shield
			player.duelSlot = 5;
			player.getDuelArena().selectRule(16);
			break;

		case 53252: // no legs
			player.duelSlot = 7;
			player.getDuelArena().selectRule(17);
			break;

		case 53255: // no gloves
			player.duelSlot = 9;
			player.getDuelArena().selectRule(18);
			break;

		case 53254: // no boots
			player.duelSlot = 10;
			player.getDuelArena().selectRule(19);
			break;

		case 53253: // no rings
			player.duelSlot = 12;
			player.getDuelArena().selectRule(20);
			break;

		case 53248: // no arrows
			player.duelSlot = 13;
			player.getDuelArena().selectRule(21);
			break;

		case 26018:
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o == null) {
				player.getDuelArena().declineDuel();
				return;
			}

			if (player.duelRule[2] && player.duelRule[3] && player.duelRule[4]) {
				player.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			player.duelStatus = 2;
			if (player.duelStatus == 2) {
				player.getPlayerAssistant().sendString("Waiting for other player...", 6684);
				o.getPlayerAssistant().sendString("Other player has accepted.", 6684);
			}
			if (o.duelStatus == 2) {
				o.getPlayerAssistant().sendString("Waiting for other player...", 6684);
				player.getPlayerAssistant().sendString("Other player has accepted.", 6684);
			}

			if (player.duelStatus == 2 && o.duelStatus == 2) {
				player.canOffer = false;
				o.canOffer = false;
				player.duelStatus = 3;
				o.duelStatus = 3;
				player.getDuelArena().confirmDuel();
				o.getDuelArena().confirmDuel();
			}
			break;

		case 25120:
			if (player.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) PlayerHandler.players[player.duelingWith];
			if (o1 == null) {
				player.getDuelArena().declineDuel();
				return;
			}

			player.duelStatus = 4;
			if (o1.duelStatus == 4 && player.duelStatus == 4) {
				player.getDuelArena().startDuel();
				o1.getDuelArena().startDuel();
				o1.duelCount = 4;
				player.duelCount = 4;
				player.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				player.getPlayerAssistant().sendString("Waiting for other player...", 6571);
				o1.getPlayerAssistant().sendString("Other player has accepted", 6571);
			}
			break;

		case 4169: // god spell charge
			player.usingMagic = true;
			if (!player.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - player.godSpellDelay < 300000) {
				player.sendMessage("You still feel the charge in your body!");
				break;
			}
			player.godSpellDelay = System.currentTimeMillis();
			player.sendMessage("You feel charged with a magical power!");
			player.gfx100(player.MAGIC_SPELLS[48][3]);
			player.startAnimation(player.MAGIC_SPELLS[48][2]);
			player.usingMagic = false;
			break;

		case 9125: // Accurate
		case 6221: // range accurate
		case 22228: // punch (unarmed)
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)
			player.fightMode = 0;
			if (player.autocasting)
				player.getPlayerAssistant().resetAutocast();
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 22229: // block (unarmed)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
			player.fightMode = 1;
			if (player.autocasting)
				player.getPlayerAssistant().resetAutocast();
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			player.fightMode = 3;
			if (player.autocasting)
				player.getPlayerAssistant().resetAutocast();
			break;

		case 9128: // Aggressive
		case 6220: // range rapid
		case 22230: // kick (unarmed)
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 8236: // slash (dagger)
			player.fightMode = 2;
			if (player.autocasting)
				player.getPlayerAssistant().resetAutocast();
			break;

		/** Prayers **/
		case 21233: // thick skin
			player.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			player.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			player.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			player.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			player.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			player.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			player.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			player.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			player.getCombat().activatePrayer(8);
			break;
		case 21240:
			player.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			player.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			player.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			player.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			player.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			player.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			player.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			player.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			player.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			player.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			player.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			player.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			player.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			player.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			player.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			player.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			player.getCombat().activatePrayer(25);
			break;

		case 13092:
			if (System.currentTimeMillis() - player.buttonDelay < 400) {
				player.buttonDelay = System.currentTimeMillis();
				break;
			} else {
				player.buttonDelay = System.currentTimeMillis();
			}
			Client ot = (Client) PlayerHandler.players[player.tradeWith];
			if (ot == null) {
				player.getTradeAndDuel().declineTrade();
				player.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			player.getPlayerAssistant().sendString("Waiting for other player...", 3431);
			ot.getPlayerAssistant().sendString("Other player has accepted", 3431);
			player.goodTrade = true;
			ot.goodTrade = true;
			for (GameItem item : player.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < player.getTradeAndDuel().offeredItems.size()) {
						player.sendMessage(ot.playerName + " only has " + ot.getItems().freeSlots() + " free slots, please remove " + (player.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots()) + " items.");
						ot.sendMessage(player.playerName + " has to remove " + (player.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots()) + " items or you could offer them " + (player.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots()) + " items.");
						player.goodTrade = false;
						ot.goodTrade = false;
						player.getPlayerAssistant().sendString("Not enough inventory space...", 3431);
						ot.getPlayerAssistant().sendString("Not enough inventory space...", 3431);
						break;
					} else {
						player.getPlayerAssistant().sendString("Waiting for other player...", 3431);
						ot.getPlayerAssistant().sendString("Other player has accepted", 3431);
						player.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (player.inTrade && !player.tradeConfirmed && ot.goodTrade && player.goodTrade) {
				player.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					player.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}
			break;

		case 13218:
			if (System.currentTimeMillis() - player.buttonDelay < 400) {
				player.buttonDelay = System.currentTimeMillis();
				break;
			} else {
				player.buttonDelay = System.currentTimeMillis();
			}
			player.tradeAccepted = true;
			Client ot1 = (Client) PlayerHandler.players[player.tradeWith];
			if (ot1 == null) {
				player.getTradeAndDuel().declineTrade();
				player.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}

			if (player.inTrade && player.tradeConfirmed && ot1.tradeConfirmed && !player.tradeConfirmed2) {
				player.tradeConfirmed2 = true;
				if (ot1.tradeConfirmed2) {
					player.acceptedTrade = true;
					ot1.acceptedTrade = true;
					player.getTradeAndDuel().giveItems();
					ot1.getTradeAndDuel().giveItems();
					break;
				}
				ot1.getPlayerAssistant().sendString("Other player has accepted.", 3535);
				player.getPlayerAssistant().sendString("Waiting for other player...", 3535);
			}
			break;

		case 24017:
			player.getPlayerAssistant().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			player.getItems().sendWeapon(player.playerEquipment[player.playerWeapon], player.getItems().getItemName(player.playerEquipment[player.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 :
			// c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (player.isAutoButton(actionButtonId))
			player.assignAutocast(actionButtonId);
	}

}
