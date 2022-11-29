package com.cdispractica.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entidad tarjeta correspondiente a la tabla tarjetas en la DB Atributos: Long
 * id, String numeroTarjeta, String icv, Date vencimiento, Cuenta cuenta
 * 
 * @author Luis Diaz
 *
 */
@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Serializable {

	private static final long serialVersionUID = -8540585749209984969L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero_tarjeta", nullable = false, length = 16)
	private String numeroTarjeta;

	@Column(name = "icv", nullable = false, length = 3)
	@NotEmpty
	private String icv;

	@Column(name = "vencimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date vencimiento;

	@JoinColumn(name = "cuenta", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Cuenta cuenta;

	/**
	 * Obten el id de la Tarjeta
	 * 
	 * @return id Retorna un valor Long con el valore de la cuenta
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor para el ID del tipo Long
	 * 
	 * @param id Ingresa valor Long para llenar el campo. En caso de NULL no se
	 *           podra crear el objeto.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obten el valor para el numero de la Tarjeta de 16 digitos
	 * 
	 * @return numeroTarjeta Retorna el numero de 16 digitos de la Tarjeta del tipo
	 *         Long
	 */
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	/**
	 * Establece el numero de la Tarjeta
	 * 
	 * @param numeroTarjeta Ingresa un String con el numero de la Tarjeta. En caso
	 *                      de NULL no se podra crear el objeto.
	 */
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	/**
	 * Obtener el ICV de la Tarjeta
	 * 
	 * @return icv Retorna un String con los 3 digitos del ICV (codigo de
	 *         seguridad).
	 */
	public String getIcv() {
		return icv;
	}

	/**
	 * Establece el ICV de la Tarjeta
	 * 
	 * @param icv Ingresa el numero ICV de la Tarjeta. En caso de NULL no se podra
	 *            crear la Tarjeta.
	 */
	public void setIcv(String icv) {
		this.icv = icv;
	}

	/**
	 * Obten la fecha de vencimiento de la Tarjeta. Se toma como primer dia de
	 * vencimiento el mes indicado en la fecha almacenada.
	 * 
	 * @return vencimiento Retorna un dato del tipo Date con la fecha del
	 *         vencimiento.
	 */
	public Date getVencimiento() {
		return vencimiento;
	}

	/**
	 * Establece la fecha de vencimiento.
	 * 
	 * @param vencimiento deseable ingresar yyyy-MM-01 indicando que se vencera
	 *                    desde el primer dia del mes. En caso de NULL no se podra
	 *                    crear la Tarjeta.
	 */
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	/**
	 * Obtener el id de la Cuenta
	 * 
	 * @return cuenta Devuelve el ID de la Cuenta referente a la Tarjeta.
	 */
	public Cuenta getCuenta() {
		return cuenta;
	}

	/**
	 * Establecere el valor Cuenta de referencia con la Tarjeta.
	 * 
	 * @param cuenta Se almacena el ID de la cuenta con la que estamos haciendo
	 *               referencia a la Tarjeta.
	 */
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}