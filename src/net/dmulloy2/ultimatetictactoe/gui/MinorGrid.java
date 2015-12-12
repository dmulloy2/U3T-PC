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
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.internals.Conquerable;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Combination;
import net.dmulloy2.ultimatetictactoe.types.Player;

/**
 * Minor Tic-Tac-Toe boards
 * @author dmulloy2
 */
public class MinorGrid extends JPanel implements Conquerable {
	private static final long serialVersionUID = - 7818649985025911957L;

	private int buffer;
	private MinorBox[][] boxes;
	private Player conquerer;
	private Box boxType;

	private final U3T main;

	public MinorGrid(U3T main, Box boxType, int buffer) {
		super();

		this.main = main;
		this.buffer = buffer;
		this.boxes = new MinorBox[3][3];
		this.boxType = boxType;

		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes.length; y++) {
				// Little hack, switch y and x
				boxes[x][y] = new MinorBox(main, this, Box.fromCoords(y, x));
			}
		}

		draw();
	}

	private void draw() {
		GridLayout layout = new GridLayout(3, 3, buffer, buffer);
		applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(layout);

		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes.length; y++) {
				add(boxes[x][y]);
			}
		}

		setSize(245, 245);
	}

	@Override
	public Player getConquerer() {
		return conquerer;
	}

	public void checkForCombo() {
		if (conquerer == null) {
			Combination comboX = Combination.threeInARow(Player.X, boxes);
			if (comboX != null) {
				this.conquerer = Player.X;

				Graphics graphics = getGraphics();
				graphics.setColor(new Color(0, 0, 255, 50));
				graphics.fillRect(0, 0, getWidth(), getHeight());
			}

			Combination comboO = Combination.threeInARow(Player.O, boxes);
			if (comboO != null) {
				this.conquerer = Player.O;

				Graphics graphics = getGraphics();
				graphics.setColor(new Color(255, 0, 0, 50));
				graphics.fillRect(0, 0, getWidth(), getHeight());
			}

			if (conquerer != null) {
				main.getMainBoard().checkForCombo();
			}
		}
	}

	public Box getBoxType() {
		return boxType;
	}

	public boolean isFull() {
		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes.length; y++) {
				if (boxes[x][y].getConquerer() == null) {
					return false;
				}
			}
		}

		return true;
	}
}
