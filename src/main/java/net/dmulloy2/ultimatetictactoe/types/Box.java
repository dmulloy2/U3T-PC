/**
 * Copyright (c) 2016 Dan Mulloy
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

/**
 * The 9 Tic-Tac-Toe boxes
 * @author Dan Mulloy
 */
public enum Box {
	TOP_LEFT(0, 0), TOP_MIDDLE(1, 0), TOP_RIGHT(2, 0),

	MIDDLE_LEFT(0, 1), MIDDLE(1, 1), MIDDLE_RIGHT(2, 1),

	BOTTOM_LEFT(0, 2), BOTTOM_MIDDLE(1, 2), BOTTOM_RIGHT(2, 2), ;

	private int x;
	private int y;

	private Box(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static Box fromCoords(int x, int y) {
		for (Box box : values()) {
			if (box.getX() == x && box.getY() == y) {
				return box;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase().replace("_", " ");
	}
}
