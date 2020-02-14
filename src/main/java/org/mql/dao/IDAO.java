package org.mql.dao;

import java.util.List;

import org.mql.models.Entree;

public interface IDAO<T> {

	public List<T> getAll();

	void add(T t);

	void delete(String id);

	void update(T t);

	Entree getById(String id);

	List<T> findBySection(String sectionName);

}
