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
package org.opensafety.hishare.managers.interfaces.remoting;

import org.opensafety.hishare.model.Parcel;

public interface PayloadManager
{
	String beginDownload(Parcel parcel);
	
	String beginUpload();
	
	boolean deletePayload(String payloadLocation);
	
	boolean downloadAvailable(String transferKey);
	
	Integer getChunkSize();
	
	boolean persistPayload(Parcel parcel, byte[] payload);
	
	byte[] resolveUpload(String transferKey);
	
	Boolean uploadChunk(String transferKey, byte[] chunk);
}
