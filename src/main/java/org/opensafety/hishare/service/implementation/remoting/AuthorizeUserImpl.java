package org.opensafety.hishare.service.implementation.remoting;

import org.opensafety.hishare.managers.interfaces.remoting.ParcelManager;
import org.opensafety.hishare.managers.interfaces.remoting.PermissionManager;
import org.opensafety.hishare.managers.interfaces.remoting.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.PermissionFactory;
import org.opensafety.hishare.model.factories.UserFactory;
import org.opensafety.hishare.service.interfaces.remoting.AuthorizeUser;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizeUserImpl implements AuthorizeUser
{
	@Autowired
	private ParcelManager parcelManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private PermissionFactory permissionFactory;
	@Autowired
	private UserFactory userFactory;
	
	private static final String genericCredentialsError = "The credentials supplied do not permit you to alter permissions.";
	private static final String permissionAddedSuccessfully = "Permission added successfully";
	
	public String authorize(String authorizingUser, String authenticationId, String parcelId,
	                        String parcelPassword, String userToAuthorize,
	                        PermissionLevel permissionLevel)
	{
		if(userManager.verifyAuthentication(authorizingUser, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				if(!userManager.userExists(userToAuthorize))
				{
					userManager.persistUser(userFactory.createUser(userToAuthorize));
				}
				
				User toBeAuthorized = userManager.getByUsername(userToAuthorize);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				Permission permission = permissionFactory.createPermission(parcel, toBeAuthorized,
				                                                           permissionLevel);
				permissionManager.persistPermission(permission);
				
				return permissionAddedSuccessfully;
			}
		}
		return genericCredentialsError;
	}
	
}
