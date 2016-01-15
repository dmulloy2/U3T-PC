/**
 * Copyright (c) 2016 Dan Mulloy
 */
package net.dmulloy2.ultimatetictactoe.types;

import java.util.ArrayList;
import java.util.List;

/**
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