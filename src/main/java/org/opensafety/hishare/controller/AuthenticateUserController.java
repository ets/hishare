package org.opensafety.hishare.controller;

import org.opensafety.hishare.service.interfaces.http.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/AuthenticateUser")
public class AuthenticateUserController
{
	@Autowired
	AuthenticateUser authenticateUser;
	
	public AuthenticateUserController()
	{
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public
	        ModelAndView
	        authenticateUser(@RequestParam("username") String username,
	                         @RequestParam("authenticationServerName") String authenticationServerName,
	                         @RequestParam("authenticationServerPassword") String authenticationServerPassword)
	{
		ModelAndView mav = new ModelAndView("outputString");
		
		String authId = authenticateUser.authenticate(username, authenticationServerName,
		                                              authenticationServerPassword);
		
		mav.addObject("string", authId);
		return mav;
	}
}
