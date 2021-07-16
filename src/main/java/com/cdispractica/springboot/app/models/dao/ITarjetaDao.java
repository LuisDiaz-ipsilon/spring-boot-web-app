package com.cdispractica.springboot.app.models.dao;

import java.util.List;

import com.cdispractica.springboot.app.models.entity.Tarjeta;

public interface ITarjetaDao {
	
	public List<Tarjeta> findAll();
	
	public void save(Tarjeta tarjeta);
	
	public Tarjeta findOne(Long id);
	
	public void delete(Long id);

}
