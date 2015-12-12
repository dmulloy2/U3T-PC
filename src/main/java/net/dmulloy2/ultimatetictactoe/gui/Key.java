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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Player;

/**
 * @author Dan Mulloy
 */
public class Key extends MinorGrid {
	private static final long serialVersionUID = 2417357033766579161L;

	public Key(U3T main, Box boxType, int buffer) {
		super(main, boxType, buffer);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				event.consume();
			}
		});
	}

	public void onConquered(Player player, Box box) {
		boxes[box.getY()][box.getX()].setConquerer(player);
	}
}
