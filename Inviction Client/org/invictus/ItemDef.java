package org.invictus;
public final class ItemDef {

	public static void nullLoader() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public boolean method192(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.method463(k)) {
			flag = false;
		}
		if (l != -1 && !Model.method463(l)) {
			flag = false;
		}
		return flag;
	}

	public static void unpackConfig(StreamLoader streamLoader) {
		stream = new Stream(streamLoader.getDataForName("obj.dat"));
		Stream stream = new Stream(streamLoader.getDataForName("obj.idx"));
		totalItems = stream.readUnsignedWord() + 21;
		// System.out.println("- 474 Items Amount: " + totalItems);
		streamIndices = new int[totalItems + 50000];
		int i = 2;
		for (int j = 0; j < totalItems - 21; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}

		cache = new ItemDef[10];
		for (int k = 0; k < 10; k++) {
			cache[k] = new ItemDef();
		}

	}

	public Model method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return null;
		}
		Model model = Model.method462(k);
		if (l != -1) {
			Model model_1 = Model.method462(l);
			Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
			model = new Model(2, aclass30_sub2_sub4_sub6s);
		}
		if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++) {
				model.method476(originalModelColors[i1], modifiedModelColors[i1]);
			}

		}
		return model;
	}

	public boolean method195(int j) {
		int k = maleEquip1;
		int l = maleEquip2;
		int i1 = anInt185;
		if (j == 1) {
			k = femaleEquip1;
			l = femaleEquip2;
			i1 = anInt162;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.method463(k)) {
			flag = false;
		}
		if (l != -1 && !Model.method463(l)) {
			flag = false;
		}
		if (i1 != -1 && !Model.method463(i1)) {
			flag = false;
		}
		return flag;
	}

	public Model method196(int i) {
		int j = maleEquip1;
		int k = maleEquip2;
		int l = anInt185;
		if (i == 1) {
			j = femaleEquip1;
			k = femaleEquip2;
			l = anInt162;
		}
		if (j == -1) {
			return null;
		}
		Model model = Model.method462(j);
		if (k != -1) {
			if (l != -1) {
				Model model_1 = Model.method462(k);
				Model model_3 = Model.method462(l);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_1, model_3 };
				model = new Model(3, aclass30_sub2_sub4_sub6_1s);
			} else {
				Model model_2 = Model.method462(k);
				Model aclass30_sub2_sub4_sub6s[] = { model, model_2 };
				model = new Model(2, aclass30_sub2_sub4_sub6s);
			}
		}
		if (i == 0 && aByte205 != 0) {
			model.method475(0, aByte205, 0);
		}
		if (i == 1 && aByte154 != 0) {
			model.method475(0, aByte154, 0);
		}
		if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++) {
				model.method476(originalModelColors[i1], modifiedModelColors[i1]);
			}

		}
		return model;
	}

	public void setDefaults() {
		modelID = 0;
		name = null;
		description = null;
		originalModelColors = null;
		modifiedModelColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		anInt204 = 0;
		modelOffset1 = 0;
		modelOffset2 = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		actions = null;
		maleEquip1 = -1;
		maleEquip2 = -1;
		aByte205 = 0;
		femaleEquip1 = -1;
		femaleEquip2 = -1;
		aByte154 = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
		team = 0;
	}

	public static ItemDef forID(int i) {
		for (int j = 0; j < 10; j++) {
			if (cache[j].id == i) {
				return cache[j];
			}
		}
		cacheIndex = (cacheIndex + 1) % 10;
		ItemDef itemDef = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		itemDef.id = i;
		itemDef.setDefaults();
		itemDef.readValues(stream);
		if (itemDef.certTemplateID != -1) {
			itemDef.toNote();
		}
		if (!isMembers && itemDef.membersObject) {
			itemDef.name = "Members Object";
			itemDef.description = "Login to a members' server to use this object.".getBytes();
			itemDef.groundActions = null;
			itemDef.actions = null;
			itemDef.team = 0;
		}
		switch (i) {

		case 1:
			itemDef.name = "Test Item";
			itemDef.description = "This is a test item.".getBytes();
			itemDef.actions = new String[5];
			itemDef.actions[0] = "First Click Item";
			itemDef.actions[1] = "Equip Item";
			itemDef.actions[2] = "Second Click Item";
			itemDef.actions[3] = "Third Click Item";
			itemDef.actions[4] = "Drop Item";
			itemDef.groundActions = new String[5];
			itemDef.groundActions[0] = "Packet 156, groundAction [0]";
			itemDef.groundActions[1] = "Packet 23, groundAction [1]";
			itemDef.groundActions[2] = "Pickup Item";
			itemDef.groundActions[3] = "Click Ground Item 2";
			itemDef.groundActions[4] = "Packet 79, groundAction [4]";
			break;

		case 1438:
		case 1440:
		case 1442:
		case 1444:
		case 1446:
		case 1448:
		case 1450:
		case 1452:
		case 1454:
		case 1456:
		case 1458:
		case 1460:
		case 1462:
			itemDef.actions[3] = "Teleport";
			break;

		/**
		 * Begin Hardcoded Items
		 * **/

		/**
		 * Elite Void Robes
		 * **/

		case 19785:
			itemDef.maleEquip1 = 60783;
			itemDef.name = "Elite void knight robe";
			itemDef.modelID = 60839;
			itemDef.modelZoom = 1744;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 357;
			itemDef.modelOffset2 = -3;
			itemDef.modelRotation2 = 1865;
			itemDef.femaleEquip1 = 60789;
			break;

		case 19786:
			itemDef.maleEquip1 = 60784;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Elite void knight top";
			itemDef.modelID = 60846;
			itemDef.modelZoom = 976;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 486;
			itemDef.modelRotation2 = 105;
			itemDef.femaleEquip1 = 60790;
			itemDef.aByte205 = 10;
			break;

		/**
		 * Korasi's Sword
		 * **/

		case 19780:
			itemDef.name = "Korasi's sword";
			itemDef.description = "The sword of a Void Knight.".getBytes();
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 57784;
			itemDef.maleEquip1 = 57780;
			itemDef.femaleEquip1 = 57780;
			itemDef.modelZoom = 1400;
			itemDef.modelRotation2 = 700;
			itemDef.modelRotation1 = 60;
			itemDef.modelOffset1 = 25;
			itemDef.modelOffset2 = 60;
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		/**
		 * Dragon Defender
		 * **/

		case 20072:
			itemDef.modelID = 62368;
			itemDef.name = "Dragon defender";
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 323;
			itemDef.modelRotation2 = 1845;
			itemDef.modelOffset1 = -16;
			itemDef.modelOffset2 = -3;
			itemDef.maleEquip1 = 62367;
			itemDef.femaleEquip1 = 62367;
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.actions[4] = "Drop";
			break;

		/**
		 * Slayer Helmets
		 * **/

		case 13263:
			itemDef.name = "Slayer helmet";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 59867;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 69;
			itemDef.modelRotation2 = 1743;
			itemDef.modelOffset1 = -4;
			itemDef.modelOffset2 = -3;
			itemDef.maleEquip1 = 59869;
			itemDef.femaleEquip1 = 59868;
			break;

		case 15492:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 51844;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 69;
			itemDef.modelRotation2 = 1743;
			itemDef.modelOffset1 = -4;
			itemDef.modelOffset2 = -3;
			itemDef.maleEquip1 = 51797;
			itemDef.femaleEquip1 = 51819;
			itemDef.name = "Full Slayer helmet";
			itemDef.description = "Slayer full helm for slayer.".getBytes();
			break;

		/**
		 * Dragon Ornamental
		 * **/

		case 19336:
			itemDef.modelID = 58253;
			itemDef.name = "Dragon full helm (or)";
			itemDef.modelZoom = 789;
			itemDef.modelRotation2 = 135;
			itemDef.modelRotation1 = 123;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 58331;
			itemDef.femaleEquip1 = 58420;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		case 19337:
			itemDef.modelID = 58207;
			itemDef.name = "Dragon platebody (or)";
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 443;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 11;
			itemDef.maleEquip1 = 58363;
			itemDef.femaleEquip1 = 58451;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		case 19338:
			itemDef.modelID = 58198;
			itemDef.name = "Dragon platelegs (or)";
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 444;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -8;
			itemDef.maleEquip1 = 58344;
			itemDef.femaleEquip1 = 58430;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 19339:
			itemDef.modelID = 58225;
			itemDef.name = "Dragon plateskirt (or)";
			itemDef.modelZoom = 1230;
			itemDef.modelRotation1 = 584;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 15;
			itemDef.maleEquip1 = 58338;
			itemDef.femaleEquip1 = 58424;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 19340:
			itemDef.modelID = 58182;
			itemDef.name = "Dragon square shield (or)";
			itemDef.modelZoom = 1730;
			itemDef.modelRotation1 = 352;
			itemDef.modelRotation2 = 120;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 10;
			itemDef.maleEquip1 = 58388;
			itemDef.femaleEquip1 = 58388;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		case 19346:
			itemDef.modelOffset1 = 3;
			itemDef.name = "Dragon full helm ornament kit (or)";
			itemDef.modelID = 58255;
			itemDef.modelZoom = 2134;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1978;
			break;

		case 19348:
			itemDef.modelOffset1 = 3;
			itemDef.name = "Dragon platelegs/skirt ornament kit (or)";
			itemDef.modelID = 58251;
			itemDef.modelZoom = 2134;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1978;
			break;

		case 19350:
			itemDef.modelOffset1 = 3;
			itemDef.name = "Dragon platebody ornament kit (or)";
			itemDef.modelID = 58210;
			itemDef.modelZoom = 2134;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1978;
			break;

		case 19352:
			itemDef.modelOffset1 = 3;
			itemDef.name = "Dragon sq shield ornament kit (or)";
			itemDef.modelID = 58193;
			itemDef.modelZoom = 2134;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1978;
			break;

		/**
		 * Dragon Spiked
		 * **/

		case 19341:
			itemDef.maleEquip1 = 58330;
			itemDef.modelOffset1 = -1;
			itemDef.name = "Dragon full helm (sp)";
			itemDef.modelID = 58184;
			itemDef.anInt175 = 58154;
			itemDef.modelZoom = 789;
			itemDef.actions = (new String[] { null, "Wear", "Split", null, "Drop" });
			itemDef.modelRotation1 = 135;
			itemDef.anInt197 = 58154;
			itemDef.modelRotation2 = 123;
			itemDef.femaleEquip1 = 58417;
			break;

		case 19342:
			itemDef.maleEquip1 = 58355;
			itemDef.modelOffset1 = -1;
			itemDef.name = "Dragon platebody (sp)";
			itemDef.modelID = 58235;
			itemDef.modelZoom = 1360;
			itemDef.actions = (new String[] { null, "Wear", "Split", null, "Drop" });
			itemDef.modelRotation1 = 443;
			itemDef.modelOffset2 = 11;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58448;
			break;

		case 19343:
			itemDef.maleEquip1 = 58347;
			itemDef.name = "Dragon platelegs (sp)";
			itemDef.modelID = 58220;
			itemDef.modelZoom = 1740;
			itemDef.actions = (new String[] { null, "Wear", "Split", null, "Drop" });
			itemDef.modelRotation1 = 444;
			itemDef.modelOffset2 = -8;
			itemDef.femaleEquip1 = 58439;
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 19344:
			itemDef.maleEquip1 = 58349;
			itemDef.modelOffset1 = 7;
			itemDef.name = "Dragon plateskirt (sp)";
			itemDef.modelID = 58186;
			itemDef.modelZoom = 1230;
			itemDef.actions = (new String[] { null, "Wear", "Split", null, "Drop" });
			itemDef.modelRotation1 = 584;
			itemDef.modelOffset2 = 14;
			itemDef.femaleEquip1 = 58434;
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 19345:
			itemDef.maleEquip1 = 58374;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Dragon sq shield (sp)";
			itemDef.modelID = 58173;
			itemDef.modelZoom = 1730;
			itemDef.actions = (new String[] { null, "Wield", "Split", null, "Drop" });
			itemDef.modelRotation1 = 352;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 120;
			itemDef.femaleEquip1 = 58374;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 19354:
			itemDef.modelOffset1 = 4;
			itemDef.name = "Dragon full helm ornament kit (sp)";
			itemDef.modelID = 58264;
			itemDef.modelZoom = 2095;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 124;
			break;

		case 19356:
			itemDef.modelOffset1 = 4;
			itemDef.name = "Dragon platelegs/skirt ornament kit (sp)";
			itemDef.modelID = 58196;
			itemDef.modelZoom = 2095;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 124;
			break;

		case 19358:
			itemDef.modelOffset1 = 4;
			itemDef.name = "Dragon platebody ornament kit (sp)";
			itemDef.modelID = 58190;
			itemDef.modelZoom = 2095;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 124;
			break;

		case 19360:
			itemDef.modelOffset1 = 4;
			itemDef.name = "Dragon sq shield ornament kit (sp)";
			itemDef.modelID = 58239;
			itemDef.modelZoom = 2095;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 124;
			break;

		/**
		 * Fury Ornamental
		 * **/

		case 19335:
			itemDef.maleEquip1 = 58351;
			itemDef.modelOffset1 = 5;
			itemDef.name = "Amulet of fury (or)";
			itemDef.modelID = 58188;
			itemDef.modelZoom = 676;
			itemDef.actions = (new String[] { null, "Wear", "Split", null, "Drop" });
			itemDef.modelRotation1 = 539;
			itemDef.modelOffset2 = 11;
			itemDef.modelRotation2 = 110;
			itemDef.femaleEquip1 = 58441;
			break;

		case 19333:
			itemDef.modelOffset1 = 3;
			itemDef.name = "Fury ornament kit";
			itemDef.modelID = 58283;
			itemDef.modelZoom = 2134;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1978;
			break;

		/**
		 * Hand Cannon
		 * **/

		case 15241:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 48512;
			itemDef.maleEquip1 = 48465;
			itemDef.femaleEquip1 = 48465;
			itemDef.modelZoom = 1579;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 1805;
			itemDef.modelOffset1 = -13;
			itemDef.modelOffset2 = 0;
			itemDef.aByte154 = -7;
			itemDef.aByte205 = -7;
			itemDef.name = "Hand cannon";
			itemDef.description = "A miniature dwarven cannon.".getBytes();
			break;

		case 15243:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 48513;
			itemDef.modelZoom = 1034;
			itemDef.modelRotation1 = 188;
			itemDef.modelRotation2 = 64;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Hand Cannon Shot";
			itemDef.description = "The bag contains shot and powder for the hand cannon.".getBytes();
			break;

		/**
		 * Dagon'Hai Robes
		 * **/

		case 14497:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.name = "Dagon'hai robe top";
			itemDef.modelID = 44594;
			itemDef.modelZoom = 1697;
			itemDef.modelRotation1 = 536;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = 7;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 43614;
			itemDef.femaleEquip1 = 43689;
			itemDef.description = "A robe worn by members of the Dagon'hai.".getBytes();
			break;

		case 14499:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 59875;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 212;
			itemDef.modelRotation2 = 1883;
			itemDef.modelOffset1 = -7;
			itemDef.modelOffset2 = -100;
			itemDef.maleEquip1 = 59874;
			itemDef.femaleEquip1 = 59874;
			itemDef.name = "Dagon'hai hat";
			itemDef.description = "A hat worn by members of the Dagon'hai.".getBytes();
			break;

		case 14501:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 59877;
			itemDef.modelZoom = 2083;
			itemDef.modelRotation1 = 572;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 1;
			itemDef.maleEquip1 = 59876;
			itemDef.femaleEquip1 = 59876;
			itemDef.name = "Dagon'hai Robe bottom";
			itemDef.description = "Robes worn by members of the Dagon'hai.".getBytes();
			break;

		/**
		 * Staff of Light
		 * **/

		case 15486:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 51845;
			itemDef.modelZoom = 2256;
			itemDef.modelRotation1 = 456;
			itemDef.modelRotation2 = 513;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 51795;
			itemDef.femaleEquip1 = 51795;
			itemDef.name = "Staff of light";
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		/**
		 * Dragon Pickaxe
		 * **/

		case 15259:
			itemDef.maleEquip1 = 48470;
			itemDef.femaleEquip1 = 48470;
			itemDef.modelID = 40560;
			itemDef.modelZoom = 1382;
			itemDef.modelRotation2 = 1145;
			itemDef.modelRotation1 = 364;
			itemDef.modelOffset1 = 7;
			itemDef.modelOffset2 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Dragon pickaxe";
			itemDef.description = "Used for mining.".getBytes();
			break;

		/**
		 * Inferno Adze
		 * **/

		case 13661:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 60001;
			itemDef.maleEquip1 = 60000;
			itemDef.femaleEquip1 = 60000;
			itemDef.modelZoom = 1450;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 300;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.name = "Inferno Adze";
			itemDef.description = "Danger: Risk of fire. ".getBytes();
			break;

		/**
		 * Sacred Clay Pickaxe
		 * **/

		case 14107:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 60002;
			itemDef.maleEquip1 = 60003;
			itemDef.femaleEquip1 = 60003;
			itemDef.modelZoom = 1450;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 300;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.name = "Sacred clay pickaxe";
			itemDef.description = "Your sacred clay tool has transformed into a pickaxe. ".getBytes();
			itemDef.aByte205 = 5;
			itemDef.aByte154 = 5;
			break;

		/**
		 * Rocktails
		 **/

		case 15270:
			itemDef.modelID = 62369;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Raw rocktail";
			itemDef.description = "I should cook this.".getBytes();
			break;

		case 15272:
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Eat";
			itemDef.modelID = 62370;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Rocktail";
			itemDef.description = "Some nicely cooked rocktail.".getBytes();
			break;

		case 15274:
			itemDef.modelID = 62371;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Burnt Rocktail";
			itemDef.description = "Oops!".getBytes();
			break;

		case 15263:
			itemDef.modelID = 48726;
			itemDef.modelZoom = 2540;
			itemDef.modelRotation1 = 206;
			itemDef.modelRotation2 = 478;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Living minerals";
			itemDef.description = "These minerals appear to be alive.".getBytes();
			break;

		/**
		 * Chaotic Equipment
		 * **/

		case 18349:
			itemDef.name = "Chaotic rapier";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 59880;
			itemDef.modelZoom = 1425;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 1300;
			itemDef.modelOffset1 = 9;
			itemDef.modelOffset2 = 13;
			itemDef.maleEquip1 = 59881;
			itemDef.femaleEquip1 = 59881;
			itemDef.description = "An incredibly powerful rapier.".getBytes();
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		case 18351:
			itemDef.name = "Chaotic longsword";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 59882;
			itemDef.modelZoom = 1650;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 1300;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 59883;
			itemDef.femaleEquip1 = 59883;
			itemDef.description = "An incredibly powerful longsword.".getBytes();
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		case 18353:
			itemDef.name = "Chaotic maul";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 59884;
			itemDef.modelZoom = 1447;
			itemDef.modelRotation1 = 525;
			itemDef.modelRotation2 = 350;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 59885;
			itemDef.femaleEquip1 = 59885;
			itemDef.description = "An incredibly powerful maul.".getBytes();
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		case 18355:
			itemDef.name = "Chaotic staff";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 59886;
			itemDef.modelZoom = 1711;
			itemDef.modelRotation1 = 350;
			itemDef.modelRotation2 = 365;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 59887;
			itemDef.femaleEquip1 = 59887;
			itemDef.description = "An incredibly powerful staff.".getBytes();
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		case 18357:
			itemDef.name = "Chaotic crossbow";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 59888;
			itemDef.modelZoom = 1028;
			itemDef.modelRotation1 = 249;
			itemDef.modelRotation2 = 2021;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 59889;
			itemDef.femaleEquip1 = 59889;
			itemDef.description = "An incredibly powerful crossbow.".getBytes();
			itemDef.aByte154 = -12;
			itemDef.aByte205 = -12;
			break;

		case 18359:
			itemDef.name = "Chaotic kiteshield";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 59890;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 276;
			itemDef.modelRotation2 = 1101;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 59891;
			itemDef.femaleEquip1 = 59891;
			itemDef.description = "An incredibly strong shield.".getBytes();
			break;

		case 18361:
			itemDef.name = "Eagle-eye kiteshield";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 59892;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 260;
			itemDef.modelRotation2 = 1673;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 59893;
			itemDef.femaleEquip1 = 59893;
			itemDef.description = "An incredibly strong shield.".getBytes();
			break;

		case 18363:
			itemDef.name = "Farseer kiteshield";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 54313;
			itemDef.maleEquip1 = 55947;
			itemDef.femaleEquip1 = 55947;
			itemDef.modelZoom = 1670;
			itemDef.modelRotation1 = 316;
			itemDef.modelRotation2 = 64;
			itemDef.modelOffset2 = -1;
			itemDef.modelOffset1 = 14;
			itemDef.description = "An incredibly strong shield.".getBytes();
			break;

		/**
		 * Primal Equipment
		 * **/

		case 17361:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 55991;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 2434;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 458;
			itemDef.modelOffset1 = -3;
			itemDef.maleEquip1 = 55991;
			itemDef.modelID = 54253;
			itemDef.name = "Primal kiteshield";
			itemDef.description = "Its a Primal kiteshield".getBytes();
			break;

		case 17259:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56515;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 1447;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 485;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 55851;
			itemDef.modelID = 54126;
			itemDef.name = "Primal platebody";
			itemDef.description = "Its a Primal platebody".getBytes();
			break;

		case 16909:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56046;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 1776;
			itemDef.modelRotation2 = 1400;
			itemDef.modelRotation1 = 350;
			itemDef.modelOffset1 = -1;
			itemDef.maleEquip1 = 56046;
			itemDef.modelID = 54373;
			itemDef.name = "Primal 2h sword";
			itemDef.description = "Its a Primal 2h sword".getBytes();
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 16711:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56434;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 921;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 121;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 55770;
			itemDef.modelID = 54164;
			itemDef.name = "Primal full helm";
			itemDef.description = "Its a Primal full helm".getBytes();
			break;

		case 16689:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56478;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 1730;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 525;
			itemDef.modelOffset1 = 7;
			itemDef.maleEquip1 = 55815;
			itemDef.modelID = 54175;
			itemDef.name = "Primal platelegs";
			itemDef.description = "It are Primal platelegs".getBytes();
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 16667:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56460;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 1711;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 488;
			itemDef.modelOffset1 = -1;
			itemDef.maleEquip1 = 55804;
			itemDef.modelID = 54068;
			itemDef.name = "Primal plateskirt";
			itemDef.description = "A Primal plateskirt".getBytes();
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 16359:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56353;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 789;
			itemDef.modelRotation2 = 156;
			itemDef.modelRotation1 = 164;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 55673;
			itemDef.modelID = 54062;
			itemDef.name = "Primal boots";
			itemDef.description = "A pair of Primal boots".getBytes();
			break;

		case 16293:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 56417;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 930;
			itemDef.modelRotation2 = 828;
			itemDef.modelRotation1 = 420;
			itemDef.modelOffset1 = 3;
			itemDef.maleEquip1 = 55728;
			itemDef.modelID = 54037;
			itemDef.name = "Primal gauntlets";
			itemDef.description = "A pair of Primal gauntlets".getBytes();
			break;

		/**
		 * Trickster Armour
		 * **/

		case 21467:
			itemDef.name = "Trickster helm";
			itemDef.description = "Its a Trickster helm".getBytes();
			itemDef.maleEquip1 = 44764;
			itemDef.femaleEquip1 = 45220;
			itemDef.modelID = 45328;
			itemDef.modelRotation1 = 5;
			itemDef.modelRotation2 = 1889;
			itemDef.modelZoom = 738;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		case 21468:
			itemDef.name = "Trickster robe";
			itemDef.description = "Its a Trickster robe".getBytes();
			itemDef.maleEquip1 = 44786;
			itemDef.femaleEquip1 = 45238;
			itemDef.modelID = 45329;
			itemDef.modelRotation1 = 593;
			itemDef.modelRotation2 = 2041;
			itemDef.modelZoom = 1420;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		case 21469:
			itemDef.name = "Trickster robe legs";
			itemDef.description = "Its a Trickster robe".getBytes();
			itemDef.maleEquip1 = 44770;
			itemDef.femaleEquip1 = 45235;
			itemDef.modelID = 45335;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 1023;
			itemDef.modelZoom = 2105;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.aByte154 = -4;
			break;

		case 21470:
			itemDef.modelID = 45317;
			itemDef.name = "Trickster gloves";
			itemDef.modelZoom = 830;
			itemDef.modelRotation2 = 150;
			itemDef.modelRotation1 = 536;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 3;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44761;
			itemDef.femaleEquip1 = 45210;
			break;

		case 21471:
			itemDef.modelID = 45316;
			itemDef.name = "Trickster boots";
			itemDef.modelZoom = 848;
			itemDef.modelRotation2 = 141;
			itemDef.modelRotation1 = 141;
			itemDef.modelOffset1 = -9;
			itemDef.modelOffset2 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44757;
			itemDef.femaleEquip1 = 44850;
			itemDef.aByte154 = -5;
			break;

		/**
		 * Vanguard Armour
		 * **/

		case 21472:
			itemDef.modelID = 44634;
			itemDef.name = "Vanguard helm";
			itemDef.modelZoom = 855;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 360;
			itemDef.modelOffset2 = 4;
			itemDef.modelOffset1 = -1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44769;
			itemDef.femaleEquip1 = 45223;
			break;

		case 21473:
			itemDef.modelID = 44678;
			itemDef.name = "Vanguard body";
			itemDef.modelZoom = 1513;
			itemDef.modelRotation2 = 2041;
			itemDef.modelRotation1 = 593;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -11;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44812;
			itemDef.femaleEquip1 = 45241;
			break;

		case 21474:
			itemDef.modelID = 44658;
			itemDef.name = "Vanguard legs";
			itemDef.modelZoom = 1711;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 360;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -11;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44771;
			itemDef.femaleEquip1 = 45232;
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 21475:
			itemDef.modelID = 44699;
			itemDef.name = "Vanguard gloves";
			itemDef.modelZoom = 830;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 536;
			itemDef.modelOffset1 = 9;
			itemDef.modelOffset2 = 3;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44758;
			itemDef.femaleEquip1 = 45205;
			itemDef.aByte205 = -2;
			itemDef.aByte154 = -4;
			break;

		case 21476:
			itemDef.modelID = 44700;
			itemDef.name = "Vanguard boots";
			itemDef.modelZoom = 848;
			itemDef.modelRotation2 = 141;
			itemDef.modelRotation1 = 141;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44752;
			itemDef.femaleEquip1 = 44890;
			break;

		/**
		 * Battle-mage Armour
		 * **/

		case 21462:
			itemDef.modelID = 44704;
			itemDef.name = "Battle-mage helm";
			itemDef.modelZoom = 658;
			itemDef.modelRotation2 = 1898;
			itemDef.modelRotation1 = 2;
			itemDef.modelOffset1 = 12;
			itemDef.modelOffset2 = 3;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44767;
			itemDef.femaleEquip1 = 45219;
			break;

		case 21463:
			itemDef.modelID = 44644;
			itemDef.name = "Battle-mage robe";
			itemDef.modelZoom = 1382;
			itemDef.modelRotation2 = 3;
			itemDef.modelRotation1 = 488;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44818;
			itemDef.femaleEquip1 = 45279;
			break;

		case 21464:
			itemDef.modelID = 44672;
			itemDef.name = "Battle-mage robe legs";
			itemDef.modelZoom = 1842;
			itemDef.modelRotation2 = 1024;
			itemDef.modelRotation1 = 498;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44775;
			itemDef.femaleEquip1 = 45234;
			itemDef.aByte205 = -3;
			itemDef.aByte154 = -3;
			break;

		case 21465:
			itemDef.modelID = 44573;
			itemDef.name = "Battle-mage gloves";
			itemDef.modelZoom = 1053;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 536;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44762;
			itemDef.femaleEquip1 = 45208;
			itemDef.aByte205 = 7;
			itemDef.aByte154 = -5;
			break;

		case 21466:
			itemDef.modelID = 44662;
			itemDef.name = "Battle-mage boots";
			itemDef.modelZoom = 987;
			itemDef.modelRotation2 = 1988;
			itemDef.modelRotation1 = 188;
			itemDef.modelOffset1 = -8;
			itemDef.modelOffset2 = 5;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44755;
			itemDef.femaleEquip1 = 45204;
			itemDef.aByte154 = -3;
			break;

		/**
		 * Ancient Prison Key
		 * **/

		case 20120:
			itemDef.modelID = 57037;
			itemDef.name = "Frozen key";
			itemDef.modelZoom = 1184;
			itemDef.modelRotation1 = 384;
			itemDef.modelRotation2 = 162;
			itemDef.modelOffset1 = -8;
			itemDef.modelOffset2 = -14;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			break;

		/**
		 * Nex Equipment
		 * **/

		case 20135:
			itemDef.modelID = 62714;
			itemDef.name = "Torva full helm";
			itemDef.description = "Torva full helm".getBytes();
			itemDef.modelZoom = 672;
			itemDef.modelRotation1 = 85;
			itemDef.modelRotation2 = 1867;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -3;
			itemDef.maleEquip1 = 62738;
			itemDef.femaleEquip1 = 62754;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.anInt175 = 62729;
			itemDef.anInt197 = 62729;
			break;

		case 20139:
			itemDef.modelID = 62699;
			itemDef.name = "Torva platebody";
			itemDef.description = "Torva platebody".getBytes();
			itemDef.modelZoom = 1506;
			itemDef.modelRotation1 = 473;
			itemDef.modelRotation2 = 2042;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 62746;
			itemDef.femaleEquip1 = 62762;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 20143:
			itemDef.modelID = 62701;
			itemDef.name = "Torva platelegs";
			itemDef.description = "Torva platelegs".getBytes();
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 474;
			itemDef.modelRotation2 = 2045;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.maleEquip1 = 62743;
			itemDef.femaleEquip1 = 62760;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 20147:
			itemDef.modelID = 62693;
			itemDef.name = "Pernix cowl";
			itemDef.description = "Pernix cowl".getBytes();
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 532;
			itemDef.modelRotation2 = 14;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.maleEquip1 = 62739;
			itemDef.femaleEquip1 = 62756;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.anInt175 = 62731;
			itemDef.anInt197 = 62727;
			break;

		case 20151:
			itemDef.modelID = 62709;
			itemDef.name = "Pernix body";
			itemDef.description = "Pernix body".getBytes();
			itemDef.modelZoom = 1378;
			itemDef.modelRotation1 = 485;
			itemDef.modelRotation2 = 2042;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.maleEquip1 = 62744;
			itemDef.femaleEquip1 = 62765;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 20155:
			itemDef.modelID = 62695;
			itemDef.name = "Pernix chaps";
			itemDef.description = "Pernix chaps".getBytes();
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 504;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 3;
			itemDef.maleEquip1 = 62741;
			itemDef.femaleEquip1 = 62757;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 20159:
			itemDef.modelID = 62710;
			itemDef.name = "Virtus mask";
			itemDef.description = "Virtus mask".getBytes();
			itemDef.modelZoom = 928;
			itemDef.modelRotation1 = 406;
			itemDef.modelRotation2 = 2041;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -5;
			itemDef.maleEquip1 = 62736;
			itemDef.femaleEquip1 = 62755;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.anInt175 = 62728;
			itemDef.anInt197 = 62728;
			break;

		case 20163:
			itemDef.modelID = 62704;
			itemDef.name = "Virtus robe top";
			itemDef.description = "Virtus robe top".getBytes();
			itemDef.modelZoom = 1122;
			itemDef.modelRotation1 = 488;
			itemDef.modelRotation2 = 3;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 62748;
			itemDef.femaleEquip1 = 62764;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 20167:
			itemDef.modelID = 62700;
			itemDef.name = "Virtus robe legs";
			itemDef.description = "Virtus robe legs".getBytes();
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 2045;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 4;
			itemDef.maleEquip1 = 62742;
			itemDef.femaleEquip1 = 62758;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 20171:
			itemDef.modelID = 62692;
			itemDef.name = "Zaryte bow";
			itemDef.description = "Zaryte bow".getBytes();
			itemDef.modelZoom = 1703;
			itemDef.modelRotation1 = 221;
			itemDef.modelRotation2 = 404;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -13;
			itemDef.maleEquip1 = 62750;
			itemDef.femaleEquip1 = 62750;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		/**
		 * Ancient Ceremonial
		 * **/

		case 20115:
			itemDef.modelID = 62694;
			itemDef.name = "Ancient ceremonial mask";
			itemDef.modelZoom = 980;
			itemDef.modelRotation1 = 208;
			itemDef.modelRotation2 = 220;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -18;
			itemDef.maleEquip1 = 62737;
			itemDef.femaleEquip1 = 62753;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.anInt175 = 62730;
			itemDef.anInt197 = 62730;
			break;

		case 20116:
			itemDef.modelID = 62705;
			itemDef.name = "Ancient ceremonial top";
			itemDef.modelZoom = 1316;
			itemDef.modelRotation1 = 477;
			itemDef.modelRotation2 = 9;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 13;
			itemDef.maleEquip1 = 62745;
			itemDef.femaleEquip1 = 62763;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 20117:
			itemDef.modelID = 62707;
			itemDef.name = "Ancient ceremonial legs";
			itemDef.modelZoom = 1828;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 0;
			itemDef.maleEquip1 = 62740;
			itemDef.femaleEquip1 = 62759;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 20118:
			itemDef.modelID = 62697;
			itemDef.name = "Ancient ceremonial gloves";
			itemDef.modelZoom = 548;
			itemDef.modelRotation1 = 618;
			itemDef.modelRotation2 = 1143;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.maleEquip1 = 62735;
			itemDef.femaleEquip1 = 62752;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 20119:
			itemDef.modelID = 62696;
			itemDef.name = "Ancient ceremonial boots";
			itemDef.modelZoom = 676;
			itemDef.modelRotation1 = 63;
			itemDef.modelRotation2 = 106;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.maleEquip1 = 62734;
			itemDef.femaleEquip1 = 62751;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		/**
		 * Glacor Boots
		 * **/

		case 21787:
			itemDef.modelID = 53835;
			itemDef.name = "Steadfast boots";
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 165;
			itemDef.modelRotation2 = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -7;
			itemDef.maleEquip1 = 53327;
			itemDef.femaleEquip1 = 53643;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			break;

		case 21790:
			itemDef.modelID = 53828;
			itemDef.name = "Glaiven boots";
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 165;
			itemDef.modelRotation2 = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -7;
			itemDef.maleEquip1 = 53309;
			itemDef.femaleEquip1 = 53631;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			break;

		case 21793:
			itemDef.modelID = 53897;
			itemDef.name = "Ragefire boots";
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 165;
			itemDef.modelRotation2 = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -7;
			itemDef.maleEquip1 = 53330;
			itemDef.femaleEquip1 = 53651;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			break;

		/**
		 * Max Cape
		 * **/

		case 20767:
			itemDef.modelID = 65262;
			itemDef.name = "Max cape";
			itemDef.description = "A cape worn by those who have at least 200 million experience in all skills.".getBytes();
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleEquip1 = 65300;
			itemDef.femaleEquip1 = 65322;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.aByte154 = -3;
			itemDef.aByte205 = -3;
			break;

		case 20768:
			itemDef.modelID = 65268;
			itemDef.name = "Max hood";
			itemDef.description = "A hood worn by those who have at least 200 million experience in all skills.".getBytes();
			itemDef.modelZoom = 760;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 90;
			itemDef.maleEquip1 = 65291;
			itemDef.femaleEquip1 = 65313;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		/**
		 * Veteran Cape
		 * **/

		case 20763:
			itemDef.modelID = 65261;
			itemDef.name = "Veteran cape";
			itemDef.description = "A cape worn by those who've spent a very long time on PkHonor.".getBytes();
			itemDef.modelZoom = 1513;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleEquip1 = 65305;
			itemDef.femaleEquip1 = 65318;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.aByte154 = -3;
			itemDef.aByte205 = -3;
			break;

		case 20764:
			itemDef.modelID = 65271;
			itemDef.name = "Veteran hood";
			itemDef.description = "A hood worn by veterans.".getBytes();
			itemDef.modelZoom = 760;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 90;
			itemDef.maleEquip1 = 65289;
			itemDef.femaleEquip1 = 65314;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

		/**
		 * Completionist Cape
		 * **/

		case 20769:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description = "A cape worn by those who've over achieved. ".getBytes();
			itemDef.modelZoom = 1316;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 24;
			itemDef.modelRotation1 = 252;
			itemDef.modelRotation2 = 1020;
			itemDef.maleEquip1 = 65297;
			itemDef.femaleEquip1 = 65316;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Customise";
			itemDef.actions[3] = "Features";
			itemDef.actions[4] = "Destroy";
			itemDef.aByte154 = -3;
			itemDef.aByte205 = -3;
			break;

		case 20770:
			itemDef.modelID = 65273;
			itemDef.name = "Completionist hood";
			itemDef.description = "A hood worn by those who've over achieved.".getBytes();
			itemDef.modelZoom = 760;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 90;
			itemDef.maleEquip1 = 65292;
			itemDef.femaleEquip1 = 65310;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Destroy";
			break;

		case 20771:
			itemDef.modelID = 65258;
			itemDef.name = "Completionist cape (t)";
			itemDef.description = "We'd pat you on the back, but this cape would get in the way.".getBytes();
			itemDef.modelZoom = 1316;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 24;
			itemDef.modelRotation1 = 252;
			itemDef.modelRotation2 = 1020;
			itemDef.maleEquip1 = 65295;
			itemDef.femaleEquip1 = 65328;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Customise";
			itemDef.actions[3] = "Features";
			itemDef.actions[4] = "Destroy";
			itemDef.aByte154 = -3;
			itemDef.aByte205 = -3;
			break;

		case 20772:
			itemDef.modelID = 65269;
			itemDef.name = "Completionist hood (t)";
			itemDef.description = "A hood worn by those who've over achieved.".getBytes();
			itemDef.modelZoom = 760;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 90;
			itemDef.maleEquip1 = 65288;
			itemDef.femaleEquip1 = 65312;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Destroy";
			break;

		/**
		 * Arcane Necklaces
		 * **/

		case 18333:
			itemDef.maleEquip1 = 55821;
			itemDef.modelOffset1 = 8;
			itemDef.name = "Arcane pulse necklace";
			itemDef.modelID = 56784;
			itemDef.modelZoom = 848;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 372;
			itemDef.modelOffset2 = -32;
			itemDef.modelRotation2 = 633;
			itemDef.femaleEquip1 = 56504;
			break;

		case 18334:
			itemDef.maleEquip1 = 55826;
			itemDef.modelOffset1 = -9;
			itemDef.name = "Arcane blast necklace";
			itemDef.modelID = 56782;
			itemDef.modelZoom = 848;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 348;
			itemDef.modelOffset2 = -24;
			itemDef.modelRotation2 = 1841;
			itemDef.femaleEquip1 = 56508;
			break;

		case 18335:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.femaleEquip1 = 56505;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -12;
			itemDef.modelZoom = 976;
			itemDef.modelRotation2 = 51;
			itemDef.modelRotation1 = 510;
			itemDef.maleEquip1 = 55825;
			itemDef.modelID = 56779;
			itemDef.name = "Arcane stream necklace";
			itemDef.description = "The energy from this necklace is unlike anything you have ever felt.".getBytes();
			break;

		/**
		 * Vesta's Equipment
		 * **/

		case 13887:
			itemDef.name = "Vesta's chainbody";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42593;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 545;
			itemDef.modelRotation2 = 2;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 4;
			itemDef.maleEquip1 = 42624;
			itemDef.femaleEquip1 = 42644;
			itemDef.description = "An ancient chainbody once worn by Vesta.".getBytes();
			break;

		case 13893:
			itemDef.name = "Vesta's plateskirt";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42581;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset2 = 11;
			itemDef.modelOffset1 = -3;
			itemDef.maleEquip1 = 42633;
			itemDef.femaleEquip1 = 42649;
			itemDef.description = "An ancient plateskirt once worn by Vesta.".getBytes();
			break;

		case 13899:
			itemDef.name = "Vesta's longsword";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 42597;
			itemDef.modelZoom = 1744;
			itemDef.modelRotation1 = 738;
			itemDef.modelRotation2 = 1985;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 42615;
			itemDef.femaleEquip1 = 42615;
			itemDef.description = "An ancient longsword once worn by Vesta.".getBytes();
			break;

		case 13905:
			itemDef.name = "Vesta's spear";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 42599;
			itemDef.modelZoom = 2022;
			itemDef.modelRotation1 = 480;
			itemDef.modelRotation2 = 15;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 42614;
			itemDef.femaleEquip1 = 42614;
			itemDef.description = "An ancient spear once worn by Vesta.".getBytes();
			break;

		/**
		 * Zuriel's Equipment
		 * **/

		case 13858:
			itemDef.name = "Zuriel's robe top";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42591;
			itemDef.modelZoom = 1373;
			itemDef.modelRotation1 = 373;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = -7;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 42627;
			itemDef.femaleEquip1 = 42642;
			itemDef.aByte205 = 5;
			itemDef.description = "An ancient robe once worn by Zuriel.".getBytes();
			break;

		case 13861:
			itemDef.name = "Zuriel's robe bottom";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42588;
			itemDef.modelZoom = 1697;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = -9;
			itemDef.modelOffset1 = 2;
			itemDef.maleEquip1 = 42634;
			itemDef.femaleEquip1 = 42645;
			itemDef.description = "An ancient robe once worn by Zuriel.".getBytes();
			break;

		case 13867:
			itemDef.name = "Zuriel's staff";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 42595;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 366;
			itemDef.modelRotation2 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 42617;
			itemDef.femaleEquip1 = 42617;
			itemDef.description = "An ancient staff once worn by Zuriel.".getBytes();
			break;

		case 13864:
			itemDef.name = "Zuriel's hood";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42604;
			itemDef.modelZoom = 720;
			itemDef.modelRotation1 = 28;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = 1;
			itemDef.modelOffset1 = 1;
			itemDef.maleEquip1 = 42638;
			itemDef.femaleEquip1 = 42653;
			itemDef.description = "An ancient hood once worn by Zuriel.".getBytes();
			break;

		/**
		 * Morrigan's Equipment
		 * **/

		case 13870:
			itemDef.name = "Morrigan's leather body";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42578;
			itemDef.modelZoom = 1184;
			itemDef.modelRotation1 = 545;
			itemDef.modelRotation2 = 2;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 4;
			itemDef.maleEquip1 = 42626;
			itemDef.femaleEquip1 = 42643;
			itemDef.description = "An ancient leather body once used by Morrigan.".getBytes();
			break;

		case 13873:
			itemDef.name = "Morrigan's leather chaps";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42603;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 482;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset2 = 11;
			itemDef.modelOffset1 = -3;
			itemDef.maleEquip1 = 42631;
			itemDef.femaleEquip1 = 42646;
			itemDef.description = "A pair of ancient leather chaps once used by Morrigan.".getBytes();
			break;

		case 13876:
			itemDef.name = "Morrigan's coif";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42583;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 537;
			itemDef.modelRotation2 = 5;
			itemDef.modelOffset2 = 6;
			itemDef.modelOffset1 = -3;
			itemDef.maleEquip1 = 42636;
			itemDef.femaleEquip1 = 42652;
			itemDef.description = "An ancient coif once used by Morrigan.".getBytes();
			break;

		case 13879:
			itemDef.name = "Morrigan's javelin";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 42592;
			itemDef.modelZoom = 1872;
			itemDef.modelRotation1 = 282;
			itemDef.modelRotation2 = 2009;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 42613;
			itemDef.femaleEquip1 = 42613;
			itemDef.description = "An ancient javelin once used by Morrigan.".getBytes();
			break;

		case 13883:
			itemDef.name = "Morrigan's throwing axe";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 42582;
			itemDef.modelZoom = 976;
			itemDef.modelRotation1 = 672;
			itemDef.modelRotation2 = 2024;
			itemDef.modelOffset2 = 4;
			itemDef.modelOffset1 = -5;
			itemDef.maleEquip1 = 42611;
			itemDef.femaleEquip1 = 42611;
			itemDef.description = "An ancient throwing axe once used by Morrigan.".getBytes();
			break;

		/**
		 * Statius's Equipment
		 * **/

		case 13884:
			itemDef.name = "Statius's platebody";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42602;
			itemDef.modelZoom = 1312;
			itemDef.modelRotation1 = 272;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset2 = 39;
			itemDef.modelOffset1 = -2;
			itemDef.maleEquip1 = 42625;
			itemDef.femaleEquip1 = 42641;
			itemDef.description = "An ancient platebody once worn by Statius.".getBytes();
			break;

		case 13890:
			itemDef.name = "Statius's platelegs";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42590;
			itemDef.modelZoom = 1625;
			itemDef.modelRotation1 = 355;
			itemDef.modelRotation2 = 2046;
			itemDef.modelOffset2 = -11;
			itemDef.modelOffset1 = 0;
			itemDef.maleEquip1 = 42632;
			itemDef.femaleEquip1 = 42647;
			itemDef.description = "An ancient platelegs once worn by Statius.".getBytes();
			break;

		case 13896:
			itemDef.name = "Statius's full helm";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42596;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 96;
			itemDef.modelRotation2 = 2039;
			itemDef.modelOffset2 = -7;
			itemDef.modelOffset1 = 2;
			itemDef.maleEquip1 = 42639;
			itemDef.femaleEquip1 = 42655;
			itemDef.description = "An ancient full helm once worn by Statius.".getBytes();
			break;

		case 13902:
			itemDef.name = "Statius's warhammer";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 42577;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 507;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset2 = 6;
			itemDef.modelOffset1 = 7;
			itemDef.maleEquip1 = 42623;
			itemDef.femaleEquip1 = 42623;
			itemDef.description = "An ancient warhammer once used by Statius.".getBytes();
			break;

		/**
		 * Dragon Claws
		 * **/

		case 14484:
			itemDef.name = "Dragon claws";
			break;

		/**
		 * Dragon Platebody
		 * **/

		case 14472:
			itemDef.name = "Ruined dragon armour lump";
			break;

		case 14474:
			itemDef.name = "Ruined dragon armour slice";
			break;

		case 14476:
			itemDef.name = "Ruined dragon armour shard";
			break;

		case 14479:
			itemDef.name = "Dragon platebody";
			break;

		/**
		 * Spirit Shields
		 * **/

		case 731:
			itemDef.name = "Holy elixir";
			itemDef.description = "I can use this on a Spirit Shield to bless it.".getBytes();
			break;

		case 13746:
			itemDef.name = "Arcane Sigil";
			break;

		case 13748:
			itemDef.name = "Divine Sigil";
			break;

		case 13750:
			itemDef.name = "Elysian Sigil";
			break;

		case 13752:
			itemDef.name = "Spectral Sigil";
			break;

		case 13742:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 40915;
			itemDef.maleEquip1 = 40942;// anInt165
			itemDef.femaleEquip1 = 40942;// anInt200
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 9;
			itemDef.name = "Elysian spirit shield";
			itemDef.description = "An ethereal shield with an elysian sigil attached to it.".getBytes();
			break;

		case 13740:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 40921;
			itemDef.maleEquip1 = 40939;// anInt165
			itemDef.femaleEquip1 = 40939;// anInt200
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 9;
			itemDef.name = "Divine spirit shield";
			itemDef.description = "An ethereal shield with a divine sigil attached to it.".getBytes();
			break;

		case 13738:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 40922;
			itemDef.maleEquip1 = 40944;// anInt165
			itemDef.femaleEquip1 = 40944;// anInt200
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 9;
			itemDef.name = "Arcane spirit shield";
			itemDef.description = "An ethereal shield with an arcane sigil attached to it.".getBytes();
			break;

		case 13744:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 40920;
			itemDef.maleEquip1 = 40940;// anInt165
			itemDef.femaleEquip1 = 40940;// anInt200
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 9;
			itemDef.name = "Spectral spirit shield";
			itemDef.description = "An ethereal shield with a spectral sigil attached to it.".getBytes();
			break;

		case 13736:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 40913;
			itemDef.maleEquip1 = 40941;// anInt165
			itemDef.femaleEquip1 = 40941;// anInt200
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 9;
			itemDef.name = "Blessed spirit shield";
			itemDef.description = "An ethereal shield blessed with holy powers.".getBytes();
			break;

		case 13734:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 40919;
			itemDef.maleEquip1 = 40943;// anInt165
			itemDef.femaleEquip1 = 40943;// anInt200
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 9;
			itemDef.name = "Spirit shield";
			itemDef.description = "An ethereal shield.".getBytes();
			break;

		/**
		 * Top Hat
		 * **/

		case 13101:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 43004;
			itemDef.modelZoom = 1100;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 500;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.maleEquip1 = 43002;
			itemDef.femaleEquip1 = 43002;
			itemDef.anInt175 = 100;
			itemDef.anInt197 = 100;
			itemDef.name = "Top Hat";
			break;

		/**
		 * Canes
		 * **/

		case 13095:
			itemDef.maleEquip1 = 33352;
			itemDef.modelOffset1 = -1;
			itemDef.name = "Black cane";
			itemDef.modelID = 33311;
			itemDef.modelZoom = 1242;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 296;
			itemDef.modelOffset2 = 8;
			itemDef.modelRotation2 = 1814;
			itemDef.femaleEquip1 = 33352;
			itemDef.aByte205 = 3;
			itemDef.aByte154 = -4;
			break;

		/**
		 * Pith Helmet
		 * **/

		case 13103:
			itemDef.maleEquip1 = 33337;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Pith helmet";
			itemDef.modelID = 33318;
			itemDef.anInt175 = 39421;
			itemDef.modelZoom = 789;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 144;
			itemDef.modelOffset2 = -11;
			itemDef.anInt197 = 38747;
			itemDef.modelRotation2 = 1916;
			itemDef.femaleEquip1 = 33347;
			break;

		/**
		 * Spiked Helmet
		 * **/

		case 13105:
			itemDef.maleEquip1 = 33338;
			itemDef.modelOffset1 = -4;
			itemDef.name = "Spiked helmet";
			itemDef.modelID = 33310;
			itemDef.anInt175 = 33319;
			itemDef.modelZoom = 994;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 47;
			itemDef.modelOffset2 = -5;
			itemDef.anInt197 = 33327;
			itemDef.modelRotation2 = 1632;
			itemDef.femaleEquip1 = 33343;
			break;

		/**
		 * Animal Masks
		 * **/

		case 13107:
			itemDef.maleEquip1 = 33340;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Sheep mask";
			itemDef.modelID = 33308;
			itemDef.anInt175 = 33326;
			itemDef.modelZoom = 738;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 29;
			itemDef.modelOffset2 = 1;
			itemDef.anInt197 = 33331;
			itemDef.modelRotation2 = 1836;
			itemDef.femaleEquip1 = 33349;

			break;

		case 13109:
			itemDef.maleEquip1 = 33335;
			itemDef.modelOffset1 = -6;
			itemDef.name = "Penguin mask";
			itemDef.modelID = 33313;
			itemDef.anInt175 = 39476;
			itemDef.modelZoom = 738;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 71;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 39014;
			itemDef.modelRotation2 = 1857;
			itemDef.femaleEquip1 = 33346;
			break;

		case 13111:
			itemDef.maleEquip1 = 33339;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Bat mask";
			itemDef.modelID = 33316;
			itemDef.anInt175 = 39374;
			itemDef.modelZoom = 1178;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 83;
			itemDef.modelOffset2 = 1;
			itemDef.anInt197 = 38794;
			itemDef.modelRotation2 = 1830;
			itemDef.femaleEquip1 = 33344;
			break;

		case 13113:
			itemDef.maleEquip1 = 33342;
			itemDef.modelOffset1 = -5;
			itemDef.name = "Cat mask";
			itemDef.modelID = 33312;
			itemDef.anInt175 = 39693;
			itemDef.modelZoom = 595;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 108;
			itemDef.modelOffset2 = -3;
			itemDef.anInt197 = 39073;
			itemDef.modelRotation2 = 1808;
			itemDef.femaleEquip1 = 33348;

			break;

		case 19272:
			itemDef.maleEquip1 = 58328;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Fox mask";
			itemDef.modelID = 58242;
			itemDef.anInt175 = 58162;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58144;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58412;

			break;

		case 19275:
			itemDef.maleEquip1 = 58324;
			itemDef.modelOffset1 = -9;
			itemDef.name = "White unicorn mask";
			itemDef.modelID = 58224;
			itemDef.anInt175 = 58166;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 387;
			itemDef.modelOffset2 = -5;
			itemDef.anInt197 = 58148;
			itemDef.modelRotation2 = 1978;
			itemDef.femaleEquip1 = 58411;

			break;

		case 19278:
			itemDef.maleEquip1 = 58325;
			itemDef.modelOffset1 = -9;
			itemDef.name = "Black unicorn mask";
			itemDef.modelID = 58249;
			itemDef.anInt175 = 58161;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 387;
			itemDef.modelOffset2 = -5;
			itemDef.anInt197 = 58138;
			itemDef.modelRotation2 = 1978;
			itemDef.femaleEquip1 = 58409;

			break;

		case 19281:
			itemDef.maleEquip1 = 58318;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Green dragon mask";
			itemDef.modelID = 58277;
			itemDef.anInt175 = 58155;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58143;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58415;

			break;

		case 19284:
			itemDef.maleEquip1 = 58329;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Blue dragon mask";
			itemDef.modelID = 58197;
			itemDef.anInt175 = 58159;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58137;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58414;

			break;

		case 19287:
			itemDef.maleEquip1 = 58326;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Red dragon mask";
			itemDef.modelID = 58174;
			itemDef.anInt175 = 58163;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58135;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58419;

			break;

		case 19290:
			itemDef.maleEquip1 = 58321;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Black dragon mask";
			itemDef.modelID = 58206;
			itemDef.anInt175 = 58164;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58136;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58413;

			break;

		case 19293:
			itemDef.maleEquip1 = 58320;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Frost dragon mask";
			itemDef.modelID = 58234;
			itemDef.anInt175 = 58160;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58145;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58422;

			break;

		case 19296:
			itemDef.maleEquip1 = 58323;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Bronze dragon mask";
			itemDef.modelID = 58175;
			itemDef.anInt175 = 58167;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58141;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58421;

			break;

		case 19299:
			itemDef.maleEquip1 = 58319;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Iron dragon mask";
			itemDef.modelID = 58258;
			itemDef.anInt175 = 58158;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58139;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58416;

			break;

		case 19302:
			itemDef.maleEquip1 = 58332;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Steel dragon mask";
			itemDef.modelID = 58284;
			itemDef.anInt175 = 58156;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58146;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58410;

			break;

		case 19305:
			itemDef.maleEquip1 = 58327;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Mithril dragon mask";
			itemDef.modelID = 58266;
			itemDef.anInt175 = 58157;
			itemDef.modelZoom = 811;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.anInt197 = 58140;
			itemDef.modelRotation2 = 221;
			itemDef.femaleEquip1 = 58418;

			break;

		/**
		 * Animal Staves
		 * **/

		case 19323:
			itemDef.maleEquip1 = 58376;
			itemDef.name = "Dragon staff";
			itemDef.modelID = 58270;
			itemDef.modelZoom = 1622;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 622;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58376;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 19325:
			itemDef.maleEquip1 = 58375;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Penguin staff";
			itemDef.modelID = 58200;
			itemDef.modelZoom = 1622;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 622;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58375;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 19327:
			itemDef.maleEquip1 = 58384;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Bat staff";
			itemDef.modelID = 58223;
			itemDef.modelZoom = 1824;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 622;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58384;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 19329:
			itemDef.maleEquip1 = 58377;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Wolf staff";
			itemDef.modelID = 58288;
			itemDef.modelZoom = 1824;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 622;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58377;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 19331:
			itemDef.maleEquip1 = 58383;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Cat staff";
			itemDef.modelID = 58279;
			itemDef.modelZoom = 1757;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 622;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58383;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		/**
		 * Third-age Druidic
		 * **/

		case 19308:
			itemDef.maleEquip1 = 58381;
			itemDef.modelOffset1 = 4;
			itemDef.name = "Third-age druidic staff";
			itemDef.modelID = 58272;
			itemDef.modelZoom = 1892;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 500;
			itemDef.modelOffset2 = 3;
			itemDef.modelRotation2 = 2019;
			itemDef.femaleEquip1 = 58381;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 19311:
			itemDef.maleEquip1 = 58370;
			itemDef.modelOffset1 = 8;
			itemDef.name = "Third-age druidic cloak";
			itemDef.modelID = 58194;
			itemDef.modelZoom = 1827;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 364;
			itemDef.modelOffset2 = 12;
			itemDef.modelRotation2 = 992;
			itemDef.femaleEquip1 = 58462;
			itemDef.aByte205 = -2;
			itemDef.aByte154 = -2;
			break;

		case 19314:
			itemDef.maleEquip1 = 58322;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Third-age druidic wreath";
			itemDef.modelID = 58281;
			itemDef.anInt175 = 58153;
			itemDef.modelZoom = 1014;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 304;
			itemDef.modelOffset2 = 20;
			itemDef.anInt197 = 58134;
			itemDef.femaleEquip1 = 58408;
			itemDef.aByte205 = -8;
			itemDef.aByte154 = -8;
			break;

		case 19317:
			itemDef.maleEquip1 = 58366;
			itemDef.name = "Third-age druidic robe top";
			itemDef.modelID = 58238;
			itemDef.modelZoom = 1574;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 581;
			itemDef.modelOffset2 = 7;
			itemDef.femaleEquip1 = 58452;
			itemDef.aByte205 = 10;
			break;

		case 19320:
			itemDef.maleEquip1 = 58333;
			itemDef.name = "Third-age druidic robe";
			itemDef.modelID = 58177;
			itemDef.modelZoom = 2341;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 595;
			itemDef.modelRotation2 = 2047;
			itemDef.femaleEquip1 = 58427;
			break;

		/**
		 * Runecrafting Staves
		 * **/

		case 13629:
			itemDef.maleEquip1 = 41397;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Runecrafting staff";
			itemDef.modelID = 41521;
			itemDef.modelZoom = 1762;
			itemDef.actions = (new String[] { null, null, null, null, "Drop" });
			itemDef.modelRotation1 = 186;
			itemDef.modelOffset2 = -9;
			itemDef.modelRotation2 = 930;
			itemDef.femaleEquip1 = 41397;
			break;

		case 13630:
			itemDef.maleEquip1 = 41406;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Air talisman staff";
			itemDef.modelID = 41527;
			itemDef.modelZoom = 1974;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -16;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41406;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13631:
			itemDef.maleEquip1 = 41393;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Mind talisman staff";
			itemDef.modelID = 41530;
			itemDef.modelZoom = 2018;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 260;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 2028;
			itemDef.femaleEquip1 = 41393;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13632:
			itemDef.maleEquip1 = 41399;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Water talisman staff";
			itemDef.modelID = 41559;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 236;
			itemDef.modelOffset2 = -37;
			itemDef.modelRotation2 = 1995;
			itemDef.femaleEquip1 = 41399;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13633:
			itemDef.maleEquip1 = 41408;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Earth talisman staff";
			itemDef.modelID = 41549;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41408;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13634:
			itemDef.maleEquip1 = 41407;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Fire talisman staff";
			itemDef.modelID = 41569;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41407;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13635:
			itemDef.maleEquip1 = 41400;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Body talisman staff";
			itemDef.modelID = 41573;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41400;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13636:
			itemDef.maleEquip1 = 41396;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Cosmic talisman staff";
			itemDef.modelID = 41565;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41396;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13637:
			itemDef.maleEquip1 = 41405;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Chaos talisman staff";
			itemDef.modelID = 41547;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41405;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13638:
			itemDef.maleEquip1 = 41391;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Nature talisman staff";
			itemDef.modelID = 41532;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41391;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13639:
			itemDef.maleEquip1 = 41395;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Law talisman staff";
			itemDef.modelID = 41544;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41395;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13640:
			itemDef.maleEquip1 = 41409;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Death talisman staff";
			itemDef.modelID = 41525;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41409;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13641:
			itemDef.maleEquip1 = 41392;
			itemDef.modelOffset1 = -3;
			itemDef.name = "Blood talisman staff";
			itemDef.modelID = 41536;
			itemDef.modelZoom = 2146;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", null, "Drop" });
			itemDef.modelRotation1 = 227;
			itemDef.modelOffset2 = -8;
			itemDef.modelRotation2 = 1965;
			itemDef.femaleEquip1 = 41392;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13642:
			itemDef.maleEquip1 = 41401;
			itemDef.modelOffset1 = 3;
			itemDef.name = "Omni-talisman staff";
			itemDef.modelID = 41509;
			itemDef.modelZoom = 2022;
			itemDef.actions = (new String[] { null, "Wield", "Teleport", "Locate", "Destroy" });
			itemDef.modelRotation1 = 249;
			itemDef.modelOffset2 = -36;
			itemDef.modelRotation2 = 2001;
			itemDef.femaleEquip1 = 41401;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 13649:
			itemDef.modelOffset1 = 2;
			itemDef.name = "Omni-talisman";
			itemDef.modelID = 41520;
			itemDef.modelZoom = 1160;
			itemDef.actions = (new String[] { null, null, null, null, "Destroy" });
			itemDef.modelRotation1 = 352;
			itemDef.modelOffset2 = 20;
			itemDef.modelRotation2 = 160;
			break;

		/**
		 * Santa Outfit
		 * **/

		case 14595:
			itemDef.maleEquip1 = 45192;
			itemDef.name = "Santa costume top (male)";
			itemDef.modelID = 45273;
			itemDef.modelZoom = 1360;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Destroy" });
			itemDef.modelRotation1 = 561;
			itemDef.modelOffset2 = 3;
			itemDef.modelRotation2 = 6;
			itemDef.femaleEquip1 = 45199;
			break;

		case 14600:
			itemDef.maleEquip1 = 45192;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Santa costume top (female)";
			itemDef.modelID = 45278;
			itemDef.modelZoom = 1114;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Destroy" });
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset2 = 4;
			itemDef.femaleEquip1 = 45199;
			break;

		case 14603:
			itemDef.maleEquip1 = 45195;
			itemDef.modelOffset1 = -1;
			itemDef.name = "Santa costume legs (male)";
			itemDef.modelID = 45275;
			itemDef.modelZoom = 1872;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Destroy" });
			itemDef.modelRotation1 = 541;
			itemDef.modelOffset2 = 4;
			itemDef.femaleEquip1 = 45202;
			break;

		case 14604:
			itemDef.maleEquip1 = 45195;
			itemDef.modelOffset1 = 1;
			itemDef.name = "Santa costume legs (female)";
			itemDef.modelID = 45274;
			itemDef.modelZoom = 1232;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Destroy" });
			itemDef.modelRotation1 = 535;
			itemDef.modelOffset2 = -7;
			itemDef.femaleEquip1 = 45202;
			break;

		case 14602:
			itemDef.maleEquip1 = 45196;
			itemDef.name = "Santa costume gloves";
			itemDef.modelID = 45280;
			itemDef.modelZoom = 659;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Destroy" });
			itemDef.modelRotation1 = 420;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 828;
			itemDef.femaleEquip1 = 45203;
			break;

		case 14605:
			itemDef.maleEquip1 = 45191;
			itemDef.modelOffset1 = 4;
			itemDef.name = "Santa costume boots";
			itemDef.modelID = 45272;
			itemDef.modelZoom = 770;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Destroy" });
			itemDef.modelRotation1 = 62;
			itemDef.modelRotation2 = 124;
			itemDef.femaleEquip1 = 45198;
			break;

		/**
		 * Royal Outfit
		 * **/

		case 15503:
			itemDef.maleEquip1 = 51963;
			itemDef.name = "Royal shirt";
			itemDef.modelID = 51949;
			itemDef.modelZoom = 1360;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 512;
			itemDef.femaleEquip1 = 51969;
			break;

		case 15505:
			itemDef.maleEquip1 = 51961;
			itemDef.name = "Royal leggings";
			itemDef.modelID = 51953;
			itemDef.modelZoom = 1872;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset2 = 7;
			itemDef.femaleEquip1 = 51967;
			break;

		case 15507:
			itemDef.maleEquip1 = 51964;
			itemDef.name = "Royal sceptre";
			itemDef.modelID = 51950;
			itemDef.modelZoom = 1360;
			itemDef.actions = (new String[] { null, "Wield", null, null, "Drop" });
			itemDef.modelRotation1 = 237;
			itemDef.modelRotation2 = 51;
			itemDef.femaleEquip1 = 51964;
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			break;

		case 15509:
			itemDef.maleEquip1 = 51960;
			itemDef.name = "Royal crown";
			itemDef.modelID = 51951;
			itemDef.anInt175 = 51948;
			itemDef.modelZoom = 592;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 240;
			itemDef.anInt197 = 51947;
			itemDef.femaleEquip1 = 51966;
			break;

		case 15511:
			itemDef.maleEquip1 = 51962;
			itemDef.name = "Royal amulet";
			itemDef.modelID = 51952;
			itemDef.modelZoom = 720;
			itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset2 = 4;
			itemDef.femaleEquip1 = 51968;
			break;

		/**
		 * Custom Items
		 * **/

		case 13951:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Demon Kiteshield";
			itemDef.description = "A rare, protective kiteshield.".getBytes();
			itemDef.modelID = 56701;
			itemDef.modelZoom = 1560;
			itemDef.modelRotation1 = 344;
			itemDef.modelRotation2 = 1104;
			itemDef.modelOffset1 = -6;
			itemDef.modelOffset2 = -14;
			itemDef.maleEquip1 = 56700;
			itemDef.femaleEquip1 = 56700;
			itemDef.anInt175 = -1;
			itemDef.anInt197 = -1;
			break;

		case 13959:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 56215;
			itemDef.modelZoom = 2257;
			itemDef.modelRotation1 = 520;
			itemDef.modelRotation2 = 1248;
			itemDef.modelOffset1 = -23;
			itemDef.modelOffset2 = -12;
			itemDef.maleEquip1 = 56214;
			itemDef.femaleEquip1 = 56214;
			itemDef.name = "Zaros godsword";
			itemDef.description = "Test".getBytes();
			break;

		case 13962:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 31000;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 572;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 1;
			itemDef.maleEquip1 = 31001;
			itemDef.femaleEquip1 = 31001;
			itemDef.aByte154 = -5;
			itemDef.aByte205 = -5;
			itemDef.name = "Masquerade's sword";
			itemDef.description = "A sword belonging to Masquerade.".getBytes();
			break;

		case 13963:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 43101;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 1284;
			itemDef.modelOffset1 = -55;
			itemDef.modelOffset2 = -1;
			itemDef.maleEquip1 = 43102;
			itemDef.femaleEquip1 = 43102;
			itemDef.name = "Thoby's sword";
			itemDef.description = "A sword belonging to Thoby.".getBytes();
			break;

		case 13952:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 48830;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = -6;
			itemDef.modelOffset2 = 1;
			itemDef.maleEquip1 = 48829;
			itemDef.femaleEquip1 = 48829;
			itemDef.anInt175 = -1;
			itemDef.anInt197 = -1;
			itemDef.name = "Death cape";
			itemDef.description = "The person who wears this could cause the outcome of something heroic, or evil.".getBytes();
			break;

		case 13953:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 48834;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = -6;
			itemDef.modelOffset2 = 1;
			itemDef.maleEquip1 = 48833;
			itemDef.femaleEquip1 = 48833;
			itemDef.anInt175 = -1;
			itemDef.anInt197 = -1;
			itemDef.name = "Demonic death cape";
			itemDef.description = "This cape has been guided in the direction of evil.".getBytes();
			break;

		case 13954:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 48836;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = -6;
			itemDef.modelOffset2 = 1;
			itemDef.maleEquip1 = 48835;
			itemDef.femaleEquip1 = 48835;
			itemDef.anInt175 = -1;
			itemDef.anInt197 = -1;
			itemDef.name = "Heroic cape";
			itemDef.description = "This cape has been guided in the direction of a hero.".getBytes();
			break;

		}
		return itemDef;
	}

	public void actionData(int a, String b) {
		actions = new String[5];
		actions[a] = b;
	}

	public void totalColors(int total) {
		originalModelColors = new int[total];
		modifiedModelColors = new int[total];
	}

	public void colors(int id, int original, int modified) {
		originalModelColors[id] = original;
		modifiedModelColors[id] = modified;
	}

	public void itemData(String n, String d) {
		name = n;
		description = d.getBytes();
	}

	public void models(int mID, int mE, int fE, int mE2, int fE2) {
		modelID = mID;
		maleEquip1 = mE;
		femaleEquip1 = fE;
		maleEquip2 = mE2;
		femaleEquip2 = fE2;
	}

	public void modelData(int mZ, int mR1, int mR2, int mO1, int mO2) {
		modelZoom = mZ;
		modelRotation1 = mR1;
		modelRotation2 = mR2;
		modelOffset1 = mO1;
		modelOffset2 = mO2;
	}

	public void toNote() {
		ItemDef itemDef = forID(certTemplateID);
		modelID = itemDef.modelID;
		modelZoom = itemDef.modelZoom;
		modelRotation1 = itemDef.modelRotation1;
		modelRotation2 = itemDef.modelRotation2;
		anInt204 = itemDef.anInt204;
		modelOffset1 = itemDef.modelOffset1;
		modelOffset2 = itemDef.modelOffset2;
		originalModelColors = itemDef.originalModelColors;
		modifiedModelColors = itemDef.modifiedModelColors;
		ItemDef itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		char c = itemDef_1.name.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".").getBytes();
		stackable = true;
	}

	public static Sprite getSprite(int i, int j, int k) {
		if (k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);
			if (sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1) {
				sprite.unlink();
				sprite = null;
			}
			if (sprite != null) {
				return sprite;
			}
		}
		ItemDef itemDef = forID(i);
		if (itemDef.stackIDs == null) {
			j = -1;
		}
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0) {
					i1 = itemDef.stackIDs[j1];
				}
			}
			if (i1 != -1) {
				itemDef = forID(i1);
			}
		}
		Model model = itemDef.method201(1);
		if (model == null) {
			return null;
		}
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null) {
				return null;
			}
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Texture.textureInt1;
		int l1 = Texture.textureInt2;
		int ai[] = Texture.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Texture.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Texture.method364();
		int k3 = itemDef.modelZoom;
		if (k == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (k > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Texture.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Texture.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.anInt204, itemDef.modelRotation1, itemDef.modelOffset1, l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--) {
				if (sprite2.myPixels[i5 + j4 * 32] == 0) {
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					}
				}
			}
		}
		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (sprite2.myPixels[j5 + k4 * 32] == 0) {
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						}
					}
				}
			}
		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--) {
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0) {
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
					}
				}
			}
		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if (k == 0) {
			mruNodes1.removeFromCache(sprite2, i);
		}
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Texture.textureInt1 = k1;
		Texture.textureInt2 = l1;
		Texture.anIntArray1472 = ai;
		Texture.aBoolean1464 = true;
		if (itemDef.stackable) {
			sprite2.anInt1444 = 33;
		} else {
			sprite2.anInt1444 = 32;
		}
		sprite2.anInt1445 = j;
		return sprite2;
	}

	public Model method201(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
					j = stackIDs[k];
				}
			}
			if (j != -1) {
				return forID(j).method201(1);
			}
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		if (model != null) {
			return model;
		}
		model = Model.method462(modelID);
		if (model == null) {
			return null;
		}
		if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128) {
			model.method478(anInt167, anInt191, anInt192);
		}
		if (originalModelColors != null) {
			for (int l = 0; l < originalModelColors.length; l++) {
				model.method476(originalModelColors[l], modifiedModelColors[l]);
			}

		}
		model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.aBoolean1659 = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}

	public Model method202(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
					j = stackIDs[k];
				}
			}
			if (j != -1) {
				return forID(j).method202(1);
			}
		}
		Model model = Model.method462(modelID);
		if (model == null) {
			return null;
		}
		if (originalModelColors != null) {
			for (int l = 0; l < originalModelColors.length; l++) {
				model.method476(originalModelColors[l], modifiedModelColors[l]);
			}

		}
		return model;
	}

	public void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelID = stream.readUnsignedWord();
			} else if (i == 2) {
				name = stream.readString();
			} else if (i == 3) {
				description = stream.readBytes();
			} else if (i == 4) {
				modelZoom = stream.readUnsignedWord();
			} else if (i == 5) {
				modelRotation1 = stream.readUnsignedWord();
			} else if (i == 6) {
				modelRotation2 = stream.readUnsignedWord();
			} else if (i == 7) {
				modelOffset1 = stream.readUnsignedWord();
				if (modelOffset1 > 32767) {
					modelOffset1 -= 0x10000;
				}
			} else if (i == 8) {
				modelOffset2 = stream.readUnsignedWord();
				if (modelOffset2 > 32767) {
					modelOffset2 -= 0x10000;
				}
			} else if (i == 10) {
				stream.readUnsignedWord();
			} else if (i == 11) {
				stackable = true;
			} else if (i == 12) {
				value = stream.readDWord();
			} else if (i == 16) {
				membersObject = true;
			} else if (i == 23) {
				maleEquip1 = stream.readUnsignedWord();
				aByte205 = stream.readSignedByte();
			} else if (i == 24) {
				maleEquip2 = stream.readUnsignedWord();
			} else if (i == 25) {
				femaleEquip1 = stream.readUnsignedWord();
				aByte154 = stream.readSignedByte();
			} else if (i == 26) {
				femaleEquip2 = stream.readUnsignedWord();
			} else if (i >= 30 && i < 35) {
				if (groundActions == null) {
					groundActions = new String[5];
				}
				groundActions[i - 30] = stream.readString();
				if (groundActions[i - 30].equalsIgnoreCase("hidden")) {
					groundActions[i - 30] = null;
				}
			} else if (i >= 35 && i < 40) {
				if (actions == null) {
					actions = new String[5];
				}
				actions[i - 35] = stream.readString();
			} else if (i == 40) {
				int j = stream.readUnsignedByte();
				originalModelColors = new int[j];
				modifiedModelColors = new int[j];
				for (int k = 0; k < j; k++) {
					originalModelColors[k] = stream.readUnsignedWord();
					modifiedModelColors[k] = stream.readUnsignedWord();
				}
			} else if (i == 78) {
				anInt185 = stream.readUnsignedWord();
			} else if (i == 79) {
				anInt162 = stream.readUnsignedWord();
			} else if (i == 90) {
				anInt175 = stream.readUnsignedWord();
			} else if (i == 91) {
				anInt197 = stream.readUnsignedWord();
			} else if (i == 92) {
				anInt166 = stream.readUnsignedWord();
			} else if (i == 93) {
				anInt173 = stream.readUnsignedWord();
			} else if (i == 95) {
				anInt204 = stream.readUnsignedWord();
			} else if (i == 97) {
				certID = stream.readUnsignedWord();
			} else if (i == 98) {
				certTemplateID = stream.readUnsignedWord();
			} else if (i >= 100 && i < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[i - 100] = stream.readUnsignedWord();
				stackAmounts[i - 100] = stream.readUnsignedWord();
			} else if (i == 110) {
				anInt167 = stream.readUnsignedWord();
			} else if (i == 111) {
				anInt192 = stream.readUnsignedWord();
			} else if (i == 112) {
				anInt191 = stream.readUnsignedWord();
			} else if (i == 113) {
				anInt196 = stream.readSignedByte();
			} else if (i == 114) {
				anInt184 = stream.readSignedByte() * 5;
			} else if (i == 115) {
				team = stream.readUnsignedByte();
			}
		} while (true);
	}

	public ItemDef() {
		id = -1;
	}

	public byte aByte154;
	public int value;
	public int[] modifiedModelColors;
	public int id;
	static MRUNodes mruNodes1 = new MRUNodes(100);
	public static MRUNodes mruNodes2 = new MRUNodes(50);
	public int[] originalModelColors;
	public boolean membersObject;
	public int anInt162;
	public int certTemplateID;
	public int femaleEquip2;
	public int maleEquip1;
	public int anInt166;
	public int anInt167;
	public String groundActions[];
	public int modelOffset1;
	public String name;
	public static ItemDef[] cache;
	public int anInt173;
	public int modelID;
	public int anInt175;
	public boolean stackable;
	public byte description[];
	public int certID;
	public static int cacheIndex;
	public int modelZoom;
	public static boolean isMembers = true;
	public static Stream stream;
	public int anInt184;
	public int anInt185;
	public int maleEquip2;
	public String actions[];
	public int modelRotation1;
	public int anInt191;
	public int anInt192;
	public int[] stackIDs;
	public int modelOffset2;
	public static int[] streamIndices;
	public int anInt196;
	public int anInt197;
	public int modelRotation2;
	public int femaleEquip1;
	public int[] stackAmounts;
	public int team;
	public static int totalItems;
	public int anInt204;
	public byte aByte205;
	public int anInt164;
	public int anInt199;
	public int anInt188;
}