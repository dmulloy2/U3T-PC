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
		tabbedPane.addTab("Default", null, defaultSettings, null);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel rightPanel = new JPanel();
		settingsPanel.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BorderLayout(0, 0));

		playerSelector = new JComboBox<String>();
		playerSelector.addItem("Starting player");
		playerSelector.addItem(Player.PLAYER_1.toString());
		playerSelector.addItem(Player.PLAYER_2.toString());

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
		customSettings.setLayout(null);
		tabbedPane.addTab("Custom", null, customSettings, null);
		
		JLabel lblNotYetImplemented = new JLabel("Not yet implemented :(");
		lblNotYetImplemented.setBounds(10, 11, 154, 14);
		customSettings.add(lblNotYetImplemented);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnStart)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
				.addComponent(titleField, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnStart)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		pack();
	}

	private void start() {
		Rules.preventGambit = gambitCheckBox.isSelected();
		Rules.allowUseOfConquered = conqueredCheckBox.isSelected();
		Rules.allowCatsGame = catsGameCheckBox.isSelected();

		Rules.starter = Player.fromString((String) playerSelector.getSelectedItem());
		if (Rules.starter == null) {
			main.error("You must select a starting player!");
			return;
		}

		main.start();
		dispose();
	}
}
