package org.invictus.world;

import java.util.ArrayList;

import org.invictus.model.objects.Object;
import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;

public class ObjectManager {

	/**
	 * Constants for the types of objects.
	 */
	//TODO: Name these better.
	public static final int WALL_OBJECT_1 = 0; // straight walls, fences etc
	public static final int WALL_OBJECT_2 = 1; // diagonal walls corner, fences etc connectors
	public static final int WALL_OBJECT_3 = 2; // entire walls, fences etc corners
	public static final int WALL_OBJECT_4 = 3; // straight wall corners, fences etc connectors
	public static final int WALL_OBJECT_5 = 4; // straight inside wall decoration
	public static final int WALL_OBJECT_6 = 5; // straight outside wall decoration
	public static final int WALL_OBJECT_7 = 6; // diagonal outside wall decoration
	public static final int WALL_OBJECT_8 = 7; //  diagonal inside wall decoration
	public static final int WALL_OBJECT_9 = 8; // diagonal in wall decoration
	public static final int WALL_OBJECT_10 = 9; // diagonal walls, fences etc
	public static final int DEFAULT_OBJECT = 10; // all kinds of objects, trees, statues, signs, fountains etc etc
	public static final int GROUND_OBJECT_1 = 11; // ground objects like daisies etc
	public static final int ROOF_OBJECT_1 = 12; // straight sloped roofs
	public static final int ROOF_OBJECT_2 = 13; // diagonal sloped roofs
	public static final int ROOF_OBJECT_3 = 14; // diagonal slope connecting roofs
	public static final int ROOF_OBJECT_4 = 15; // straight sloped corner connecting roofs
	public static final int ROOF_OBJECT_5 = 16; // straight sloped corner roof
	public static final int ROOF_OBJECT_6 = 17; // straight flat top roofs
	public static final int ROOF_OBJECT_7 = 18; // straight bottom egde roofs
	public static final int ROOF_OBJECT_8 = 19; // diagonal bottom edge connecting roofs
	public static final int ROOF_OBJECT_9 = 20; // straight bottom edge connecting roofs
	public static final int ROOF_OBJECT_10 = 21; // straight bottom edge connecting corner roofs
	public static final int GROUND_OBJECT_2 = 22; // ground decoration + map signs (quests, water fountains, shops, trapdoors, etc)

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();

	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (Object o : toRemove) {
			objects.remove(o);
		}
		toRemove.clear();
	}

	public boolean objectExists(final int x, final int y) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y) {
				return true;
			}
		}
		return false;
	}

	public void removeObject(int x, int y) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPlayerAssistant().object(-1, x, y, 0, 10);
			}
		}
	}

	public void updateObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPlayerAssistant().object(o.newId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public void placeObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPlayerAssistant().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}
		return null;
	}

	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o, c))
				c.getPlayerAssistant().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
		c.getChampChallenge().championStatue();
		if (c.distanceToPoint(2813, 3463) <= 60) {
			c.getFarming().updateHerbPatch();
		}
	}

	public void loadCustomSpawns(Client c) {
		c.getPlayerAssistant().checkObjectSpawn(410, 3218, 3433, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(3044, 3044, 9790, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(2213, 3049, 9786, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(2213, 2541, 3886, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(2213, 2542, 3886, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(2213, 2543, 3886, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(2213, 2544, 3886, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(2213, 2545, 3886, 2, 10);
		c.getPlayerAssistant().checkObjectSpawn(6552, 3208, 3438, 2, 10);
		if (c.heightLevel == 0) {
			c.getPlayerAssistant().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		} else {
			c.getPlayerAssistant().checkObjectSpawn(-1, 2911, 3614, 1, 10);
		}
	}

	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}

}