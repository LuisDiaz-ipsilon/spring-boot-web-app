package com.cdispractica.springboot.app.errors;

/**
 * Excepcion personalizada para expresar errores con la base de datos en
 * general.
 * 
 * @author Luis Diaz
 *
 */
public class DataBaseBancoException extends RuntimeException {

	private static final long serialVersionUID = -5066266045243902498L;

	/**
	 * Realiza la notificacion con el mensaje en caso de cumplir la condicion para
	 * que surja la excepcion.
	 */
	public DataBaseBancoException() {
		super("Contacte con la administracion, hubo un error con la base de datos");
	}

}
