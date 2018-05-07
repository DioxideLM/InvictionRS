package org.invictus.model.players.skills.firemaking;

public class LogData {

	public static enum logData {

		LOG(1511, 1, 1400), 
		ACHEY(2862, 1, 1400), 
		OAK(1521, 15, 1600), 
		WILLOW(1519, 30, 1900), 
		TEAK(6333, 35, 11050), 
		ARCTIC_PINE(10810, 42, 11250), 
		MAPLE(1517, 45, 11350), 
		MAHOGANY(6332, 50, 11570), 
		EUCALYPTUS(12581, 58, 11930), 
		YEW(1515, 60, 12020), 
		MAGIC(1513, 75, 13030);

		private int logId, level;
		private double xp;

		private logData(int logId, int level, double xp) {
			this.logId = logId;
			this.level = level;
			this.xp = xp;
		}

		public int getLogId() {
			return logId;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}
	}
}