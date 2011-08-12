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
	}
}
