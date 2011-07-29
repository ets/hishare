package org.opensafety.hishare.managers.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;
import org.opensafety.hishare.managers.implementation.helpers.UploadingPayload;
import org.opensafety.hishare.managers.interfaces.PayloadManager;
import org.opensafety.hishare.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;

public class PayloadManagerImpl implements PayloadManager
{
	@Autowired
	PayloadDao payloadDao;
	
	private Log log;
	
	private Map<String, UploadingPayload> uploadingPayloads;
	
	public static final int CHUNK_SIZE = 8;
	
	public static String createTransferKey()
	{
		return UUID.randomUUID().toString();
	}
	
	public PayloadManagerImpl()
	{
		log = LogFactory.getLog(this.getClass());
		uploadingPayloads = new HashMap<String, UploadingPayload>();
	}
	
	public Integer getChunkSize()
	{
		return CHUNK_SIZE;
	}
	
	public String beginUpload()
	{
		String transferKey = createTransferKey();
		
		// keep track of this upload
		uploadingPayloads.put(transferKey, new UploadingPayload());
		
		// give the user a transfer key to tie them to the object
		return transferKey;
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

	public boolean storePayload(Parcel parcel, byte[] payload)
    {
		return payloadDao.savePayload(parcel.getPayloadLocation(), payload);
    }
}
