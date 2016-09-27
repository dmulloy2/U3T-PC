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
package net.dmulloy2.ultimatetictactoe.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import net.dmulloy2.ultimatetictactoe.util.Closer;

/**
 * Ultimate TicTacToe Configuration files
 * @author Dan Mulloy
 */
public class Config {
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final Yaml YAML = new Yaml(new SafeConstructor());

	private Map<String, Object> data = new LinkedHashMap<>();

	public Config() {
	}

	@SuppressWarnings("unchecked")
	public void load(File file) throws IOException {
		checkFile(file);

		try (Closer closer = Closer.create()) {
			FileInputStream fio = closer.register(new FileInputStream(file));
			this.data = (Map<String, Object>) YAML.load(fio);
		}
	}

	public void save(File file) throws IOException {
		checkFile(file);

		String string = YAML.dump(data);

		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), UTF_8)) {
			writer.write(string);
			writer.close();
		}
	}

	private void checkFile(File file) throws IOException {
		if (file == null || !file.exists()) {
			throw new IOException("Specified file does not exist!");
		}
		if (file.isDirectory()) {
			throw new IOException("Specified file is a directory!");
		}
	}

	public void set(String path, Object value) {
		data.put(path, value);
	}

	public Object get(String path) {
		return data.get(path);
	}

	public boolean contains(String path) {
		return data.containsKey(path);
	}

	public String getString(String path) {
		return (String) get(path);
	}

	public boolean getBoolean(String path) {
		return (boolean) get(path);
	}

	@SuppressWarnings("unchecked")
	public <K, V> Map<K,V> getMap(String path) {
		return (Map<K, V>) get(path);
	}

	public <E extends Enum<E>> E getEnum(Class<E> clazz, String path) {
		return Enum.valueOf(clazz, getString(path).toUpperCase().replace(" ", "_"));
	}
}