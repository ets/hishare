package org.opensafety.hishare.service.interfaces.http;

import org.opensafety.hishare.model.PermissionLevel;

public interface AuthorizeUser
{
	
	        String
	        authorize(String authorizingUser, String authenticationId, String parcelId,
	                  String parcelPassword, String userToAuthorize, PermissionLevel permissionLevel);
	
}
