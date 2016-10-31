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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Combination;
import net.dmulloy2.ultimatetictactoe.types.Conquerable;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Serializable;

/**
 * Minor Tic-Tac-Toe boards
 * @author Dan Mulloy
 */
public class MinorGrid extends JPanel implements Conquerable, Serializable {
	private static final long serialVersionUID = - 7818649985025911957L;

	private int buffer;
	protected MinorBox[][] boxes;
	private Box boxType;

	private Combination combo;
	private Player conquerer;
	private Color tint;

	protected final U3T main;

	public MinorGrid(U3T main, Box boxType) {
		super();

		this.main = main;
		this.buffer = main.getBuffer();
		this.boxes = new MinorBox[3][3];
		this.boxType = boxType;
	}

	@FunctionalInterface
	public static interface CoordConsumer {
		void accept(int x, int y);
	}

	@FunctionalInterface
	public static interface BoxConsumer {
		void accept(MinorBox box);
	}

	protected void forEach(CoordConsumer iter) {
		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes.length; y++) {
				iter.accept(x, y);
			}
		}
	}

	protected void forEach(BoxConsumer iter) {
		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes.length; y++) {
				iter.accept(boxes[x][y]);
			}
		}
	}

	public void init() {
		GridLayout layout = new GridLayout(3, 3, buffer, buffer);
		applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(layout);

		forEach((x, y) -> {
			MinorBox box = new MinorBox(main, this, Box.fromCoords(y, x));
			add(box);

			box.init();
			boxes[x][y] = box;
		});
	}

	@SuppressWarnings("unchecked")
	public void load(Map<String, Object> data) {
		GridLayout layout = new GridLayout(3, 3, buffer, buffer);
		applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(layout);

		forEach((x, y) -> {
			MinorBox box = new MinorBox(main, this, Box.fromCoords(y, x));
			add(box);

			box.load((Map<String, Object>) data.get("boxes." + x + "." + y));
			boxes[x][y] = box;
		});

		if (data.containsKey("combo")) {
			this.combo = Combination.valueOf((String) data.get("combo"));
		}
	}

	public void finishLoad() {
		forEach(box -> box.finishLoad());
	}

	@Override
	public Player getConquerer() {
		return conquerer;
	}

	public void triggerUpdate() {
		triggerUpdate(false);
	}

	public void triggerUpdate(boolean clean) {
		if (clean) conquerer = null;

		if (conquerer == null) {
			Combination comboX = Combination.threeInARow(Player.PLAYER_1, boxes);
			if (comboX != null) {
				this.combo = comboX;
				this.conquerer = Player.PLAYER_1;
				paint(getGraphics());
			}

			Combination comboO = Combination.threeInARow(Player.PLAYER_2, boxes);
			if (comboO != null) {
				this.combo = comboO;
				this.conquerer = Player.PLAYER_2;
				paint(getGraphics());
			}

			if (conquerer != null || isFull()) {
				main.getMajorGrid().triggerUpdate();
				
				if (conquerer != null) {
					main.getBoard().getKey().onConquered(conquerer, boxType);
				}
			}
		} else if (clean) {
			paint(getGraphics(), clean);
		}
	}

	public void paint(Graphics graphics, boolean clean) {
		super.paint(graphics);
		if (conquerer == null || graphics == null) {
			if (! clean) {
				return;
			}

			graphics.clearRect(0, 0, getWidth(), getHeight());
			return;
		}

		Color color = graphics.getColor();

		if (tint == null) {
			tint = derive(conquerer.getColor(), 50);
		}

		graphics.setColor(tint);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (combo != null) {
			Graphics2D graphics2 = (Graphics2D) graphics;
			Stroke old = graphics2.getStroke();
			graphics2.setStroke(new BasicStroke(10f));

			Box[] boxes = combo.getBoxes();
			// Draw the line from the first to last
			MinorBox first = getBox(boxes[0]);
			MinorBox last = getBox(boxes[2]);

			// Middle of the first
			int x1 = first.getX() + first.getWidth() / 2;
			int y1 = first.getY() + first.getHeight() / 2;

			// Middle of the last
			int x2 = last.getX() + last.getWidth() / 2;
			int y2 = last.getY() + last.getHeight() / 2;

			graphics.setColor(conquerer.getColor());
			graphics.drawLine(x1, y1, x2, y2);
			graphics2.setStroke(old);
		}

		graphics.setColor(color);
	}

	private MinorBox getBox(Box box) {
		return boxes[box.getX()][box.getY()];
	}

	@Override
	public void paint(Graphics graphics) {
		paint(graphics, false);
	}

	private Color derive(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	public Box getBoxType() {
		return boxType;
	}

	public boolean isFull() {
		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes.length; y++) {
				if (boxes[x][y].getConquerer() == null)
					return false;
			}
		}

		return true;
	}

	protected void clear() {
		forEach(box -> box.clear());
	}

	public Color getTint() {
		return tint;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		forEach((x, y) -> map.put("boxes." + x + "." + y, boxes[x][y].serialize()));

		if (combo != null) {
			map.put("combo", combo.name());
		}

		return map;
	}
}
