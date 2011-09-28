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
package org.opensafety.hishare.service.implementation.remoting;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.managers.interfaces.ParcelManager;
import org.opensafety.hishare.managers.interfaces.PermissionManager;
import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.service.interfaces.DownloadParcel;
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
	private static final String threadInterruptedError = "The thread was interrupted.";
	private int crackerBreaker;
	
	public DownloadParcelImpl(int crackerBreaker)
	{
		this.crackerBreaker = crackerBreaker;
	}
	
	public String beginParcelDownload(String username, String authenticationId, String parcelId,
	                                  String parcelPassword)
	{
		try
		{
			this.wait(crackerBreaker * 1000);
		}
		catch(InterruptedException e)
		{
			log.error("Brute-Force-Breaking wait was interrupted");
			return threadInterruptedError;
		}
		
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				User user = userManager.getByUsername(username);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				if(permissionManager.hasDownloadPermission(user, parcel))
				{
					;
				}
				{
					String transferKey = "Transfer Key";//parcelManager.beginDownload(parcel);
					return transferKey;
				}
			}
		}
		return genericCredentialsError;
	}
	
	public boolean downloadAvailable(String username, String authenticationId, String parcelId,
	                                 String parcelPassword, String transferKey)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				return false;//parcelManager.downloadAvailable(transferKey);
			}
		}
		return false;
	}
	
	public byte[] downloadParcelChunk(String username, String authenticationId, String parcelId,
	                                  String parcelPassword, String transferKey)
	{
		return "ERROR, NOT IMPLEMENTED".getBytes();
	}
	
	public String finishParcelDownload(String username, String authenticationId, String parcelId,
	                                   String parcelPassword, boolean success)
	{
		return "ERROR, NOT IMPLEMENTED";
	}
	
	public Integer getChunkSize()
	{
		return 512;//parcelManager.getChunkSize();
	}

	public byte[] downloadParcel(String username, String authenticationId,
			String parcelId, String parcelPassword) {
		// TODO Auto-generated method stub
		return null;
	}
}
