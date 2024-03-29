package com.gcu;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.SecurityServiceInterface;
import com.gcu.model.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginController 
{
	@Autowired
	SecurityServiceInterface securityService;
	
	@GetMapping("/")
	public String display(Model model)
	{
		securityService.getLogins();
		
		// Display Login Form View
		model.addAttribute("title", "Login Form");
		model.addAttribute("loginModel", new LoginModel());
		return "login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(@Valid LoginModel loginModel, BindingResult bindingResult, Model model) 
	{
		// Check for Validation Errors
		if (bindingResult.hasErrors()) 
		{
			model.addAttribute("title", "Login Form");
			return "login";
		}
		
		// Check for Valid Login Name & Password
		if (securityService.isAuthenticated(loginModel))
		{
			model.addAttribute("loginModel", loginModel);
			return "home";
		}
		else
		{
			return "login";
		}
	}
}
