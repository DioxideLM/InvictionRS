package org.invictus.model.players.packets.in;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.PacketType;
import org.invictus.model.players.skills.agility.BarbarianAgilityCourse;
import org.invictus.util.Misc;

public class ClickObjectPacketHandler implements PacketType {

	public static final int FIRST_CLICK = 132;
	public static final int SECOND_CLICK = 252;
	public static final int THIRD_CLICK = 70;

	public ClickObjectPacketHandler() {
	}

	public void processPacket(Client player, int i, int j) {
		player.clickObjectType = player.objectX = player.objectId = player.objectY = 0;
		player.objectYOffset = player.objectXOffset = 0;
		player.getPlayerAssistant().resetFollow();
		switch (i) {
		default:
			break;

		case FIRST_CLICK:
			player.objectX = player.getInStream().readSignedWordBigEndianA();
			player.objectId = player.getInStream().readUnsignedWord();
			player.objectY = player.getInStream().readUnsignedWordA();
			player.objectDistance = 1;
			if (player.playerRights >= 3 && player.playerName.equalsIgnoreCase("Sanity")) {
				Misc.println((new StringBuilder()).append("objectId: ").append(player.objectId).append("  ObjectX: ").append(player.objectX).append("  objectY: ").append(player.objectY).append(" Xoff: ").append(player.getX() - player.objectX).append(" Yoff: ").append(player.getY() - player.objectY).toString());
			} else if (player.playerRights == 3 && player.debugMode) {
				player.sendMessage((new StringBuilder()).append("objectId: ").append(player.objectId).append(" objectX: ").append(player.objectX).append(" objectY: ").append(player.objectY).toString());
			}
			if (Math.abs(player.getX() - player.objectX) > 25 || Math.abs(player.getY() - player.objectY) > 25) {
				player.resetWalkingQueue();
			} else {
				for (int k = 0; k < player.getRunecrafting().altarID.length; k++) {
					if (player.objectId == player.getRunecrafting().altarID[k]) {
						player.getRunecrafting().craftRunes(player.objectId);
					}
				}

				switch (player.objectId) {
				case 410:
					if (player.playerMagicBook == 0) {
						player.setSidebarInterface(6, 29999);
						player.playerMagicBook = 2;
						player.autocasting = false;
						player.sendMessage("An ancient wisdomin fills your mind.");
						player.autocastId = -1;
						player.getPlayerAssistant().resetAutocast();
					} else {
						player.setSidebarInterface(6, 1151);
						player.playerMagicBook = 0;
						player.autocasting = false;
						player.sendMessage("You feel a drain on your memory.");
						player.autocastId = -1;
						player.getPlayerAssistant().resetAutocast();
					}
					break;
					
				case BarbarianAgilityCourse.ROPESWING:
					player.objectDistance = 4;
					break;

				case 1733:
					player.objectYOffset = 2;
					break;

				case 3044:
					player.objectDistance = 3;
					break;

				case 245:
					player.objectYOffset = -1;
					player.objectDistance = 0;
					break;

				case 272:
					player.objectYOffset = 1;
					player.objectDistance = 0;
					break;

				case 273:
					player.objectYOffset = 1;
					player.objectDistance = 0;
					break;

				case 246:
					player.objectYOffset = 1;
					player.objectDistance = 0;
					break;

				case 4493:
				case 4494:
				case 4495:
				case 4496:
					player.objectDistance = 5;
					break;

				case 6522:
				case 10229:
					player.objectDistance = 2;
					break;

				case 8959:
					player.objectYOffset = 1;
					break;

				case 4417:
					if (player.objectX == 2425 && player.objectY == 3074) {
						player.objectYOffset = 2;
					}
					break;

				case 4420:
					if (player.getX() >= 2383 && player.getX() <= 2385) {
						player.objectYOffset = 1;
					} else {
						player.objectYOffset = -2;
					}
					// fall through

				case 409:
				case 6552:
					player.objectDistance = 2;
					break;

				case 2878:
				case 2879:
					player.objectDistance = 3;
					break;

				case 2558:
					player.objectDistance = 0;
					if (player.absX > player.objectX && player.objectX == 3044) {
						player.objectXOffset = 1;
					}
					if (player.absY > player.objectY) {
						player.objectYOffset = 1;
					}
					if (player.absX < player.objectX && player.objectX == 3038) {
						player.objectXOffset = -1;
					}
					break;

				case 9356:
					player.objectDistance = 2;
					break;

				case 1815:
				case 1816:
				case 5959:
				case 5960:
					player.objectDistance = 0;
					break;

				case 9293:
					player.objectDistance = 2;
					break;

				case 4418:
					if (player.objectX == 2374 && player.objectY == 3131) {
						player.objectYOffset = -2;
					} else if (player.objectX == 2369 && player.objectY == 3126) {
						player.objectXOffset = 2;
					} else if (player.objectX == 2380 && player.objectY == 3127) {
						player.objectYOffset = 2;
					} else if (player.objectX == 2369 && player.objectY == 3126) {
						player.objectXOffset = 2;
					} else if (player.objectX == 2374 && player.objectY == 3131) {
						player.objectYOffset = -2;
					}
					break;

				case 9706:
					player.objectDistance = 0;
					player.objectXOffset = 1;
					break;

				case 9707:
					player.objectDistance = 0;
					player.objectYOffset = -1;
					break;

				case 4419:
				case 6707:
					player.objectYOffset = 3;
					break;

				case 6823:
					player.objectDistance = 2;
					player.objectYOffset = 1;
					break;

				case 6706:
					player.objectXOffset = 2;
					break;

				case 6772:
					player.objectDistance = 2;
					player.objectYOffset = 1;
					break;

				case 6705:
					player.objectYOffset = -1;
					break;

				case 6822:
					player.objectDistance = 2;
					player.objectYOffset = 1;
					break;

				case 6704:
					player.objectYOffset = -1;
					break;

				case 6773:
					player.objectDistance = 2;
					player.objectXOffset = 1;
					player.objectYOffset = 1;
					break;

				case 6703:
					player.objectXOffset = -1;
					break;

				case 6771:
					player.objectDistance = 2;
					player.objectXOffset = 1;
					player.objectYOffset = 1;
					break;

				case 6702:
					player.objectXOffset = -1;
					break;

				case 6821:
					player.objectDistance = 2;
					player.objectXOffset = 1;
					player.objectYOffset = 1;
					break;

				case 1276:
				case 1278:
				case 1281:
				case 1306:
				case 1307:
				case 1308:
				case 1309:
					player.objectDistance = 3;
					break;

				default:
					player.objectDistance = 1;
					player.objectXOffset = 0;
					player.objectYOffset = 0;
					break;
				}
				if (player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
					player.getActions().firstClickObject(player.objectId, player.objectX, player.objectY);
				} else {
					player.clickObjectType = 1;
				}
			}
			break;

		case SECOND_CLICK:
			player.objectId = player.getInStream().readUnsignedWordBigEndianA();
			player.objectY = player.getInStream().readSignedWordBigEndian();
			player.objectX = player.getInStream().readUnsignedWordA();
			player.objectDistance = 1;
			if (player.playerRights == 3 && player.debugMode) {
				Misc.println((new StringBuilder()).append("objectId: ").append(player.objectId).append("  ObjectX: ").append(player.objectX).append("  objectY: ").append(player.objectY).append(" Xoff: ").append(player.getX() - player.objectX).append(" Yoff: ").append(player.getY() - player.objectY).toString());
			}
			switch (player.objectId) {
			case 6162:
			case 6163:
			case 6164:
			case 6165:
			case 6166:
				player.objectDistance = 2;
				break;

			default:
				player.objectDistance = 1;
				player.objectXOffset = 0;
				player.objectYOffset = 0;
				break;
			}
			if (player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
				player.getActions().secondClickObject(player.objectId, player.objectX, player.objectY);
			} else {
				player.clickObjectType = 2;
			}
			break;

		case THIRD_CLICK: // 'F'
			player.objectX = player.getInStream().readSignedWordBigEndian();
			player.objectY = player.getInStream().readUnsignedWord();
			player.objectId = player.getInStream().readUnsignedWordBigEndianA();
			if (player.playerRights == 3 && player.debugMode) {
				Misc.println((new StringBuilder()).append("objectId: ").append(player.objectId).append("  ObjectX: ").append(player.objectX).append("  objectY: ").append(player.objectY).append(" Xoff: ").append(player.getX() - player.objectX).append(" Yoff: ").append(player.getY() - player.objectY).toString());
			}
			switch (player.objectId) {
			default:
				player.objectDistance = 1;
				break;
			}
			player.objectXOffset = 0;
			player.objectYOffset = 0;
			if (player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
				player.getActions().secondClickObject(player.objectId, player.objectX, player.objectY);
			} else {
				player.clickObjectType = 3;
			}
			break;
		}
	}

	public void handleSpecialCase(Client client, int i, int j, int k) {
	}
}
