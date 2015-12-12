/**
 * Copyright (c) 2015 Dan Mulloy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.dmulloy2.ultimatetictactoe.types;

import static net.dmulloy2.ultimatetictactoe.types.Box.BOTTOM_LEFT;
import static net.dmulloy2.ultimatetictactoe.types.Box.BOTTOM_MIDDLE;
import static net.dmulloy2.ultimatetictactoe.types.Box.BOTTOM_RIGHT;
import static net.dmulloy2.ultimatetictactoe.types.Box.MIDDLE;
import static net.dmulloy2.ultimatetictactoe.types.Box.MIDDLE_LEFT;
import static net.dmulloy2.ultimatetictactoe.types.Box.MIDDLE_RIGHT;
import static net.dmulloy2.ultimatetictactoe.types.Box.TOP_LEFT;
import static net.dmulloy2.ultimatetictactoe.types.Box.TOP_MIDDLE;
import static net.dmulloy2.ultimatetictactoe.types.Box.TOP_RIGHT;
import net.dmulloy2.ultimatetictactoe.internals.Conquerable;

/**
 * A list of all the possible combinations of Tic-Tac-Toe
 * @author dmulloy2
 */
public enum Combination {
	FIRST(TOP_LEFT, TOP_MIDDLE, TOP_RIGHT),
	SECOND(TOP_LEFT, MIDDLE, BOTTOM_RIGHT),
	THIRD(TOP_LEFT, MIDDLE_LEFT, BOTTOM_LEFT),
	FOURTH(TOP_MIDDLE, MIDDLE, BOTTOM_MIDDLE),
	FIFTH(TOP_RIGHT, MIDDLE, BOTTOM_LEFT),
	SIXTH(TOP_RIGHT, MIDDLE_RIGHT, BOTTOM_RIGHT),
	SEVENTH(MIDDLE_LEFT, MIDDLE, MIDDLE_RIGHT),
	EIGTH(BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT),
	;

	private Box[] boxes;

	private Combination(Box... boxes) {
		this.boxes = boxes;
	}

	public Box[] getBoxes() {
		return boxes;
	}

	public static <T extends Conquerable> Combination threeInARow(Player player, T[][] boxes) {
		Combination[] combinations = Combination.values();

		comboLabel: for (Combination combination : combinations) {
			Box[] required = combination.getBoxes();

			for (Box box : required) {
				T check = boxes[box.getX()][box.getY()];
				if (check.getConquerer() != player) {
					continue comboLabel;
				}
			}

			return combination;
		}

		return null;
	}
}
