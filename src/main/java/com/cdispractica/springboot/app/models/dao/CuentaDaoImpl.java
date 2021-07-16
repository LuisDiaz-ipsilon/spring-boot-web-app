package com.cdispractica.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdispractica.springboot.app.models.entity.Cuenta;

@Repository
public class CuentaDaoImpl implements ICuentaDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cuenta> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cuenta").getResultList();
	}

	@Override
	@Transactional
	public void save(Cuenta cuenta) {
		if(cuenta.getId() != null && cuenta.getId() > 0) {
			em.merge(cuenta);
		} else {
			em.persist(cuenta);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findOne(Long id) {
		return em.find(Cuenta.class, id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
		
	}
	
	
	

}
