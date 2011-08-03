package org.opensafety.hishare.managers.implementation.helpers;

import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		// accumulate payload
		payload.write(chunk, 0, chunk.length);
		log.info("Payload: '" + new String(payload.toByteArray()) + "'");
	}
	
	public byte[] getPayload()
	{
		return payload.toByteArray();
	}
}
