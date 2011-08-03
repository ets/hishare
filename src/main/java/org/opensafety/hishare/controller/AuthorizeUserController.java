package org.opensafety.hishare.controller;

import java.io.IOException;

import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.service.interfaces.AuthenticateUser;
import org.opensafety.hishare.service.interfaces.AuthorizeUser;
import org.opensafety.hishare.service.interfaces.DownloadParcel;
import org.opensafety.hishare.service.interfaces.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/AuthorizeUser")
public class AuthorizeUserController
{
	@Autowired
	AuthorizeUser authorizeUser;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView
	        handleFormUpload(@RequestParam("authorizingUser") String authorizingUser,
	                         @RequestParam("authenticationId") String authenticationId,
	                         @RequestParam("parcelId") String parcelId,
	                         @RequestParam("parcelPassword") String parcelPassword,
	                         @RequestParam("userToAuthorize") String userToAuthorize,
	                         @RequestParam("permissionLevel") Integer permissionLevel)
	{
		ModelAndView mav = new ModelAndView("AuthorizeUser");
		
		PermissionLevel permission = PermissionLevel.values()[permissionLevel];
		
		String result = authorizeUser.AuthorizeUser(authorizingUser, authenticationId, parcelId,
		                                            parcelPassword, userToAuthorize,
		                                            permission);
		
		mav.addObject("result", result);
		
		return mav;
	}
}
