package org.opensafety.hishare.managers.implementation.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;
import org.opensafety.hishare.managers.interfaces.http.PayloadManager;
import org.opensafety.hishare.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;

public class PayloadManagerImpl implements PayloadManager
{
	@Autowired
	PayloadDao payloadDao;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public PayloadManagerImpl()
	{
	}
	
	public boolean deletePayload(String payloadLocation)
	{
		return payloadDao.deletePayload(payloadLocation);
	}
	
	public byte[] downloadPayload(Parcel parcel)
	{
		return payloadDao.retrievePayload(parcel);
	}
	
	public boolean persistPayload(Parcel parcel, byte[] payload)
	{
		return payloadDao.savePayload(parcel, payload);
	}
}
