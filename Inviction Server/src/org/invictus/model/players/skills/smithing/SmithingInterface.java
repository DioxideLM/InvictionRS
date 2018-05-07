package org.invictus.model.players.skills.smithing;

import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class SmithingInterface extends SkillHandler {

	Client player;

	public SmithingInterface(Client player) {
		this.player = player;
	}

	public void showSmithInterface(int itemId) {
		if (itemId == 2349)
			makeBronzeInterface();
		else if (itemId == 2351)
			makeIronInterface();
		else if (itemId == 2353)
			makeSteelInterface();
		else if (itemId == 2359)
			makeMithInterface();
		else if (itemId == 2361)
			makeAddyInterface();
		else if (itemId == 2363)
			makeRuneInterface();

	}

	private void makeRuneInterface() {
		String fiveb = GetForBars(2363, 5);
		String threeb = GetForBars(2363, 3);
		String twob = GetForBars(2363, 2);
		String oneb = GetForBars(2363, 1);
		player.getPlayerAssistant().sendString(fiveb + "5 Bars" + fiveb, 1112);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1109);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1110);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1118);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1111);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1095);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1115);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1090);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1113);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1116);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1114);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1089);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 8428);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1124);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1125);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1126);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1127);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1128);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1129);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1130);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1131);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 13357);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 11459);
		player.getPlayerAssistant().sendString(GetForlvl(88) + "Plate Body" + GetForlvl(18), 1101);
		player.getPlayerAssistant().sendString(GetForlvl(99) + "Plate Legs" + GetForlvl(16), 1099);
		player.getPlayerAssistant().sendString(GetForlvl(99) + "Plate Skirt" + GetForlvl(16), 1100);
		player.getPlayerAssistant().sendString(GetForlvl(99) + "2 Hand Sword" + GetForlvl(14), 1088);
		player.getPlayerAssistant().sendString(GetForlvl(97) + "Kite Shield" + GetForlvl(12), 1105);
		player.getPlayerAssistant().sendString(GetForlvl(96) + "Chain Body" + GetForlvl(11), 1098);
		player.getPlayerAssistant().sendString(GetForlvl(95) + "Battle Axe" + GetForlvl(10), 1092);
		player.getPlayerAssistant().sendString(GetForlvl(94) + "Warhammer" + GetForlvl(9), 1083);
		player.getPlayerAssistant().sendString(GetForlvl(93) + "Square Shield" + GetForlvl(8), 1104);
		player.getPlayerAssistant().sendString(GetForlvl(92) + "Full Helm" + GetForlvl(7), 1103);
		player.getPlayerAssistant().sendString(GetForlvl(92) + "Throwing Knives" + GetForlvl(7), 1106);
		player.getPlayerAssistant().sendString(GetForlvl(91) + "Long Sword" + GetForlvl(6), 1086);
		player.getPlayerAssistant().sendString(GetForlvl(90) + "Scimitar" + GetForlvl(5), 1087);
		player.getPlayerAssistant().sendString(GetForlvl(90) + "Arrowtips" + GetForlvl(5), 1108);
		player.getPlayerAssistant().sendString(GetForlvl(89) + "Sword" + GetForlvl(4), 1085);
		player.getPlayerAssistant().sendString(GetForlvl(89) + "Bolts" + GetForlvl(4), 9144);
		player.getPlayerAssistant().sendString(GetForlvl(89) + "Nails" + GetForlvl(4), 13358);
		player.getPlayerAssistant().sendString(GetForlvl(88) + "Medium Helm" + GetForlvl(3), 1102);
		player.getPlayerAssistant().sendString(GetForlvl(87) + "Mace" + GetForlvl(2), 1093);
		player.getPlayerAssistant().sendString(GetForlvl(85) + "Dagger" + GetForlvl(1), 1094);
		player.getPlayerAssistant().sendString(GetForlvl(86) + "Axe" + GetForlvl(1), 1091);
		player.getPlayerAssistant().sendUpdateSingleItem(1213, 0, 1119, 1); // dagger
		player.getPlayerAssistant().sendUpdateSingleItem(1359, 0, 1120, 1); // axe
		player.getPlayerAssistant().sendUpdateSingleItem(1113, 0, 1121, 1); // chain body
		player.getPlayerAssistant().sendUpdateSingleItem(1147, 0, 1122, 1); // med helm
		player.getPlayerAssistant().sendUpdateSingleItem(9144, 0, 1123, 10); // Bolts
		player.getPlayerAssistant().sendUpdateSingleItem(1289, 1, 1119, 1); // s-sword
		player.getPlayerAssistant().sendUpdateSingleItem(1432, 1, 1120, 1); // mace
		player.getPlayerAssistant().sendUpdateSingleItem(1079, 1, 1121, 1); // platelegs
		player.getPlayerAssistant().sendUpdateSingleItem(1163, 1, 1122, 1); // full helm
		player.getPlayerAssistant().sendUpdateSingleItem(44, 1, 1123, 15); // arrowtips
		player.getPlayerAssistant().sendUpdateSingleItem(1333, 2, 1119, 1); // scimmy
		player.getPlayerAssistant().sendUpdateSingleItem(1347, 2, 1120, 1); // warhammer
		player.getPlayerAssistant().sendUpdateSingleItem(1093, 2, 1121, 1); // plateskirt
		player.getPlayerAssistant().sendUpdateSingleItem(1185, 2, 1122, 1); // Sq. Shield
		player.getPlayerAssistant().sendUpdateSingleItem(868, 2, 1123, 5); // throwing-knives
		player.getPlayerAssistant().sendUpdateSingleItem(1303, 3, 1119, 1); // longsword
		player.getPlayerAssistant().sendUpdateSingleItem(1373, 3, 1120, 1); // battleaxe
		player.getPlayerAssistant().sendUpdateSingleItem(1127, 3, 1121, 1); // platebody
		player.getPlayerAssistant().sendUpdateSingleItem(1201, 3, 1122, 1); // kiteshield
		player.getPlayerAssistant().sendUpdateSingleItem(1319, 4, 1119, 1); // 2h sword
		player.getPlayerAssistant().sendUpdateSingleItem(4824, 4, 1122, 15); // nails
		player.getPlayerAssistant().sendUpdateSingleItem(-1, 3, 1123, 1);
		player.getPlayerAssistant().sendString("", 1135);
		player.getPlayerAssistant().sendString("", 1134);
		player.getPlayerAssistant().sendString("", 11461);
		player.getPlayerAssistant().sendString("", 11459);
		player.getPlayerAssistant().sendString("", 1132);
		player.getPlayerAssistant().sendString("", 1096);
		player.getPlayerAssistant().sendInterface(994);
	}

	private void makeAddyInterface() {
		String fiveb = GetForBars(2361, 5);
		String threeb = GetForBars(2361, 3);
		String twob = GetForBars(2361, 2);
		String oneb = GetForBars(2361, 1);
		player.getPlayerAssistant().sendString(fiveb + "5 Bars" + fiveb, 1112);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1109);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1110);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1118);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1111);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1095);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1115);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1090);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1113);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1116);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1114);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1089);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 8428);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1124);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1125);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1126);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1127);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1128);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1129);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1130);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1131);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 13357);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 11459);
		player.getPlayerAssistant().sendString(GetForlvl(88) + "Plate Body" + GetForlvl(18), 1101);
		player.getPlayerAssistant().sendString(GetForlvl(86) + "Plate Legs" + GetForlvl(16), 1099);
		player.getPlayerAssistant().sendString(GetForlvl(86) + "Plate Skirt" + GetForlvl(16), 1100);
		player.getPlayerAssistant().sendString(GetForlvl(84) + "2 Hand Sword" + GetForlvl(14), 1088);
		player.getPlayerAssistant().sendString(GetForlvl(82) + "Kite Shield" + GetForlvl(12), 1105);
		player.getPlayerAssistant().sendString(GetForlvl(81) + "Chain Body" + GetForlvl(11), 1098);
		player.getPlayerAssistant().sendString(GetForlvl(80) + "Battle Axe" + GetForlvl(10), 1092);
		player.getPlayerAssistant().sendString(GetForlvl(79) + "Warhammer" + GetForlvl(9), 1083);
		player.getPlayerAssistant().sendString(GetForlvl(78) + "Square Shield" + GetForlvl(8), 1104);
		player.getPlayerAssistant().sendString(GetForlvl(77) + "Full Helm" + GetForlvl(7), 1103);
		player.getPlayerAssistant().sendString(GetForlvl(77) + "Throwing Knives" + GetForlvl(7), 1106);
		player.getPlayerAssistant().sendString(GetForlvl(76) + "Long Sword" + GetForlvl(6), 1086);
		player.getPlayerAssistant().sendString(GetForlvl(75) + "Scimitar" + GetForlvl(5), 1087);
		player.getPlayerAssistant().sendString(GetForlvl(75) + "Arrowtips" + GetForlvl(5), 1108);
		player.getPlayerAssistant().sendString(GetForlvl(74) + "Sword" + GetForlvl(4), 1085);
		player.getPlayerAssistant().sendString(GetForlvl(74) + "Bolts" + GetForlvl(4), 9143);
		player.getPlayerAssistant().sendString(GetForlvl(74) + "Nails" + GetForlvl(4), 13358);
		player.getPlayerAssistant().sendString(GetForlvl(73) + "Medium Helm" + GetForlvl(3), 1102);
		player.getPlayerAssistant().sendString(GetForlvl(72) + "Mace" + GetForlvl(2), 1093);
		player.getPlayerAssistant().sendString(GetForlvl(70) + "Dagger" + GetForlvl(1), 1094);
		player.getPlayerAssistant().sendString(GetForlvl(71) + "Axe" + GetForlvl(1), 1091);
		player.getPlayerAssistant().sendUpdateSingleItem(1211, 0, 1119, 1); // dagger
		player.getPlayerAssistant().sendUpdateSingleItem(1357, 0, 1120, 1); // axe
		player.getPlayerAssistant().sendUpdateSingleItem(1111, 0, 1121, 1); // chain body
		player.getPlayerAssistant().sendUpdateSingleItem(1145, 0, 1122, 1); // med helm
		player.getPlayerAssistant().sendUpdateSingleItem(9143, 0, 1123, 10); // Bolts
		player.getPlayerAssistant().sendUpdateSingleItem(1287, 1, 1119, 1); // s-sword
		player.getPlayerAssistant().sendUpdateSingleItem(1430, 1, 1120, 1); // mace
		player.getPlayerAssistant().sendUpdateSingleItem(1073, 1, 1121, 1); // platelegs
		player.getPlayerAssistant().sendUpdateSingleItem(1161, 1, 1122, 1); // full helm
		player.getPlayerAssistant().sendUpdateSingleItem(43, 1, 1123, 15); // arrowtips
		player.getPlayerAssistant().sendUpdateSingleItem(1331, 2, 1119, 1); // scimmy
		player.getPlayerAssistant().sendUpdateSingleItem(1345, 2, 1120, 1); // warhammer
		player.getPlayerAssistant().sendUpdateSingleItem(1091, 2, 1121, 1); // plateskirt
		player.getPlayerAssistant().sendUpdateSingleItem(1183, 2, 1122, 1); // Sq. Shield
		player.getPlayerAssistant().sendUpdateSingleItem(867, 2, 1123, 5); // throwing-knives
		player.getPlayerAssistant().sendUpdateSingleItem(1301, 3, 1119, 1); // longsword
		player.getPlayerAssistant().sendUpdateSingleItem(1371, 3, 1120, 1); // battleaxe
		player.getPlayerAssistant().sendUpdateSingleItem(1123, 3, 1121, 1); // platebody
		player.getPlayerAssistant().sendUpdateSingleItem(1199, 3, 1122, 1); // kiteshield
		player.getPlayerAssistant().sendUpdateSingleItem(1317, 4, 1119, 1); // 2h sword
		player.getPlayerAssistant().sendUpdateSingleItem(4823, 4, 1122, 15); // nails
		player.getPlayerAssistant().sendUpdateSingleItem(-1, 3, 1123, 1);
		player.getPlayerAssistant().sendString("", 1135);
		player.getPlayerAssistant().sendString("", 1134);
		player.getPlayerAssistant().sendString("", 11461);
		player.getPlayerAssistant().sendString("", 11459);
		player.getPlayerAssistant().sendString("", 1132);
		player.getPlayerAssistant().sendString("", 1096);
		player.getPlayerAssistant().sendInterface(994);
	}

	private void makeMithInterface() {
		String fiveb = GetForBars(2359, 5);
		String threeb = GetForBars(2359, 3);
		String twob = GetForBars(2359, 2);
		String oneb = GetForBars(2359, 1);
		player.getPlayerAssistant().sendString(fiveb + "5 Bars" + fiveb, 1112);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1109);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1110);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1118);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1111);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1095);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1115);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1090);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1113);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1116);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1114);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1089);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 8428);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1124);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1125);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1126);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1127);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1128);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1129);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1130);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1131);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 13357);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 11459);
		player.getPlayerAssistant().sendString(GetForlvl(68) + "Plate Body" + GetForlvl(18), 1101);
		player.getPlayerAssistant().sendString(GetForlvl(66) + "Plate Legs" + GetForlvl(16), 1099);
		player.getPlayerAssistant().sendString(GetForlvl(66) + "Plate Skirt" + GetForlvl(16), 1100);
		player.getPlayerAssistant().sendString(GetForlvl(64) + "2 Hand Sword" + GetForlvl(14), 1088);
		player.getPlayerAssistant().sendString(GetForlvl(62) + "Kite Shield" + GetForlvl(12), 1105);
		player.getPlayerAssistant().sendString(GetForlvl(61) + "Chain Body" + GetForlvl(11), 1098);
		player.getPlayerAssistant().sendString(GetForlvl(60) + "Battle Axe" + GetForlvl(10), 1092);
		player.getPlayerAssistant().sendString(GetForlvl(59) + "Warhammer" + GetForlvl(9), 1083);
		player.getPlayerAssistant().sendString(GetForlvl(58) + "Square Shield" + GetForlvl(8), 1104);
		player.getPlayerAssistant().sendString(GetForlvl(57) + "Full Helm" + GetForlvl(7), 1103);
		player.getPlayerAssistant().sendString(GetForlvl(57) + "Throwing Knives" + GetForlvl(7), 1106);
		player.getPlayerAssistant().sendString(GetForlvl(56) + "Long Sword" + GetForlvl(6), 1086);
		player.getPlayerAssistant().sendString(GetForlvl(55) + "Scimitar" + GetForlvl(5), 1087);
		player.getPlayerAssistant().sendString(GetForlvl(55) + "Arrowtips" + GetForlvl(5), 1108);
		player.getPlayerAssistant().sendString(GetForlvl(54) + "Sword" + GetForlvl(4), 1085);
		player.getPlayerAssistant().sendString(GetForlvl(54) + "Bolts" + GetForlvl(4), 9142);
		player.getPlayerAssistant().sendString(GetForlvl(54) + "Nails" + GetForlvl(4), 13358);
		player.getPlayerAssistant().sendString(GetForlvl(53) + "Medium Helm" + GetForlvl(3), 1102);
		player.getPlayerAssistant().sendString(GetForlvl(52) + "Mace" + GetForlvl(2), 1093);
		player.getPlayerAssistant().sendString(GetForlvl(50) + "Dagger" + GetForlvl(1), 1094);
		player.getPlayerAssistant().sendString(GetForlvl(51) + "Axe" + GetForlvl(1), 1091);
		player.getPlayerAssistant().sendUpdateSingleItem(1209, 0, 1119, 1); // dagger
		player.getPlayerAssistant().sendUpdateSingleItem(1355, 0, 1120, 1); // axe
		player.getPlayerAssistant().sendUpdateSingleItem(1109, 0, 1121, 1); // chain body
		player.getPlayerAssistant().sendUpdateSingleItem(1143, 0, 1122, 1); // med helm
		player.getPlayerAssistant().sendUpdateSingleItem(9142, 0, 1123, 10); // Bolts
		player.getPlayerAssistant().sendUpdateSingleItem(1285, 1, 1119, 1); // s-sword
		player.getPlayerAssistant().sendUpdateSingleItem(1428, 1, 1120, 1); // mace
		player.getPlayerAssistant().sendUpdateSingleItem(1071, 1, 1121, 1); // platelegs
		player.getPlayerAssistant().sendUpdateSingleItem(1159, 1, 1122, 1); // full helm
		player.getPlayerAssistant().sendUpdateSingleItem(42, 1, 1123, 15); // arrowtips
		player.getPlayerAssistant().sendUpdateSingleItem(1329, 2, 1119, 1); // scimmy
		player.getPlayerAssistant().sendUpdateSingleItem(1343, 2, 1120, 1); // warhammer
		player.getPlayerAssistant().sendUpdateSingleItem(1085, 2, 1121, 1); // plateskirt
		player.getPlayerAssistant().sendUpdateSingleItem(1181, 2, 1122, 1); // Sq. Shield
		player.getPlayerAssistant().sendUpdateSingleItem(866, 2, 1123, 5); // throwing-knives
		player.getPlayerAssistant().sendUpdateSingleItem(1299, 3, 1119, 1); // longsword
		player.getPlayerAssistant().sendUpdateSingleItem(1369, 3, 1120, 1); // battleaxe
		player.getPlayerAssistant().sendUpdateSingleItem(1121, 3, 1121, 1); // platebody
		player.getPlayerAssistant().sendUpdateSingleItem(1197, 3, 1122, 1); // kiteshield
		player.getPlayerAssistant().sendUpdateSingleItem(1315, 4, 1119, 1); // 2h sword
		player.getPlayerAssistant().sendUpdateSingleItem(4822, 4, 1122, 15); // nails
		player.getPlayerAssistant().sendUpdateSingleItem(-1, 3, 1123, 1);
		player.getPlayerAssistant().sendString("", 1135);
		player.getPlayerAssistant().sendString("", 1134);
		player.getPlayerAssistant().sendString("", 11461);
		player.getPlayerAssistant().sendString("", 11459);
		player.getPlayerAssistant().sendString("", 1132);
		player.getPlayerAssistant().sendString("", 1096);
		player.getPlayerAssistant().sendInterface(994);
	}

	private void makeSteelInterface() {
		String fiveb = GetForBars(2353, 5);
		String threeb = GetForBars(2353, 3);
		String twob = GetForBars(2353, 2);
		String oneb = GetForBars(2353, 1);
		player.getPlayerAssistant().sendString(fiveb + "5 Bars" + fiveb, 1112);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1109);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1110);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1118);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1111);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1095);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1115);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1090);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1113);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1116);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1114);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1089);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 8428);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1124);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1125);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1126);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1127);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1128);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1129);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1130);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1131);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 13357);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1132);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1135);
		player.getPlayerAssistant().sendString("", 11459);
		player.getPlayerAssistant().sendString(GetForlvl(48) + "Plate Body" + GetForlvl(18), 1101);
		player.getPlayerAssistant().sendString(GetForlvl(46) + "Plate Legs" + GetForlvl(16), 1099);
		player.getPlayerAssistant().sendString(GetForlvl(46) + "Plate Skirt" + GetForlvl(16), 1100);
		player.getPlayerAssistant().sendString(GetForlvl(44) + "2 Hand Sword" + GetForlvl(14), 1088);
		player.getPlayerAssistant().sendString(GetForlvl(42) + "Kite Shield" + GetForlvl(12), 1105);
		player.getPlayerAssistant().sendString(GetForlvl(41) + "Chain Body" + GetForlvl(11), 1098);
		player.getPlayerAssistant().sendString("", 11461);
		player.getPlayerAssistant().sendString(GetForlvl(40) + "Battle Axe" + GetForlvl(10), 1092);
		player.getPlayerAssistant().sendString(GetForlvl(39) + "Warhammer" + GetForlvl(9), 1083);
		player.getPlayerAssistant().sendString(GetForlvl(38) + "Square Shield" + GetForlvl(8), 1104);
		player.getPlayerAssistant().sendString(GetForlvl(37) + "Full Helm" + GetForlvl(7), 1103);
		player.getPlayerAssistant().sendString(GetForlvl(37) + "Throwing Knives" + GetForlvl(7), 1106);
		player.getPlayerAssistant().sendString(GetForlvl(36) + "Long Sword" + GetForlvl(6), 1086);
		player.getPlayerAssistant().sendString(GetForlvl(35) + "Scimitar" + GetForlvl(5), 1087);
		player.getPlayerAssistant().sendString(GetForlvl(35) + "Arrowtips" + GetForlvl(5), 1108);
		player.getPlayerAssistant().sendString(GetForlvl(34) + "Sword" + GetForlvl(4), 1085);
		player.getPlayerAssistant().sendString(GetForlvl(34) + "Bolts" + GetForlvl(4), 9141);
		player.getPlayerAssistant().sendString(GetForlvl(34) + "Nails" + GetForlvl(4), 13358);
		player.getPlayerAssistant().sendString(GetForlvl(33) + "Medium Helm" + GetForlvl(3), 1102);
		player.getPlayerAssistant().sendString(GetForlvl(32) + "Mace" + GetForlvl(2), 1093);
		player.getPlayerAssistant().sendString(GetForlvl(30) + "Dagger" + GetForlvl(1), 1094);
		player.getPlayerAssistant().sendString(GetForlvl(31) + "Axe" + GetForlvl(1), 1091);
		player.getPlayerAssistant().sendString(GetForlvl(35) + "Cannon Ball" + GetForlvl(35), 1096);
		player.getPlayerAssistant().sendString(GetForlvl(36) + "Studs" + GetForlvl(36), 1134);
		player.getPlayerAssistant().sendUpdateSingleItem(1207, 0, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1353, 0, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1105, 0, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1141, 0, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(9141, 0, 1123, 10);
		player.getPlayerAssistant().sendUpdateSingleItem(1281, 1, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1424, 1, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1069, 1, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1157, 1, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(41, 1, 1123, 15);
		player.getPlayerAssistant().sendUpdateSingleItem(1325, 2, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1339, 2, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1083, 2, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1177, 2, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(865, 2, 1123, 5);
		player.getPlayerAssistant().sendUpdateSingleItem(1295, 3, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1365, 3, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1119, 3, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1193, 3, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1311, 4, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1539, 4, 1122, 15);
		player.getPlayerAssistant().sendUpdateSingleItem(2, 3, 1123, 4);
		player.getPlayerAssistant().sendUpdateSingleItem(2370, 4, 1123, 1);
		player.getPlayerAssistant().sendInterface(994);
	}

	private void makeIronInterface() {
		String fiveb = GetForBars(2351, 5);
		String threeb = GetForBars(2351, 3);
		String twob = GetForBars(2351, 2);
		String oneb = GetForBars(2351, 1);
		player.getPlayerAssistant().sendString(fiveb + "5 Bars" + fiveb, 1112);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1109);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1110);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1118);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1111);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1095);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1115);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1090);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1113);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1116);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1114);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1089);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 8428);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1124);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1125);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1126);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1127);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1128);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1129);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1130);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1131);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 13357);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 11459);
		player.getPlayerAssistant().sendString(GetForlvl(33) + "Plate Body" + GetForlvl(18), 1101);
		player.getPlayerAssistant().sendString(GetForlvl(31) + "Plate Legs" + GetForlvl(16), 1099);
		player.getPlayerAssistant().sendString(GetForlvl(31) + "Plate Skirt" + GetForlvl(16), 1100);
		player.getPlayerAssistant().sendString(GetForlvl(29) + "2 Hand Sword" + GetForlvl(14), 1088);
		player.getPlayerAssistant().sendString(GetForlvl(27) + "Kite Shield" + GetForlvl(12), 1105);
		player.getPlayerAssistant().sendString(GetForlvl(26) + "Chain Body" + GetForlvl(11), 1098);
		player.getPlayerAssistant().sendString(GetForlvl(26) + "Oil Lantern Frame" + GetForlvl(11), 11461);
		player.getPlayerAssistant().sendString(GetForlvl(25) + "Battle Axe" + GetForlvl(10), 1092);
		player.getPlayerAssistant().sendString(GetForlvl(24) + "Warhammer" + GetForlvl(9), 1083);
		player.getPlayerAssistant().sendString(GetForlvl(23) + "Square Shield" + GetForlvl(8), 1104);
		player.getPlayerAssistant().sendString(GetForlvl(22) + "Full Helm" + GetForlvl(7), 1103);
		player.getPlayerAssistant().sendString(GetForlvl(21) + "Throwing Knives" + GetForlvl(7), 1106);
		player.getPlayerAssistant().sendString(GetForlvl(21) + "Long Sword" + GetForlvl(6), 1086);
		player.getPlayerAssistant().sendString(GetForlvl(20) + "Scimitar" + GetForlvl(5), 1087);
		player.getPlayerAssistant().sendString(GetForlvl(20) + "Arrowtips" + GetForlvl(5), 1108);
		player.getPlayerAssistant().sendString(GetForlvl(19) + "Sword" + GetForlvl(4), 1085);
		player.getPlayerAssistant().sendString(GetForlvl(19) + "Bolts" + GetForlvl(4), 9140);
		player.getPlayerAssistant().sendString(GetForlvl(19) + "Nails" + GetForlvl(4), 13358);
		player.getPlayerAssistant().sendString(GetForlvl(18) + "Medium Helm" + GetForlvl(3), 1102);
		player.getPlayerAssistant().sendString(GetForlvl(17) + "Mace" + GetForlvl(2), 1093);
		player.getPlayerAssistant().sendString(GetForlvl(15) + "Dagger" + GetForlvl(1), 1094);
		player.getPlayerAssistant().sendString(GetForlvl(16) + "Axe" + GetForlvl(1), 1091);
		player.getPlayerAssistant().sendUpdateSingleItem(1203, 0, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1349, 0, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1101, 0, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1137, 0, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(9140, 0, 1123, 10);
		player.getPlayerAssistant().sendUpdateSingleItem(1279, 1, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1420, 1, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1067, 1, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1153, 1, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(40, 1, 1123, 15);
		player.getPlayerAssistant().sendUpdateSingleItem(1323, 2, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1335, 2, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1081, 2, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1175, 2, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(863, 2, 1123, 5);
		player.getPlayerAssistant().sendUpdateSingleItem(1293, 3, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1363, 3, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1115, 3, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1191, 3, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1309, 4, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(4820, 4, 1122, 15);
		player.getPlayerAssistant().sendUpdateSingleItem(4540, 4, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(-1, 3, 1123, 1);
		player.getPlayerAssistant().sendString("", 1135);
		player.getPlayerAssistant().sendString("", 1134);
		player.getPlayerAssistant().sendString("", 1132);
		player.getPlayerAssistant().sendString("", 1096);
		player.getPlayerAssistant().sendInterface(994);
	}

	private void makeBronzeInterface() {
		String fiveb = GetForBars(2349, 5);
		String threeb = GetForBars(2349, 3);
		String twob = GetForBars(2349, 2);
		String oneb = GetForBars(2349, 1);
		player.getPlayerAssistant().sendString(fiveb + "5 Bars" + fiveb, 1112);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1109);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1110);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1118);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1111);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1095);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1115);
		player.getPlayerAssistant().sendString(threeb + "3 Bars" + threeb, 1090);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1113);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1116);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1114);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 1089);
		player.getPlayerAssistant().sendString(twob + "2 Bars" + twob, 8428);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1124);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1125);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1126);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1127);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1128);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1129);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1130);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 1131);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 13357);
		player.getPlayerAssistant().sendString(oneb + "1 Bar" + oneb, 11459);
		player.getPlayerAssistant().sendString(GetForlvl(18) + "Plate Body" + GetForlvl(18), 1101);
		player.getPlayerAssistant().sendString(GetForlvl(16) + "Plate Legs" + GetForlvl(16), 1099);
		player.getPlayerAssistant().sendString(GetForlvl(16) + "Plate Skirt" + GetForlvl(16), 1100);
		player.getPlayerAssistant().sendString(GetForlvl(14) + "2 Hand Sword" + GetForlvl(14), 1088);
		player.getPlayerAssistant().sendString(GetForlvl(12) + "Kite Shield" + GetForlvl(12), 1105);
		player.getPlayerAssistant().sendString(GetForlvl(11) + "Chain Body" + GetForlvl(11), 1098);
		player.getPlayerAssistant().sendString(GetForlvl(10) + "Battle Axe" + GetForlvl(10), 1092);
		player.getPlayerAssistant().sendString(GetForlvl(9) + "Warhammer" + GetForlvl(9), 1083);
		player.getPlayerAssistant().sendString(GetForlvl(8) + "Square Shield" + GetForlvl(8), 1104);
		player.getPlayerAssistant().sendString(GetForlvl(7) + "Full Helm" + GetForlvl(7), 1103);
		player.getPlayerAssistant().sendString(GetForlvl(7) + "Throwing Knives" + GetForlvl(7), 1106);
		player.getPlayerAssistant().sendString(GetForlvl(6) + "Long Sword" + GetForlvl(6), 1086);
		player.getPlayerAssistant().sendString(GetForlvl(5) + "Scimitar" + GetForlvl(5), 1087);
		player.getPlayerAssistant().sendString(GetForlvl(5) + "Arrowtips" + GetForlvl(5), 1108);
		player.getPlayerAssistant().sendString(GetForlvl(4) + "Sword" + GetForlvl(4), 1085);
		player.getPlayerAssistant().sendString(GetForlvl(4) + "Bolts" + GetForlvl(4), 1107);
		player.getPlayerAssistant().sendString(GetForlvl(4) + "Nails" + GetForlvl(4), 13358);
		player.getPlayerAssistant().sendString(GetForlvl(3) + "Medium Helm" + GetForlvl(3), 1102);
		player.getPlayerAssistant().sendString(GetForlvl(2) + "Mace" + GetForlvl(2), 1093);
		player.getPlayerAssistant().sendString(GetForlvl(1) + "Dagger" + GetForlvl(1), 1094);
		player.getPlayerAssistant().sendString(GetForlvl(1) + "Axe" + GetForlvl(1), 1091);
		player.getPlayerAssistant().sendUpdateSingleItem(1205, 0, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1351, 0, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1103, 0, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1139, 0, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(819, 0, 1123, 10);
		player.getPlayerAssistant().sendUpdateSingleItem(1277, 1, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1422, 1, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1075, 1, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1155, 1, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(39, 1, 1123, 15);
		player.getPlayerAssistant().sendUpdateSingleItem(1321, 2, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1337, 2, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1087, 2, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1173, 2, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(864, 2, 1123, 5);
		player.getPlayerAssistant().sendUpdateSingleItem(1291, 3, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1375, 3, 1120, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1117, 3, 1121, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1189, 3, 1122, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(1307, 4, 1119, 1);
		player.getPlayerAssistant().sendUpdateSingleItem(4819, 4, 1122, 15);
		player.getPlayerAssistant().sendUpdateSingleItem(-1, 3, 1123, 1);
		player.getPlayerAssistant().sendString("", 1135);
		player.getPlayerAssistant().sendString("", 1134);
		player.getPlayerAssistant().sendString("", 11461);
		player.getPlayerAssistant().sendString("", 11459);
		player.getPlayerAssistant().sendString("", 1132);
		player.getPlayerAssistant().sendString("", 1096);
		player.getPlayerAssistant().sendInterface(994);
	}

	private String GetForlvl(int i) {
		if (player.playerLevel[SMITHING] >= i)
			return "@whi@";

		return "@bla@";
	}

	private String GetForBars(int i, int j) {
		if (player.getItems().playerHasItem(i, j))
			return "@gre@";

		return "@red@";
	}

}