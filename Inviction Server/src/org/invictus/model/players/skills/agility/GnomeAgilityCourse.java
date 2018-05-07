package org.invictus.model.players.skills.agility;

import org.invictus.event.CycleEvent;
import org.invictus.event.CycleEventContainer;
import org.invictus.event.CycleEventHandler;
import org.invictus.model.players.Client;
import org.invictus.model.players.skills.SkillHandler;

/**
 * A simple class for the Gnome Agility Course. It's not great, and could probably do with a rewrite. But it'll do for now.
 * 
 * @author Joe
 *
 */
public class GnomeAgilityCourse extends SkillHandler {

	private Client player;

	public GnomeAgilityCourse(Client player) {
		this.player = player;
	}

	/**
	 * Constants for the agility object ID's.
	 */
	public static final int LOG = 2295;
	public static final int FIRST_NET = 2285;
	public static final int FIRST_BRANCH = 2313;
	public static final int TIGHTROPE = 2312;
	public static final int SECOND_BRANCH_WEST = 2314;
	public static final int SECOND_BRANCH_NORTH = 2315;
	public static final int SECOND_NET = 2286;
	public static final int PIPE_WEST = 154;
	public static final int PIPE_EAST = 4058;

	/**
	 * Booleans to check whether or not the indivudal agility objects have been used.
	 */
	private boolean logBalanceUsed, firstNetUsed, firstBranchUsed, tightropeUsed, secondBranchUsed, secondNetUsed;

	/**
	 * A single boolean to check if all of the agility objects have been used.
	 * 
	 * @return
	 */
	private boolean allObjectsUsed() {
		if (logBalanceUsed && firstNetUsed && firstBranchUsed && tightropeUsed && secondBranchUsed && secondNetUsed) {
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
			player.sendMessage("You have completed the full gnome agility course.");
			player.agilityPoints += 2;
			player.sendMessage("You are awarded 2 Agility Points! You now have " + player.agilityPoints + " Agility Points.");
			player.gnomeCourseLaps += 1;
			player.sendMessage("You have now successfuly completed " + player.gnomeCourseLaps + " Gnome Agility Course laps.");
		} else {
			player.getPlayerAssistant().addSkillXP(7 * EXPERIENCE_MULTIPLIER, AGILITY);
		}
		logBalanceUsed = false;
		firstNetUsed = false;
		firstBranchUsed = false;
		tightropeUsed = false;
		secondBranchUsed = false;
		secondNetUsed = false;
		isSkilling[AGILITY] = false;
		player.doingObstacle = false;
	}

	/**
	 * Handles the crossing of the the log obstacle.
	 */
	public void crossLog() {
		if (player.absX == 2474 && player.absY == 3436) {
			isSkilling[AGILITY] = true;
			player.doingObstacle = true;
			while (player.absX != 2474 && player.absY != 3436) {
				player.getPlayerAssistant().walkTo(2474 - player.absX, 3436 - player.absY);
			}
			player.getAgility().agilityWalk(Agility.BALANCE_WALK_ANIMATION, 0, -7);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.absX == 2474 && player.absY == 3429) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.getAgility().resetAgilityWalk();
					player.getPlayerAssistant().addSkillXP((int) 7.5 * EXPERIENCE_MULTIPLIER, AGILITY);
					logBalanceUsed = true;
					isSkilling[AGILITY] = false;
					player.doingObstacle = false;
				}
			}, 1);
		}
	}

	/**
	 * Handles the climbing of the first net obstacle.
	 */
	public void climbFirstNet() {
		if (player.absY == 3426) {
			player.startAnimation(Agility.CLIMB_UP_ANIMATION);
			player.getPlayerAssistant().movePlayer(player.absX, 3424, 1);
			player.getPlayerAssistant().addSkillXP((int) 7.5 * EXPERIENCE_MULTIPLIER, AGILITY);
			firstNetUsed = true;
		}
	}

	/**
	 * Handles the climbing up of the first tree branch obstacle.
	 */
	public void climbFirstBranch() {
		if (player.absX == 2473 && player.absY == 3423) {
			player.startAnimation(Agility.CLIMB_UP_ANIMATION);
			player.getPlayerAssistant().movePlayer(2473, 3420, 2);
			player.getPlayerAssistant().addSkillXP(5 * EXPERIENCE_MULTIPLIER, AGILITY);
			firstBranchUsed = true;
		}
	}

	/**
	 * Handles the crossing of the tightrope obstacle.
	 */
	public void crossTightrope() {
		if (player.absX == 2477 && player.absY == 3420) {
			player.getAgility().doingAgility = true;
			player.doingObstacle = true;
			while (player.absX != 2477 && player.absY != 3420) {
				player.getPlayerAssistant().walkTo(2477 - player.absX, 3420 - player.absY);
			}
			player.getAgility().agilityWalk(Agility.BALANCE_WALK_ANIMATION, 6, 0);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.absX == 2483 && player.absY == 3420) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.getAgility().resetAgilityWalk();
					player.getPlayerAssistant().addSkillXP(7 * EXPERIENCE_MULTIPLIER, AGILITY);
					tightropeUsed = true;
					isSkilling[AGILITY] = false;
					player.doingObstacle = false;
				}
			}, 1);
		}
	}

	/**
	 * Handles the climbing down of the second tree branch obstacle.
	 */
	public void climbSecondBranch() {
		if (player.absX == 2485 || player.absX == 2486 || player.absX == 2487 && player.absY == 3418 || player.absY == 3419 || player.absY == 3420 || player.absY == 3421) {
			player.startAnimation(Agility.CLIMB_DOWN_ANIMATION);
			player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
			player.getPlayerAssistant().addSkillXP(5 * EXPERIENCE_MULTIPLIER, AGILITY);
			secondBranchUsed = true;
		}
	}

	/**
	 * Handles the climbing of the second net obstacle.
	 */
	public void climbSecondNet() {
		if (player.absY == 3425) {
			player.getAgility().doingAgility = true;
			player.doingObstacle = true;
			player.startAnimation(Agility.CLIMB_UP_ANIMATION);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					player.getPlayerAssistant().movePlayer(player.absX, 3427, 0);
					container.stop();
				}

				@Override
				public void stop() {
					player.turnPlayerTo(player.absX, 3426);
					player.getPlayerAssistant().addSkillXP(8 * EXPERIENCE_MULTIPLIER, AGILITY);
					secondNetUsed = true;
					isSkilling[AGILITY] = false;
					player.doingObstacle = false;
				}
			}, 1);
		}
	}

	/**
	 * Handles the crawling through the pipe obstacle and finishing the course.
	 */
	public void crawlThroughPipe() {
		if (player.absX == 2484 && player.absY == 3430 || player.absX == 2487 && player.absY == 3430) {
			isSkilling[AGILITY] = true;
			player.doingObstacle = true;
			while (player.absX != 2484 && player.absY != player.objectY - 1) {
				player.getPlayerAssistant().walkTo(2484 - player.absX, (player.objectY - 1) - player.absY);
			}
			player.startAnimation(Agility.ENTER_PIPE_ANIMATION);
			player.getAgility().agilityWalk(Agility.PIPE_CRAWL_ANIMATION, 0, 7);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.absY == 3437) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.startAnimation(Agility.EXIT_PIPE_ANIMATION);
					player.getAgility().resetAgilityWalk();
					completeCourse();
				}
			}, 1);
		}
	}

	/**
	 * Does a certain obstacle action depending on which obstacle is clicked.
	 * 
	 * @param objectType
	 */

	public void agilityCourse(int objectType) {
		switch (objectType) {
		case LOG:
			crossLog();
			break;
		case FIRST_NET:
			climbFirstNet();
			break;
		case FIRST_BRANCH:
			climbFirstBranch();
			break;
		case TIGHTROPE:
			crossTightrope();
			break;
		case SECOND_BRANCH_WEST:
		case SECOND_BRANCH_NORTH:
			climbSecondBranch();
			break;
		case SECOND_NET:
			climbSecondNet();
			break;
		case PIPE_WEST:
		case PIPE_EAST:
			crawlThroughPipe();
			break;
		}
	}
}