package com.sopra.resa.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.sopra.resa.dao.DaoGeneric;

@Transactional
public class DaoGenericImpl<T, K extends Serializable> implements DaoGeneric<T, K> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public DaoGenericImpl() {
		try {
			ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
			Type typeT = parameterizedType.getActualTypeArguments()[0];
			if (!typeT.toString().equals("T")) {
				this.persistentClass = (Class<T>) typeT;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public T insert(T obj) {
		em.persist(obj);
		return obj;
	}

	@Override
	public T update(T obj) {
		em.merge(obj);
		return obj;
	}

	@Override
	public void delete(T obj) {
		em.remove(obj);
	}

	@Override
	public T findByKey(K key) {
		return em.find(persistentClass, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return em.createQuery("SELECT o FROM " + persistentClass.getSimpleName() + " o").getResultList();
	}

}
