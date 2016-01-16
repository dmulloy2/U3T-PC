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
package net.dmulloy2.ultimatetictactoe.internals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.dmulloy2.ultimatetictactoe.util.Closer;

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
