package org.opensafety.hishare.service.implementation.http;

import org.opensafety.hishare.managers.interfaces.http.ParcelManager;
import org.opensafety.hishare.managers.interfaces.http.PermissionManager;
import org.opensafety.hishare.managers.interfaces.http.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.service.interfaces.http.DeleteParcel;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteParcelImpl implements DeleteParcel
{
	@Autowired
	UserManager userManager;
	@Autowired
	ParcelManager parcelManager;
	@Autowired
	PermissionManager permissionManager;
	
	private static final String genericCredentialsError = "The credentials supplied do not permit you to delete.";
	private static final String deletedSuccessfully = "The parcel was deleted successfully";
	private static final String unsuccessfullDelete = "The parcel could not be deleted";
	
	public String deleteParcel(String username, String authenticationId, String parcelId,
	                           String parcelPassword)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				User user = userManager.getByUsername(username);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				if(permissionManager.hasDeletePermission(user, parcel))
				{
					;
				}
				{
					boolean deletePermissions = permissionManager.deletePermissions(parcel);
					boolean deleteParcel = parcelManager.deleteParcel(parcel);
					if(deletePermissions && deleteParcel)
					{
						return deletedSuccessfully;
					}
					return unsuccessfullDelete;
				}
			}
		}
		return genericCredentialsError;
	}
	
}
