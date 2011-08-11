package org.opensafety.hishare.service.implementation.remoting;

import org.opensafety.hishare.managers.interfaces.remoting.ParcelManager;
import org.opensafety.hishare.managers.interfaces.remoting.PermissionManager;
import org.opensafety.hishare.managers.interfaces.remoting.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.ParcelFactory;
import org.opensafety.hishare.model.factories.PermissionFactory;
import org.opensafety.hishare.service.interfaces.remoting.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadParcelImpl implements UploadParcel
{
	@Autowired
	private ParcelManager parcelManagerRemoting;
	@Autowired
	private PermissionManager permissionManagerRemoting;
	@Autowired
	private UserManager userManagerRemoting;
	
	@Autowired
	private ParcelFactory parcelFactory;
	@Autowired
	private PermissionFactory permissionFactory;
	
	private static final String error = "ERROR";
	private static final String errorAuthenticating = "Error: Invalid user credentials.";
	private static final String errorResolving = "Error: Incorrect transfer key, or uploaded data could not be read.";
	private static final String errorWithPayload = "Error: File was null";
	
	public String beginParcelUpload(String username, String authenticationId, String parcelName,
	                                Integer daysToLive)
	{
		if(userManagerRemoting.verifyAuthentication(username, authenticationId))
		{
			Parcel newParcel;
			newParcel = parcelFactory.createParcel(parcelName, daysToLive);
			
			String transferKey = parcelManagerRemoting.beginUpload(newParcel);
			return transferKey;
		}
		
		return errorAuthenticating;
	}
	
	public String finishParcelUpload(String username, String authenticationId, String transferKey)
	{
		if(userManagerRemoting.verifyAuthentication(username, authenticationId))
		{
			byte[] payload = parcelManagerRemoting.resolveUpload(transferKey);
			if(payload != null)
			{
				Parcel parcel = parcelManagerRemoting.closeUpload(transferKey);
				if(parcel != null)
				{
					// Retrieve the owner
					User owner = userManagerRemoting.getByUsername(username);
					
					// Persist Uploaded Parcel and it's payload
					parcelManagerRemoting.persistParcel(parcel, payload);
					userManagerRemoting.updateUser(owner);
					
					// Add permissions to parcel
					Permission ownerPermission = permissionFactory.createPermission(parcel,
					                                                                owner,
					                                                                PermissionLevel.OWNER);
					permissionManagerRemoting.persistPermission(ownerPermission);
					
					// Give user the access information
					return parcel.getId() + ":" + parcel.getPassword();
				}
			}
			return errorResolving;
		}
		return errorAuthenticating;
	}
	
	public Integer getChunkSize()
	{
		return parcelManagerRemoting.getChunkSize();
	}
	
	public String[] uploadParcel(String username, String authenticationId, String parcelName,
	                             Integer daysToLive, byte[] payload)
	{
		if(userManagerRemoting.verifyAuthentication(username, authenticationId))
		{
			if(payload != null)
			{
				// create the parcel
				Parcel newParcel = parcelFactory.createParcel(parcelName, daysToLive);
				
				// Retrieve the owner
				User owner = userManagerRemoting.getByUsername(username);
				
				// Persist Uploaded Parcel and it's payload
				parcelManagerRemoting.persistParcel(newParcel, payload);
				userManagerRemoting.updateUser(owner);
				
				// Add permissions to parcel
				Permission ownerPermission = permissionFactory.createPermission(newParcel,
				                                                                owner,
				                                                                PermissionLevel.OWNER);
				permissionManagerRemoting.persistPermission(ownerPermission);
				
				// Give user the access information
				return new String[] { newParcel.getId(), newParcel.getPassword() };
			}
			return new String[] { error, errorWithPayload };
		}
		
		return new String[] { error, errorAuthenticating };
	}
	
	public Boolean uploadParcelChunk(String username, String authenticationId, String transferKey,
	                                 byte[] chunk)
	{
		if(userManagerRemoting.verifyAuthentication(username, authenticationId))
		{
			return parcelManagerRemoting.uploadChunk(transferKey, chunk);
		}
		
		return false;
	}
}