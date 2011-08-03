package org.opensafety.hishare.util.interfaces;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.opensafety.hishare.model.Parcel;

public interface Encryption
{
	public class CryptographyException extends Exception
	{
        public CryptographyException(String message)
        {
        	super(message);
        }

		private static final long serialVersionUID = 1L;
    };
	
	public byte[] createSalt() throws CryptographyException;
	public String createPassword() throws CryptographyException;
	public byte[] hashPassword(String password, byte[] salt) throws CryptographyException;
	public byte[] encryptPayload(Parcel parcel, byte[] payload) throws CryptographyException;
	public byte[] decryptPayload(Parcel parcel, byte[] encryptedPayload) throws CryptographyException;
}
