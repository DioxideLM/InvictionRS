package org.invictus.model.players;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.Future;

import org.apache.mina.common.IoSession;
import org.invictus.Configuration;
import org.invictus.Server;
import org.invictus.model.items.ItemAssistant;
import org.invictus.model.items.OperateItem;
import org.invictus.model.npcs.NPCHandler;
import org.invictus.model.players.commands.AdministratorCommands;
import org.invictus.model.players.commands.DeveloperCommands;
import org.invictus.model.players.commands.MemberCommands;
import org.invictus.model.players.commands.ModeratorCommands;
import org.invictus.model.players.commands.PlayerCommands;
import org.invictus.model.players.content.Emotes;
import org.invictus.model.players.content.Resting;
import org.invictus.model.players.content.Transformation;
import org.invictus.model.players.content.consumables.Food;
import org.invictus.model.players.content.consumables.PotionMixing;
import org.invictus.model.players.content.consumables.Potions;
import org.invictus.model.players.content.dialogue.DialogueButtons;
import org.invictus.model.players.content.dialogue.DialogueHandler;
import org.invictus.model.players.content.dialogue.DialogueList;
import org.invictus.model.players.content.minigames.barrows.Barrows;
import org.invictus.model.players.content.minigames.championchallenge.ChampionChallenge;
import org.invictus.model.players.content.minigames.duelarena.DuelArena;
import org.invictus.model.players.content.minigames.fightcaves.FightCavesMisc;
import org.invictus.model.players.content.quests.QuestHandler;
import org.invictus.model.players.content.quests.testquest.TestQuest;
import org.invictus.model.players.packets.Packet;
import org.invictus.model.players.packets.PacketHandler;
import org.invictus.model.players.packets.StaticPacketBuilder;
import org.invictus.model.players.skills.SkillGuides;
import org.invictus.model.players.skills.agility.Agility;
import org.invictus.model.players.skills.agility.BarbarianAgilityCourse;
import org.invictus.model.players.skills.agility.GnomeAgilityCourse;
import org.invictus.model.players.skills.agility.WildernessAgilityCourse;
import org.invictus.model.players.skills.construction.Construction;
import org.invictus.model.players.skills.cooking.Cooking;
import org.invictus.model.players.skills.crafting.Crafting;
import org.invictus.model.players.skills.farming.Farming;
import org.invictus.model.players.skills.firemaking.Firemaking;
import org.invictus.model.players.skills.fishing.Fishing;
import org.invictus.model.players.skills.fletching.Fletching;
import org.invictus.model.players.skills.herblore.Grinding;
import org.invictus.model.players.skills.herblore.HerbCleaning;
import org.invictus.model.players.skills.herblore.PotionMaking;
import org.invictus.model.players.skills.magic.LunarSpellbook;
import org.invictus.model.players.skills.mining.Mining;
import org.invictus.model.players.skills.prayer.Prayer;
import org.invictus.model.players.skills.runecrafting.Runecrafting;
import org.invictus.model.players.skills.slayer.Slayer;
import org.invictus.model.players.skills.smithing.Smithing;
import org.invictus.model.players.skills.smithing.SmithingInterface;
import org.invictus.model.players.skills.thieving.Pickpocketing;
import org.invictus.model.players.skills.thieving.Thieving;
import org.invictus.model.players.skills.thieving.WallSafes;
import org.invictus.model.players.skills.woodcutting.Woodcutting;
import org.invictus.model.shops.ShopAssistant;
import org.invictus.net.HostList;
import org.invictus.util.Announcements;
import org.invictus.util.Misc;
import org.invictus.util.Stream;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private Trading tradeAndDuel = new Trading(this);
	private DuelArena duelArena = new DuelArena(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private Emotes emotes = new Emotes(this);
	private ChampionChallenge champChallenge = new ChampionChallenge(this);
	private DialogueButtons dialogueButtons = new DialogueButtons(this);
	private Banking banking = new Banking(this);
	private Resting resting = new Resting(this);
	private Announcements announcements = new Announcements(this);
	private FightCavesMisc fightCavesMisc = new FightCavesMisc(this);
	private OperateItem operateItem = new OperateItem(this);
	private Teleports teleports = new Teleports(this);
	private PlayerSettings playerSettings = new PlayerSettings(this);
	private Transformation transformation = new Transformation(this);
	private DialogueList dialogueList = new DialogueList(this);

	/**
	 * Quest Instances
	 */
	private TestQuest testQuest = new TestQuest(this);
	private QuestHandler questHandler = new QuestHandler(this);

	/**
	 * Skill instances
	 */
	private HerbCleaning herbCleaning = new HerbCleaning(this);
	private PotionMaking potionMaking = new PotionMaking(this);
	private Grinding grinding = new Grinding(this);
	private Slayer slayer = new Slayer(this);
	private LunarSpellbook lunars = new LunarSpellbook(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	private Woodcutting woodcutting = new Woodcutting(this);
	private Mining mine = new Mining(this);
	private Cooking cooking = new Cooking(this);
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private Prayer prayer = new Prayer(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Pickpocketing pickpocketing = new Pickpocketing(this);
	private WallSafes wallSafes = new WallSafes(this);
	private Firemaking firemaking = new Firemaking(this);
	private Construction construction = new Construction(this);
	private SkillGuides skillGuides = new SkillGuides(this);
	private Agility agility = new Agility(this);
	private GnomeAgilityCourse gnomeAgilityCourse = new GnomeAgilityCourse(this);
	private BarbarianAgilityCourse barbarianAgilityCourse = new BarbarianAgilityCourse(this);
	private WildernessAgilityCourse wildernessAgilityCourse = new WildernessAgilityCourse(this);

	/**
	 * Command instances
	 */
	private PlayerCommands playerCommands = new PlayerCommands(this);
	private MemberCommands donatorCommands = new MemberCommands(this);
	private ModeratorCommands modCommands = new ModeratorCommands(this);
	private AdministratorCommands adminCommands = new AdministratorCommands(this);
	private DeveloperCommands devCommands = new DeveloperCommands(this);

	private Barrows barrows = new Barrows(this);

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	private Future<?> currentTask;

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		synchronized (this) {
			outStream = new Stream(new byte[10000]);
			outStream.currentOffset = 0;
		}
		inStream = new Stream(new byte[10000]);
		inStream.currentOffset = 0;
		buffer = new byte[10000];
	}

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0)
			return;
		synchronized (this) {
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset];
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		}
	}

	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
	        0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
	        0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
	        0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
	        2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
	        0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
	        8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
	        6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
	        0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
	        0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
	        0, 13, 0, -1, 0, 0, 0, 0, 0, 0, // 100
	        0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
	        1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
	        0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
	        0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
	        0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
	        0, 0, 0, 0, -1, -1, 0, 0, 0, 0, // 160
	        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
	        0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
	        0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
	        2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
	        4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
	        0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
	        1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
	        0, 4, 0, 0, 0, 0, -1, 0, -1, 4, // 240
	        0, 0, 6, 6, 0, 0, 0 // 250
	};

	public void destruct() {
		if (session == null)
			return;
		// PlayerSaving.getSingleton().requestSave(playerId);
		getPlayerAssistant().removeFromCW();
		if (inPits)
			Server.fightPits.removePlayerFromPits(playerId);
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		Misc.println("[Disconnected] Username: " + playerName + ", IP: " + connectedFrom);
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public void sendMessage(String s) {
		synchronized (this) {
			if (getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			}
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		synchronized (this) {
			if (getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			}
		}
	}

	public void initialize() {
		synchronized (this) {
			outStream.createFrame(249);
			outStream.writeByteA(1); // 1 for members, zero for free
			outStream.writeWordBigEndianA(playerId);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			for (int i = 0; i < 25; i++) {
				getPlayerAssistant().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPlayerAssistant().refreshSkill(i);
			}
			for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
				prayerActive[p] = false;
				getPlayerAssistant().sendConfig(PRAYER_GLOW[p], 0);
			}
			if(isInvisible) {
				getTransformation().ghostTransform();
			}
			getPlayerAssistant().handleWeaponStyle();
			getEmotes().displayEmotes();
			getQuestHandler().updateQuestTabText();
			accountFlagged = getPlayerAssistant().checkForFlags();
			// getPA().sendFrame36(43, fightMode-1);
			getPlayerAssistant().sendConfig(108, 0);// resets autocast button
			getPlayerAssistant().sendConfig(172, 1);
			getPlayerAssistant().sendCameraReset(); // reset screen
			getPlayerAssistant().setChatOptions(0, 0, 0); // reset private messaging options
			setSidebarInterface(1, 3917);
			setSidebarInterface(2, 638);
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 5608);
			if (playerMagicBook == 0) {
				setSidebarInterface(6, 1151); // modern
			} else {
				if (playerMagicBook == 2) {
					setSidebarInterface(6, 29999); // lunar
				} else {
					setSidebarInterface(6, 12855); // ancient
				}
			}
			correctCoordinates();
			setSidebarInterface(7, 18128);
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			// setSidebarInterface(11, 4445); // wrench tab
			setSidebarInterface(11, 904); // wrench tab
			setSidebarInterface(12, 147); // run tab
			setSidebarInterface(13, -1);
			setSidebarInterface(0, 2423);
			sendMessage("Welcome to " + Configuration.SERVER_NAME);
			getAnnouncements().loadAnnouncements();
			//getQuestTab().questTabText();
			getPlayerAssistant().showOption(4, 0, "Trade With", 3);
			getPlayerAssistant().showOption(5, 0, "Follow", 4);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
			getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows], playerEquipmentN[playerArrows], playerArrows);
			getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
			getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
			getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
			getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
			getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
			getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon], playerEquipmentN[playerWeapon], playerWeapon);
			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPlayerAssistant().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveCharacter = true;
			Misc.println("[Connected] Username: " + playerName + ", IP: " + connectedFrom);
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPlayerAssistant().clearClanChat();
			getPlayerAssistant().resetFollow();
			if (addStarter)
				getPlayerAssistant().addStarter();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Calendar cal = Calendar.getInstance();
			creationDate = dateFormat.format(cal.getTime());
			if (autoRet)
				getPlayerAssistant().sendConfig(172, 1);
			else
				getPlayerAssistant().sendConfig(172, 0);
		}
	}

	public void update() {
		synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		}
	}

	public void logout() {
		synchronized (this) {
			if (System.currentTimeMillis() - logoutDelay > 10000) {
				outStream.createFrame(109);
				properLogout = true;
			} else {
				sendMessage("You must wait a few seconds from being out of combat to logout.");
			}
		}
	}

	public int packetSize = 0, packetType = -1;
	public int donatorPoints = 0;
	public boolean isResting;
	public boolean doingObstacle;

	public void process() {

		if (wcTimer > 0 && woodcut[0] > 0) {
			wcTimer--;
		} else if (wcTimer == 0 && woodcut[0] > 0) {
			getWoodcutting().cutWood();
		} else if (miningTimer > 0 && mining[0] > 0) {
			miningTimer--;
		} else if (miningTimer == 0 && mining[0] > 0) {
			getMining().mineOre();
		} else if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
		} else if (fishing && fishTimer > 0) {
			fishTimer--;
		} else if (fishing && fishTimer == 0) {
			getFishing().catchFish();
		}

		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage / 2;
			if (damage > 0) {
				sendMessage("Applying poison damage.");
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}
		}

		if (System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + (--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Configuration.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}

		if (System.currentTimeMillis() - specDelay > 17500) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if (clickObjectType > 0 && goodDistance(objectX + objectXOffset, objectY + objectYOffset, getX(), getY(), objectDistance)) {
			if (clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if (clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if (clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}

		if ((clickNpcType > 0) && NPCHandler.npcs[npcClickIndex] != null) {
			if (goodDistance(getX(), getY(), NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY(), 1)) {
				if (clickNpcType == 1) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType);
				}
				if (clickNpcType == 2) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if (clickNpcType == 3) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}

		if (walkingToItem) {
			if (getX() == pItemX && getY() == pItemY || goodDistance(getX(), getY(), pItemX, pItemY, 1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX, pItemY, true);
			}
		}

		if (followId > 0) {
			getPlayerAssistant().followPlayer();
		} else if (followId2 > 0) {
			getPlayerAssistant().followNpc();
		}

		getCombat().handlePrayerDrain();

		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPlayerAssistant().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPlayerAssistant().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPlayerAssistant().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPlayerAssistant().refreshSkill(level);
				}
			}
		}

		if (System.currentTimeMillis() - teleGrabDelay > 1550 && usingMagic) {
			usingMagic = false;
			if (Server.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
			}
		}

		if (inWild()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			getPlayerAssistant().sendWalkableInterface(197);
			if (inMulti()) {
				getPlayerAssistant().sendString("@yel@Level: " + wildLevel, 199);
			} else {
				getPlayerAssistant().sendString("@yel@Level: " + wildLevel, 199);
			}
			getPlayerAssistant().showOption(3, 0, "Attack", 1);
		} else if (inDuelArena()) {
			getPlayerAssistant().sendWalkableInterface(201);
			if (duelStatus == 5) {
				getPlayerAssistant().showOption(3, 0, "Attack", 1);
			} else {
				getPlayerAssistant().showOption(3, 0, "Challenge", 1);
			}
		} else if (inBarrows()) {
			getPlayerAssistant().sendMinimapState(2);
			getPlayerAssistant().sendString("Kill Count: " + barrowsKillCount, 4536);
			getPlayerAssistant().sendWalkableInterface(4535);
		} else if (inCwGame || inPits) {
			getPlayerAssistant().showOption(3, 0, "Attack", 1);
		} else if (getPlayerAssistant().inPitsWait()) {
			getPlayerAssistant().showOption(3, 0, "Null", 1);
		} else if (!inCwWait) {
			getPlayerAssistant().sendMinimapState(0);
			getPlayerAssistant().sendWalkableInterface(-1);
			getPlayerAssistant().showOption(3, 0, "Null", 1);
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPlayerAssistant().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPlayerAssistant().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPlayerAssistant().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPlayerAssistant().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPlayerAssistant().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, PlayerHandler.players[frozenBy].absX, PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPlayerAssistant().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getTeleports().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}

		if (timeOutCounter > Configuration.TIMEOUT) {
			disconnected = true;
		}

		timeOutCounter++;

		if (inTrade && tradeResetNeeded) {
			Client o = (Client) PlayerHandler.players[tradeWith];
			if (o != null) {
				if (o.tradeResetNeeded) {
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}

	public DialogueHandler getDialogueHandler() {
		return dialogueHandler;
	}

	public DialogueButtons getDialogueButtons() {
		return dialogueButtons;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public Trading getTradeAndDuel() {
		return tradeAndDuel;
	}

	public DuelArena getDuelArena() {
		return duelArena;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public IoSession getSession() {
		return session;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	public LunarSpellbook getLunars() {
		return lunars;
	}

	public Announcements getAnnouncements() {
		return announcements;
	}

	public FightCavesMisc getFightCavesMisc() {
		return fightCavesMisc;
	}

	public OperateItem getOperateItem() {
		return operateItem;
	}

	public Teleports getTeleports() {
		return teleports;
	}

	public PlayerSettings getPlayerSettings() {
		return playerSettings;
	}
	
	public Transformation getTransformation() {
		return transformation;
	}
	
	public DialogueList getDialogueList() {
		return dialogueList;
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	public Woodcutting getWoodcutting() {
		return woodcutting;
	}

	public Mining getMining() {
		return mine;
	}

	public Cooking getCooking() {
		return cooking;
	}

	public GnomeAgilityCourse getGnomeAgilityCourse() {
		return gnomeAgilityCourse;
	}
	
	public BarbarianAgilityCourse getBarbarianAgilityCourse() {
		return barbarianAgilityCourse;
	}
	
	public WildernessAgilityCourse getWildernessAgilityCourse() {
		return wildernessAgilityCourse;
	}

	public Pickpocketing getPickpocketing() {
		return pickpocketing;
	}

	public WallSafes getWallSafes() {
		return wallSafes;
	}

	public Agility getAgility() {
		return agility;
	}

	public Fishing getFishing() {
		return fish;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public Farming getFarming() {
		return farming;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public HerbCleaning getHerbCleaning() {
		return herbCleaning;
	}

	public PotionMaking getPotionMaking() {
		return potionMaking;
	}

	public Grinding getGrinding() {
		return grinding;
	}

	public Construction getConstruction() {
		return construction;
	}

	public Firemaking getFiremaking() {
		return firemaking;
	}

	public SkillGuides getSkillGuides() {
		return skillGuides;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public Fletching getFletching() {
		return fletching;
	}

	/**
	 * End of Skill Constructors
	 */

	public PlayerCommands getPlayerCommands() {
		return playerCommands;
	}

	public MemberCommands getDonatorCommands() {
		return donatorCommands;
	}

	public ModeratorCommands getModCommands() {
		return modCommands;
	}

	public AdministratorCommands getAdminCommands() {
		return adminCommands;
	}

	public DeveloperCommands getDevCommands() {
		return devCommands;
	}

	public Barrows getBarrows() {
		return barrows;
	}

	public Emotes getEmotes() {
		return emotes;
	}

	public QuestHandler getQuestHandler() {
		return questHandler;
	}

	public TestQuest getTestQuest() {
		return testQuest;
	}

	public ChampionChallenge getChampChallenge() {
		return champChallenge;
	}

	public Banking getBank() {
		return banking;
	}

	public Resting getRest() {
		return resting;
	}

	public void queueMessage(Packet arg1) {
		// synchronized(queuedPackets) {
		// if (arg1.getId() != 41)
		queuedPackets.add(arg1);
		// else
		// processPacket(arg1);
		// }
	}

	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized (queuedPackets) {
			p = queuedPackets.poll();
		}
		if (p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if (packetType > 0) {
			// sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}

	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if (p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if (packetType > 0) {
				// sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}

	public void correctCoordinates() {
		if (inPcGame()) {
			getPlayerAssistant().movePlayer(2657, 2639, 0);
		}
		/*if (inFightCaves()) {
			getPlayerAssistant().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
		
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client) PlayerHandler.players[playerId]);
					c.stop();
				}
			}, 10000);
		
		}*/

	}

}
