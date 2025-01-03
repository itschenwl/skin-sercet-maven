package util;

import java.util.List;

public interface Dao<T> {
	int insert(T item) throws Exception;

	int update(T item) throws Exception;

	int deleteById(String id) throws Exception;
	
	int deleteById(Integer id) throws Exception;

	T findById(String id) throws Exception;

	T findById(Integer id) throws Exception;

	List<T> findAll() throws Exception;

}
