package org.invictus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.invictus.clip.region.ObjectDef;
import org.invictus.clip.region.Region;
import org.invictus.event.CycleEventHandler;
import org.invictus.model.npcs.NPCDrops;
import org.invictus.model.npcs.NPCHandler;
import org.invictus.model.players.Client;
import org.invictus.model.players.Player;
import org.invictus.model.players.PlayerHandler;
import org.invictus.model.players.PlayerSave;
import org.invictus.model.players.content.minigames.castlewars.CastleWars;
import org.invictus.model.players.content.minigames.fightcaves.FightCaves;
import org.invictus.model.players.content.minigames.fightpits.FightPits;
import org.invictus.model.players.content.minigames.pestcontrol.PestControl;
import org.invictus.model.shops.ShopHandler;
import org.invictus.net.ConnectionHandler;
import org.invictus.net.ConnectionThrottleFilter;
import org.invictus.util.SimpleTimer;
import org.invictus.util.log.Logger;
import org.invictus.util.maptool.MapClient;
import org.invictus.world.ClanChatHandler;
import org.invictus.world.GroundItemHandler;
import org.invictus.world.ObjectHandler;
import org.invictus.world.ObjectManager;

/**
 * The server class.
 * 
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 * 
 */

public class Server {

	public static boolean sleeping;
	public static final int cycleRate;
	public static boolean UpdateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	private static IoAcceptor acceptor;
	private static ConnectionHandler connectionHandler;
	private static ConnectionThrottleFilter throttleFilter;
	private static SimpleTimer engineTimer, debugTimer;
	private static long cycleTime, cycles, totalCycleTime, sleepTime;
	private static DecimalFormat debugPercentFormat;
	public static boolean shutdownServer = false;
	public static int garbageCollectDelay = 40;
	public static boolean shutdownClientHandler;
	public static int serverlistenerPort;
	public static GroundItemHandler itemHandler = new GroundItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NPCHandler npcHandler = new NPCHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static CastleWars castleWars = new CastleWars();
	public static FightPits fightPits = new FightPits();
	public static PestControl pestControl = new PestControl();
	public static NPCDrops npcDrops = new NPCDrops();
	public static ClanChatHandler clanChat = new ClanChatHandler();
	public static FightCaves fightCaves = new FightCaves();
	//public static MapClient mapClient = new MapClient();
	// public static WorldMap worldMap = new WorldMap();
	// private static final WorkerThread engine = new WorkerThread();

	static {
		serverlistenerPort = 43594;
		cycleRate = 600;
		shutdownServer = false;
		engineTimer = new SimpleTimer();
		debugTimer = new SimpleTimer();
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat("0.0#%");
	}

	// height,absX,absY,toAbsX,toAbsY,type
	/*
	 * public static final boolean checkPos(int height,int absX,int absY,int
	 * toAbsX,int toAbsY,int type) { return
	 * I.I(height,absX,absY,toAbsX,toAbsY,type); }
	 */
	public static void main(java.lang.String args[]) throws NullPointerException, IOException {
		/**
		 * Starting Up Server
		 */

		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));
		System.out.println("Launching " + Configuration.SERVER_NAME + "...");

		if (Configuration.MAP_TOOL) {
			MapClient.main(args);
		}

		/**
		 * World Map Loader
		 */
		// if(!Config.SERVER_DEBUG)
		// VirtualWorld.init();
		// WorldMap.loadWorldMap();

		/**
		 * Script Loader
		 */
		// ScriptManager.loadScripts();

		/**
		 * Accepting Connections
		 */
		acceptor = new SocketAcceptor();
		connectionHandler = new ConnectionHandler();

		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay(false);
		sac.setReuseAddress(true);
		sac.setBacklog(100);

		throttleFilter = new ConnectionThrottleFilter(100);
		sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
		acceptor.bind(new InetSocketAddress(serverlistenerPort), connectionHandler, sac);

		/**
		 * Initialise Handlers
		 */
		//EventManager.initialize(); //CycleEvent Implemented
		Connection.initialize();

		ObjectDef.loadConfig();
		Region.load();
		// PlayerSaving.initialize();
		// MysqlManager.createConnection();

		/**
		 * Server Successfully Loaded
		 */
		System.out.println("Server listening on port: " + serverlistenerPort);
		/**
		 * Main Server Tick
		 */
		try {
			while (!Server.shutdownServer) {
				if (sleepTime >= 0)
					Thread.sleep(sleepTime);
				else
					Thread.sleep(600);
				engineTimer.reset();
				itemHandler.process();
				CycleEventHandler.getSingleton().process();
				playerHandler.process();
				npcHandler.process();
				shopHandler.process();
				objectManager.process();
				fightPits.process();
				pestControl.process();
				cycleTime = engineTimer.elapsed();
				sleepTime = cycleRate - cycleTime;
				totalCycleTime += cycleTime;
				cycles++;
				debug();
				garbageCollectDelay--;
				if (garbageCollectDelay == 0) {
					garbageCollectDelay = 40;
					System.gc();
				}
				if (System.currentTimeMillis() - lastMassSave > 300000) {
					for (Player p : PlayerHandler.players) {
						if (p == null)
							continue;
						PlayerSave.saveGame((Client) p);
						System.out.println("Saved game for " + p.playerName + ".");
						lastMassSave = System.currentTimeMillis();
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A fatal exception has been thrown!");
			for (Player p : PlayerHandler.players) {
				if (p == null)
					continue;
				PlayerSave.saveGame((Client) p);
				System.out.println("Saved game for " + p.playerName + ".");
			}
		}
		acceptor = null;
		connectionHandler = null;
		sac = null;
		System.exit(0);
	}

	public static void processAllPackets() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				while (PlayerHandler.players[j].processQueuedPackets())
					;
			}
		}
	}

	public static boolean playerExecuted = false;

	private static void debug() {
		if (debugTimer.elapsed() > 360 * 1000 || playerExecuted) {
			long averageCycleTime = totalCycleTime / cycles;
			System.out.println("Average Cycle Time: " + averageCycleTime + "ms");
			double engineLoad = ((double) averageCycleTime / (double) cycleRate);
			System.out.println("Players online: " + PlayerHandler.playerCount + ", engine load: " + debugPercentFormat.format(engineLoad));
			totalCycleTime = 0;
			cycles = 0;
			System.gc();
			System.runFinalization();
			debugTimer.reset();
			playerExecuted = false;
		}
	}

	public static long getSleepTimer() {
		return sleepTime;
	}

}
