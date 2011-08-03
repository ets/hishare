package org.opensafety.hishare.service.implementation.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.managers.interfaces.http.ParcelManager;
import org.opensafety.hishare.managers.interfaces.http.PermissionManager;
import org.opensafety.hishare.managers.interfaces.http.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.service.interfaces.http.DownloadParcel;
import org.springframework.beans.factory.annotation.Autowired;

public class DownloadParcelImpl implements DownloadParcel
{
	@Autowired
	private ParcelManager parcelManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private UserManager userManager;
	
	Log log = LogFactory.getLog(this.getClass());
	
	// genericCredentialsError is intentionally generic so as to avoid giving
	// away information about which credentials succeeded or failed
	private static final String genericCredentialsError = "The credentials supplied do not permit a download.";
	
	public DownloadParcelImpl(int crackerBreaker)
	{
	}
	
	public byte[] downloadParcel(String username, String authenticationId, String parcelId,
	                             String parcelPassword)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				User user = userManager.getByUsername(username);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				if(permissionManager.hasDownloadPermission(user, parcel))
				{
					byte[] payload = parcelManager.downloadPayload(parcel);
					return payload;
				}
				else
				{
					log.info("User does not have permission to download");
				}
			}
			else
			{
				log.info("Parcel was not verified");
			}
		}
		else
		{
			log.info("User was not authenticated");
		}
		
		return genericCredentialsError.getBytes();
	}
}
