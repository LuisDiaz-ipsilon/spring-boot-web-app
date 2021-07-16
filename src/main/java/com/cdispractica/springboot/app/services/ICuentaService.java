package com.cdispractica.springboot.app.services;

import java.util.List;

import com.cdispractica.springboot.app.models.entity.Cuenta;

public interface ICuentaService {

	public Cuenta getById(Long id, List<Cuenta> lista);
	
}
