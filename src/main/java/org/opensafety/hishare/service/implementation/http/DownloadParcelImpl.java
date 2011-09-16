/*******************************************************************************
 * Copyright 2011 Pascal Metrics
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.opensafety.hishare.service.implementation.http;

import java.util.Date;

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
	
	int BruteForceSpin;

	// genericCredentialsError is intentionally generic so as to avoid giving
	// away information about which credentials succeeded or failed
	private static final String genericCredentialsError = "The credentials supplied do not permit a download.";
	
	public DownloadParcelImpl()
	{
		this(0);
	}
	
	public DownloadParcelImpl(int BruteForceSpin)
	{
		this.BruteForceSpin = BruteForceSpin;
	}
	
	public int getBruteForceSpin()
	{
		return BruteForceSpin;
	}

	public void setBruteForceSpin(int bruteForceSpin)
	{
		BruteForceSpin = bruteForceSpin;
	}
	
	public byte[] downloadParcel(String username, String authenticationId, String parcelId,
	                             String parcelPassword)
	{
		log.info((new Date())+" Parcel Download");
		log.debug("username: "+username);
		log.debug("parcel Id: "+parcelId);
		
		try
        {
	        Thread.sleep(BruteForceSpin*1000);
        }
        catch(InterruptedException e)
        {
        	log.warn((new Date())+" Brute Force Spin was interrupted");
        }
		
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				User user = userManager.getByUsername(username);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				if(permissionManager.hasDownloadPermission(user, parcel))
				{
					byte[] payload = parcelManager.downloadPayload(parcel);
					
					log.info((new Date())+" Parcel Download Successfull");
					return payload;
				}
				else
				{
					log.info((new Date())+" Parcel Download Unsuccessfull: No Permissions for Download");
				}
			}
			else
			{
				log.info((new Date())+" Parcel Download Unsuccessfull: Parcel Not Available");
			}
		}
		else
		{
			log.info((new Date())+" Parcel Download Unsuccessfull: User Not Verified");
		}
		
		return genericCredentialsError.getBytes();
	}
}
