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
package net.dmulloy2.ultimatetictactoe.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.internals.Conquerable;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Combination;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;

/**
 * The main Tic-Tac-Toe board
 * @author dmulloy2
 */
public class MajorGrid extends JPanel implements Conquerable {
	private static final long serialVersionUID = - 3516271935043975261L;

	private int buffer;
	private MinorGrid[][] minor;

	private Player conquerer;

	private final U3T main;

	public MajorGrid(U3T main) {
		super();

		this.main = main;
		this.buffer = main.getBuffer();
		this.minor = new MinorGrid[3][3];

		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				// Little hack, switch y and x
				minor[x][y] = new MinorGrid(main, Box.fromCoords(y, x), main.getBuffer());
			}
		}

		draw();
	}

	private void draw() {
		GridLayout layout = new GridLayout(3, 3, buffer, buffer);
		setLayout(layout);

		setBackground(Color.DARK_GRAY);

		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				add(minor[x][y]);
			}
		}

		setSize(735, 735);
	}

	public void triggerUpdate() {
		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				minor[x][y].triggerUpdate();
			}
		}

		if (conquerer == null) {
			Combination comboX = Combination.threeInARow(Player.X, minor);
			if (comboX != null) {
				this.conquerer = Player.X;
				main.win(conquerer);
			}

			Combination comboO = Combination.threeInARow(Player.O, minor);
			if (comboO != null) {
				this.conquerer = Player.O;
				main.win(conquerer);
			}
		}

		if (conquerer != null) {
			return;
		}

		// Check for cat's game
		int xTotal = 0;
		int oTotal = 0;
		int cats = 0;

		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				MinorGrid grid = minor[x][y];
				Player conquerer = grid.getConquerer();

				if (conquerer == Player.X) {
					xTotal++;
				} else if (conquerer == Player.O) {
					oTotal++;
				} else if (grid.isFull()) {
					cats++;
				}
			}
		}

		if (xTotal + oTotal + cats == 9) {
			// We have a cat's game. Bummer.
			// Check the rulebook!
			if (Rules.allowCatsGame) {
				main.info("We have a Cat's game!");
				return;
			}

			// Now let's see if someone had more
			if (xTotal > oTotal) {
				main.win(Player.X);
			} else if (oTotal > xTotal) {
				main.win(Player.O);
			} else {
				main.info("We have a Cat's game!");
			}
		} else {
			// Keep on playin'
		}
	}

	@Override
	public Player getConquerer() {
		return conquerer;
	}

	public MinorGrid getGrid(Box boxType) {
		return minor[boxType.getY()][boxType.getX()];
	}
}
