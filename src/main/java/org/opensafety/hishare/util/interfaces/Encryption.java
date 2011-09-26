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
package org.opensafety.hishare.util.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface Encryption
{
	public class CryptographyException extends Exception
	{
		private static final long serialVersionUID = 1L;
		
		public CryptographyException(String message)
		{
			super(message);
		}
	};
	
	public String createPassword() throws CryptographyException;
	
	public byte[] createSalt() throws CryptographyException;
	
	public byte[] decryptPayload(Parcel parcel, byte[] encryptedPayload) throws CryptographyException;
	
	public byte[] encryptPayload(Parcel parcel, byte[] payload) throws CryptographyException;
	
	public byte[] hashPassword(String password, byte[] salt) throws CryptographyException;
	
	public Long hash(String plainText) throws CryptographyException;
}
