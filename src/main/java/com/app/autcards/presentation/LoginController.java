package com.app.autcards.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
	
	@RequestMapping(value = "/login-verified")
	public String loginVerified(Model model) {
		model.addAttribute("verified", true);
		return "login";
	}
}
