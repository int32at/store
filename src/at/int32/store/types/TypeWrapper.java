package at.int32.store.types;

public class TypeWrapper<T> {

	public T data;
	public String type;

	public TypeWrapper(T data) {
		this.data = data;
		this.type = data.getClass().getCanonicalName();
	}
}
