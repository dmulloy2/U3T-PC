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
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Conquerable;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;
import net.dmulloy2.ultimatetictactoe.types.Serializable;

/**
 * Individual boxes in the minor grids
 * @author Dan Mulloy
 */
public class MinorBox extends JPanel implements Conquerable, Serializable {
	private static final long serialVersionUID = - 5595318327449610027L;
	private static boolean firstMove = false;

	private Player conquerer;
	private JLabel label;

	private final MinorGrid minor;
	private final Box thisType;

	private final U3T main;

	public MinorBox(U3T main, MinorGrid minor, Box thisType) {
		super();

		this.main = main;
		this.minor = minor;
		this.thisType = thisType;
	}

	/**
	 * Initializes this component
	 */
	public void init() {
		setBackground(Color.WHITE);
		setLayout(null);

		label = new JLabel();
		add(label);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				MinorBox.this.mouseClicked();
			}
		});
	}

	public void load(Map<String, Object> data) {
		setBackground(Color.WHITE);
		setLayout(null);

		label = new JLabel();
		add(label);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				MinorBox.this.mouseClicked();
			}
		});

		if (data.containsKey("conquerer")) {
			this.conquerer = Player.valueOf((String) data.get("conquerer"));
		}
	}

	public void finishLoad() {
		if (conquerer != null) {
			drawMark(conquerer, false);
			minor.triggerUpdate();
			main.getMajorGrid().repaint();
			firstMove = true;
		}
	}

	/**
	 * Called when the mouse is clicked inside this box
	 */
	private void mouseClicked() {
		if (Rules.preventGambit && ! firstMove && thisType == Box.MIDDLE && minor.getBoxType() == Box.MIDDLE) {
			// Prevent Orlin's gambit
			main.error("You cannot go in the very middle box to start the game!");
			return;
		}

		Box nextBox = main.getNextBox();
		if (nextBox != null && nextBox != minor.getBoxType()) {
			main.error("You must go in the " + main.getNextBox() + " box!");
			return;
		}

		if (! Rules.allowUseOfConquered && minor.getConquerer() != null) {
			main.info("This box has been conquered.");
			return;
		}

		if (conquerer != null) {
			if (minor.isFull()) {
				// Arbitrary move
				main.setNextBox(null);
				main.info("This box is full. You may go anywhere on the board.");
				return;
			}

			main.error(conquerer.getName() + " has already conquered this box!");
			return;
		}

		this.conquerer = main.getPlayer();
		drawMark(conquerer, false);

		main.nextPlayer();
		main.setNextBox(thisType);

		if (! Rules.allowUseOfConquered) {
			MinorGrid nextGrid = main.getMajorGrid().getGrid(thisType);
			if (nextGrid.getConquerer() != null) {
				// Arbitrary move
				main.setNextBox(null);
			}
		}

		minor.triggerUpdate();

		if (minor.getConquerer() != null)
			main.getMajorGrid().repaint();
		
		firstMove = true;

		main.logMove(minor.getBoxType(), thisType, conquerer);
		main.logNextPlayer();
	}

	private void drawMark(Player player, boolean fill) {
		if (fill) {
			switch (player) {
				case PLAYER_1:
					setBackground(Player.PLAYER_1.getColor());
					break;
				case PLAYER_2:
					setBackground(Player.PLAYER_2.getColor());
					break;
			}

			return;
		}

		// Hacky way to get it centered
		// Basically it was centered at x=15 when width=99
		double magicRatio = 15.0D / 99.0D;
		int x = (int) (magicRatio * getWidth());

		label.setBounds(x, 0, getWidth(), getHeight());
		label.setFont(label.getFont().deriveFont(Font.BOLD, getWidth()));

		switch (player) {
			case PLAYER_1:
				label.setText(Player.PLAYER_1.getSymbol());
				label.setForeground(Player.PLAYER_1.getColor());
				break;
			case PLAYER_2:
				label.setText(Player.PLAYER_2.getSymbol());
				label.setForeground(Player.PLAYER_2.getColor());
				break;
		}
	}

	@Override
	public Player getConquerer() {
		return conquerer;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<>();
		if (conquerer != null) {
			map.put("conquerer", conquerer.name());
		}
		return map;
	}

	public void setConquerer(Player player, boolean fill) {
		this.conquerer = player;
		drawMark(player, fill);
	}

	public void clear() {
		label.setText(null);
		this.conquerer = null;
		minor.triggerUpdate();
	}
}
