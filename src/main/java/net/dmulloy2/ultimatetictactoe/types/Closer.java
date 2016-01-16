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
package net.dmulloy2.ultimatetictactoe.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing resources
 * @author Dan Mulloy
 */
public class Closer implements AutoCloseable {
	private final List<AutoCloseable> close;

	private Closer() {
		this.close = new ArrayList<>();
	}

	public <C extends AutoCloseable> C register(C register) {
		close.add(register);
		return register;
	}

	@Override
	public void close() {
		for (AutoCloseable toClose : close) {
			closeQuietly(toClose);
		}
	}

	public static Closer create() {
		return new Closer();
	}

	public static void closeQuietly(AutoCloseable close) {
		try {
			if (close != null) {
				close.close();
			}
		} catch (Throwable ex) {
		}
	}
}
