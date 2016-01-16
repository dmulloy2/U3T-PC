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

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * The available players in U3T
 * @author Dan Mulloy
 */
public enum Player implements Serializable {
	PLAYER_1("Player 1", "X", Constants.BLUE),
	PLAYER_2("Player 2", "O", Constants.RED),
	;

	private String name;
	private String symbol;
	private Color color;

	private Player(String defName, String defSymbol, Color defColor) {
		this.name = defName;
		this.symbol = defSymbol;
		this.color = defColor;
	}

	public void load(Map<String, Object> data) {
		this.name = (String) data.get("name");
		this.symbol = (String) data.get("symbol");
		this.color = new Color((int) data.get("color"));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return symbol + " (Color: " + (color == Constants.BLUE ? "blue" : "red") + ")";
	}

	public static Player fromString(String string) {
		for (Player player : values()) {
			if (player.toString().equals(string)) {
				return player;
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<>();
		data.put("name", name);
		data.put("symbol", symbol);
		data.put("color", color.getRGB());
		return data;
	}
}
