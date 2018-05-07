package org.invictus.model.players.content.quests.testquest;

import org.invictus.model.players.Client;
import org.invictus.model.players.content.quests.QuestHandler;
import org.invictus.model.players.skills.SkillHandler;

/**
 * A class for handling the test quest.
 * 
 * @author Joe
 *
 */

public class TestQuest {

	private Client player;

	public TestQuest(Client player) {
		this.player = player;
	}

	/**
	 * The name of the quest
	 */
	public static final String QUEST_TITLE = "Test Quest";

	/**
	 * The ID of the text button for the quest in the quest tab.
	 */
	public static final int QUEST_TAB_BUTTON = 28164;

	/**
	 * The ID of the string for the quest title in the quest tab.
	 */
	public static final int QUEST_TAB_STRING = 7332;

	/**
	 * The starting stage of the quest.
	 */
	public static int STARTING_STAGE = 0;

	/**
	 * The ending stage of the quest.
	 */
	public static int ENDING_STAGE = 4;

	/**
	 * The ID of the quest giving NPC.
	 */
	public static final int MOSOL_REI = 500;

	/**
	 * A boolean to check if the quest has been started.
	 */
	public boolean questStarted() {
		if (player.testQuestProgress > STARTING_STAGE && player.testQuestProgress < ENDING_STAGE) {
			return true;
		}
		return false;
	}

	/**
	 * A boolean to check if the quest has been finished.
	 */
	public boolean questCompleted() {
		if (player.testQuestProgress == ENDING_STAGE) {
			return true;
		}
		return false;
	}

	/**
	 * A boolean if the quest hasn't beens started yet.
	 */
	public boolean questNotStarted() {
		if (player.testQuestProgress == STARTING_STAGE) {
			return true;
		}
		return false;
	}

	/**
	 * A method for changing the colour of the quest title in the quest tab depending on progress.
	 */
	public void updateQuestTab() {
		if (questStarted()) {
			player.getPlayerAssistant().sendString("@yel@" + QUEST_TITLE, QUEST_TAB_STRING);
		}
		if (questCompleted()) {
			player.getPlayerAssistant().sendString("@gre@" + QUEST_TITLE, QUEST_TAB_STRING);
		}
		if (questNotStarted()) {
			player.getPlayerAssistant().sendString(QUEST_TITLE, QUEST_TAB_STRING);
		}
	}
	
	/**
	 * The text for the quest journal before the quest has been started.
	 */
	public String[] questJournalStart = { 
			"@dbl@I can start this quest by speaking to @dre@Mosolo Rei@blu@", 
			"@dbl@He is located in @dre@Edgeville@blu@.", 
			"", 
			"@dbl@This quest has the following requirements:", 
			"",
	        "",
	        "", 
	        ""

	};

	/**
	 * An array for the quest journal text.
	 */
	public static String[][] questSteps = { 
			{ "", "step_1_1", "step_1_2" }, // Step 1
	        { "", "step_2_1", "step_2_2" }, // Step 2
	        { "", "step_3_1", "step_3_2" }, // Step 3
	        { "", "step_4_1", "step_4_2" }, // Step 4
	};

	/**
	 * A method for opening the quest journal and displaying the text depending on progress.
	 */
	public void questJournal() {
		player.getQuestHandler().openQuestJournal(QUEST_TITLE);
		int journalLine = 0;
		if (questNotStarted()) {
			for (int x = 0; x < questJournalStart.length; x++) {
				player.getPlayerAssistant().sendString(questJournalStart[x], QuestHandler.QUEST_JOURNAL_TEXT_IDS[journalLine++]);
			}
			player.getPlayerAssistant().sendString(player.getQuestHandler().skillRequirement("Farming", SkillHandler.FARMING, 28), QuestHandler.QUEST_JOURNAL_TEXT_IDS[5]);
			player.getPlayerAssistant().sendString(player.getQuestHandler().skillRequirement("Herblore", SkillHandler.HERBLORE, 42), QuestHandler.QUEST_JOURNAL_TEXT_IDS[6]);
			player.getPlayerAssistant().sendString(player.getQuestHandler().skillRequirement("Cooking", SkillHandler.COOKING, 58), QuestHandler.QUEST_JOURNAL_TEXT_IDS[7]);
		}
		for (int i = 0; i < player.testQuestProgress; i++) {
			for (int j = 0; j < questSteps[i].length; j++) {
				player.getPlayerAssistant().sendString(questSteps[i][j], QuestHandler.QUEST_JOURNAL_TEXT_IDS[journalLine++]);
			}
		}
	}

}
