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
package org.opensafety.hishare.service.interfaces.remoting;

public interface UploadParcel
{
	String beginParcelUpload(String username, String authenticationId, String parcelName,
	                         Integer daysToLive);
	
	String finishParcelUpload(String username, String authenticationId, String transferKey);
	
	/**
	 * Size in bytes of the bytes to be uplaoded
	 */
	Integer getChunkSize();
	
	Boolean uploadParcelChunk(String username, String authenticationId, String transferKey,
	                          byte[] chunk);
}
