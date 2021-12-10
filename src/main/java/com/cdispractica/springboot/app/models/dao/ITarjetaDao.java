package com.cdispractica.springboot.app.models.dao;

import java.util.List;

import com.cdispractica.springboot.app.models.entity.Tarjeta;

/**
 * Interface para manejar datos correspondientes a una Tarjte, lista de las
 * tarjetas, poder sobreescribir la tabla de tarjetas y busqueda personalizadas.
 * 
 * @author Luis Diaz
 *
 */
public interface ITarjetaDao {
	/**
	 * Retorna una lista de todas la tarjetas almacenadas en el contexto de
	 * persistencia.
	 * 
	 * @return List<Tarjeta> Retorna todas las tarjetas con todos sus datos.
	 */
	public List<Tarjeta> findAll();

	/**
	 * Guarda o sobreescribe una cuenta en la tabla cuentas y en el contexto de
	 * persistencia.
	 * 
	 * @param tarjeta Es necesario que la tarjeta a sobreescribir sea una tarjeta
	 *                valida.
	 */
	public void save(Tarjeta tarjeta);

	/**
	 * Realiza una busqueda en especifico con un ID correspondiente a una Tarjeta.
	 * 
	 * @param id Ingresa un ID del tipo Long correspondiente a una Tarjeta. En caso
	 *           de NULL no se podra continuar con la busqueda.
	 * @return tarjeta Retorna una tarjeta con todos sus valores.
	 */
	public Tarjeta findOne(Long id);
	
	public List<Tarjeta> findByCuentaId(String term);

	/**
	 * Elimina una Tarjeta con el ID de la Tarjeta existente.
	 * @param id En caso de que el id no coincida con ninguna tarjeta no se eliminara ninguna tarjeta.
	 */
	public void delete(Long id);
	


}
