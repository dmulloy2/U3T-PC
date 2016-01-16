package net.dmulloy2.ultimatetictactoe.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.dmulloy2.ultimatetictactoe.types.Instructions;

public class InstructionGUI extends JFrame {
	private static final long serialVersionUID = - 3141968953604151162L;

	public InstructionGUI() {
		super("Ultimate TicTacToe Instructions");
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(Instructions.getInstructions());
		scrollPane.setViewportView(textArea);
		setVisible(true);

		setSize(600, 700);
	}
}