package org.invictus.model.players;

import org.invictus.Configuration;
import org.invictus.Server;
import org.invictus.model.npcs.NPCHandler;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;
import org.invictus.world.Location;

public class PlayerAssistant {

	private Client player;

	public PlayerAssistant(Client player) {
		this.player = player;
	}

	/**
	 * MulitCombat icon
	 * 
	 * @param iconStatus
	 *            0 = off 1 = on
	 */
	public void multiWay(int iconStatus) {
		player.outStream.createFrame(61);
		player.outStream.writeByte(iconStatus);
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	/**
	 * Applies a specified amount of damage to the player.
	 * 
	 * @param damageAmount
	 */
	public void doDamage(int damageAmount) {
		player.playerLevel[3] -= damageAmount;
		player.getPlayerAssistant().refreshSkill(3);
	}

	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(player, x, y, true, 1, 1);
	}

	/**
	 * Used to announce a global message to the entire server.
	 * 
	 * @param message
	 */
	public void sendGlobalMessage(String message) {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				c.sendMessage("@blu@[Server Announcement]: " + message);
			}
		}
	}

	public void clearClanChat() {
		player.clanId = -1;
		player.getPlayerAssistant().sendString("Talking in: ", 18139);
		player.getPlayerAssistant().sendString("Owner: ", 18140);
		for (int j = 18144; j < 18244; j++)
			player.getPlayerAssistant().sendString("", j);
	}

	public void resetAutocast() {
		player.autocastId = 0;
		player.autocasting = false;
		player.getPlayerAssistant().sendConfig(108, 0);
	}

	/**
	 * Attaches text to an interface.
	 * 
	 * @param text
	 * @param interfaceId
	 */
	public void sendString(String text, int interfaceId) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(126);
			player.getOutStream().writeString(text);
			player.getOutStream().writeWordA(interfaceId);
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(134);
			player.getOutStream().writeByte(skillNum);
			player.getOutStream().writeDWord_v1(XP);
			player.getOutStream().writeByte(currentLevel);
			player.flushOutStream();
		}
	}

	/**
	 * Force sets a players current sidebar interface.
	 * 
	 * @param sideIcon
	 */
	public void sendForceTab(int sideIcon) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(106);
			player.getOutStream().writeByteC(sideIcon);
			player.flushOutStream();
			requestUpdates();
		}
	}

	/**
	 * Resets the camera position.
	 */
	public void sendCameraReset() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(107);
			player.flushOutStream();
		}
	}

	/**
	 * Sends a configuration (e.g duel interface rules)
	 * 
	 * @param id
	 * @param state
	 */
	public void sendConfig(int id, int state) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(36);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeByte(state);
			player.flushOutStream();
		}
	}

	/**
	 * Displays a player's head model on an interface.
	 * 
	 * @param Frame
	 */
	public void sendPlayerHeadOnInterface(int Frame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(185);
			player.getOutStream().writeWordBigEndianA(Frame);
		}
	}

	/**
	 * Displays a normal interface.
	 * 
	 * @param interfaceid
	 */
	public void sendInterface(int interfaceid) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(97);
			player.getOutStream().writeWord(interfaceid);
			player.flushOutStream();
		}
	}

	/**
	 * Displays an interface over the sidebar area.
	 * 
	 * @param MainFrame
	 * @param SubFrame
	 */
	public void sendInventoryOverlay(int MainFrame, int SubFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(248);
			player.getOutStream().writeWordA(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
	}

	/**
	 * Displays an item model inside an interface.
	 * 
	 * @param MainFrame
	 * @param SubFrame
	 * @param SubFrame2
	 */
	public void sendInterfaceItem(int MainFrame, int SubFrame, int SubFrame2) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(246);
			player.getOutStream().writeWordBigEndian(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.getOutStream().writeWord(SubFrame2);
			player.flushOutStream();
		}
	}

	/**
	 * Sets an interface to be hidden until hovered over.
	 * 
	 * @param MainFrame
	 * @param SubFrame
	 */
	public void sendHiddenInterface(int MainFrame, int SubFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(171);
			player.getOutStream().writeByte(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
	}

	/**
	 * Sets an interface's model animation.
	 * 
	 * @param MainFrame
	 * @param SubFrame
	 */
	public void sendInterfaceAnimation(int MainFrame, int SubFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(200);
			player.getOutStream().writeWord(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
	}

	/**
	 * Sets the offset for drawing of an interface.
	 * 
	 * @param i
	 * @param o
	 * @param id
	 */
	public void sendInterfaceOffset(int i, int o, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(70);
			player.getOutStream().writeWord(i);
			player.getOutStream().writeWordBigEndian(o);
			player.getOutStream().writeWordBigEndian(id);
			player.flushOutStream();
		}
	}

	public void sendFrame75(int MainFrame, int SubFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(75);
			player.getOutStream().writeWordBigEndianA(MainFrame);
			player.getOutStream().writeWordBigEndianA(SubFrame);
			player.flushOutStream();
		}
	}

	/**
	 * Shows an interface in the chat box.
	 * 
	 * @param Frame
	 */
	public void sendChatInterface(int Frame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(164);
			player.getOutStream().writeWordBigEndian_dup(Frame);
			player.flushOutStream();
		}
	}

	/**
	 * Friends list load status.
	 * 
	 * @param i
	 */
	public void setPrivateMessaging(int i) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(221);
			player.getOutStream().writeByte(i);
			player.flushOutStream();
		}
	}

	/**
	 * Sends the chat privacy settings.
	 * 
	 * @param publicChat
	 * @param privateChat
	 * @param tradeBlock
	 */
	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(206);
			player.getOutStream().writeByte(publicChat);
			player.getOutStream().writeByte(privateChat);
			player.getOutStream().writeByte(tradeBlock);
			player.flushOutStream();
		}
	}

	/**
	 * Toggles an interface button (this can have multiple states, e.g dueling interface)
	 * 
	 * @param id
	 * @param state
	 */
	public void sendToggleInterfaceButton(int id, int state) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(87);
			player.getOutStream().writeWordBigEndian_dup(id);
			player.getOutStream().writeDWord_v1(state);
			player.flushOutStream();
		}
	}

	/**
	 * Sends a private message to another player.
	 * 
	 * @param name
	 * @param rights
	 * @param chatmessage
	 * @param messagesize
	 */
	public void sendPrivateMessage(long name, int rights, byte[] chatmessage, int messagesize) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSize(196);
			player.getOutStream().writeQWord(name);
			player.getOutStream().writeDWord(player.lastChatId++);
			player.getOutStream().writeByte(rights);
			player.getOutStream().writeBytes(chatmessage, messagesize, 0);
			player.getOutStream().endFrameVarSize();
			player.flushOutStream();
			Misc.textUnpack(chatmessage, messagesize);
			Misc.longToPlayerName(name);
		}
	}

	/**
	 * Displays a hint icon over a mob
	 * 
	 * @param type
	 * @param id
	 */
	public void sendMobHintIcon(int type, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(254);
			player.getOutStream().writeByte(type);
			player.getOutStream().writeWord(id);
			player.getOutStream().write3Byte(0);
			player.flushOutStream();
		}
	}

	public void createObjectHints(int x, int y, int height, int pos) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(254);
			player.getOutStream().writeByte(pos);
			player.getOutStream().writeWord(x);
			player.getOutStream().writeWord(y);
			player.getOutStream().writeByte(height);
			player.flushOutStream();
		}
	}

	public void loadPM(long playerName, int world) {
		boolean worldListFix = false;
		if (player.getOutStream() != null && player != null) {
			if (world != 0) {
				world += 9;
			} else if (!worldListFix) {
				world += 1;
			}
			player.getOutStream().createFrame(50);
			player.getOutStream().writeQWord(playerName);
			player.getOutStream().writeByte(world);
			player.flushOutStream();
		}
	}

	public void removeAllWindows() {
		if (player.getOutStream() != null && player != null) {
			player.getPlayerAssistant().resetVariables();
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
	}

	public void closeAllWindows() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
	}

	/**
	 * Updates a single item in a users inventory.
	 * 
	 * @param id
	 * @param slot
	 * @param column
	 * @param amount
	 */
	public void sendUpdateSingleItem(int id, int slot, int column, int amount) {
		if (player.getOutStream() != null && player != null) {
			player.outStream.createFrameVarSizeWord(34); // init item to smith screen
			player.outStream.writeWord(column); // Column Across Smith Screen
			player.outStream.writeByte(4); // Total Rows?
			player.outStream.writeDWord(slot); // Row Down The Smith Screen
			player.outStream.writeWord(id + 1); // item
			player.outStream.writeByte(amount); // how many there are?
			player.outStream.endFrameVarSizeWord();
		}
	}

	/**
	 * Displays an interface in walkable mode.
	 * 
	 * @param id
	 */
	public void sendWalkableInterface(int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(208);
			player.getOutStream().writeWordBigEndian_dup(id);
			player.flushOutStream();
		}
	}

	public int mapStatus = 0;

	/**
	 * Sets the mini map's state.
	 * 
	 * @param state
	 */
	public void sendMinimapState(int state) { // used for disabling map
		if (player.getOutStream() != null && player != null) {
			if (mapStatus != state) {
				mapStatus = state;
				player.getOutStream().createFrame(99);
				player.getOutStream().writeByte(state);
				player.flushOutStream();
			}
		}
	}

	/**
	 * Reseting animations for everyone
	 **/

	public void frame1() {
		for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Client person = (Client) PlayerHandler.players[i];
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (player.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPlayerAssistant().requestUpdates();
						}
					}
				}
			}
		}
	}

	/**
	 * Creating projectile
	 **/
	public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC((y - (player.getMapRegionY() * 8)) - 2);
			player.getOutStream().writeByteC((x - (player.getMapRegionX() * 8)) - 3);
			player.getOutStream().createFrame(117);
			player.getOutStream().writeByte(angle);
			player.getOutStream().writeByte(offY);
			player.getOutStream().writeByte(offX);
			player.getOutStream().writeWord(lockon);
			player.getOutStream().writeWord(gfxMoving);
			player.getOutStream().writeByte(startHeight);
			player.getOutStream().writeByte(endHeight);
			player.getOutStream().writeWord(time);
			player.getOutStream().writeWord(speed);
			player.getOutStream().writeByte(16);
			player.getOutStream().writeByte(64);
			player.flushOutStream();
		}
	}

	public void createProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC((y - (player.getMapRegionY() * 8)) - 2);
			player.getOutStream().writeByteC((x - (player.getMapRegionX() * 8)) - 3);
			player.getOutStream().createFrame(117);
			player.getOutStream().writeByte(angle);
			player.getOutStream().writeByte(offY);
			player.getOutStream().writeByte(offX);
			player.getOutStream().writeWord(lockon);
			player.getOutStream().writeWord(gfxMoving);
			player.getOutStream().writeByte(startHeight);
			player.getOutStream().writeByte(endHeight);
			player.getOutStream().writeWord(time);
			player.getOutStream().writeWord(speed);
			player.getOutStream().writeByte(slope);
			player.getOutStream().writeByte(64);
			player.flushOutStream();
		}
	}

	// projectiles for everyone within 25 squares
	public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							if (p.heightLevel == player.heightLevel)
								person.getPlayerAssistant().createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time);
						}
					}
				}
			}
		}
	}

	public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
		for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPlayerAssistant().createProjectile2(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time, slope);
						}
					}
				}
			}
		}
	}

	/**
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(y - (player.getMapRegionY() * 8));
			player.getOutStream().writeByteC(x - (player.getMapRegionX() * 8));
			player.getOutStream().createFrame(4);
			player.getOutStream().writeByte(0);
			player.getOutStream().writeWord(id);
			player.getOutStream().writeByte(height);
			player.getOutStream().writeWord(time);
			player.flushOutStream();
		}
	}

	// creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		for (int i = 0; i < Configuration.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPlayerAssistant().stillGfx(id, x, y, height, time);
						}
					}
				}
			}
		}
	}

	/**
	 * Objects, add and remove
	 **/
	public void object(int objectId, int objectX, int objectY, int face, int objectType) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(objectY - (player.getMapRegionY() * 8));
			player.getOutStream().writeByteC(objectX - (player.getMapRegionX() * 8));
			player.getOutStream().createFrame(101);
			player.getOutStream().writeByteC((objectType << 2) + (face & 3));
			player.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				player.getOutStream().createFrame(151);
				player.getOutStream().writeByteS(0);
				player.getOutStream().writeWordBigEndian(objectId);
				player.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			player.flushOutStream();
		}
	}

	public void checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
		if (player.distanceToPoint(objectX, objectY) > 60)
			return;
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(objectY - (player.getMapRegionY() * 8));
			player.getOutStream().writeByteC(objectX - (player.getMapRegionX() * 8));
			player.getOutStream().createFrame(101);
			player.getOutStream().writeByteC((objectType << 2) + (face & 3));
			player.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				player.getOutStream().createFrame(151);
				player.getOutStream().writeByteS(0);
				player.getOutStream().writeWordBigEndian(objectId);
				player.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			player.flushOutStream();
		}
	}

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public void showOption(int i, int l, String s, int a) {
		if (player.getOutStream() != null && player != null) {
			if (!optionType.equalsIgnoreCase(s)) {
				optionType = s;
				player.getOutStream().createFrameVarSize(104);
				player.getOutStream().writeByteC(i);
				player.getOutStream().writeByteA(l);
				player.getOutStream().writeString(s);
				player.getOutStream().endFrameVarSize();
				player.flushOutStream();
			}
		}
	}

	/**
	 * Private Messaging
	 **/
	public void logIntoPM() {
		setPrivateMessaging(2);
		for (int i1 = 0; i1 < Configuration.MAX_PLAYERS; i1++) {
			Player p = PlayerHandler.players[i1];
			if (p != null && p.isActive) {
				Client o = (Client) p;
				if (o != null) {
					o.getPlayerAssistant().updatePM(player.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for (int i = 0; i < player.friends.length; i++) {
			if (player.friends[i] != 0) {
				for (int i2 = 1; i2 < Configuration.MAX_PLAYERS; i2++) {
					Player p = PlayerHandler.players[i2];
					if (p != null && p.isActive && Misc.playerNameToInt64(p.playerName) == player.friends[i]) {
						Client o = (Client) p;
						if (o != null) {
							if (player.playerRights >= 2 || p.privateChat == 0 || (p.privateChat == 1 && o.getPlayerAssistant().isInPM(Misc.playerNameToInt64(player.playerName)))) {
								loadPM(player.friends[i], 1);
								pmLoaded = true;
							}
							break;
						}
					}
				}
				if (!pmLoaded) {
					loadPM(player.friends[i], 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < Configuration.MAX_PLAYERS; i1++) {
				Player p = PlayerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					if (o != null) {
						o.getPlayerAssistant().updatePM(player.playerId, 1);
					}
				}
			}
		}
	}

	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = PlayerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		long l = Misc.playerNameToInt64(PlayerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (int i = 0; i < player.friends.length; i++) {
				if (player.friends[i] != 0) {
					if (l == player.friends[i]) {
						loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (int i = 0; i < player.friends.length; i++) {
				if (player.friends[i] != 0) {
					if (l == player.friends[i]) {
						if (o.getPlayerAssistant().isInPM(Misc.playerNameToInt64(player.playerName))) {
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (int i = 0; i < player.friends.length; i++) {
				if (player.friends[i] != 0) {
					if (l == player.friends[i] && player.playerRights < 2) {
						loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (int i = 0; i < player.friends.length; i++) {
			if (player.friends[i] != 0) {
				if (l == player.friends[i]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */

	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId, int healType) {
		player.attackTimer = player.getCombat().getAttackDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
		if (player.duelRule[5]) {
			player.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if (!player.isDead && System.currentTimeMillis() - player.foodDelay > 2000) {
			if (player.getItems().playerHasItem(itemId, 1, itemSlot)) {
				player.sendMessage("You drink the " + player.getItems().getItemName(itemId).toLowerCase() + ".");
				player.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				player.startAnimation(0x33D);
				player.getItems().deleteItem(itemId, itemSlot, 1);
				player.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}

	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {
		if (!player.getItems().playerHasItem(itemId, 1, slot)) {
			return;
		}

		switch (spellId) {
		case 1162: // low alch
			if (System.currentTimeMillis() - player.alchDelay > 1000) {
				if (!player.getCombat().checkMagicReqs(49)) {
					break;
				}
				if (itemId == 995) {
					player.sendMessage("You can't alch coins");
					break;
				}
				player.getItems().deleteItem(itemId, slot, 1);
				player.getItems().addItem(995, player.getShops().getItemShopValue(itemId) / 3);
				player.startAnimation(player.MAGIC_SPELLS[49][2]);
				player.gfx100(player.MAGIC_SPELLS[49][3]);
				player.alchDelay = System.currentTimeMillis();
				sendForceTab(6);
				addSkillXP(player.MAGIC_SPELLS[49][7] * SkillHandler.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;

		case 1178: // high alch
			if (System.currentTimeMillis() - player.alchDelay > 2000) {
				if (!player.getCombat().checkMagicReqs(50)) {
					break;
				}
				if (itemId == 995) {
					player.sendMessage("You can't alch coins");
					break;
				}
				player.getItems().deleteItem(itemId, slot, 1);
				player.getItems().addItem(995, (int) (player.getShops().getItemShopValue(itemId) * .75));
				player.startAnimation(player.MAGIC_SPELLS[50][2]);
				player.gfx100(player.MAGIC_SPELLS[50][3]);
				player.alchDelay = System.currentTimeMillis();
				sendForceTab(6);
				addSkillXP(player.MAGIC_SPELLS[50][7] * SkillHandler.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;
		}
	}

	/**
	 * Dieing
	 **/

	public void applyDead() {
		player.respawnTimer = 15;
		player.isDead = false;

		if (player.duelStatus != 6) {
			// c.killerId = c.getCombat().getKillerId(c.playerId);
			player.killerId = findKiller();
			Client o = (Client) PlayerHandler.players[player.killerId];
			if (o != null) {
				if (player.killerId != player.playerId)
					o.sendMessage("You have defeated " + player.playerName + "!");
				if (o.duelStatus == 5) {
					o.duelStatus++;
				}
			}
		}
		player.faceUpdate(0);
		player.npcIndex = 0;
		player.playerIndex = 0;
		player.stopMovement();
		if (player.duelStatus <= 4) {
			player.sendMessage("Oh dear you are dead!");
		} else if (player.duelStatus != 6) {
			player.sendMessage("You have lost the duel!");
		}
		resetDamageDone();
		player.specAmount = 10;
		player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
		player.lastVeng = 0;
		player.vengOn = false;
		resetFollowers();
		player.attackTimer = 10;
		removeAllWindows();
		player.tradeResetNeeded = true;
	}

	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[player.playerId] = 0;
			}
		}
	}

	public void vengMe() {
		if (System.currentTimeMillis() - player.lastVeng > 30000) {
			if (player.getItems().playerHasItem(557, 10) && player.getItems().playerHasItem(9075, 4) && player.getItems().playerHasItem(560, 2)) {
				player.vengOn = true;
				player.lastVeng = System.currentTimeMillis();
				player.startAnimation(4410);
				player.gfx100(726);
				player.getItems().deleteItem(557, player.getItems().getItemSlot(557), 10);
				player.getItems().deleteItem(560, player.getItems().getItemSlot(560), 2);
				player.getItems().deleteItem(9075, player.getItems().getItemSlot(9075), 4);
			} else {
				player.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			player.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

	public void resetTb() {
		player.teleBlockLength = 0;
		player.teleBlockDelay = 0;
	}

	public void resetFollowers() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].followId == player.playerId) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPlayerAssistant().resetFollow();
				}
			}
		}
	}

	public void giveLife() {
		player.isDead = false;
		player.faceUpdate(-1);
		player.freezeTimer = 0;
		if (player.duelStatus <= 4 && !player.getPlayerAssistant().inPitsWait()) {
			if (!player.inPits && !player.inFightCaves()) {
				player.getItems().resetKeepItems();
				if ((player.playerRights == 2 && Configuration.ADMINISTRATOR_DROPPING) || player.playerRights != 2) {
					if (!player.isSkulled) { // what items to keep
						player.getItems().keepItem(0, true);
						player.getItems().keepItem(1, true);
						player.getItems().keepItem(2, true);
					}
					if (player.prayerActive[10] && System.currentTimeMillis() - player.lastProtItem > 700) {
						player.getItems().keepItem(3, true);
					}
					player.getItems().dropAllItems(); // drop all items
					player.getItems().deleteAllItems(); // delete all items

					if (!player.isSkulled) { // add the kept items once we finish
					                             // deleting and dropping them
						for (int i1 = 0; i1 < 3; i1++) {
							if (player.itemKeptId[i1] > 0) {
								player.getItems().addItem(player.itemKeptId[i1], 1);
							}
						}
					}
					if (player.prayerActive[10]) { // if we have protect items
						if (player.itemKeptId[3] > 0) {
							player.getItems().addItem(player.itemKeptId[3], 1);
						}
					}
				}
				player.getItems().resetKeepItems();
			} else if (player.inPits) {
				Server.fightPits.removePlayerFromPits(player.playerId);
				player.pitsStatus = 1;
			}
		}
		player.getCombat().resetPrayers();
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = getLevelForXP(player.playerXP[i]);
			player.getPlayerAssistant().refreshSkill(i);
		}
		if (player.pitsStatus == 1) {
			movePlayer(2399, 5173, 0);
		} else if (player.duelStatus <= 4) { // if we are not in a duel repawn to
		                                         // wildy
			movePlayer(Location.RESPAWN[0], Location.RESPAWN[1], Location.RESPAWN[2]);
			player.isSkulled = false;
			player.skullTimer = 0;
			player.attackedPlayers.clear();
		} else if (player.inFightCaves()) {
			player.getFightCavesMisc().resetTzhaar();
		} else { // we are in a duel, respawn outside of arena
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o != null) {
				o.getPlayerAssistant().sendMobHintIcon(10, -1);
				if (o.duelStatus == 6) {
					o.getDuelArena().duelVictory();
				}
			}
			movePlayer(Location.DUELING_RESPAWN[0] + (Misc.random(Location.RANDOM_DUELING_RESPAWN)), Location.DUELING_RESPAWN[1] + (Misc.random(Location.RANDOM_DUELING_RESPAWN)), 0);
			if (player.duelStatus != 6) { // if we have won but have died, don't
			                                  // reset the duel status.
				player.getDuelArena().resetDuel();
			}
		}
		// PlayerSaving.getSingleton().requestSave(c.playerId);
		PlayerSave.saveGame(player);
		player.getCombat().resetPlayerAttack();
		resetAnimation();
		player.startAnimation(65535);
		frame1();
		resetTb();
		player.isSkulled = false;
		player.attackedPlayers.clear();
		player.headIconPk = -1;
		player.skullTimer = -1;
		player.damageTaken = new int[Configuration.MAX_PLAYERS];
		player.getPlayerAssistant().requestUpdates();
		removeAllWindows();
		player.tradeResetNeeded = true;
	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (player.newLocation) {
		case 1:
			sendMinimapState(2);
			movePlayer(3578, 9706, -1);
			break;
		case 2:
			sendMinimapState(2);
			movePlayer(3568, 9683, -1);
			break;
		case 3:
			sendMinimapState(2);
			movePlayer(3557, 9703, -1);
			break;
		case 4:
			sendMinimapState(2);
			movePlayer(3556, 9718, -1);
			break;
		case 5:
			sendMinimapState(2);
			movePlayer(3534, 9704, -1);
			break;
		case 6:
			sendMinimapState(2);
			movePlayer(3546, 9684, -1);
			break;
		}
		player.newLocation = 0;
	}

	public void movePlayer(int x, int y, int h) {
		player.resetWalkingQueue();
		player.teleportToX = x;
		player.teleportToY = y;
		player.heightLevel = h;
		requestUpdates();
	}

	/**
	 * Following
	 **/
	public void followPlayer() {
		if (PlayerHandler.players[player.followId] == null || PlayerHandler.players[player.followId].isDead) {
			resetFollow();
			return;
		}
		if (player.freezeTimer > 0) {
			return;
		}
		if (player.isDead || player.playerLevel[3] <= 0)
			return;

		int otherX = PlayerHandler.players[player.followId].getX();
		int otherY = PlayerHandler.players[player.followId].getY();

		boolean sameSpot = (player.absX == otherX && player.absY == otherY);

		boolean hallyDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 2);

		boolean rangeWeaponDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 4);
		boolean bowDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 6);
		boolean mageDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 7);

		boolean castingMagic = (player.usingMagic || player.mageFollow || player.autocasting || player.spellId > 0) && mageDistance;
		boolean playerRanging = (player.usingRangeWeapon) && rangeWeaponDistance;
		boolean playerBowOrCross = (player.usingBow) && bowDistance;

		if (!player.goodDistance(otherX, otherY, player.getX(), player.getY(), 25)) {
			player.followId = 0;
			resetFollow();
			return;
		}
		player.faceUpdate(player.followId + 32768);
		if (!sameSpot) {
			if (player.playerIndex > 0 && !player.usingSpecial && player.inWild()) {
				if (player.usingSpecial && (playerRanging || playerBowOrCross)) {
					player.stopMovement();
					return;
				}
				if (castingMagic || playerRanging || playerBowOrCross) {
					player.stopMovement();
					return;
				}
				if (player.getCombat().usingHally() && hallyDistance) {
					player.stopMovement();
					return;
				}
			}
		}
		if (otherX == player.absX && otherY == player.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (player.isRunning2) {
			if (otherY > player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > player.getX() && otherY == player.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > player.getX() && otherY > player.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < player.getX() && otherY > player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if (otherY > player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > player.getX() && otherY == player.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > player.getX() && otherY > player.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < player.getX() && otherY > player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > player.getX() && otherY < player.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		player.faceUpdate(player.followId + 32768);
	}

	public void followNpc() {
		if (NPCHandler.npcs[player.followId2] == null || NPCHandler.npcs[player.followId2].isDead) {
			player.followId2 = 0;
			return;
		}
		if (player.freezeTimer > 0) {
			return;
		}
		if (player.isDead || player.playerLevel[3] <= 0)
			return;

		int otherX = NPCHandler.npcs[player.followId2].getX();
		int otherY = NPCHandler.npcs[player.followId2].getY();
		boolean withinDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 2);
		player.goodDistance(otherX, otherY, player.getX(), player.getY(), 1);
		boolean hallyDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 2);
		boolean bowDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 8);
		boolean rangeWeaponDistance = player.goodDistance(otherX, otherY, player.getX(), player.getY(), 4);
		boolean sameSpot = player.absX == otherX && player.absY == otherY;
		if (!player.goodDistance(otherX, otherY, player.getX(), player.getY(), 25)) {
			player.followId2 = 0;
			return;
		}
		if (player.goodDistance(otherX, otherY, player.getX(), player.getY(), 1)) {
			if (otherX != player.getX() && otherY != player.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((player.usingBow || player.mageFollow || (player.npcIndex > 0 && player.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if (player.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (player.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		player.faceUpdate(player.followId2);
		if (otherX == player.absX && otherY == player.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (player.isRunning2 && !withinDistance) {
			if (otherY > player.getY() && otherX == player.getX()) {
				walkTo(0, getMove(player.getY(), otherY - 1) + getMove(player.getY(), otherY - 1));
			} else if (otherY < player.getY() && otherX == player.getX()) {
				walkTo(0, getMove(player.getY(), otherY + 1) + getMove(player.getY(), otherY + 1));
			} else if (otherX > player.getX() && otherY == player.getY()) {
				walkTo(getMove(player.getX(), otherX - 1) + getMove(player.getX(), otherX - 1), 0);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1) + getMove(player.getX(), otherX + 1), 0);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1) + getMove(player.getX(), otherX + 1), getMove(player.getY(), otherY + 1) + getMove(player.getY(), otherY + 1));
			} else if (otherX > player.getX() && otherY > player.getY()) {
				walkTo(getMove(player.getX(), otherX - 1) + getMove(player.getX(), otherX - 1), getMove(player.getY(), otherY - 1) + getMove(player.getY(), otherY - 1));
			} else if (otherX < player.getX() && otherY > player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1) + getMove(player.getX(), otherX + 1), getMove(player.getY(), otherY - 1) + getMove(player.getY(), otherY - 1));
			} else if (otherX > player.getX() && otherY < player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1) + getMove(player.getX(), otherX + 1), getMove(player.getY(), otherY - 1) + getMove(player.getY(), otherY - 1));
			}
		} else {
			if (otherY > player.getY() && otherX == player.getX()) {
				walkTo(0, getMove(player.getY(), otherY - 1));
			} else if (otherY < player.getY() && otherX == player.getX()) {
				walkTo(0, getMove(player.getY(), otherY + 1));
			} else if (otherX > player.getX() && otherY == player.getY()) {
				walkTo(getMove(player.getX(), otherX - 1), 0);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1), 0);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1), getMove(player.getY(), otherY + 1));
			} else if (otherX > player.getX() && otherY > player.getY()) {
				walkTo(getMove(player.getX(), otherX - 1), getMove(player.getY(), otherY - 1));
			} else if (otherX < player.getX() && otherY > player.getY()) {
				walkTo(getMove(player.getX(), otherX + 1), getMove(player.getY(), otherY - 1));
			} else if (otherX > player.getX() && otherY < player.getY()) {
				walkTo(getMove(player.getX(), otherX - 1), getMove(player.getY(), otherY + 1));
			}
		}
		player.faceUpdate(player.followId2);
	}

	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else
			return j - i;
	}

	public void resetFollow() {
		player.followId = 0;
		player.followId2 = 0;
		player.mageFollow = false;
		player.outStream.createFrame(174);
		player.outStream.writeWord(0);
		player.outStream.writeByte(0);
		player.outStream.writeWord(1);
	}

	public void walkTo(int i, int j) {
		player.newWalkCmdSteps = 0;
		if (++player.newWalkCmdSteps > 50)
			player.newWalkCmdSteps = 0;
		int k = player.getX() + i;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + j;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		if (player.freezeDelay > 0)
			return;
		player.newWalkCmdSteps = 0;
		if (++player.newWalkCmdSteps > 50)
			player.newWalkCmdSteps = 0;
		int k = player.getX() + i;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + j;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (player.freezeDelay > 0)
			return;
		player.newWalkCmdSteps = 1;
		int xMove = otherX - player.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - player.getY();
		/*
		 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

		int k = player.getX() + xMove;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + yMove;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}

	}

	public void walkToCheck(int i, int j) {
		if (player.freezeDelay > 0)
			return;
		player.newWalkCmdSteps = 0;
		if (++player.newWalkCmdSteps > 50)
			player.newWalkCmdSteps = 0;
		int k = player.getX() + i;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + j;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		if (System.currentTimeMillis() - player.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
			return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean fullVeracs() {
		return player.playerEquipment[player.playerHat] == 4753 && player.playerEquipment[player.playerChest] == 4757 && player.playerEquipment[player.playerLegs] == 4759 && player.playerEquipment[player.playerWeapon] == 4755;
	}

	public boolean fullGuthans() {
		return player.playerEquipment[player.playerHat] == 4724 && player.playerEquipment[player.playerChest] == 4728 && player.playerEquipment[player.playerLegs] == 4730 && player.playerEquipment[player.playerWeapon] == 4726;
	}

	/**
	 * reseting animation
	 **/
	public void resetAnimation() {
		player.getCombat().getPlayerAnimIndex(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
		player.startAnimation(player.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	public void handleAlt(int id) {
		if (!player.getItems().playerHasItem(id)) {
			player.getItems().addItem(id, 1);
		}
	}

	public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(player.playerXP[0]) + getLevelForXP(player.playerXP[1]) + getLevelForXP(player.playerXP[2]) + getLevelForXP(player.playerXP[3]) + getLevelForXP(player.playerXP[4]) + getLevelForXP(player.playerXP[5]) + getLevelForXP(player.playerXP[6]) + getLevelForXP(player.playerXP[7]) + getLevelForXP(player.playerXP[8]) + getLevelForXP(player.playerXP[9]) + getLevelForXP(player.playerXP[10]) + getLevelForXP(player.playerXP[11]) + getLevelForXP(player.playerXP[12]) + getLevelForXP(player.playerXP[13]) + getLevelForXP(player.playerXP[14]) + getLevelForXP(player.playerXP[15]) + getLevelForXP(player.playerXP[16]) + getLevelForXP(player.playerXP[17]) + getLevelForXP(player.playerXP[18]) + getLevelForXP(player.playerXP[19]) + getLevelForXP(player.playerXP[20]) + getLevelForXP(player.playerXP[21]) + getLevelForXP(player.playerXP[22]) + getLevelForXP(player.playerXP[23]) + getLevelForXP(player.playerXP[24]));
		sendString("Total Lvl: " + totalLevel, 3984);
		switch (skill) {
		case 0:
			sendString("Congratulations, you just advanced an attack level!", 6248);
			sendString("Your attack level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6249);
			player.sendMessage("Congratulations, you just advanced an attack level.");
			sendChatInterface(6247);
			break;

		case 1:
			sendString("Congratulations, you just advanced a defence level!", 6254);
			sendString("Your defence level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6255);
			player.sendMessage("Congratulations, you just advanced a defence level.");
			sendChatInterface(6253);
			break;

		case 2:
			sendString("Congratulations, you just advanced a strength level!", 6207);
			sendString("Your strength level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6208);
			player.sendMessage("Congratulations, you just advanced a strength level.");
			sendChatInterface(6206);
			break;

		case 3:
			sendString("Congratulations, you just advanced a hitpoints level!", 6217);
			sendString("Your hitpoints level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6218);
			player.sendMessage("Congratulations, you just advanced a hitpoints level.");
			sendChatInterface(6216);
			break;

		case 4:
			sendString("Congratulations, you just advanced a ranged level!", 5453);
			sendString("Your ranged level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6114);
			player.sendMessage("Congratulations, you just advanced a ranging level.");
			sendChatInterface(4443);
			break;

		case 5:
			sendString("Congratulations, you just advanced a prayer level!", 6243);
			sendString("Your prayer level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6244);
			player.sendMessage("Congratulations, you just advanced a prayer level.");
			sendChatInterface(6242);
			break;

		case 6:
			sendString("Congratulations, you just advanced a magic level!", 6212);
			sendString("Your magic level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6213);
			player.sendMessage("Congratulations, you just advanced a magic level.");
			sendChatInterface(6211);
			break;

		case 7:
			sendString("Congratulations, you just advanced a cooking level!", 6227);
			sendString("Your cooking level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6228);
			player.sendMessage("Congratulations, you just advanced a cooking level.");
			sendChatInterface(6226);
			break;

		case 8:
			sendString("Congratulations, you just advanced a woodcutting level!", 4273);
			sendString("Your woodcutting level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4274);
			player.sendMessage("Congratulations, you just advanced a woodcutting level.");
			sendChatInterface(4272);
			break;

		case 9:
			sendString("Congratulations, you just advanced a fletching level!", 6232);
			sendString("Your fletching level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6233);
			player.sendMessage("Congratulations, you just advanced a fletching level.");
			sendChatInterface(6231);
			break;

		case 10:
			sendString("Congratulations, you just advanced a fishing level!", 6259);
			sendString("Your fishing level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6260);
			player.sendMessage("Congratulations, you just advanced a fishing level.");
			sendChatInterface(6258);
			break;

		case 11:
			sendString("Congratulations, you just advanced a fire making level!", 4283);
			sendString("Your firemaking level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4284);
			player.sendMessage("Congratulations, you just advanced a fire making level.");
			sendChatInterface(4282);
			break;

		case 12:
			sendString("Congratulations, you just advanced a crafting level!", 6264);
			sendString("Your crafting level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6265);
			player.sendMessage("Congratulations, you just advanced a crafting level.");
			sendChatInterface(6263);
			break;

		case 13:
			sendString("Congratulations, you just advanced a smithing level!", 6222);
			sendString("Your smithing level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6223);
			player.sendMessage("Congratulations, you just advanced a smithing level.");
			sendChatInterface(6221);
			break;

		case 14:
			sendString("Congratulations, you just advanced a mining level!", 4417);
			sendString("Your mining level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4438);
			player.sendMessage("Congratulations, you just advanced a mining level.");
			sendChatInterface(4416);
			break;

		case 15:
			sendString("Congratulations, you just advanced a herblore level!", 6238);
			sendString("Your herblore level is now " + getLevelForXP(player.playerXP[skill]) + ".", 6239);
			player.sendMessage("Congratulations, you just advanced a herblore level.");
			sendChatInterface(6237);
			break;

		case 16:
			sendString("Congratulations, you just advanced a agility level!", 4278);
			sendString("Your agility level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4279);
			player.sendMessage("Congratulations, you just advanced an agility level.");
			sendChatInterface(4277);
			break;

		case 17:
			sendString("Congratulations, you just advanced a thieving level!", 4263);
			sendString("Your theiving level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4264);
			player.sendMessage("Congratulations, you just advanced a thieving level.");
			sendChatInterface(4261);
			break;

		case 18:
			sendString("Congratulations, you just advanced a slayer level!", 12123);
			sendString("Your slayer level is now " + getLevelForXP(player.playerXP[skill]) + ".", 12124);
			player.sendMessage("Congratulations, you just advanced a slayer level.");
			sendChatInterface(12122);
			break;

		case 20:
			sendString("Congratulations, you just advanced a runecrafting level!", 4268);
			sendString("Your runecrafting level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4269);
			player.sendMessage("Congratulations, you just advanced a runecrafting level.");
			sendChatInterface(4267);
			break;
		case 21:
			sendString("Congratulations, you just advanced a construction level!", 4268);
			sendString("Your construction level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4269);
			player.sendMessage("Congratulations, you just advanced a construction level.");
			sendChatInterface(4267);
			break;
		case 22:
			sendString("Congratulations, you just advanced a hunter level!", 4268);
			sendString("Your hunter level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4269);
			player.sendMessage("Congratulations, you just advanced a hunter level.");
			sendChatInterface(4267);
			break;
		case 23:
			sendString("Congratulations, you just advanced a summoning level!", 4268);
			sendString("Your summoning level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4269);
			player.sendMessage("Congratulations, you just advanced a summoning level.");
			sendChatInterface(4267);
			break;
		case 24:
			sendString("Congratulations, you just advanced a dungeoneering level!", 4268);
			sendString("Your dungeoneering level is now " + getLevelForXP(player.playerXP[skill]) + ".", 4269);
			player.sendMessage("Congratulations, you just advanced a dungeoneering level.");
			sendChatInterface(4267);
			break;
		}
		player.dialogueAction = 0;
		player.nextChat = 0;
	}

	public void refreshSkill(int i) {
		switch (i) {
		case 0:
			sendString("" + player.playerLevel[0] + "", 4004);
			sendString("" + getLevelForXP(player.playerXP[0]) + "", 4005);
			sendString("" + player.playerXP[0] + "", 4044);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[0]) + 1) + "", 4045);
			break;

		case 1:
			sendString("" + player.playerLevel[1] + "", 4008);
			sendString("" + getLevelForXP(player.playerXP[1]) + "", 4009);
			sendString("" + player.playerXP[1] + "", 4056);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[1]) + 1) + "", 4057);
			break;

		case 2:
			sendString("" + player.playerLevel[2] + "", 4006);
			sendString("" + getLevelForXP(player.playerXP[2]) + "", 4007);
			sendString("" + player.playerXP[2] + "", 4050);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[2]) + 1) + "", 4051);
			break;

		case 3:
			sendString("" + player.playerLevel[3] + "", 4016);
			sendString("" + getLevelForXP(player.playerXP[3]) + "", 4017);
			sendString("" + player.playerXP[3] + "", 4080);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[3]) + 1) + "", 4081);
			break;

		case 4:
			sendString("" + player.playerLevel[4] + "", 4010);
			sendString("" + getLevelForXP(player.playerXP[4]) + "", 4011);
			sendString("" + player.playerXP[4] + "", 4062);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[4]) + 1) + "", 4063);
			break;

		case 5:
			sendString("" + player.playerLevel[5] + "", 4012);
			sendString("" + getLevelForXP(player.playerXP[5]) + "", 4013);
			sendString("" + player.playerXP[5] + "", 4068);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[5]) + 1) + "", 4069);
			sendString("" + player.playerLevel[5] + "/" + getLevelForXP(player.playerXP[5]) + "", 687);// Prayer frame
			break;

		case 6:
			sendString("" + player.playerLevel[6] + "", 4014);
			sendString("" + getLevelForXP(player.playerXP[6]) + "", 4015);
			sendString("" + player.playerXP[6] + "", 4074);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[6]) + 1) + "", 4075);
			break;

		case 7:
			sendString("" + player.playerLevel[7] + "", 4034);
			sendString("" + getLevelForXP(player.playerXP[7]) + "", 4035);
			sendString("" + player.playerXP[7] + "", 4134);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[7]) + 1) + "", 4135);
			break;

		case 8:
			sendString("" + player.playerLevel[8] + "", 4038);
			sendString("" + getLevelForXP(player.playerXP[8]) + "", 4039);
			sendString("" + player.playerXP[8] + "", 4146);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[8]) + 1) + "", 4147);
			break;

		case 9:
			sendString("" + player.playerLevel[9] + "", 4026);
			sendString("" + getLevelForXP(player.playerXP[9]) + "", 4027);
			sendString("" + player.playerXP[9] + "", 4110);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[9]) + 1) + "", 4111);
			break;

		case 10:
			sendString("" + player.playerLevel[10] + "", 4032);
			sendString("" + getLevelForXP(player.playerXP[10]) + "", 4033);
			sendString("" + player.playerXP[10] + "", 4128);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[10]) + 1) + "", 4129);
			break;

		case 11:
			sendString("" + player.playerLevel[11] + "", 4036);
			sendString("" + getLevelForXP(player.playerXP[11]) + "", 4037);
			sendString("" + player.playerXP[11] + "", 4140);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[11]) + 1) + "", 4141);
			break;

		case 12:
			sendString("" + player.playerLevel[12] + "", 4024);
			sendString("" + getLevelForXP(player.playerXP[12]) + "", 4025);
			sendString("" + player.playerXP[12] + "", 4104);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[12]) + 1) + "", 4105);
			break;

		case 13:
			sendString("" + player.playerLevel[13] + "", 4030);
			sendString("" + getLevelForXP(player.playerXP[13]) + "", 4031);
			sendString("" + player.playerXP[13] + "", 4122);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[13]) + 1) + "", 4123);
			break;

		case 14:
			sendString("" + player.playerLevel[14] + "", 4028);
			sendString("" + getLevelForXP(player.playerXP[14]) + "", 4029);
			sendString("" + player.playerXP[14] + "", 4116);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[14]) + 1) + "", 4117);
			break;

		case 15:
			sendString("" + player.playerLevel[15] + "", 4020);
			sendString("" + getLevelForXP(player.playerXP[15]) + "", 4021);
			sendString("" + player.playerXP[15] + "", 4092);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[15]) + 1) + "", 4093);
			break;

		case 16:
			sendString("" + player.playerLevel[16] + "", 4018);
			sendString("" + getLevelForXP(player.playerXP[16]) + "", 4019);
			sendString("" + player.playerXP[16] + "", 4086);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[16]) + 1) + "", 4087);
			break;

		case 17:
			sendString("" + player.playerLevel[17] + "", 4022);
			sendString("" + getLevelForXP(player.playerXP[17]) + "", 4023);
			sendString("" + player.playerXP[17] + "", 4098);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[17]) + 1) + "", 4099);
			break;

		case 18:
			sendString("" + player.playerLevel[18] + "", 12166);
			sendString("" + getLevelForXP(player.playerXP[18]) + "", 12167);
			sendString("" + player.playerXP[18] + "", 12171);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[18]) + 1) + "", 12172);
			break;

		case 19:
			sendString("" + player.playerLevel[19] + "", 13926);
			sendString("" + getLevelForXP(player.playerXP[19]) + "", 13927);
			sendString("" + player.playerXP[19] + "", 13921);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[19]) + 1) + "", 13922);
			break;

		case 20:
			sendString("" + player.playerLevel[20] + "", 4152);
			sendString("" + getLevelForXP(player.playerXP[20]) + "", 4153);
			sendString("" + player.playerXP[20] + "", 4157);
			sendString("" + getXPForLevel(getLevelForXP(player.playerXP[20]) + 1) + "", 4158);
			break;

		/*case 21:
			sendFrame126("" + c.playerLevel[21] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[21]) + "", 4153);
			sendFrame126("" + c.playerXP[21] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[21]) + 1) + "", 4158);
			break;
			
		case 22:
			sendFrame126("" + c.playerLevel[22] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[22]) + "", 4153);
			sendFrame126("" + c.playerXP[22] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[22]) + 1) + "", 4158);
			break;
			
		case 23:
			sendFrame126("" + c.playerLevel[23] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[23]) + "", 4153);
			sendFrame126("" + c.playerXP[23] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[23]) + 1) + "", 4158);
			break;
			
		case 24:
			sendFrame126("" + c.playerLevel[24] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[24]) + "", 4153);
			sendFrame126("" + c.playerXP[24] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[24]) + 1) + "", 4158);
			break;*/
		}
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public boolean addSkillXP(double amount, int skill) {
		if (amount + player.playerXP[skill] < 0 || player.playerXP[skill] > 200000000) {
			if (player.playerXP[skill] > 200000000) {
				player.playerXP[skill] = 200000000;
			}
			return false;
		}
		amount *= SkillHandler.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(player.playerXP[skill]);
		player.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(player.playerXP[skill])) {
			if (player.playerLevel[skill] < player.getLevelForXP(player.playerXP[skill]) && skill != 3 && skill != 5)
				player.playerLevel[skill] = player.getLevelForXP(player.playerXP[skill]);
			levelUp(skill);
			player.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, player.playerLevel[skill], player.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	/**
	 * Show an arrow icon on the selected player.
	 * 
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		// synchronized(c) {
		player.outStream.createFrame(254);
		player.outStream.writeByte(i);

		if (i == 1 || i == 10) {
			player.outStream.writeWord(j);
			player.outStream.writeWord(k);
			player.outStream.writeByte(l);
		} else {
			player.outStream.writeWord(k);
			player.outStream.writeWord(l);
			player.outStream.writeByte(j);
		}
	}

	public int getNpcId(int id) {
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}

	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
	}

	public void handleGlory(int gloryId) {
		player.getDialogueHandler().sendOption("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
		player.usingGlory = true;
	}

	public void resetVariables() {
		player.getFishing().resetFishing();
		player.getCrafting().resetCrafting();
		player.usingGlory = false;
		player.smeltInterface = false;
		player.smeltType = 0;
		player.smeltAmount = 0;
		player.woodcut[0] = player.woodcut[1] = player.woodcut[2] = 0;
		player.mining[0] = player.mining[1] = player.mining[2] = 0;
	}

	public boolean inPitsWait() {
		return player.getX() <= 2404 && player.getX() >= 2394 && player.getY() <= 5175 && player.getY() >= 5169;
	}

	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}

	public void removeFromCW() {
		if (player.castleWarsTeam == 1) {
			if (player.inCwWait) {
				Server.castleWars.saradominWait.remove(Server.castleWars.saradominWait.indexOf(player.playerId));
			} else {
				Server.castleWars.saradomin.remove(Server.castleWars.saradomin.indexOf(player.playerId));
			}
		} else if (player.castleWarsTeam == 2) {
			if (player.inCwWait) {
				Server.castleWars.zamorakWait.remove(Server.castleWars.zamorakWait.indexOf(player.playerId));
			} else {
				Server.castleWars.zamorak.remove(Server.castleWars.zamorak.indexOf(player.playerId));
			}
		}
	}

	public int antiFire() {
		int toReturn = 0;
		if (player.antiFirePot)
			toReturn++;
		if (player.playerEquipment[player.playerShield] == 1540 || player.prayerActive[12] || player.playerEquipment[player.playerShield] == 11284)
			toReturn++;
		return toReturn;
	}

	/**
	 * Checks if the account has the quantity of the specified items, and flags them if they do.
	 */

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100_000_000 }, { 35, 5 }, { 667, 5 }, { 2402, 5 }, { 746, 5 }, { 4151, 150 }, { 565, 100000 }, { 560, 100000 }, { 555, 300000 }, { 11235, 10 } };
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < player.getBank().getTotalCount(itemsToCheck[j][0]))
				return true;
		}
		return false;
	}

	/**
	 * Adds the starter items when creating a new account.
	 */

	public void addStarter() {
		player.getItems().addItem(995, 100000);
		player.getItems().addItem(1731, 1);
		player.getItems().addItem(554, 200);
		player.getItems().addItem(555, 200);
		player.getItems().addItem(556, 200);
		player.getItems().addItem(558, 600);
		player.getItems().addItem(1381, 1);
		player.getItems().addItem(1323, 1);
		player.getItems().addItem(841, 1);
		player.getItems().addItem(882, 500);
		player.getItems().addItem(380, 100);
	}

	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < player.playerEquipment.length; j++) {
			if (player.playerEquipment[j] > 0)
				count++;
		}
		return count;
	}

	public void getSpeared(int otherX, int otherY) {
		int x = player.absX - otherX;
		int y = player.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		moveCheck(x, y);
		player.lastSpear = System.currentTimeMillis();
	}

	public void moveCheck(int xMove, int yMove) {
		movePlayer(player.absX + xMove, player.absY + yMove, player.heightLevel);
	}

	public int findKiller() {
		int killer = player.playerId;
		int damage = 0;
		for (int j = 0; j < Configuration.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == player.playerId)
				continue;
			if (player.goodDistance(player.absX, player.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40) || player.goodDistance(player.absX, player.absY + 9400, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40) || player.goodDistance(player.absX, player.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY + 9400, 40))
				if (player.damageTaken[j] > damage) {
					damage = player.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}

	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - player.lastPoisonSip > player.poisonImmune) {
			player.sendMessage("You have been poisoned.");
			player.poisonDamage = damage;
		}
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}
		}
		return false;
	}

	public void handleWeaponStyle() {
		if (player.fightMode == 0) {
			player.getPlayerAssistant().sendConfig(43, player.fightMode);
		} else if (player.fightMode == 1) {
			player.getPlayerAssistant().sendConfig(43, 3);
		} else if (player.fightMode == 2) {
			player.getPlayerAssistant().sendConfig(43, 1);
		} else if (player.fightMode == 3) {
			player.getPlayerAssistant().sendConfig(43, 2);
		}
	}

}
