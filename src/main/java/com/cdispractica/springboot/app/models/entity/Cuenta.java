package com.cdispractica.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entidad Cuenta con persistencia, representa tabla "cuentas" en DB Atributos.
 * de una cuenta: id(Long), nombre(String), saldo(double),
 * numeroTelefono(String), diaCreacion(Date).
 * 
 * @author Luis Diaz
 */

@Entity
@Table(name = "cuentas")
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 2990602998676380780L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotEmpty
	private String nombre;

	@Column
	@NotNull
	private double saldo;

	@Column(name = "numero_telefono")
	private String numeroTelefono;

	@Column(name = "dia_creacion")
	// @Temporal(TemporalType.DATE)
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date diaCreacion;

	/**
	 * Obtener el id de la Cuenta.
	 * 
	 * @return id Long ID de la cuenta.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el id de la cuenta.
	 * 
	 * @param id Es necesario un numero del tipo Long para el ID de la Cuenta, en
	 *           caso de NULL no se podra crear el objeto.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtenemos el nombre del cliente referente a su Cuenta.
	 * 
	 * @return nombre retorna String con el nombre del cliente.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del cliente en su cuenta
	 * 
	 * @param nombre Ingresa un String con el nombre, es posible ingresar cualquier
	 *               longitud, en caso de NULL no se podra crear el objeto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtene en saldo actual de la Cuenta
	 * 
	 * @return saldo Retorna un valor double correspondiente al saldo actual de la
	 *         Cuenta.
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Establece el saldo de la Cuenta
	 * 
	 * @param saldo el valor no puede ser nulo, ingresa el saldo del cliente, en
	 *              caso de NULL no se podra crear el objeto.
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * Obten el numero telefonico del cliente referente a la Cuenta.
	 * 
	 * @return numeroTelefono El numero telefonico preferente de 10 digitos sin
	 *         espacios.
	 */
	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	/**
	 * Establece el numero de telefono a diez digitos.
	 * 
	 * @param numeroTelefono Numero telefonico correspondiente la Cuenta del
	 *                       cliente, en caso de NULL no se podra crear el objeto.
	 */
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	/**
	 * Obtener el dia de la creacion de la Cuenta
	 * 
	 * @return diaCreacion el dato puede estar en formato yyyy-MM-dd o revisar las
	 *         propiedades.
	 */
	public Date getDiaCreacion() {
		return diaCreacion;
	}

	/**
	 * Establece la fecha de la creacion de la cuenta.
	 * 
	 * @param diaCreacion ingresa el formato yyyy-MM-dd, en caso de NULL no se podra
	 *                    crear el objeto.
	 */
	public void setDiaCreacion(Date diaCreacion) {
		this.diaCreacion = diaCreacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
