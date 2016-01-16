/**
 * (c) 2016 dmulloy2
 */
package net.dmulloy2.ultimatetictactoe.types;

/**
 * @author dmulloy2
 */

public class Versioning {
	public static String NAME = "Ultimate TicTacToe";
	public static String AUTHORS = "Dan Mulloy and PJ McFarlane";
	public static String VERSION = getVersion();
	public static String YEAR = "2016";

	private static String getVersion() {
		String version = Versioning.class.getPackage().getImplementationVersion();
		return version != null ? version : "1.0";
	}
}