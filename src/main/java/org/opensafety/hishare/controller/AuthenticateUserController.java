package org.opensafety.hishare.controller;

import org.opensafety.hishare.service.interfaces.AuthenticateUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

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
	public ModelAndView authenticateUser(@RequestParam("username") String username)
	{
		String authId = authenticateUser.justAuthenticateMe(username);
		
		ModelAndView mav = new ModelAndView("AuthenticateUser");
		mav.addObject("AuthenticationId", authId);
        return mav;
	}
}
