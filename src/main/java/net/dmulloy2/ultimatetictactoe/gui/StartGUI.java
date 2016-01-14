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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;

/**
 * @author Dan Mulloy
 */
public class StartGUI extends JFrame {
	private static final long serialVersionUID = - 296145553631856631L;

	private U3T main;

	public StartGUI(U3T main) {
		super("Ultimate Tic-Tac-Toe");
		this.main = main;

		init();
		setVisible(true);
	}

	private JTextField titleField;
	private JCheckBox gambitCheckBox;
	private JCheckBox conqueredCheckBox;
	private JCheckBox catsGameCheckBox;
	private JComboBox<String> playerSelector;

	private JTextField textField_1;
	private JTextField textField_4;
	private JTextField player1Symbol;
	private JTextField textField_2;
	private JTextField player1R;
	private JTextField player1G;
	private JTextField player1B;
	private JTextField player2R;
	private JTextField player2G;
	private JTextField player2B;
	private JLabel label_2;
	private JTextField player1Name;
	private JTextField player2Name;
	private JTextField player2Symbol;

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
		}
		
		titleField = new JTextField();
		titleField.setText("Ultimate Tic-Tac-Toe Settings");
		titleField.setBackground(Color.WHITE);
		titleField.setEditable(false);
		titleField.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				start();
			}
		});
		
		JPanel defaultSettings = new JPanel();
		tabbedPane.addTab("Settings", null, defaultSettings, null);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel rightPanel = new JPanel();
		settingsPanel.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BorderLayout(0, 0));

		playerSelector = new JComboBox<String>();
		playerSelector.setEditable(false);
		playerSelector.addItem("Player 1 Starts");
		playerSelector.setToolTipText("This is a vestige of the old version; unnecessary now that customization exists.");
		//playerSelector.addItem("Starting player");
		//playerSelector.addItem(Player.PLAYER_1.toString());
		//playerSelector.addItem(Player.PLAYER_2.toString());

		rightPanel.add(playerSelector, BorderLayout.SOUTH);
		
		conqueredCheckBox = new JCheckBox("Allow Use of Conquered");
		conqueredCheckBox.setSelected(true);
		rightPanel.add(conqueredCheckBox, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		settingsPanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		gambitCheckBox = new JCheckBox("Prevent Orlin's Gambit");
		leftPanel.add(gambitCheckBox, BorderLayout.NORTH);
		gambitCheckBox.setSelected(true);
		
		catsGameCheckBox = new JCheckBox("Allow Cat's Games");
		leftPanel.add(catsGameCheckBox, BorderLayout.SOUTH);

		GroupLayout defaultSettingsLayout = new GroupLayout(defaultSettings);
		defaultSettingsLayout.setHorizontalGroup(
			defaultSettingsLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(defaultSettingsLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(settingsPanel, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
					.addContainerGap())
		);
		defaultSettingsLayout.setVerticalGroup(
			defaultSettingsLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(defaultSettingsLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(settingsPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(40, Short.MAX_VALUE))
		);
		defaultSettings.setLayout(defaultSettingsLayout);
		
		JPanel customSettings = new JPanel();

		tabbedPane.addTab("Custom", null, customSettings, "Customize the players in the game");
		
		JPanel player1Panel = new JPanel();		
		JPanel player2Panel = new JPanel();

		GroupLayout customSettingsLayout = new GroupLayout(customSettings);
		customSettingsLayout.setHorizontalGroup(
			customSettingsLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(customSettingsLayout.createSequentialGroup()
					.addComponent(player1Panel, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(player2Panel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
		);
		customSettingsLayout.setVerticalGroup(
			customSettingsLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(customSettingsLayout.createSequentialGroup()
					.addGroup(customSettingsLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(player1Panel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(player2Panel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		player2R = new JTextField();
		player2R.setColumns(10);
		
		JLabel label = new JLabel("R");
		
		JLabel label_1 = new JLabel("G");
		
		player2G = new JTextField();
		player2G.setColumns(10);
		
		player2B = new JTextField();
		player2B.setColumns(10);

		Color player2Default = Player.PLAYER_2.getColor();
		player2R.setText(Integer.toString(player2Default.getRed()));
		player2G.setText(Integer.toString(player2Default.getGreen()));
		player2B.setText(Integer.toString(player2Default.getBlue()));
		
		label_2 = new JLabel("B");
		
		player2Name = new JTextField();
		player2Name.setText("Player 2");
		player2Name.setColumns(10);
		
		player2Symbol = new JTextField();
		player2Symbol.setText("O");
		player2Symbol.setColumns(10);

		GroupLayout player2Layout = new GroupLayout(player2Panel);
		player2Layout.setHorizontalGroup(
			player2Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(player2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(player2Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(player2Layout.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(player2R, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(player2B, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
						.addGroup(player2Layout.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(player2G, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addGroup(player2Layout.createSequentialGroup()
							.addComponent(player2Name, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(player2Symbol, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		player2Layout.setVerticalGroup(
			player2Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(player2Layout.createSequentialGroup()
					.addGap(12)
					.addGroup(player2Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(player2Layout.createSequentialGroup()
							.addGap(2)
							.addComponent(player2Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(player2Layout.createSequentialGroup()
							.addGap(2)
							.addComponent(player2Symbol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(player2Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(player2Layout.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(player2R, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(player2Layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(player2B, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_2)))
					.addGap(6)
					.addGroup(player2Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1)
						.addComponent(player2G, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		player2Panel.setLayout(player2Layout);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		player1Symbol = new JTextField();
		player1Symbol.setText("X");
		player1Symbol.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblR = new JLabel("R");
		
		JLabel lblG = new JLabel("G");
		
		player1R = new JTextField();
		player1R.setColumns(10);
		
		player1G = new JTextField();
		player1G.setColumns(10);
		
		JLabel lblB = new JLabel("B");

		player1B = new JTextField();
		player1B.setColumns(10);

		Color player1Default = Player.PLAYER_1.getColor();
		player1R.setText(Integer.toString(player1Default.getRed()));
		player1G.setText(Integer.toString(player1Default.getGreen()));
		player1B.setText(Integer.toString(player1Default.getBlue()));
		
		player1Name = new JTextField();
		player1Name.setText("Player 1");
		player1Name.setColumns(10);

		GroupLayout player1Layout = new GroupLayout(player1Panel);
		player1Layout.setHorizontalGroup(
			player1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(player1Layout.createSequentialGroup()
					.addGroup(player1Layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(player1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(player1Name, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(player1Symbol, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, player1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(player1Layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblR)
								.addGroup(player1Layout.createSequentialGroup()
									.addComponent(lblG)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(player1Layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(player1G, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(player1R, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
							.addGroup(player1Layout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(player1Layout.createSequentialGroup()
									.addGap(55)
									.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE))
								.addGroup(player1Layout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblB)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(player1B, 0, 0, Short.MAX_VALUE)))))
					.addGap(57)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		player1Layout.setVerticalGroup(
			player1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(player1Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(player1Layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(player1Symbol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(player1Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(player1Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(player1Layout.createSequentialGroup()
							.addGap(32)
							.addGroup(player1Layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(player1Layout.createSequentialGroup()
							.addGroup(player1Layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblR)
								.addComponent(player1R, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblB)
								.addComponent(player1B, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(player1Layout.createParallelGroup(Alignment.LEADING)
								.addComponent(player1G, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblG))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		player1Panel.setLayout(player1Layout);
		customSettings.setLayout(customSettingsLayout);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(titleField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnStart)
								.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
							.addGap(0, 0, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnStart)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		JPanel continuePanel = new JPanel();
		tabbedPane.addTab("Continue", null, continuePanel, "Continue a previously saved game");
		
		JLabel lblWhenCompletedThis = new JLabel("When completed, this will allow you to continue saved games :)");
		GroupLayout gl_continuePanel = new GroupLayout(continuePanel);
		gl_continuePanel.setHorizontalGroup(
			gl_continuePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_continuePanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblWhenCompletedThis)
					.addContainerGap())
		);
		gl_continuePanel.setVerticalGroup(
			gl_continuePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_continuePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWhenCompletedThis)
					.addContainerGap(72, Short.MAX_VALUE))
		);
		continuePanel.setLayout(gl_continuePanel);
		pack();
	}

	private void start() {
		Rules.preventGambit = gambitCheckBox.isSelected();
		Rules.allowUseOfConquered = conqueredCheckBox.isSelected();
		Rules.allowCatsGame = catsGameCheckBox.isSelected();

		/*Rules.starter = Player.fromString((String) playerSelector.getSelectedItem());
		if (Rules.starter == null) {
			main.error("You must select a starting player!");
			return;
		}*/

		// For simplicity's sake, player 1 will always start
		Rules.starter = Player.PLAYER_1;

		String name1 = player1Name.getText();
		if (!name1.isEmpty()) {
			Player.PLAYER_1.setName(name1);
		}

		String name2 = player2Name.getText();
		if (!name2.isEmpty()) {
			Player.PLAYER_2.setName(name2);
		}

		String symbol1 = player1Symbol.getText();
		if (symbol1.length() == 1) {
			Player.PLAYER_1.setSymbol(symbol1);
		}

		String symbol2 = player2Symbol.getText();
		if (symbol2.length() == 1) {
			Player.PLAYER_2.setSymbol(symbol2);
		}

		try {
			Player.PLAYER_1.setColor(parseRGB(player1R.getText(), player1G.getText(), player1B.getText()));
		} catch (NumberFormatException ex) {
			main.error("One of Player 1's RGB values was entered incorrectly!");
			return;
		}

		try {
			Player.PLAYER_2.setColor(parseRGB(player2R.getText(), player2G.getText(), player2B.getText()));
		} catch (NumberFormatException ex) {
			main.error("One of Player 2's RGB values was entered incorrectly!");
			return;
		}

		main.start();
		dispose();
	}

	private Color parseRGB(String r, String g, String b) {
		return new Color(rgbStandard(Integer.parseInt(r)), rgbStandard(Integer.parseInt(g)), rgbStandard(Integer.parseInt(b)));
	}

	private int rgbStandard(int i) {
		return Math.max(0,  Math.min(i, 255));
	}
}