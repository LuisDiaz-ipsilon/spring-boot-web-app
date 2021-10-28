package com.cdispractica.springboot.app.services;

import java.util.List;

import com.cdispractica.springboot.app.models.entity.Cuenta;

/**
 * Interface que permite la extraccion de los datos para siertos parametros.
 * 
 * @author Luis Diaz
 *
 */

public interface ICuentaService {
	/**
	 * Obten una Cuenta deacuerdo a un ID y una lista de cuentas en especifico
	 * 
	 * @param id    Ingresa el ID corresponciente a la Cuenta. En caso de null
	 *              devolvera una lista de cuentas vacia.
	 * @param lista Ingresa la lista de Cuentas de donde deseas realizar la busqueda
	 *              especial.
	 * @return cuentaResult Retorna una cuenta en caso de acertar con el ID y la
	 *         lista de cuentas previa. En caso de no encontrar una Cuenta con ese
	 *         ID se devolvera una lista vacia.
	 */
	public Cuenta getById(Long id, List<Cuenta> lista);

}
