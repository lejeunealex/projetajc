package com.sopra.resa.dao;

import java.io.Serializable;
import java.util.List;

//CrudRepository<T,K> équivalent
public interface DaoGeneric<T, K extends Serializable> {

	public T insert(T obj);

	public T update(T obj);

	public void delete(T obj);

	public T findByKey(K key);

	public List<T> findAll();
}
