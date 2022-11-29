package com.cdispractica.springboot.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error,
						@RequestParam(value="logout", required = false) String logout,
						Principal principal, RedirectAttributes flash
			) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Sesion iniciada previamente");
			return "redirect:/";
		}
		
		if(error != null) {
			flash.addFlashAttribute("error", "Escribe correctamente tus credenciales");
		}
		
		if(logout != null) {
			flash.addFlashAttribute("success", "Sesion terminada");
			return "redirect:/";
		}
		
		return "login";
	}
}
