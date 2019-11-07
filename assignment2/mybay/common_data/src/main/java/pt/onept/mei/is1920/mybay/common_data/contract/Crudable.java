package pt.onept.mei.is1920.mybay.common_data.contract;

public interface Crudable<T> {
	boolean create(T t);
	T read(T t);
	boolean update(T t);
	boolean delete(T t);
}
