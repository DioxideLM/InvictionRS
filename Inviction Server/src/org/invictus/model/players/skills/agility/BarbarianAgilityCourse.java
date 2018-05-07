package org.invictus.model.players.skills.agility;

import org.invictus.event.CycleEvent;
import org.invictus.event.CycleEventContainer;
import org.invictus.event.CycleEventHandler;
import org.invictus.model.players.Animation;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

public class BarbarianAgilityCourse extends SkillHandler {

	private Client player;

	public BarbarianAgilityCourse(Client player) {
		this.player = player;
	}

	/**
	 * Constants for the agility object ID's.
	 */
	public static final int ROPESWING = 2282;
	public static final int LOG = 2294;
	public static final int NET = 2284;
	public static final int BALANCING_LEDGE = 2302;
	public static final int LADDER = 3205;
	public static final int LADDER_1_UP = 1747;
	public static final int CRUMBLING_WALL = 1948;

	/**
	 * Booleans to check whether or not the indivudal agility objects have been used.
	 */
	private boolean ropeSwingUsed, logUsed, netUsed, balancingLedgeUsed, ladderUsed;

	private int crumblingWallUsed;

	/**
	 * A single boolean to check if all of the agility objects have been used.
	 * 
	 * @return
	 */
	private boolean allObjectsUsed() {
		if (ropeSwingUsed && logUsed && netUsed && balancingLedgeUsed && ladderUsed && crumblingWallUsed == 3) {
			return true;
		}
		return false;
	}

	/**
	 * A method that rewards the player for fully completing a course, and then resets the course.
	 */
	public void completeCourse() {
		if (allObjectsUsed()) {
			player.getPlayerAssistant().addSkillXP(47 * EXPERIENCE_MULTIPLIER, AGILITY);
			player.sendMessage("You have completed the full barbarian agility course.");
			player.agilityPoints += 4;
			player.sendMessage("You are awarded 4 Agility Points! You now have " + player.agilityPoints + " Agility Points.");
			player.barbarianCourseLaps += 1;
			player.sendMessage("You have now successfuly completed " + player.barbarianCourseLaps + " Barbarian Agility Course laps.");
		} else {
			player.getPlayerAssistant().addSkillXP(7 * EXPERIENCE_MULTIPLIER, AGILITY);
		}
		ropeSwingUsed = false;
		logUsed = false;
		netUsed = false;
		balancingLedgeUsed = false;
		ladderUsed = false;
		crumblingWallUsed = 0;
		isSkilling[AGILITY] = false;
		player.doingObstacle = false;
	}

	/**
	 * Handles the crossing of the the log obstacle.
	 */
	public void crossRopeSwing() {
		if (player.absX == 2551 && player.absY == 3554) {
			isSkilling[AGILITY] = true;
			player.doingObstacle = true;
			while (player.absX != 2551 && player.absY != 3549) {
				player.getPlayerAssistant().walkTo(2551 - player.absX, 3549 - player.absY);
			}
			player.getAgility().agilityRun(Agility.ROPE_SWING, 0, -5);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.absX == 2551 && player.absY == 3549) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.getAgility().resetAgilityWalk();
					player.getPlayerAssistant().addSkillXP((int) 7.5 * EXPERIENCE_MULTIPLIER, AGILITY);
					ropeSwingUsed = true;
					isSkilling[AGILITY] = false;
					player.doingObstacle = false;
				}
			}, 1);
		}
	}

	/**
	 * Handles the crossing of the the log obstacle.
	 */
	public void crossLog() {
		if (player.absX == 2551 && player.absY == 3546) {
			isSkilling[AGILITY] = true;
			player.doingObstacle = true;
			while (player.absX != 2541 && player.absY != 3546) {
				player.getPlayerAssistant().walkTo(2541 - player.absX, 3546 - player.absY);
			}
			player.getAgility().agilityWalk(Agility.BALANCE_WALK_ANIMATION, -10, 0);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.absX == 2541 && player.absY == 3546) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.getAgility().resetAgilityWalk();
					player.getPlayerAssistant().addSkillXP((int) 7.5 * EXPERIENCE_MULTIPLIER, AGILITY);
					logUsed = true;
					isSkilling[AGILITY] = false;
					player.doingObstacle = false;
				}
			}, 1);
		}
	}

	/**
	 * Handles the climbing of the first net obstacle.
	 */
	public void climbNet() {
		if (player.absX == 2539 && player.absY == 3545 || player.absY == 3546) {
			player.startAnimation(Agility.CLIMB_UP_ANIMATION);
			player.getPlayerAssistant().movePlayer(player.absX -2, player.absY, 1);
			player.getPlayerAssistant().addSkillXP((int) 7.5 * EXPERIENCE_MULTIPLIER, AGILITY);
			netUsed = true;
		}
	}
	
	public void crossBalance() {
		if (player.absX == 2536 && player.absY == 3547 && player.heightLevel == 1) {
			isSkilling[AGILITY] = true;
			player.doingObstacle = true;
			while (player.absX != 2532 && player.absY != 3547) {
				player.turnPlayerTo(2536, 3546);
				player.getPlayerAssistant().walkTo(2532 - player.absX, 3547 - player.absY);
			}
			player.getAgility().agilityWalk(Agility.WALL_CLIMB, -4, 0);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.absX == 2532 && player.absY == 3547) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.getAgility().resetAgilityWalk();
					player.getPlayerAssistant().addSkillXP((int) 7.5 * EXPERIENCE_MULTIPLIER, AGILITY);
					balancingLedgeUsed = true;
					isSkilling[AGILITY] = false;
					player.doingObstacle = false;
				}
			}, 1);
		}
	}
	
	public void goDownLadder() {
		if(player.absX == 2532 && player.absY == 3546 && player.heightLevel == 1) {
			player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
			player.startAnimation(Animation.CLIMB_DOWN_LADDER);
			ladderUsed = true;
		}
	}

	/**
	 * Does a certain obstacle action depending on which obstacle is clicked.
	 * 
	 * @param objectType
	 */

	public void agilityCourse(int objectType) {
		switch (objectType) {
		case ROPESWING:
			crossRopeSwing();
			break;
		case LOG:
			crossLog();
			break;
		case NET:
			climbNet();
			break;
		case BALANCING_LEDGE:
			crossBalance();
			break;
		case LADDER:
			goDownLadder();
			break;
		case CRUMBLING_WALL:
			break;
		}
	}

}
