package at.int32.store.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import at.int32.store.IStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class JsonStore<T> implements IStore<String, T> {

	private Gson gson;
	private String configPath;
	private HashMap<String, Object> map;

	public JsonStore(String configPath) {
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.configPath = configPath;
		this.map = new HashMap<String, Object>();
	}

	@Override
	public JsonStore<T> put(String key, T value) {
		this.map.put(key, value);
		return this;
	}

	@Override
	public <M> M get(String key, Class<M> mClass) {
		Object o = map.get(key);
		
		if(o == null)
			return null;

		if (o.getClass() == LinkedTreeMap.class || mClass != null) {
			M data = gson.fromJson(o.toString(), mClass);
			this.map.put(key, data);
			return data;
		} else {
			return (M) o;
		}
	}

	@Override
	public boolean contains(String key) {
		return map.containsKey(key);
	}

	@Override
	public JsonStore<T> load() {
		try {
			File file = new File(configPath);
			String json = new String(Files.readAllBytes(file.toPath())).trim();
			map = gson.fromJson(json, map.getClass());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (map == null)
				map = new HashMap<String, Object>();
		}

		return this;
	}

	@Override
	public JsonStore<T> save() {
		PrintWriter writer = null;
		try {
			String json = gson.toJson(map);
			writer = new PrintWriter(configPath);
			writer.write(json);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}

		return this;
	}

	@Override
	public HashMap<String, T> getAll() {
		HashMap<String, T> m = new HashMap<String, T>();
		for (String key : map.keySet()) {
			m.put(key, (T) map.get(key));
		}
		return m;
	}
}
