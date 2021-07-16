package com.cdispractica.springboot.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdispractica.springboot.app.models.dao.ICuentaDao;
import com.cdispractica.springboot.app.services.ICuentaService;

@Component
public class CuentaPropertyEditor extends PropertyEditorSupport {
	
	@Autowired
	private ICuentaService cuentaService;
	
	@Autowired
	private ICuentaDao cuentaDao;
	

	@Override
	public void setAsText(String idStr) throws IllegalArgumentException {
		try {
			Long id = Long.parseLong(idStr);
			this.setValue(cuentaService.getById(id, cuentaDao.findAll()));
		} catch (Exception e) {
			System.out.println("hubo un error al asignar el objeto cuenta a la tarjeta");
		}
	}
	
	

}
