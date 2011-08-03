package org.opensafety.hishare.service.implementation.http;

import org.opensafety.hishare.managers.interfaces.http.ParcelManager;
import org.opensafety.hishare.managers.interfaces.http.PermissionManager;
import org.opensafety.hishare.managers.interfaces.http.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.ParcelFactory;
import org.opensafety.hishare.model.factories.PermissionFactory;
import org.opensafety.hishare.service.interfaces.http.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadParcelImpl implements UploadParcel
{
	@Autowired
	private ParcelManager parcelManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ParcelFactory parcelFactory;
	@Autowired
	private PermissionFactory permissionFactory;
	
	private static final String error = "ERROR";
	private static final String errorAuthenticating = "Error: Invalid user credentials.";
	private static final String errorWithPayload = "Error: File was null";
	
	public String[] uploadParcel(String username, String authenticationId, String parcelName,
	                             Integer daysToLive, byte[] payload)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(payload != null)
			{
				// create the parcel
				Parcel newParcel = parcelFactory.createParcel(parcelName, daysToLive);
				
				// Retrieve the owner
				User owner = userManager.getByUsername(username);
				
				// Persist Uploaded Parcel and it's payload
				parcelManager.persistParcel(newParcel, payload);
				userManager.updateUser(owner);
				
				// Add permissions to parcel
				Permission ownerPermission = permissionFactory.createPermission(newParcel,
				                                                                owner,
				                                                                PermissionLevel.OWNER);
				permissionManager.persistPermission(ownerPermission);
				
				// Give user the access information
				return new String[] { newParcel.getId(), newParcel.getPassword() };
			}
			return new String[] { error, errorWithPayload };
		}
		
		return new String[] { error, errorAuthenticating };
	}
}
