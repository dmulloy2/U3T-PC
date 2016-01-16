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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.dmulloy2.ultimatetictactoe.gui.MajorGrid;
import net.dmulloy2.ultimatetictactoe.gui.StartGUI;
import net.dmulloy2.ultimatetictactoe.gui.U3TGUI;
import net.dmulloy2.ultimatetictactoe.internals.U3Thread;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;
import net.dmulloy2.ultimatetictactoe.util.Closer;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * UltimateTicTacToe Main Class
 * @author Dan Mulloy
 */

// TODO List
// - Testing and polish

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

	public void win(Player player) {
		info(player.getName() + " has won!");
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

	// TODO This code could use some polish and documentation

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

				Map<String, Object> data = new HashMap<>();

				// Save settings
				data.put("rules.preventGambit", Rules.preventGambit);
				data.put("rules.allowUseOfConquered", Rules.allowUseOfConquered);
				data.put("rules.allowCatsGame", Rules.allowCatsGame);

				// Save players
				data.put("players.1", Player.PLAYER_1.serialize());
				data.put("players.2", Player.PLAYER_2.serialize());

				// Save the board
				data.put("grid", majorGrid.serialize());

				if (nextBox != null) {
					data.put("nextBox", nextBox.name());
				}

				// I need to branch out more...
				Yaml yaml = new Yaml(new SafeConstructor());
				String string = yaml.dump(data);

				try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
					writer.write(string);
					writer.close();
				} catch (UnsupportedEncodingException ex) {
					// Basically impossible, who doesn't support UTF-8
					return "Somehow your computer doesn't support UTF-8";
				} catch (FileNotFoundException ex) {
					// We already ensured it existed...
					return "File that we created doesn't exist";
				} catch (IOException ex) {
					// Generic IOException, not much we can do
					return ex.toString();
				}
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
	@SuppressWarnings("unchecked")
	public String load() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (!file.exists()) {
					return "Specified file does not exist!";
				}

				StringBuilder builder = new StringBuilder();

				try (Closer closer = Closer.create()) {
					FileReader fr = closer.register(new FileReader(file));
					BufferedReader br = closer.register(new BufferedReader(fr));

					String line = null;
					while ((line = br.readLine()) != null) {
						builder.append(line).append("\n");
					}
				} catch (FileNotFoundException ex) {
					return "Could not find a file that we made sure exists";
				} catch (IOException ex) {
					return ex.toString();
				}

				Yaml yaml = new Yaml(new SafeConstructor());
				Map<String, Object> map = (Map<String, Object>) yaml.load(builder.toString());

				// Load settings
				Rules.preventGambit = (boolean) map.get("rules.preventGambit");
				Rules.allowUseOfConquered = (boolean) map.get("rules.allowUseOfConquered");
				Rules.allowCatsGame = (boolean) map.get("rules.allowCatsGame");

				// Load players
				Player.PLAYER_1.load((Map<String, Object>) map.get("players.1"));
				Player.PLAYER_2.load((Map<String, Object>) map.get("players.2"));

				// Load the grid
				// This is the complicated part. Basically we have to load the settings now, then finish loading later

				this.majorGrid = new MajorGrid(this);
				this.board = new U3TGUI(this);
				majorGrid.load((Map<String, Object>) map.get("grid"));

				if (map.containsKey("nextBox")) {
					this.nextBox = Box.valueOf((String) map.get("nextBox"));
				}
			}

			return null;
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
}
