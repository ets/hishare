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
package org.opensafety.hishare.managers.implementation.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;
import org.opensafety.hishare.managers.interfaces.PayloadManager;
import org.opensafety.hishare.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
