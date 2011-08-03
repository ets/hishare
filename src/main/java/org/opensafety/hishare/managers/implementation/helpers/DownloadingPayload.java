package org.opensafety.hishare.managers.implementation.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.model.Parcel;

public class DownloadingPayload
{
	private Log log;
	private ByteArrayInputStream payload;
	
	public DownloadingPayload(byte[] payload)
	{
		this.payload = new ByteArrayInputStream(payload);
		log = LogFactory.getLog(this.getClass());
	}
	
	public void read(byte[] chunk) throws IOException
	{
		//accumulate payload
		payload.read(chunk);
		log.info("Payload: '"+new String(chunk)+"'");
	}
	
	public boolean dataAvailable()
    {
		return (payload.available() > 0);
    }
}
