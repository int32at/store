package at.int32.store;

import java.util.HashMap;

import at.int32.store.types.JsonStore;

public class Store implements IStore<String, Object> {

	private IStore<String, Object> store;

	public Store() {
		this("config.db");
	}

	public Store(String configPath) {
		this.store = new JsonStore<Object>(configPath);
	}

	@Override
	public Store put(String key, Object value) {
		this.store.put(key, value);
		return this;
	}
	
	public <M> M get(String key) {
		return this.store.get(key, null);
	}
	
	public <M> M get(String key, Class<M> mClass) {
		return this.store.get(key, mClass);
	}

	@Override
	public boolean contains(String key) {
		return this.store.contains(key);
	}
	
	@Override
	public Object remove(String key) {
		return this.store.remove(key);
	}
	
	@Override
	public Store load() {
		this.store.load();
		return this;
	}

	@Override
	public Store save() {
		this.store.save();
		return this;
	}

	@Override
	public HashMap<String, Object> getAll() {
		return this.store.getAll();
	}
}