package org.opensafety.hishare.controller;

import org.opensafety.hishare.service.interfaces.http.AuthenticateUser;
import org.opensafety.hishare.service.interfaces.http.ChangeAuthenticationServerPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ChangeAuthenticationServerPassword")
public class ChangeAuthenticationServerPasswordController
{
	@Autowired
	ChangeAuthenticationServerPassword changeAuthenticationServerPassword;
	
	public ChangeAuthenticationServerPasswordController()
	{
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public
	        ModelAndView
	        authenticateUser(@RequestParam("authenticationServerName") String authenticationServerName,
	                         @RequestParam("authenticationServerPassword") String authenticationServerPassword,
	                         @RequestParam("newAuthenticationServerPassword") String newAuthenticationServerPassword)
	{
		ModelAndView mav = new ModelAndView("outputString");
		
		String successMessage = changeAuthenticationServerPassword.changePassword(authenticationServerName,
		                                                                          authenticationServerPassword,
		                                                                          newAuthenticationServerPassword);
		mav.addObject("string", successMessage);
		return mav;
	}
}
