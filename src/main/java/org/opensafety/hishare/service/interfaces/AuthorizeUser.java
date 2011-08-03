package org.opensafety.hishare.service.interfaces;

import org.opensafety.hishare.model.PermissionLevel;

public interface AuthorizeUser
{

	String AuthorizeUser(String authorizingUser, String authenticationId, String parcelId,
                         String parcelPassword, String userToAuthorize,
                         PermissionLevel permissionLevel);
	
}
