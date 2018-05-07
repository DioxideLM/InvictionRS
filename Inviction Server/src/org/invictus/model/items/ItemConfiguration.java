package org.invictus.model.items;

/**
 * A class containing various item configurations.
 * 
 * @author Joe
 *
 */

public class ItemConfiguration {

	/**
	 * The button ID's of the item destroy interface.
	 */
	public static final int YES_DESTROY_ITEM_BUTTON = 55095;
	public static final int NO_DESTROY_ITEM_BUTTON = 55096;

	/**
	 * An array of item ID's that can be sold to shops.
	 */
	public static final int[] ITEMS_SELLABLE = { 3842, 3844, 3840, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 10551, 6570, 7462, 7461, 7460, 7459, 7458, 7457, 7456, 7455, 7454, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764, 9803, 9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812, 9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 995 };

	/**
	 * An array of item ID's that are tradeable to other players.
	 */
	public static final int[] ITEMS_TRADEABLE = { 3842, 3844, 3840, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 10551, 6570, 7462, 7461, 7460, 7459, 7458, 7457, 7456, 7455, 7454, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764, 9803, 9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812, 9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765 };

	/**
	 * An array of item ID's that are completely destroyed instead of being dropped.
	 */
	public static final int[] UNDROPPABLE_ITEMS = { 18349 };

	/**
	 * An array of item ID's for 'fun weapons' in the Duel Arena.
	 */
	public static final int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464, 2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475, 2476, 2477 };

	/**
	 * The amount of items in the game.
	 */
	public static final int ITEM_AMOUNT = 25000;
}
