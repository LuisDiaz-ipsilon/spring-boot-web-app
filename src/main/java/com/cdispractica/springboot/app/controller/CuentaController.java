package com.cdispractica.springboot.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cdispractica.springboot.app.models.dao.CuentaDaoImpl;
import com.cdispractica.springboot.app.models.dao.ICuentaDao;
import com.cdispractica.springboot.app.models.entity.Cuenta;
import com.cdispractica.springboot.app.validator.CuentaValidator;

/**
 * Controlador para nuestra entidad Cuenta en donde manejamos la informacion que
 * proviene de nuestros templates para ser procesada en esta parte realizando la
 * validacion de los objetos y el objetivo del metodo llamado.
 * 
 * @author Luis Diaz
 *
 */

@Controller
@SessionAttributes("cuenta")
public class CuentaController {

	@Autowired
	private ICuentaDao cuentaDao;

	@Autowired
	private CuentaValidator cuentaValidator;

	/**
	 * Realiza la validacion implicita, en todo objeto Cuenta se tomara en cuenta si
	 * se tiene un formato o validaciones en general.
	 * 
	 * @param binder Es el validador de parametros de los datos que llegan de los
	 *               templates.
	 */
	// validaciones implicita
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(cuentaValidator);

		// validamos la fecha:
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false); // este metodo indica que no podemos ser flexibles con el formato de entrada
		binder.registerCustomEditor(Date.class, "diaCreacion", new CustomDateEditor(dateFormat, false)); // Date.util
																											// ----CUIDADO---

	}

	/**
	 * Realiza una llamada a {@link ICuentaDao.findAll()} para igualarlo a un
	 * atributo Model y hacer uso en una template.
	 * 
	 * @param model Agrega cualquier objeto a los templates.
	 * @param modelCuenta Es el mapeo de nuestro objeto atraves de los templates. Es
	 *                    un objeto creado previamente antes de colocarlo en el
	 *                    template.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "lista".
	 */

	@RequestMapping(value = "/lista", method = RequestMethod.GET)
	public String cuentaLista(Model model, Map<String, Object> modelCuenta) {
		Cuenta cuenta = new Cuenta();
		modelCuenta.put("cuenta", cuenta);
		model.addAttribute("titulo", "Lista de cuentas");
		model.addAttribute("cuentas", cuentaDao.findAll());

		return "lista";
	}

	/**
	 * Realiza la creacion de una Cuenta nueva, para ser llenada desde el formulario
	 * "/form-cuenta".
	 * 
	 * @param model Agrega cualquier objeto a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "form-cuenta".
	 */
	@RequestMapping(value = "/form-cuenta")
	public String crear(Map<String, Object> model) {
		Cuenta cuenta = new Cuenta();
		model.put("cuenta", cuenta);
		model.put("titulo", "Nueva cuenta, llene los datos");
		return "form-cuenta";
	}

	/**
	 * Realiza la sobreescritura de una Cuenta existente en la base de datos y en el
	 * contexto de persistencia.
	 * 
	 * @param id    Es el ID de la Cuenta existente de donde se desea realizar la
	 *              edicion de la cuenta, a excepcion de poder editar el ID de la
	 *              misma Cuenta.
	 * @param model Agrega cualquier objeto a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "form-cuenta".
	 */

	@RequestMapping(value = "/form-cuenta/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cuenta cuenta = null;

		if (id > 0) {
			cuenta = cuentaDao.findOne(id);
		} else {
			return "redirect:/lista";
		}
		model.put("cuenta", cuenta);
		model.put("titulo", "Edite la cuenta");
		return "form-cuenta";

	}

	/**
	 * Realiza almacenamiento en el contexto de persistencia y en la base de datos,
	 * que previamente se lleno en el "form-cuenta", la diferencia del metodo
	 * "crear" es el method del tipo POST en donde el formulario envia los datos de
	 * la nueva cuenta.
	 * 
	 * @param cuenta Previamente llenado en el formulario, en caso de que no se
	 *               cumpla con la validacion no sera posible guardarse y se enviara
	 *               un mensaje con le detalle del problema al intentar guardar.
	 * @param result Es la variable que esta atenta a la validacion del formato de
	 *               la fecha o cualquier validacion colocada en el
	 *               {@link CuentaController.initBinder()}
	 * @param model  Agrega cualquier objeto a los templates.
	 * @param status Atributo que informa el estado del formulario, al terminar el
	 *               guardado y dirijir al usuario a una nuevo template se le ordena
	 *               el estado completo que elimina los datos del template.
	 * @param flash  Agrega mensajes rapidos a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "form-cuenta".
	 */
	@RequestMapping(value = "form-cuenta", method = RequestMethod.POST)
	public String guardar(@Valid Cuenta cuenta, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes flash) {

		// validacion de forma explicita
		// cuentaValidator.validate(cuenta, result);

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario la cuenta");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensaje", "Error al registrar la cuenta");
			// model.addAttribute("errList", result.getFieldErrors());
			return "form-cuenta";
		} else {
			model.addAttribute("result", false);
			model.addAttribute("errList", "");
		}

		flash.addAttribute("completeMsj", "Se guardo correctamente");
		cuentaDao.save(cuenta);
		status.setComplete(); // Este metodo se encarga de la limpieza adecuada despues de usar el formulario
								// HTML
		// elimina el objeto con el que estamos trabajando

		return "redirect:form-cuenta";
	}

	/**
	 * Realiza el borrado del contexto de persistencia y base de datos nuestro
	 * registro de Cuenta especificado por ID.
	 * 
	 * @param id    Variable del tipo Long que identifica nuestra Cuenta a eliminar.
	 * @param flash Agrega mensajes rapidos a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "lista". 
	 */
	@RequestMapping(value = "/eliminarcuenta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id != null && id > 0) {
			Cuenta cuenta = cuentaDao.findOne(id);
			if (cuenta.getTarjetas().isEmpty()) {
				cuentaDao.delete(id);
			} else {
				flash.addFlashAttribute("mensaje", "La cuenta tiene tarjetas pendientes por eliminar");
			}
		}
		return "redirect:/lista";
	}

	/**
	 * Realiza la busqueda por el atributo numeroTelefono de la Cuenta para despues
	 * atribuirlos al template "lista"
	 * 
	 * @param cuenta En el formulario de busqueda se manda un objeto Cuenta con
	 *               unicamente el dato numeroTelefono para realizar la busqueda.
	 *               Por el motivo anterior no se usa "@valid" anteponiendolo en el
	 *               parametro.
	 * @param flash Agrega mensajes rapidos a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "lista".
	 */
	@RequestMapping(value = "/buscar-numero-tel", method = RequestMethod.POST)
	public String cargarCuentasNumeroTelfono(Cuenta cuenta, RedirectAttributes flash) {

		if (!cuentaDao.findByNumeroTelefono(cuenta.getNumeroTelefono()).isEmpty()) {
			flash.addFlashAttribute("listCuentasNumeroT", cuentaDao.findByNumeroTelefono(cuenta.getNumeroTelefono()));
			flash.addFlashAttribute("mensajeSucces", "Se encontraron cuentas");
		} else
			flash.addFlashAttribute("mensaje", "No se encontraron cuentas");

		return "redirect:/lista";
	}

}
