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
package net.dmulloy2.ultimatetictactoe;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.dmulloy2.ultimatetictactoe.config.Config;
import net.dmulloy2.ultimatetictactoe.gui.MajorGrid;
import net.dmulloy2.ultimatetictactoe.gui.StartGUI;
import net.dmulloy2.ultimatetictactoe.gui.U3TGUI;
import net.dmulloy2.ultimatetictactoe.internals.U3Thread;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Move;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;

/**
 * UltimateTicTacToe Main Class
 * @author Dan Mulloy
 */

// TODO List

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

	/**
	 * Actually starts the game
	 */
	public void start() {
		this.majorGrid = new MajorGrid(this);
		this.board = new U3TGUI(this);
		majorGrid.init();

		this.player = Rules.starter;
		logNextPlayer();
	}

	// ---- Getters

	public Box getNextBox() {
		return nextBox;
	}

	public MajorGrid getMajorGrid() {
		return majorGrid;
	}

	public U3TGUI getBoard() {
		return board;
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

	/**
	 * Switches to the next player
	 */
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

	public void win(final Player player) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				info(player.getName() + " has won!");
			}
		});
	}

	public void logNextPlayer() {
		String message = player.getName() + "\'s turn";
		if (nextBox != null) {
			message += " in " + nextBox;
		}

		board.logInBox(message);
	}

	/**
	 * Logs a message to a pop-up dialog box
	 * @param level Logging level, can be INFO or SEVERE
	 * @param message Message to log
	 */
	public void log(Level level, String message) {
		JOptionPane.showMessageDialog(null, message, "Ultimate Tic-Tac-Toe", level == Level.SEVERE ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE, null);
	}

	/**
	 * Saves the game to disk.
	 * @return Result, null if successful
	 */
	public String save() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (file.exists()) {
					// Allow the overwriting of files ending in .u3t
					if (file.getName().endsWith(".u3t")) {
						file.delete();
					} else {
						return "You cannot select a file that already exists!";
					}
				}

				// Append .u3t to the end if they don't specify an extension
				if (!file.getName().contains(".")) {
					file = new File(file.getAbsolutePath() + ".u3t");
					if (file.exists()) {
						file.delete();
					}
				}

				try {
					file.createNewFile();
				} catch (IOException ex) {
					return ex.toString();
				}

				Config config = new Config();

				// Save settings
				config.set("rules.preventGambit", Rules.preventGambit);
				config.set("rules.allowUseOfConquered", Rules.allowUseOfConquered);
				config.set("rules.allowCatsGame", Rules.allowCatsGame);

				// Save players
				config.set("players.1", Player.PLAYER_1.serialize());
				config.set("players.2", Player.PLAYER_2.serialize());

				// Save the board
				config.set("grid", majorGrid.serialize());

				if (nextBox != null) {
					config.set("nextBox", nextBox.name());
				}

				config.save(file);
			}

			return null;
		} catch (Throwable ex) {
			ex.printStackTrace();
			return ex.toString();
		}
	}

	/**
	 * Loads a previously saved game from disk.
	 * @return Result, null if successful
	 */
	public String load() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();

				Config config = new Config();
				config.load(file);

				// Load settings
				Rules.preventGambit = config.getBoolean("rules.preventGambit");
				Rules.allowUseOfConquered = config.getBoolean("rules.allowUseOfConquered");
				Rules.allowCatsGame = config.getBoolean("rules.allowCatsGame");

				// Load players
				Player.PLAYER_1.load(config.<String, Object>getMap("players.1"));
				Player.PLAYER_2.load(config.<String, Object>getMap("players.2"));

				// Load the grid
				// We have to load the settings now, then finish loading later

				this.majorGrid = new MajorGrid(this);
				this.board = new U3TGUI(this);
				majorGrid.load(config.<String, Object>getMap("grid"));

				if (config.contains("nextBox")) {
					this.nextBox = config.getEnum(Box.class, "nextBox");
				}

				return null;
			} else {
				return "No file selected!";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return ex.toString();
		}
	}

	public void finishLoad() {
		majorGrid.finishLoad();
		this.player = Rules.starter = Player.PLAYER_1;
		logNextPlayer();
	}

	private Move lastMove;

	public void logMove(Box major, Box minor, Player player) {
		this.lastMove = new Move(major, minor, player);
	}

	public void undo() {
		if (lastMove == null) {
			error("There is no move to undo!");
			return;
		}

		majorGrid.undo(lastMove);
		this.player = lastMove.getPlayer();
	}
}
