/**
 * Copyright (c) 2016 Dan Mulloy
 */
package net.dmulloy2.ultimatetictactoe.types;

import java.io.BufferedReader;
import java.io.FileReader;
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
			BufferedReader br = null;

			InputStream is = Instructions.class.getResourceAsStream("/instructions.txt");
			if (is != null) {
				closer.register(is);
				InputStreamReader isr = closer.register(new InputStreamReader(is, "UTF-8"));
				br = closer.register(new BufferedReader(isr));
			} else {
				FileReader fr = closer.register(new FileReader("instructions.txt"));
				br = closer.register(new BufferedReader(fr));
			}

			StringBuilder instructions = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				instructions.append(line + "\n");
			}

			Instructions.instructions = instructions.toString();
		} catch (Throwable ex) {
			instructions = "Failed to load instructions from JAR: " + ex.toString();
		}
	}
}