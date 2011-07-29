package org.opensafety.hishare.service.implementation;

import org.opensafety.hishare.managers.interfaces.ParcelManager;
import org.opensafety.hishare.managers.interfaces.PayloadManager;
import org.opensafety.hishare.managers.interfaces.PermissionManager;
import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.service.interfaces.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadParcelImpl implements UploadParcel
{
	@Autowired
	private ParcelManager parcelManager;
	@Autowired
	private PayloadManager payloadManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private UserManager userManager;
	
	private static final String errorAuthenticating = "Error: Invalid user credentials.";
	private static final String errorResolving = "Error: Incorrect transfer key, or uploaded data could not be read.";
	
	public Integer getChunkSize()
	{
		return payloadManager.getChunkSize();
	}
	
	public String beginParcelUpload(String username, String authenticationId, String parcelName,
	                                Integer daysToLive)
	{
		if(userManager.verifyUser(username, authenticationId))
		{
			String transferKey = payloadManager.beginUpload();
			
			Parcel newParcel = parcelManager.createParcel(parcelName, daysToLive);
			parcelManager.beginUpload(transferKey, newParcel);
			return transferKey;
		}
		
		return errorAuthenticating;
	}
	
	public Boolean uploadParcelChunk(String username, String authenticationId, String transferKey,
	                                 byte[] chunk)
	{
		if(userManager.verifyUser(username, authenticationId))
		{
			return payloadManager.uploadChunk(transferKey, chunk);
		}
		
		return false;
	}
	
	public String finishParcelUpload(String username, String authenticationId, String transferKey)
	{
		if(userManager.verifyUser(username, authenticationId))
		{
			byte[] payload = payloadManager.resolveUpload(transferKey);
			if(payload != null)
			{
				Parcel parcel = parcelManager.closeUpload(transferKey);
				if(parcel != null)
				{
					parcelManager.addParcel(parcel);
					payloadManager.storePayload(parcel, payload);
					return parcel.getId() + ":" + parcel.getPassword();
				}
			}
			return errorResolving;
		}
		return errorAuthenticating;
	}
}
