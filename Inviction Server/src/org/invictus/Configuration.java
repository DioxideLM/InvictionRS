package org.invictus;

/**
 * Various Configurations for the server.
 * 
 * @author Joe
 *
 */

public class Configuration {

	/**
	 * The name of the server.
	 */
	public static final String SERVER_NAME = "Invictus";

	/**
	 * How long before the player times out.
	 */
	public static final int TIMEOUT = 20;

	/**
	 * Map tool toggle.
	 */
	public static final boolean MAP_TOOL = true;

	/**
	 * How many accounts are allowed to be logged in per IP.
	 */
	public static final int ACCOUNTS_PER_IP = 2;

	/**
	 * The amount of NPC's in the game.
	 */
	public static final int NPC_AMOUNT = 11257;

	/**
	 * The maximum amount of players allowed on the server.
	 */
	public static final int MAX_PLAYERS = 1024;

	/**
	 * Administrator restriction toggles.
	 */
	public static final boolean ADMINISTRATOR_TRADING = false;
	public static final boolean ADMINISTRATOR_DROPPING = false;
	public static final boolean ADMINISTRATOR_SELLING = false;

}
