package sopra.vol.dao;

import java.util.List;

public interface IDao<T,PK> {
	List<T> findAll();

	T findById(PK id);

	void create(T obj);

	void update(T obj);

	void delete(T obj);

	void deleteById(PK id);
}
