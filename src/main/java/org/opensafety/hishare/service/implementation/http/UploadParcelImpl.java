package org.opensafety.hishare.service.implementation.http;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import sun.tools.tree.ThisExpression;
import sun.util.logging.resources.logging;

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
	private static final String errorAuthenticating = "Invalid user credentials.";
	private static final String errorWithPayload = "File was null.";
	private static final String errorWithDaysToLive = "Invalid parcel lifetime.";
	
	Log log = LogFactory.getLog(this.getClass());
	
	public String[] uploadParcel(String username, String authenticationId, String parcelName,
	                             Integer daysToLive, byte[] payload)
	{
		log.info((new Date())+" Parcel Upload");
		log.debug("username: "+username);
		log.debug("parcel Name: "+parcelName);
		log.debug("days To Live: "+daysToLive);
		
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyDaysToLive(daysToLive))
			{
				if(payload != null)
				{
					// create the parcel
					Parcel newParcel = parcelFactory.createParcel(parcelName, daysToLive);
					log.debug("parcel Id: "+newParcel.getId());
					
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
					
					log.info((new Date())+" Parcel Upload Successfull ");
					
					// Give user the access information
					return new String[] { newParcel.getId(), newParcel.getPassword() };
				}
				log.info((new Date())+" Parcel Upload Unsuccessfull: "+errorWithPayload);
				return new String[] { error, errorWithPayload };
			}
			log.info((new Date())+" Parcel Upload Unsuccessfull: "+errorWithDaysToLive);
			return new String[] { error, errorWithDaysToLive };
		}
		log.info((new Date())+" Parcel Upload Unsuccessfull: "+errorAuthenticating);
		return new String[] { error, errorAuthenticating };
	}
}
