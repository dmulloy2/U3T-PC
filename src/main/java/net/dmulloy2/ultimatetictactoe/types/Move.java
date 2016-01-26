/**
 * (c) 2016 Dan Mulloy
 */
package net.dmulloy2.ultimatetictactoe.types;

/**
 * Represents the last move made.
 * @author Dan Mulloy
 */
public class Move {
	private final Box majorBox;
	private final Box minorBox;
	private final Player player;

	public Move(Box major, Box minor, Player player) {
		this.majorBox = major;
		this.minorBox = minor;
		this.player = player;
	}

	public Box getMajorBox() {
		return majorBox;
	}

	public Box getMinorBox() {
		return minorBox;
	}

	public Player getPlayer() {
		return player;
	}
}