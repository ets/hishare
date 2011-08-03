package org.opensafety.hishare.managers.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;
import org.opensafety.hishare.managers.implementation.helpers.DownloadingPayload;
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
	private Map<String, DownloadingPayload> downloadingPayloads;
	
	private int chunkSize;
	
	public static String createTransferKey()
	{
		return UUID.randomUUID().toString();
	}
	
	public PayloadManagerImpl(int chunkSize)
	{
		this.chunkSize = chunkSize;
		
		log = LogFactory.getLog(this.getClass());
		uploadingPayloads = new HashMap<String, UploadingPayload>();
	}
	
	public Integer getChunkSize()
	{
		return chunkSize;
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

	public boolean persistPayload(Parcel parcel, byte[] payload)
    {
		return payloadDao.savePayload(parcel, payload);
    }

	public String beginDownload(Parcel parcel)
    {
		String transferKey = createTransferKey();
		
		byte[] payload = payloadDao.retrievePayload(parcel);

		//keep track of this download
		downloadingPayloads.put(transferKey, new DownloadingPayload(payload));
		
		// give the user a transfer key to tie them to the object
		return transferKey;
    }

	public boolean downloadAvailable(String transferKey)
    {
		if(downloadingPayloads.containsKey(transferKey))
		{
			return downloadingPayloads.get(transferKey).dataAvailable();
		}
		return false;
    }
	
	public byte[] downloadPayload(Parcel parcel)
	{
		return payloadDao.retrievePayload(parcel);
	}

	public boolean deletePayload(String payloadLocation)
    {
		return payloadDao.deletePayload(payloadLocation);
    }
}
