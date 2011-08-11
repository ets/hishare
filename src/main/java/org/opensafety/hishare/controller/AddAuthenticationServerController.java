package org.opensafety.hishare.controller;

import org.opensafety.hishare.service.interfaces.http.AddAuthenticationServer;
import org.opensafety.hishare.service.interfaces.http.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/AddAuthenticationServer")
public class AddAuthenticationServerController
{
	@Autowired
	AddAuthenticationServer addAuthenticationServer;
	
	public AddAuthenticationServerController()
	{
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public
	        ModelAndView
	        authenticateUser(@RequestParam("authenticationServerName") String authenticationServerName)
	{
		ModelAndView mav = new ModelAndView("outputString");
		
		mav.addObject("string", "This functionality is disabled");
		
		return mav;
				
		/*
		ModelAndView mav;
		
		String successMessage = addAuthenticationServer.addAuthenticationServer(authenticationServerName);
		
		mav = new ModelAndView("outputString");
		mav.addObject("string", successMessage);
		return mav;
		*/
	}
}
