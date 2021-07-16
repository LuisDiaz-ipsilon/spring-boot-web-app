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

import com.cdispractica.springboot.app.models.dao.ICuentaDao;
import com.cdispractica.springboot.app.models.entity.Cuenta;
import com.cdispractica.springboot.app.validator.CuentaValidator;

@Controller
@SessionAttributes("cuenta")
public class CuentaController {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Autowired
	private CuentaValidator cuentaValidator;
		
	//validaciones implicita
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(cuentaValidator);
		
		//validamos la fecha: 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false); //este metodo indica que no podemos ser flexibles con el formato de entrada
		binder.registerCustomEditor(Date.class, "diaCreacion", new CustomDateEditor(dateFormat, false)); //Date.util ----CUIDADO---
	
	}

	@RequestMapping(value="/lista", method = RequestMethod.GET)
	public String cuentaLista(Model model) {
		model.addAttribute("titulo", "Lista de cuentas");
		model.addAttribute("cuentas", cuentaDao.findAll());
		return "lista";
	}
	
	@RequestMapping(value = "/form-cuenta")
	public String crear(Map<String, Object> model) {
		Cuenta cuenta = new Cuenta();
		model.put("cuenta", cuenta);
		model.put("titulo", "Nueva cuenta, llene los datos");
		return "form-cuenta";
	}
	
	@RequestMapping(value = "/form-cuenta/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Cuenta cuenta = null;
		
		if(id > 0) {
			cuenta = cuentaDao.findOne(id);
		} else {
			return "redirect:/lista";
		}
		model.put("cuenta", cuenta);
		model.put("titulo", "Edite la cuenta");
		return "form-cuenta";
		
	}
	
	@RequestMapping(value = "form-cuenta", method = RequestMethod.POST)
	public String guardar(@Valid Cuenta cuenta, BindingResult result, Model model, SessionStatus status) {
		
		//validacion de forma explicita
		//cuentaValidator.validate(cuenta, result);
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario la cuenta");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensaje", "Error al registrar la cuenta");
			//model.addAttribute("errList", result.getFieldErrors());
			return "form-cuenta";
		} else {
			model.addAttribute("result", false);
			model.addAttribute("errList", "");
		}
		
		model.addAttribute("titulo", "Formulario cuenta");
		model.addAttribute("mensaje", "se guardo la cuenta correctamente");
		cuentaDao.save(cuenta);
		status.setComplete(); //Este metodo se encarga de la limpieza adecuada despues de usar el formulario HTML 
		//elimina el objeto con el que estamos trabajando
		
		return "redirect:form-cuenta";
	}
	
	@RequestMapping(value="/eliminarcuenta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
				
		if(id != null && id > 0 ) {
			Cuenta cuenta = cuentaDao.findOne(id);
			if(cuenta.getTarjetas().isEmpty()) {
				cuentaDao.delete(id);
			} else {
				flash.addFlashAttribute("mensaje", "La cuenta tiene tarjetas pendientes por eliminar");
			}
		} 
		return "redirect:/lista";
	}
	
}
