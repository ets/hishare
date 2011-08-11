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
	
	public byte[]
	        decryptPayload(Parcel parcel, byte[] encryptedPayload) throws CryptographyException;
	
	public byte[] encryptPayload(Parcel parcel, byte[] payload) throws CryptographyException;
	
	public byte[] hashPassword(String password, byte[] salt) throws CryptographyException;
	
	public Long hash(String plainText) throws CryptographyException;
}
