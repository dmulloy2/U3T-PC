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

import java.awt.GridLayout;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.dmulloy2.ultimatetictactoe.U3T;

/**
 * @author dmulloy2
 */
public class U3TBoard extends JFrame {
	private static final long serialVersionUID = - 5224324241899628439L;

	private final U3T main;

	public U3TBoard(U3T main) {
		super("Ultimate TicTacToe by Dan Mulloy");
		this.main = main;

		init();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
			}
		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void init() {
		GridLayout layout = new GridLayout(1, 1, main.getBuffer(), main.getBuffer());
		getContentPane().setLayout(layout);

		getContentPane().add(main.getMajorGrid());
		setSize(755, 755);
	}

	public void log(Level level, String message) {
		JOptionPane.showMessageDialog(null, message, "Ultimate TicTacToe", level == Level.SEVERE ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE, null);
	}
}
