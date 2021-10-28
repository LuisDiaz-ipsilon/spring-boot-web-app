package com.cdispractica.springboot.app.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cdispractica.springboot.app.editors.CuentaPropertyEditor;
import com.cdispractica.springboot.app.errors.DataBaseBancoException;
import com.cdispractica.springboot.app.models.dao.ICuentaDao;
import com.cdispractica.springboot.app.models.dao.ITarjetaDao;
import com.cdispractica.springboot.app.models.entity.Cuenta;
import com.cdispractica.springboot.app.models.entity.Tarjeta;

/**
 * Controlador para la entidad Tarjeta de donde tomamos la informacion de los
 * templates, realiza validacion y ejecucion de instrucciones llamadas desde los
 * services, dao, editors, entre otros.
 * 
 * @author Luis Diaz
 *
 */
@Controller
@SessionAttributes("tarjeta")
public class TarjetaController {

	@Autowired
	private ITarjetaDao tarjetaDao;

	@Autowired
	private ICuentaDao cuentaDao;

	@Autowired
	private CuentaPropertyEditor cuentaEditor;

	/**
	 * Realiza la validacion implicita, en todo objeto Tarjeta especificando la
	 * validacion del tipo relacional en donde usamos el archivo "cuentaEditor".
	 * 
	 * @param binder Es el validador de parametros de los datos que llegan de los
	 *               templates.
	 */
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Cuenta.class, "cuenta", cuentaEditor);
	}

	/**
	 * Realiza la toma de todas las tarjetas del contexto de persistencia existentes
	 * y las coloca en un atributo para compartirlo con el template usando el Model.
	 * 
	 * @param model Agrega cualquier objeto a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "tarjeta-lista".
	 */
	@RequestMapping(value = "/tarjetas-lista", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Lista de tarjetas");
		model.addAttribute("tarjetas", tarjetaDao.findAll());
		return "tarjetas-lista";
	}

	/**
	 * Realiza una nueva cuenta vacia por definir en el template "formtarjeta".
	 * 
	 * @param model     Agrega cualquier objeto a los templates.
	 * @param modelList Atributo exclusivo para mostrar la lista de Cuentas
	 *                  disponibles para seleccionar el campo Cuenta de nuestra
	 *                  Tarjeta.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "formtarjeta".
	 */
	@RequestMapping(value = "/formtarjeta")
	public String crear(Map<String, Object> model, Model modelList) {
		Tarjeta tarjeta = new Tarjeta();
		List<Cuenta> listaCuentas = cuentaDao.findAll();
		model.put("tarjeta", tarjeta);
		modelList.addAttribute("listaCuentas", listaCuentas);
		model.put("titulo", "Llenar los datos de una tarjeta");
		return "formtarjeta";
	}

	/**
	 * Realiza la sobreescritura de una Tarjeta ya existente, se toma en cuenta el
	 * ID para realizar la actualizacion a los atributos.
	 * 
	 * @param id    De la actualizacion de excluye el valor ID. se solicita la
	 *              varibale ID del tipo Long.
	 * @param model Agrega cualquier objeto a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "formtarjeta".
	 */
	@RequestMapping(value = "/formtarjeta/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Tarjeta tarjeta = null;

		if (id != null && id > 0) {
			tarjeta = tarjetaDao.findOne(id);
		} else {
			return "index";
		}
		model.put("tarjeta", tarjeta);
		model.put("titulo", "Editar tarjeta");

		return "formtarjeta";
	}

	/**
	 * Realiza almacenamiento de una Cuenta en el contexto de persistencia, que
	 * previamente se lleno en el "formtarjeta", la diferencia del metodo "crear" es
	 * el method del tipo POST en donde el formulario envia los datos de la nueva
	 * Tarjeta.
	 * 
	 * @param tarjeta Acepta unicamente Tarjetas aceptadas por la validacion al
	 *                objeto tarjeta.
	 * @param result  Es la variable que esta atenta a la validacion; En caso de una
	 *                validacion falsa este atributo tendra toda el detalle del
	 *                error.
	 * @param model   Agrega cualquier objeto a los templates.
	 * @param status  Atributo que informa el estado del formulario, al terminar el
	 *                guardado y dirijir al usuario a una nuevo template se le
	 *                ordena el estado completo que elimina los datos del template.
	 * @param flash   Agrega mensajes rapidos a los templates.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista "formtarjeta".
	 */
	@RequestMapping(value = "/formtarjeta", method = RequestMethod.POST)
	public String guardar(@Valid Tarjeta tarjeta, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Llene correctamente los campos");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensaje", "Error al enviar los datos, por favor escriba correctamente los campos");
			return "formtarjeta";
		} else {
			model.addAttribute("result", false);
		}

		// Con este codigo agregamos a la vista de tarjetas de nuestra cuenta.
		// misma cuenta a la que estamos haciendo referencia a la tarjeta en creacion
		Cuenta cuenta = cuentaDao.findOne(tarjeta.getCuenta().getId());
		List<Tarjeta> tarjetas = cuenta.getTarjetas();
		if (tarjetas.size() < 2) {// si la cuenta tiene menos de 2 tarjetas entonces continuar:
			tarjetas.add(tarjeta);
			cuenta.setTarjetas(tarjetas);
		} else { // si la cuenta tiene mas de 2 tarjetas entonces mencionar la imposibilidad:
			flash.addFlashAttribute("mensaje", "La cuenta no puede tener mas de 2 tarjetas");
			return "redirect:formtarjeta";
		}

		model.addAttribute("titulo", "Formulario de tarjeta");
		model.addAttribute("mensaje", "Se envio la informacion correctamente");
		try {
			tarjetaDao.save(tarjeta);
			cuentaDao.save(cuenta);
		} catch (DataBaseBancoException e) {
			e.printStackTrace();
			flash.addFlashAttribute("mensaje", e.getMessage());
		}
		status.setComplete();

		return "redirect:formtarjeta";
	}

	/**
	 * Realiza el borrado del contexto de persistencia y base de datos el registro
	 * de la Tarjeta con el ID que se coloca en el PathVariable. No se especifica
	 * una pagina de borrado unicamente se le redirije al mismo template.
	 * 
	 * @param id En caso de valor NULL o ID no existente la operacion no se
	 *           realizara para ninguna Tarjeta.
	 * @return El valor de retorno es un String que hace la funcion de dirijir a un
	 *         template escrito en el return; En este caso la vista
	 *         "tarjetas-lista".
	 */
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {

		if (id != null && id > 0) {
			tarjetaDao.delete(id);
		}
		return "redirect:/tarjetas-lista";
	}

}