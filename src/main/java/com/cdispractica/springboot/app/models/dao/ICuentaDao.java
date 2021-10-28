package com.cdispractica.springboot.app.models.dao;

import java.util.List;

import com.cdispractica.springboot.app.models.entity.Cuenta;

/**
 * Interface para manejar datos correspondientes a una cuenta, lista de las
 * cuentas, poder sobreescribir la tabla de cuentas y busqueda personalizadas.
 * 
 * @author Luis Diaz
 *
 */

public interface ICuentaDao {

	/**
	 * Devuelve una coleccion de todas las cuentas de la tabla cuentas.
	 * 
	 * @return findAll Lista de todas las cuentas de la tabla cuentas, incluye todos
	 *         los datos de cada cuenta.
	 */
	public List<Cuenta> findAll();

	/**
	 * Metodo para guardar una nueva cuenta o sobreescritura de una cuenta en
	 * especifico.
	 * 
	 * @param cuenta Es necesario una cuenta con todos sus datos validos. En caso de
	 *               NULL no podra guardarse la Cuenta en la Tabla cuentas.
	 */
	public void save(Cuenta cuenta);

	/**
	 * Busqueda por el ID del tipo Ling de cualquier cuenta existente en la tabla
	 * cuentas.
	 * 
	 * @param id Ingresa un dato Long correspondiente a ID existente en la table
	 *           Cuentas.
	 * @return findOne Devuelve un objeto Cuenta correpondiente al ID de la busqueda
	 *         en la tabla Cuentas
	 */
	public Cuenta findOne(Long id);

	/**
	 * Elimina una una cuenta de la tabla Cuentas
	 * 
	 * @param id ingresa el dato Long de la Cuenta que desees eliminar. En caso de
	 *           NULL no se eliminara ninguna Cuenta.
	 */
	public void delete(Long id);

	/**
	 * Realiza busqueda de una Cuenta de la tabla cuentas con el numero telefonico
	 * 
	 * @param term Ingresa unicamente el numero telefonico a 10 digitos de la Cuenta
	 *             existente, en caso de no encontrar ningun valor se retornara una
	 *             lista de cuentas vacia.
	 * @return cuentas Retorna una lista de cuentas donde el termino coincida con el
	 *         campo numeroTelefonico.
	 */
	public List<Cuenta> findByNumeroTelefono(String term);

}
