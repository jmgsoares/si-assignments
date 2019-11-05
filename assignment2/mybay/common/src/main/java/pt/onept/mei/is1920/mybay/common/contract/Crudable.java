package pt.onept.mei.is1920.mybay.common.contract;

public interface Crudable<T> {
	boolean create(T t);

	T read(T t);

	boolean update(T t1);

	boolean delete(T t);
}
