/*******************************************************************************
 * Copyright 2011 Pascal Metrics
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.opensafety.hishare.controller;

import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.service.interfaces.AuthorizeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/AuthorizeUser")
public class AuthorizeUserController
{
	@Autowired
	AuthorizeUser authorizeUser;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView handleFormUpload(@RequestParam("authorizingUser") String authorizingUser,
	                                     @RequestParam("authenticationId") String authenticationId,
	                                     @RequestParam("parcelId") String parcelId,
	                                     @RequestParam("parcelPassword") String parcelPassword,
	                                     @RequestParam("userToAuthorize") String userToAuthorize,
	                                     @RequestParam("permissionLevel") Integer permissionLevel)
	{
		ModelAndView mav;
		
		mav = new ModelAndView("outputString");
		
		PermissionLevel permission = PermissionLevel.values()[permissionLevel];
		
		String result = authorizeUser.authorizeUser(authorizingUser, authenticationId, parcelId,
		                                            parcelPassword, userToAuthorize, permission);
		
		mav.addObject("string", result);
		
		return mav;
	}
}
