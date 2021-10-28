package com.cdispractica.springboot.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdispractica.springboot.app.models.dao.ICuentaDao;
import com.cdispractica.springboot.app.services.ICuentaService;

/**
 * Reliza la relacion entre la cuenta y tarjeta cumpliendo con la normalizacion
 * en la base de datos correspondiente a la logica de negocio.
 * 
 * @author Luis Diaz
 *
 */
@Component
public class CuentaPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private ICuentaService cuentaService;

	@Autowired
	private ICuentaDao cuentaDao;

	/**
	 * Tomamos el String referente a un ID de una Cuenta existente para poder
	 * realizar la busqueda en los objetos Cuenta para poder cumplir con la relacion
	 * Cuenta-Tarjeta.
	 * 
	 * @param idStr String que provien desde el HTML ya que son los datos que
	 *              podemos usar en el formulario, en este metodo hacemos el mapeo a
	 *              la relacion.
	 * 
	 */

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
