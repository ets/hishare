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
package org.opensafety.hishare.managers.implementation.remoting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;
import org.opensafety.hishare.managers.implementation.remoting.helpers.DownloadingPayload;
import org.opensafety.hishare.managers.implementation.remoting.helpers.UploadingPayload;
import org.opensafety.hishare.managers.interfaces.PayloadManager;
import org.opensafety.hishare.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;

public class PayloadManagerImpl implements PayloadManager
{
	public static String createTransferKey()
	{
		return UUID.randomUUID().toString();
	}
	
	@Autowired
	PayloadDao payloadDao;
	
	private Log log = LogFactory.getLog(this.getClass());
	private Map<String, UploadingPayload> uploadingPayloads;
	
	private Map<String, DownloadingPayload> downloadingPayloads;
	
	private int chunkSize;
	
	public PayloadManagerImpl(int chunkSize)
	{
		this.chunkSize = chunkSize;
		
		uploadingPayloads = new HashMap<String, UploadingPayload>();
	}
	
	public String beginDownload(Parcel parcel)
	{
		String transferKey = createTransferKey();
		
		byte[] payload = payloadDao.retrievePayload(parcel);
		
		// keep track of this download
		downloadingPayloads.put(transferKey, new DownloadingPayload(payload));
		
		// give the user a transfer key to tie them to the object
		return transferKey;
	}
	
	public String beginUpload()
	{
		String transferKey = createTransferKey();
		
		// keep track of this upload
		uploadingPayloads.put(transferKey, new UploadingPayload());
		
		// give the user a transfer key to tie them to the object
		return transferKey;
	}
	
	public boolean deletePayload(String payloadLocation)
	{
		return payloadDao.deletePayload(payloadLocation);
	}
	
	public boolean downloadAvailable(String transferKey)
	{
		if(downloadingPayloads.containsKey(transferKey))
		{
			return downloadingPayloads.get(transferKey).dataAvailable();
		}
		return false;
	}
	
	public Integer getChunkSize()
	{
		return chunkSize;
	}
	
	public boolean persistPayload(Parcel parcel, byte[] payload)
	{
		return payloadDao.savePayload(parcel, payload);
	}
	
	public byte[] resolveUpload(String transferKey)
	{
		UploadingPayload uploadedPayload = uploadingPayloads.get(transferKey);
		
		if(uploadedPayload != null)
		{
			byte[] payload = uploadedPayload.getPayload();
			
			if(payload != null)
			{
				uploadingPayloads.remove(transferKey);
			}
			
			return payload;
		}
		return null;
	}
	
	public Boolean uploadChunk(String transferKey, byte[] chunk)
	{
		UploadingPayload uploadingPayload = uploadingPayloads.get(transferKey);
		
		if(uploadingPayload != null)
		{
			uploadingPayload.append(chunk);
			return true;
		}
		return false;
	}

	public byte[] downloadPayload(Parcel parcel) {
		// TODO Auto-generated method stub
		return null;
	}
}
