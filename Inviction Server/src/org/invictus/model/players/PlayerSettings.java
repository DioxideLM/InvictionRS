package org.invictus.model.players;

public class PlayerSettings {

	private Client player;

	public PlayerSettings(Client player) {
		this.player = player;
	}
	public static final int LOGOUT_BUTTON_ID = 9154;
	
	public static final int MOUSE_BUTTONS_BUTTON_ID = 3146;
	public static final int SPLIT_PRIVATE_CHAT_BUTTON_ID = 74184;
	public static final int ACCEPT_AID_BUTTON_ID = 74188;
	public static final int CHAT_EFFECTS_BUTTON_ID = 74180;
	public static final int AUTO_RETALIATE_BUTTON_ID = 150;
	public static final int RUN_BUTTON_ID = 74214;
	public static final int RUN_BUTTON_ORB_ID = 151;
	public static final int AREA_VOLUME_BUTTON_1 = 74206;
	public static final int AREA_VOLUME_BUTTON_2 = 74207;
	public static final int AREA_VOLUME_BUTTON_3 = 74208;
	public static final int AREA_VOLUME_BUTTON_4 = 74209;
	public static final int AREA_VOLUME_BUTTON_5 = 74210;
	public static final int BRIGHTNESS_BUTTON_1 = 3138;
	public static final int BRIGHTNESS_BUTTON_2 = 3140;
	public static final int BRIGHTNESS_BUTTON_3 = 3142;
	public static final int BRIGHTNESS_BUTTON_4 = 31444;

	public void toggleAutoRetaliate() {
		player.autoRet = !player.autoRet;
	}

	public void toggleRunStatus() {
		player.isRunning2 = !player.isRunning2;
		int frame = player.isRunning2 == true ? 1 : 0;
		player.getPlayerAssistant().sendConfig(173, frame);
		//player.getPlayerAssistant().sendConfig(504, frame);
	}

	public void toggleMouseButtons() {
		player.mouseButton = !player.mouseButton;
		int frame = player.mouseButton == true ? 1 : 0;
		player.getPlayerAssistant().sendConfig(170, frame);
		//player.getPlayerAssistant().sendConfig(500, frame);
	}

	public void toggleSplitPrivateChat() {
		player.splitChat = !player.splitChat;
		int frame = player.splitChat == true ? 1 : 0;
		player.getPlayerAssistant().sendConfig(287, frame);
		//player.getPlayerAssistant().sendConfig(502, frame);
	}

	public void toggleChatEffects() {
		player.chatEffects = !player.chatEffects;
		int frame = player.chatEffects == true ? 1 : 0;
		player.getPlayerAssistant().sendConfig(171, frame);
		//player.getPlayerAssistant().sendConfig(501, frame);
	}

	public void toggleAcceptAid() {
		player.acceptAid = !player.acceptAid;
		int frame = player.acceptAid == true ? 1 : 0;
		player.getPlayerAssistant().sendConfig(427, frame);
		//player.getPlayerAssistant().sendConfig(503, frame);
	}
	
	public void changeBrightness(int brightnessAmount) {
		switch (brightnessAmount) {
		case 1:
			player.getPlayerAssistant().sendConfig(505, 1);
			player.getPlayerAssistant().sendConfig(506, 0);
			player.getPlayerAssistant().sendConfig(507, 0);
			player.getPlayerAssistant().sendConfig(508, 0);
			player.getPlayerAssistant().sendConfig(166, 1);
			break;
		case 2:
			player.getPlayerAssistant().sendConfig(505, 0);
			player.getPlayerAssistant().sendConfig(506, 1);
			player.getPlayerAssistant().sendConfig(507, 0);
			player.getPlayerAssistant().sendConfig(508, 0);
			player.getPlayerAssistant().sendConfig(166, 2);
			break;
		case 3:
			player.getPlayerAssistant().sendConfig(505, 0);
			player.getPlayerAssistant().sendConfig(506, 0);
			player.getPlayerAssistant().sendConfig(507, 1);
			player.getPlayerAssistant().sendConfig(508, 0);
			player.getPlayerAssistant().sendConfig(166, 3);
			break;
		case 4:
			player.getPlayerAssistant().sendConfig(505, 0);
			player.getPlayerAssistant().sendConfig(506, 0);
			player.getPlayerAssistant().sendConfig(507, 0);
			player.getPlayerAssistant().sendConfig(508, 1);
			player.getPlayerAssistant().sendConfig(166, 4);
			break;
		}
	}

	public void changeAreaVolume(int volumeAmount) {
		switch (volumeAmount) {
		case 1:
			player.getPlayerAssistant().sendConfig(509, 1);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 0);
			player.getPlayerAssistant().sendConfig(513, 0);
			break;
		case 2:
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 1);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 0);
			player.getPlayerAssistant().sendConfig(513, 0);
			break;
		case 3:
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 1);
			player.getPlayerAssistant().sendConfig(512, 0);
			player.getPlayerAssistant().sendConfig(513, 0);
			break;
		case 4:
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 1);
			player.getPlayerAssistant().sendConfig(513, 0);
			break;
		case 5:
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 0);
			player.getPlayerAssistant().sendConfig(513, 1);
			break;
		}
	}

}
