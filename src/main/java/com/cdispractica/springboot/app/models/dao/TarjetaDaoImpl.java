package com.cdispractica.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdispractica.springboot.app.errors.DataBaseBancoException;
import com.cdispractica.springboot.app.models.entity.Tarjeta;

@Repository
public class TarjetaDaoImpl implements ITarjetaDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)//Con esta anotacion se marca este metodo como Transactional, con el el valor unico de lectura
	@Override
	public List<Tarjeta> findAll() {
		return em.createQuery("from Tarjeta").getResultList();
	}

	@Override
	@Transactional
	public void save(Tarjeta tarjeta) throws DataBaseBancoException{
		if (tarjeta.getId() != null && tarjeta.getId() > 0) {
			try {
				em.merge(tarjeta);
			} catch (DataException e) {
				throw new DataBaseBancoException();
			}
		} else {
			try {
				em.persist(tarjeta);
			} catch (DataException e) {
				throw new DataBaseBancoException();
			}
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Tarjeta findOne(Long id) {
		return em.find(Tarjeta.class, id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}
