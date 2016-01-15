/**
 * Copyright (c) 2016 Dan Mulloy
 */
package net.dmulloy2.ultimatetictactoe.types;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Dan Mulloy
 */
public class Instructions {

	private static String instructions;

	public static String getInstructions() {
		if (instructions == null) {
			loadInstructions();
		}

		return instructions;
	}

	private static void loadInstructions() {
		try (Closer closer = Closer.create()) {
			InputStream is = closer.register(Instructions.class.getResourceAsStream("instructions.txt"));
			InputStreamReader isr = closer.register(new InputStreamReader(is));
			BufferedReader br = closer.register(new BufferedReader(isr));

			StringBuilder instructions = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				instructions.append(line);
			}

			Instructions.instructions = instructions.toString();
		} catch (Throwable ex) {
			instructions = "Failed to load instructions from JAR: " + ex.toString();
		}
	}
}