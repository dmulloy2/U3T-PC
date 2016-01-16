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
package net.dmulloy2.ultimatetictactoe.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Combination;
import net.dmulloy2.ultimatetictactoe.types.Conquerable;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;
import net.dmulloy2.ultimatetictactoe.types.Serializable;

/**
 * The main Tic-Tac-Toe board
 * @author Dan Mulloy
 */
public class MajorGrid extends JPanel implements Conquerable, Serializable {
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
	}

	public void init() {
		GridLayout layout = new GridLayout(3, 3, buffer, buffer);
		setLayout(layout);

		setBackground(Color.DARK_GRAY);

		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				// The grid layout goes column first, just remember to switch y and x
				MinorGrid grid = new MinorGrid(main, Box.fromCoords(y, x));
				add(grid);

				grid.init();
				minor[x][y] = grid;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void load(Map<String, Object> data) {
		GridLayout layout = new GridLayout(3, 3, buffer, buffer);
		setLayout(layout);

		setBackground(Color.DARK_GRAY);

		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				MinorGrid grid = new MinorGrid(main, Box.fromCoords(y, x));
				add(grid);

				grid.load((Map<String, Object>) data.get("minor." + x + "." + y));
				minor[x][y] = grid;
			}
		}
	}

	public void finishLoad() {
		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				minor[x][y].finishLoad();
			}
		}
	}

	public void triggerUpdate() {
		if (conquerer == null) {
			Combination comboX = Combination.threeInARow(Player.PLAYER_1, minor);
			if (comboX != null) {
				this.conquerer = Player.PLAYER_1;
				main.win(conquerer);
			}

			Combination comboO = Combination.threeInARow(Player.PLAYER_2, minor);
			if (comboO != null) {
				this.conquerer = Player.PLAYER_2;
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

				if (conquerer == Player.PLAYER_1) {
					xTotal++;
				} else if (conquerer == Player.PLAYER_2) {
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
				main.win(Player.PLAYER_1);
			} else if (oTotal > xTotal) {
				main.win(Player.PLAYER_2);
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

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		for (int x = 0; x < minor.length; x++) {
			for (int y = 0; y < minor.length; y++) {
				map.put("minor." + x + "." + y, minor[x][y].serialize());
			}
		}

		return map;
	}
}
