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

import org.springframework.format.annotation.DateTimeFormat;

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
	private String icv;
	
	/*este auxiliar de id cuenta nos apoyara para determinar la cuenta correspondiente a nuestra tarjeta
	debido a que thymeleaf y spring no nos permite seleccionar un objeto del tipo Cuenta desde la vista html
	tenemos en nuestro formtarjeta por lo que al utilizar esta variable,
	 justo antes de enviar a guardar el archivo con el 	.save tarjetaDao, con el cuentaDao en el controller tarjeta vamos a buscar una cuenta con ese mismo id*/
	//@Column(nullable=false)
	//private Long idCuentaAux;
	
	@Column(name = "vencimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date vencimiento;
	
	//mientras utilizamos esta alternativa de crear otra columna manejaremos el optional=false, pero el valor id_cuenta en optional=true
	@JoinColumn(name = "cuenta", referencedColumnName = "id", nullable = true)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Cuenta cuenta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getIcv() {
		return icv;
	}

	public void setIcv(String icv) {
		this.icv = icv;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*public Long getIdCuentaAux() {
		return idCuentaAux;
	}

	public void setIdCuentaAux(Long idCuentaAux) {
		this.idCuentaAux = idCuentaAux;
	}*/
	
	
}