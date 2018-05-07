package org.invictus.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.invictus.model.players.Client;

public class Announcements {

	Properties p = new Properties();
	Client player;

	public Announcements(Client Client) {
		this.player = Client;
	}

	public void loadAnnouncements() {
		try {
			loadCfgFile();
			if (p.getProperty("announcement1").length() > 0) {
				player.sendMessage(p.getProperty("announcement1"));
			}
			if (p.getProperty("announcement2").length() > 0) {
				player.sendMessage(p.getProperty("announcement2"));
			}
			if (p.getProperty("announcement3").length() > 0) {
				player.sendMessage(p.getProperty("announcement3"));
			}
			if (p.getProperty("announcement4").length() > 0) {
				player.sendMessage(p.getProperty("announcement4"));
			}
		} catch (Exception e) {
		}
	}

	private void loadCfgFile() {
		try {
			p.load(new FileInputStream("./data/announcements.cfg"));
		} catch (Exception e) {
		}
	}
}