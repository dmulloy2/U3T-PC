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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Player;
import net.dmulloy2.ultimatetictactoe.types.Rules;

/**
 * @author dmulloy2
 */
public class StartGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final U3T main;

	public StartGUI(U3T main) {
		super("UltimateTicTacToe");
		this.main = main;

		init();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
			}
		});
	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
		}

		JLabel lblUltimateTictactoeSettings = new JLabel("Ultimate Tic-Tac-Toe Settings");

		final JCheckBox gambitCheckBox = new JCheckBox("Prevent Orlin's Gambit");
		gambitCheckBox.setToolTipText("Prevents a sequence to start the game wherein the first player can completely conquer the middle");
		gambitCheckBox.setSelected(true);

		final JCheckBox conqueredCheckBox = new JCheckBox("Allow use of conquered boxes");
		conqueredCheckBox.setToolTipText("Allow people to go in conquered boxes");
		conqueredCheckBox.setSelected(true);

		final JCheckBox catsGamesCheckBox = new JCheckBox("Allow Cat's games");
		catsGamesCheckBox.setToolTipText("Allow cat's games. If not, the winner is determined by the most number of boxes");
		catsGamesCheckBox.setSelected(false);

		final JComboBox<Player> comboBox = new JComboBox<Player>();
		comboBox.addItem(Player.X);
		comboBox.addItem(Player.O);

		JLabel lblStartingPlayer = new JLabel("Starting Player:");

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Rules.preventGambit = gambitCheckBox.isSelected();
				Rules.allowUseOfConquered = conqueredCheckBox.isSelected();
				Rules.allowCatsGame = catsGamesCheckBox.isSelected();
				Rules.starter = (Player) comboBox.getSelectedItem();
				main.start();

				dispose();
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblUltimateTictactoeSettings).addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false).addGroup(groupLayout.createSequentialGroup().addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(159).addComponent(btnStart)).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(gambitCheckBox).addComponent(catsGamesCheckBox)).addGap(56).addComponent(conqueredCheckBox)))).addContainerGap(68, Short.MAX_VALUE)).addGroup(groupLayout.createSequentialGroup().addComponent(lblStartingPlayer).addContainerGap(349, Short.MAX_VALUE)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblUltimateTictactoeSettings).addGap(18).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(gambitCheckBox).addComponent(conqueredCheckBox)).addGap(18).addComponent(catsGamesCheckBox).addGap(18).addComponent(lblStartingPlayer).addGap(7).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnStart).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(92, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
		setSize(400, 225);
	}
}
