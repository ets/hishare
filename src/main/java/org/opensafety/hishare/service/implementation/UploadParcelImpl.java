package org.opensafety.hishare.service.implementation;

import java.security.NoSuchAlgorithmException;

import org.opensafety.hishare.managers.interfaces.ParcelManager;
import org.opensafety.hishare.managers.interfaces.PayloadManager;
import org.opensafety.hishare.managers.interfaces.PermissionManager;
import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.ParcelFactory;
import org.opensafety.hishare.model.factories.PermissionFactory;
import org.opensafety.hishare.service.interfaces.UploadParcel;
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
	private static final String errorResolving = "Error: Incorrect transfer key, or uploaded data could not be read.";
	private static final String errorWithPayload = "Error: File was null";
	private static final String errorSecuringUpload = "Error: There was an error while securing your upload.";
	
	public Integer getChunkSize()
	{
		return parcelManager.getChunkSize();
	}
	
	public String beginParcelUpload(String username, String authenticationId, String parcelName,
	                                Integer daysToLive)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			Parcel newParcel;
            newParcel = parcelFactory.createParcel(parcelName, daysToLive);
            
			String transferKey = parcelManager.beginUpload(newParcel);
			return transferKey;
		}
		
		return errorAuthenticating;
	}
	
	public Boolean uploadParcelChunk(String username, String authenticationId, String transferKey,
	                                 byte[] chunk)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			return parcelManager.uploadChunk(transferKey, chunk);
		}
		
		return false;
	}
	
	public String finishParcelUpload(String username, String authenticationId, String transferKey)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			byte[] payload = parcelManager.resolveUpload(transferKey);
			if(payload != null)
			{
				Parcel parcel = parcelManager.closeUpload(transferKey);
				if(parcel != null)
				{
					//Retrieve the owner
					User owner = userManager.getByUsername(username);
					
					//Persist Uploaded Parcel and it's payload
					parcelManager.persistParcel(parcel, payload);
					userManager.updateUser(owner);
					
					//Add permissions to parcel
					Permission ownerPermission = permissionFactory.createPermission(parcel, owner, PermissionLevel.OWNER);
					permissionManager.persistPermission(ownerPermission);
					
					//Give user the access information
					return parcel.getId() + ":" + parcel.getPassword();
				}
			}
			return errorResolving;
		}
		return errorAuthenticating;
	}

	public String[] uploadParcel(String username, String authenticationId, String parcelName,
                               Integer daysToLive, byte[] payload)
    {
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(payload != null)
			{
				//create the parcel
				Parcel newParcel = parcelFactory.createParcel(parcelName, daysToLive);
				
				//Retrieve the owner
				User owner = userManager.getByUsername(username);
				
				//Persist Uploaded Parcel and it's payload
				parcelManager.persistParcel(newParcel, payload);
				userManager.updateUser(owner);
				
				//Add permissions to parcel
				Permission ownerPermission = permissionFactory.createPermission(newParcel, owner, PermissionLevel.OWNER);
				permissionManager.persistPermission(ownerPermission);
				
				//Give user the access information
				return new String[] {newParcel.getId(), newParcel.getPassword()};
			}
			return new String[] {error, errorWithPayload};
		}
		
		return new String[] {error, errorAuthenticating};
    }
}
