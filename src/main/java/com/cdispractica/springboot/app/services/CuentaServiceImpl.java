package com.cdispractica.springboot.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdispractica.springboot.app.models.entity.Cuenta;

@Service
public class CuentaServiceImpl implements ICuentaService {
	
	private List<Cuenta> lista;

	public CuentaServiceImpl() {
		
	}
	
	@Override
	public Cuenta getById(Long id, List<Cuenta> lista) {
		this.lista = lista;
		Cuenta cuentaResult = null;
		
		for(Cuenta cuenta : this.lista) {
			if(id == cuenta.getId()) {
				cuentaResult = cuenta;
				break;
			}
		}
		return cuentaResult; 
	}

}
