package at.int32.store;

import java.util.HashMap;

public interface IStore<T, K> {

	public IStore<T, K> put(T key, K value);

	public HashMap<T, K> getAll();
	
	public <M> M get(String key, Class<M> mClass);

	public boolean contains(T key);

	public IStore<T, K> load();

	public IStore<T, K> save();

}
