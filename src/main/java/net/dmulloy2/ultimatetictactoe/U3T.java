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
package net.dmulloy2.ultimatetictactoe;

import java.util.logging.Level;

import javax.swing.JOptionPane;

import net.dmulloy2.ultimatetictactoe.gui.MajorGrid;
import net.dmulloy2.ultimatetictactoe.gui.StartGUI;
import net.dmulloy2.ultimatetictactoe.gui.U3TGUI;
import net.dmulloy2.ultimatetictactoe.internals.U3Thread;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;

/**
 * UltimateTicTacToe Main Class
 * @author Dan Mulloy
 */

// TODO List
// - Implement key
// - Implement saving
// - Figure out how to tint boxes properly
// - Implement player customization

public class U3T {
	private Box nextBox;
	private Player player;

	private int buffer = 10;

	private boolean running;
	private U3Thread thread;

	private MajorGrid majorGrid;
	private U3TGUI board;

	// ---- Starters

	public static void main(String[] args) {
		new U3Thread();
	}

	public U3T(U3Thread thread) {
		this.running = true;
		this.thread = thread;
		new StartGUI(this);
	}

	public void start() {
		this.majorGrid = new MajorGrid(this);
		this.board = new U3TGUI(this);
		this.player = Rules.starter;
		this.logNextPlayer();
	}

	// ---- Getters

	public Box getNextBox() {
		return nextBox;
	}

	public MajorGrid getMajorGrid() {
		return majorGrid;
	}

	public Player getPlayer() {
		return player;
	}

	public int getBuffer() {
		return buffer;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isMainThread() {
		return Thread.currentThread().equals(thread);
	}

	// ---- Setters

	public void setNextBox(Box nextBox) {
		this.nextBox = nextBox;
	}

	public void nextPlayer() {
		this.player = player == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
	}

	// ---- Utility

	public void error(String error) {
		log(Level.SEVERE, error);
	}

	public void info(String message) {
		log(Level.INFO, message);
	}

	public void win(Player player) {
		info(player + " has won!");
	}

	public void logNextPlayer() {
		String message = player.getSymbol() + "\'s turn";
		if (nextBox != null) {
			message += " in " + nextBox;
		}

		board.logInBox(message);
	}

	public void log(Level level, String message) {
		JOptionPane.showMessageDialog(null, message, "Ultimate Tic-Tac-Toe", level == Level.SEVERE ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE, null);
	}

	public void save() {
		
	}
}
