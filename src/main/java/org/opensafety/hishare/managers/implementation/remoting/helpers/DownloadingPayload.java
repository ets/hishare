package org.opensafety.hishare.managers.implementation.remoting.helpers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownloadingPayload
{
	private Log log;
	private ByteArrayInputStream payload;
	
	public DownloadingPayload(byte[] payload)
	{
		this.payload = new ByteArrayInputStream(payload);
		log = LogFactory.getLog(this.getClass());
	}
	
	public boolean dataAvailable()
	{
		return (payload.available() > 0);
	}
	
	public void read(byte[] chunk) throws IOException
	{
		// accumulate payload
		payload.read(chunk);
		log.info("Payload: '" + new String(chunk) + "'");
	}
}
