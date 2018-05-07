package org.invictus;

public final class RSInterface {

	public static final int QUEST_HEADLINE = 663;
	public static final int QUEST_1 = 7332;
	public static final int QUEST_2 = 7333;
	public static final int QUEST_3 = 7334;
	public static final int QUEST_4 = 7336;
	public static final int QUEST_5 = 7383;
	public static final int QUEST_6 = 7339;
	public static final int QUEST_7 = 7338;
	public static final int QUEST_8 = 7340;
	public static final int QUEST_9 = 7346;
	public static final int QUEST_10 = 7341;

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public static void unpack(StreamLoader streamLoader, TextDrawingArea textDrawingAreas[], StreamLoader streamLoader_1) {
		aMRUNodes_238 = new MRUNodes(50000);
		Stream stream = new Stream(streamLoader.getDataForName("data"));
		int i = -1;
		int j = stream.readUnsignedWord();
		interfaceCache = new RSInterface[j + 10000];
		while (stream.currentOffset < stream.buffer.length) {
			int k = stream.readUnsignedWord();
			if (k == 65535) {
				i = stream.readUnsignedWord();
				k = stream.readUnsignedWord();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.readUnsignedByte();
			rsInterface.atActionType = stream.readUnsignedByte();
			rsInterface.contentType = stream.readUnsignedWord();
			rsInterface.width = stream.readUnsignedWord();
			rsInterface.height = stream.readUnsignedWord();
			rsInterface.aByte254 = (byte) stream.readUnsignedByte();
			rsInterface.mOverInterToTrigger = stream.readUnsignedByte();
			if (rsInterface.mOverInterToTrigger != 0) {
				rsInterface.mOverInterToTrigger = (rsInterface.mOverInterToTrigger - 1 << 8) + stream.readUnsignedByte();
			} else {
				rsInterface.mOverInterToTrigger = -1;
			}
			int i1 = stream.readUnsignedByte();
			if (i1 > 0) {
				rsInterface.anIntArray245 = new int[i1];
				rsInterface.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.anIntArray245[j1] = stream.readUnsignedByte();
					rsInterface.anIntArray212[j1] = stream.readUnsignedWord();
				}

			}
			int k1 = stream.readUnsignedByte();
			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.readUnsignedWord();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++) {
						rsInterface.valueIndexArray[l1][l4] = stream.readUnsignedWord();
					}

				}

			}
			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = stream.readUnsignedWord();
				rsInterface.isMouseoverTriggered = stream.readUnsignedByte() == 1;
				int i2 = stream.readUnsignedWord();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = stream.readUnsignedWord();
					rsInterface.childX[j3] = stream.readSignedWord();
					rsInterface.childY[j3] = stream.readSignedWord();
				}
			}
			if (rsInterface.type == 1) {
				stream.readUnsignedWord();
				stream.readUnsignedByte();
			}
			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.aBoolean259 = stream.readUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
				rsInterface.aBoolean235 = stream.readUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.readUnsignedByte();
				rsInterface.invSpritePadY = stream.readUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.readUnsignedByte();
					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.readSignedWord();
						rsInterface.spritesY[j2] = stream.readSignedWord();
						String s1 = stream.readString();
						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
						}
					}
				}
				rsInterface.actions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.readString();
					if (rsInterface.actions[l3].length() == 0) {
						rsInterface.actions[l3] = null;
					}
				}
			}
			if (rsInterface.type == 3) {
				rsInterface.aBoolean227 = stream.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int k2 = stream.readUnsignedByte();
				if (textDrawingAreas != null) {
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				}
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4) {
				// rsInterface.message =
				// stream.readString().replaceAll("RuneScape", "Reconcile");
				rsInterface.message = stream.readString().replaceAll("RuneScape", Configuration.SERVER_NAME);
				rsInterface.aString228 = stream.readString();
			}
			if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.textColor = stream.readDWord();
			}
			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = stream.readDWord();
				rsInterface.anInt216 = stream.readDWord();
				rsInterface.anInt239 = stream.readDWord();
			}
			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
				}
				s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
				}
			}
			if (rsInterface.type == 6) {
				int l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt233 = 1;
					rsInterface.mediaID = (l - 1 << 8) + stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8) + stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt257 = (l - 1 << 8) + stream.readUnsignedByte();
				} else {
					rsInterface.anInt257 = -1;
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt258 = (l - 1 << 8) + stream.readUnsignedByte();
				} else {
					rsInterface.anInt258 = -1;
				}
				rsInterface.modelZoom = stream.readUnsignedWord();
				rsInterface.modelRotation1 = stream.readUnsignedWord();
				rsInterface.modelRotation2 = stream.readUnsignedWord();
			}
			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int l2 = stream.readUnsignedByte();
				if (textDrawingAreas != null) {
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				}
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
				rsInterface.textColor = stream.readDWord();
				rsInterface.invSpritePadX = stream.readSignedWord();
				rsInterface.invSpritePadY = stream.readSignedWord();
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.actions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.readString();
					if (rsInterface.actions[k4].length() == 0) {
						rsInterface.actions[k4] = null;
					}
				}

			}
			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.readString();
				rsInterface.spellName = stream.readString();
				rsInterface.spellUsableOn = stream.readUnsignedWord();
			}

			if (rsInterface.type == 8) {
				rsInterface.message = stream.readString();
			}

			//Clearing the quest journal
			if (rsInterface.id >= 8144 && rsInterface.id <= 8195) {
				rsInterface.message = "";
			}
			if (rsInterface.id >= 12174 && rsInterface.id <= 12224) {
				rsInterface.message = "";
			}
			//Clearing the quest tab
			if (rsInterface.id >= 7332 && rsInterface.id <= 7383) {
				rsInterface.message = "";
			}
			if (rsInterface.id >= 7332 && rsInterface.id <= 7383) {
				rsInterface.message = "";
			}

			switch (rsInterface.id) {
			// Clearing old Quest Tab text
			case 13136:
			case 682:
			case 673:
			case 12772:
			case 12129:
			case 8438:
			case 18517:
			case 15847:
			case 15487:
			case 12852:
			case 8679:
			case 7459:
			case 14912:
			case 249:
			case 6024:
			case 191:
			case 15235:
			case 15592:
			case 6987:
			case 15098:
			case 15352:
			case 18306:
			case 15499:
			case 668:
			case 18684:
			case 6027:
			case 18157:
			case 16128:
			case 12836:
			case 16149:
			case 15841:
			case 17510:
			case 14169:
			case 10115:
			case 14604:
			case 12282:
			case 13577:
			case 12839:
			case 11857:
			case 10135:
			case 4508:
			case 11907:
			case 13389:
			case 11132:
			case 12389:
			case 13974:
			case 8137:
			case 12345:
			case 8115:
			case 8576:
			case 12139:
			case 8969:
			case 1740:
			case 3278:
			case 6518:
			case 11858:
			case 9927:
			case 13356:
				rsInterface.message = "";
				break;
			//Custom Quest Tab Text
			case QUEST_HEADLINE:
				rsInterface.message = "Quests";
				break;
			case QUEST_1:
				rsInterface.message = "Test Quest";
				break;
			case QUEST_2:
				rsInterface.message = "";
				break;
			case QUEST_3:
				rsInterface.message = "";
				break;
			case QUEST_4:
				rsInterface.message = "";
				break;
			case QUEST_5:
				rsInterface.message = "";
				break;
			case QUEST_6:
				rsInterface.message = "";
				break;
			case QUEST_7:
				rsInterface.message = "";
				break;
			case QUEST_8:
				rsInterface.message = "";
				break;
			case QUEST_9:
				rsInterface.message = "";
				break;
			case QUEST_10:
				rsInterface.message = "";
				break;
			}

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.readString();
				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1) {
						rsInterface.tooltip = "Ok";
					}
					if (rsInterface.atActionType == 4) {
						rsInterface.tooltip = "Select";
					}
					if (rsInterface.atActionType == 5) {
						rsInterface.tooltip = "Select";
					}
					if (rsInterface.atActionType == 6) {
						rsInterface.tooltip = "Continue";
					}
				}
			}
		}
		aClass44 = streamLoader;
		EquipmentScreen(textDrawingAreas);
		BankInterface(textDrawingAreas);
		summoningTab(textDrawingAreas);
		petSummoningTab(textDrawingAreas);
		aMRUNodes_238 = null;
	}

	private static final int WHITE_TEXT = 0xFFFFFF;
	private static final int GREY_TEXT = 0xB9B855;
	private static final int ORANGE_TEXT = 0xFF981F;

	private static void summoningTab(TextDrawingArea[] tda) {
		final String dir = "Interfaces/Summoning/Tab/SPRITE";
		RSInterface rsi = addTabInterface(18017);
		//addButton(18018, 0, dir, 143, 13, "Cast special", 1);
		addText(18019, "S P E C I A L  M O V E", tda, 0, WHITE_TEXT, false, false);
		addSprite(18020, 1, dir);
		addFamiliarHead(18021, 75, 50, 875);
		addSprite(18022, 2, dir);
		//addConfigButton(18023, 18017, 4, 3, dir, 30, 31, "Cast special", 0, 1, 330);
		addText(18024, "0", tda, 0, ORANGE_TEXT, false, true);
		addSprite(18025, 5, dir);
		//addConfigButton(18026, 18017, 7, 6, dir, 29, 39, "Attack", 0, 1, 333);
		addSprite(18027, 8, dir);
		addText(18028, "None", tda, 2, ORANGE_TEXT, true, false);
		addHoverButton(18029, dir, 9, 38, 38, "Withdraw BoB", -1, 18030, 1);
		addHoveredButton(18030, dir, 10, 38, 38, 18031);
		addHoverButton(18032, dir, 11, 38, 38, "Renew familiar", -1, 18033, 1);
		addHoveredButton(18033, dir, 12, 38, 38, 18034);
		addHoverButton(18035, dir, 13, 38, 38, "Call follower", -1, 18036, 1);
		addHoveredButton(18036, dir, 14, 38, 38, 18037);
		addHoverButton(18038, dir, 15, 38, 38, "Dismiss familiar", -1, 18039, 1);
		addHoveredButton(18039, dir, 16, 38, 38, 18040);
		addSprite(18041, 17, dir);
		addSprite(18042, 18, dir);
		addText(18043, "35.30", tda, 0, GREY_TEXT, false, true);
		addSprite(18044, 19, dir);
		addText(18045, "63/69", tda, 0, GREY_TEXT, false, true);
		setChildren(24, rsi);
		setBounds(18018, 23, 10, 0, rsi);
		setBounds(18019, 43, 12, 1, rsi);
		setBounds(18020, 10, 32, 2, rsi);
		setBounds(18021, 63, 60, 3, rsi);
		setBounds(18022, 11, 32, 4, rsi);
		setBounds(18023, 14, 35, 5, rsi);
		setBounds(18024, 25, 69, 6, rsi);
		setBounds(18025, 138, 32, 7, rsi);
		setBounds(18026, 143, 36, 8, rsi);
		setBounds(18027, 12, 144, 9, rsi);
		setBounds(18028, 93, 146, 10, rsi);
		setBounds(18029, 23, 168, 11, rsi);
		setBounds(18030, 23, 168, 12, rsi);
		setBounds(18032, 75, 168, 13, rsi);
		setBounds(18033, 75, 168, 14, rsi);
		setBounds(18035, 23, 213, 15, rsi);
		setBounds(18036, 23, 213, 16, rsi);
		setBounds(18038, 75, 213, 17, rsi);
		setBounds(18039, 75, 213, 18, rsi);
		setBounds(18041, 130, 168, 19, rsi);
		setBounds(18042, 153, 170, 20, rsi);
		setBounds(18043, 148, 198, 21, rsi);
		setBounds(18044, 149, 213, 22, rsi);
		setBounds(18045, 145, 241, 23, rsi);
	}

	private static void petSummoningTab(TextDrawingArea[] tda) {
		final String dir = "Interfaces/Summoning/Tab/SPRITE";
		RSInterface rsi = addTabInterface(19017);
		addSprite(19018, 1, dir);
		addFamiliarHead(19019, 75, 50, 900);
		addSprite(19020, 8, dir);
		addText(19021, "None", tda, 2, ORANGE_TEXT, true, false);
		addHoverButton(19022, dir, 13, 38, 38, "Call follower", -1, 19023, 1);
		addHoveredButton(19023, dir, 14, 38, 38, 19024);
		addHoverButton(19025, dir, 15, 38, 38, "Dismiss familiar", -1, 19026, 1);
		addHoveredButton(19026, dir, 16, 38, 38, 19027);
		addSprite(19028, 17, dir);
		addSprite(19029, 20, dir);
		addText(19030, "0%", tda, 0, GREY_TEXT, false, true);
		addSprite(19031, 21, dir);
		addText(19032, "0%", tda, 0, WHITE_TEXT, false, true);
		setChildren(13, rsi);
		setBounds(19018, 10, 32, 0, rsi);
		setBounds(19019, 65, 65, 1, rsi);
		setBounds(19020, 12, 145, 2, rsi);
		setBounds(19021, 93, 147, 3, rsi);
		setBounds(19022, 23, 213, 4, rsi);
		setBounds(19023, 23, 213, 5, rsi);
		setBounds(19025, 75, 213, 6, rsi);
		setBounds(19026, 75, 213, 7, rsi);
		setBounds(19028, 130, 168, 8, rsi);
		setBounds(19029, 148, 170, 9, rsi);
		setBounds(19030, 152, 198, 10, rsi);
		setBounds(19031, 149, 220, 11, rsi);
		setBounds(19032, 155, 241, 12, rsi);
	}

	private static void addFamiliarHead(int interfaceID, int width, int height, int zoom) {
		RSInterface rsi = addTabInterface(interfaceID);
		rsi.type = 6;
		rsi.anInt233 = 2;
		rsi.mediaID = 4000;
		rsi.modelZoom = zoom;
		rsi.modelRotation1 = 40;
		rsi.modelRotation2 = 1800;
		rsi.height = height;
		rsi.width = width;
	}

	public static void addCharacter(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.aByte254 = 0;
		t.mOverInterToTrigger = 0;
		t.modelZoom = 560;
		t.modelRotation1 = 30;
		t.modelRotation2 = 0;
		t.width = 180;
		t.height = 190;
		t.anInt257 = -1;
		t.anInt258 = -1;
	}

	public static void EquipmentScreen(TextDrawingArea[] wid) {
		RSInterface tab = addTabInterface(15106);
		addSprite(15107, 1, "Interfaces/EquipmentScreen/background");
		addHoverButton(15210, "Misc/CLOSE", 1, 16, 16, "Close", 250, 15211, 3);
		addHoveredButton(15211, "Misc/CLOSE", 2, 16, 16, 15212);
		addButton(15215, 1, "", "Turn Left", 80, 292);
		addButton(15216, 1, "", "Turn Right", 80, 292);
		addText(15111, "", wid, 2, 0xe4a146, false, true);
		addText(15112, "Attack bonuses", wid, 2, 0xFF8900, false, true);
		addText(15113, "Defence bonuses", wid, 2, 0xFF8900, false, true);
		addText(15114, "Other bonuses", wid, 2, 0xFF8900, false, true);
		addCharacter(15125);
		tab.totalChildren(46);
		tab.child(0, 15107, 15, 5);
		tab.child(1, 15210, 477, 8);
		tab.child(2, 15211, 477, 8);
		tab.child(3, 15111, 14, 30);

		int childID = 4;
		int Y = 45;
		for (int i = 1675; i <= 1679; i++) {
			tab.child(childID, i, 29, Y);
			childID++;
			Y += 14;
		}

		tab.child(9, 1680, 29, 137);
		tab.child(10, 1681, 29, 153);
		tab.child(11, 1682, 29, 168);
		tab.child(12, 1683, 29, 183);
		tab.child(13, 1684, 29, 197);
		tab.child(14, 1686, 29, 238);
		tab.child(17, 1687, 29, 252);
		tab.child(15, 15125, 170, 200);
		tab.child(16, 15112, 24, 30);
		tab.child(18, 15113, 24, 122);
		tab.child(19, 15114, 24, 223);
		tab.child(20, 1645, 399, 97);
		tab.child(21, 1646, 399, 163);
		tab.child(22, 1647, 399, 163);
		tab.child(23, 1648, 399, 204);
		tab.child(24, 1649, 343, 176);
		tab.child(25, 1650, 343, 58 + 154);
		tab.child(26, 1651, 455, 58 + 118);
		tab.child(27, 1652, 455, 58 + 154);
		tab.child(28, 1653, 321 + 48, 58 + 81);
		tab.child(29, 1654, 321 + 107, 58 + 81);
		tab.child(30, 1655, 321 + 58, 58 + 42);
		tab.child(31, 1656, 321 + 112, 58 + 41);
		tab.child(32, 1657, 399, 58 + 4);
		tab.child(33, 1658, 358, 58 + 43);
		tab.child(34, 1659, 399, 58 + 43);
		tab.child(35, 1660, 321 + 119, 58 + 43);
		tab.child(36, 1661, 343, 58 + 82);
		tab.child(37, 1662, 399, 58 + 82);
		tab.child(38, 1663, 455, 58 + 82);
		tab.child(39, 1664, 399, 58 + 122);
		tab.child(40, 1665, 399, 58 + 162);
		tab.child(41, 1666, 343, 58 + 162);
		tab.child(42, 1667, 455, 58 + 162);
		tab.child(43, 1688, 50 + 297 - 2, 102);
		tab.child(44, 15215, 179, 28); // Turn Left
		tab.child(45, 15216, 259, 28); // Turn Right
		for (int i = 1675; i <= 1684; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xFF9200;
			rsi.centerText = false;
		}
		for (int i = 1686; i <= 1687; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xFF9200;
			rsi.centerText = false;
		}
	}

	public static void BankInterface(TextDrawingArea[] TDA) {
		RSInterface Tab = addTabInterface(23000);
		addText(23003, "", TDA, 0, 0xFFB000, false, false);
		addHoverButton(23004, "Misc/CLOSE", 1, 16, 16, "Close", 250, 23005, 3);
		addHoveredButton(23005, "Misc/CLOSE", 2, 16, 16, 23006);
		addHoverButton(23007, "Interfaces/Bank/BANK", 1, 35, 25, "Deposit carried items", 250, 23008, 4);
		addHoveredButton(23008, "Interfaces/Bank/BANK", 2, 35, 25, 23009);
		Tab.children = new int[7];
		Tab.childX = new int[7];
		Tab.childY = new int[7];
		Tab.child(0, 5292, 0, 0);
		Tab.child(1, 23003, 410, 30);
		Tab.child(2, 23004, 472, 29);
		Tab.child(3, 23005, 472, 29);
		Tab.child(4, 23007, 450, 288);
		Tab.child(5, 23008, 450, 288);
		Tab.child(6, 23008, 3000, 5000);
		RSInterface rsi = interfaceCache[5292];
		addText(5384, "", TDA, 0, 0xFFB000, false, false);
		rsi.childX[90] = 410;
		rsi.childY[90] = 288;
	}

	public static void setBounds(int ID, int X, int Y, int frame, RSInterface RSinterface) {
		RSinterface.children[frame] = ID;
		RSinterface.childX[frame] = X;
		RSinterface.childY[frame] = Y;
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
	                                                                                                                                                   // button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = hoverOver;
		tab.sprite1 = imageLoader(j, imageName);
		tab.sprite2 = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {// hoverable
	                                                                                                    // button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(j, name);
		tab.sprite2 = imageLoader(k, name);
	}

	public static void addTransparentSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static RSInterface addScreenInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 0;
		return tab;
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = -1;// Int 230
		return tab;
	}

	private static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null) {
			return model;
		}
		if (i == 1) {
			model = Model.method462(j);
		}
		if (i == 2) {
			model = EntityDef.forID(j).method160();
		}
		if (i == 3) {
			model = Client.myPlayer.method453();
		}
		if (i == 4) {
			model = ItemDef.forID(j).method202(50);
		}
		if (i == 5) {
			model = null;
		}
		if (model != null) {
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		}
		return model;
	}

	private static Sprite method207(int i, StreamLoader streamLoader, String s) {
		long l = (TextClass.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag) {
			return;
		}
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4) {
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
		}
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag) {
			model = method206(anInt255, anInt256);
		} else {
			model = method206(anInt233, mediaID);
		}
		if (model == null) {
			return null;
		}
		if (k == -1 && j == -1 && model.anIntArray1640 == null) {
			return model;
		}
		Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);
		if (k != -1 || j != -1) {
			model_1.method469();
		}
		if (k != -1) {
			model_1.method470(k);
		}
		if (j != -1) {
			model_1.method470(j);
		}
		model_1.method479(64, 768, -50, -10, -50, true);
		return model_1;
	}

	public RSInterface() {
	}

	public static StreamLoader aClass44;
	public boolean drawsTransparent;
	public Sprite sprite1;
	public int anInt208;
	public Sprite sprites[];
	public static RSInterface interfaceCache[];
	public int anIntArray212[];
	public int contentType;// anInt214
	public int spritesX[];
	public int anInt216;
	public int atActionType;
	public String spellName;
	public int anInt219;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public String actions[];
	public int valueIndexArray[][];
	public boolean aBoolean227;
	public String aString228;
	public int mOverInterToTrigger;
	public int invSpritePadX;
	public int textColor;
	public int anInt233;
	public int mediaID;
	public boolean aBoolean235;
	public int parentID;
	public int spellUsableOn;
	private static MRUNodes aMRUNodes_238;
	public int anInt239;
	public int children[];
	public int childX[];
	public boolean usableItemInterface;
	public TextDrawingArea textDrawingAreas;
	public int invSpritePadY;
	public int anIntArray245[];
	public int anInt246;
	public int spritesY[];
	public String message;
	public boolean isInventoryInterface;
	public int id;
	public int invStackSizes[];
	public int inv[];
	public byte aByte254;
	private int anInt255;
	private int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public Sprite sprite2;
	public int scrollMax;
	public int type;
	public int anInt263;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public int anInt265;
	public boolean isMouseoverTriggered;
	public int height;
	public boolean textShadow;
	public int modelZoom;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];

}
