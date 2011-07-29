package org.opensafety.hishare.managers.implementation.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.model.Parcel;

public class UploadingPayload
{
	private Log log;
	private ByteArrayOutputStream payload;
	
	public UploadingPayload()
	{
		payload = new ByteArrayOutputStream();
		log = LogFactory.getLog(this.getClass());
	}
	
	public void append(byte[] chunk)
	{
		//accumulate payload
		payload.write(chunk, 0, chunk.length);
		log.info("Payload: '"+new String(payload.toByteArray())+"'");
	}
	
	public byte[] getPayload()
	{
		return payload.toByteArray();
	}
}
