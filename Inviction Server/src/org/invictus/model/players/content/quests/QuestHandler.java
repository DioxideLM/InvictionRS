package org.invictus.model.players.content.quests;

import org.invictus.model.players.Client;

public class QuestHandler {

	private Client player;

	public QuestHandler(Client player) {
		this.player = player;
	}

	/**
	 * The ID of the Quest Journal interface.
	 */
	public static int QUEST_JOURNAL_INTERFACE_ID = 8134;

	/**
	 * The ID if the Quest Complete interface.
	 */
	public static int QUEST_COMPLETE_INTERFACE_ID = 297;

	/**
	 * The ID of the string for the Quest Journal Quest Title.
	 */
	public static int QUEST_JOURNAL_TITLE_STRING_ID = 8144;

	/**
	 * The ID of
	 */
	public static int QUEST_POINT_COUNT_STRING_ID = 640;

	/**
	 * An array containing the ID's of the strings in the Quest Journal interface.
	 */
	public static final int[] QUEST_JOURNAL_TEXT_IDS = { 8145, 8147, 8148, 8149, 8150, 8151, 8152, 8153, 8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164, 8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175, 8176, 8177, 8178, 8179, 8180, 8181, 8182, 8183, 8184, 8185, 8186, 8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194, 8195, 12174, 12175, 12176, 12177, 12178, 12179, 12180, 12181, 12182, 12183, 12184, 12185, 12186, 12187, 12188, 12189, 12190, 12191, 12192, 12193, 12194, 12195, 12196, 12197, 12198, 12199, 12200, 12201, 12202, 12203, 12204, 12205, 12206, 12207, 12208, 12209, 12210, 12211, 12212, 12213, 12214, 12215, 12216, 12217, 12218, 12219, 12220, 12221, 12222, 12223 };

	/**
	 * Updates the Quest Tab to change text depending on quest progress.
	 */
	public void updateQuestTabText() {
		player.getPlayerAssistant().sendString("Quest Points: " + player.questPoints, QUEST_POINT_COUNT_STRING_ID);
		player.getTestQuest().updateQuestTab();
	}

	/**
	 * Clears the Quest Journal of all text.
	 */
	public void clearQuestJournal() {
		for (int i = 8144; i < 8195; i++) {
			player.getPlayerAssistant().sendString("", i);
		}
		for (int i = 12174; i < 12224; i++) {
			player.getPlayerAssistant().sendString("", i);
		}
	}

	/**
	 * Opens the Quest Journal for a quest.
	 * 
	 * @param questName
	 */
	public void openQuestJournal(String questName) {
		clearQuestJournal();
		player.getPlayerAssistant().sendString(questName, QuestHandler.QUEST_JOURNAL_TITLE_STRING_ID);
		player.getPlayerAssistant().sendInterface(QuestHandler.QUEST_JOURNAL_INTERFACE_ID);
	}

	/**
	 * Returns a string for a skill requirement that changes depending on if the requirement has been met or not.
	 * 
	 * @param skillName
	 * @param skillId
	 * @param requirementLevel
	 */
	public String skillRequirement(String skillName, int skillId, int requirementLevel) {
		int playerSkillLevel = player.playerLevel[skillId];
		if (playerSkillLevel >= requirementLevel) {
			return "@str@" + requirementLevel + " " + skillName + "";
		}
		return "@dre@" + requirementLevel + " " + skillName + "";
	}
}
