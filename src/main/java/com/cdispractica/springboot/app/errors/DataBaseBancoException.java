package com.cdispractica.springboot.app.errors;

public class DataBaseBancoException extends RuntimeException{

	private static final long serialVersionUID = -5066266045243902498L;

	public DataBaseBancoException() {
		super("Contacte con la administracion, hubo un error con la base de datos");
	}

	

}
