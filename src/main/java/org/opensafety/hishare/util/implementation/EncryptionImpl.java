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
package org.opensafety.hishare.util.implementation;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.util.interfaces.Encryption;

public class EncryptionImpl implements Encryption
{
	private int saltLength;
	private int pbeIterationCount;
	private int pbeKeyLength;
	private int passwordLength;
	
	private String randomAlgorithm;
	private String pbeAlgorithm;
	private String cipherAlgorithm;
	private String keyGenerator;
	private String passwordHashAlgorithm;
	
	public EncryptionImpl()
	{
		this(64, 1000, 256, 256, "SHA1PRNG", "PBEWITHSHA256AND128BITAES-CBC-BC",
		     "AES/CBC/PKCS5Padding", "AES", "SHA-512");
	}
	
	public EncryptionImpl(int saltLength, int pbeIterationCount, int passwordLength,
	        int pbeKeyLength, String randomAlgorithm, String pbeAlgorithm, String cipherAlgorithm,
	        String keyGenerator, String passwordHashAlgorihtm)
	{
		Security.addProvider(new BouncyCastleProvider());
		
		this.saltLength = saltLength;
		this.pbeIterationCount = pbeIterationCount;
		this.passwordLength = passwordLength;
		this.pbeKeyLength = pbeKeyLength;
		
		this.randomAlgorithm = randomAlgorithm;
		this.pbeAlgorithm = pbeAlgorithm;
		this.cipherAlgorithm = cipherAlgorithm;
		this.keyGenerator = keyGenerator;
		this.passwordHashAlgorithm = passwordHashAlgorihtm;
	}
	
	public int getSaltLength() {
		return saltLength;
	}

	public void setSaltLength(int saltLength) {
		this.saltLength = saltLength;
	}

	public int getPbeIterationCount() {
		return pbeIterationCount;
	}

	public void setPbeIterationCount(int pbeIterationCount) {
		this.pbeIterationCount = pbeIterationCount;
	}

	public int getPbeKeyLength() {
		return pbeKeyLength;
	}

	public void setPbeKeyLength(int pbeKeyLength) {
		this.pbeKeyLength = pbeKeyLength;
	}

	public int getPasswordLength() {
		return passwordLength;
	}

	public void setPasswordLength(int passwordLength) {
		this.passwordLength = passwordLength;
	}

	public String getRandomAlgorithm() {
		return randomAlgorithm;
	}

	public void setRandomAlgorithm(String randomAlgorithm) {
		this.randomAlgorithm = randomAlgorithm;
	}

	public String getPbeAlgorithm() {
		return pbeAlgorithm;
	}

	public void setPbeAlgorithm(String pbeAlgorithm) {
		this.pbeAlgorithm = pbeAlgorithm;
	}

	public String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	public void setCipherAlgorithm(String cipherAlgorithm) {
		this.cipherAlgorithm = cipherAlgorithm;
	}

	public String getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(String keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public String getPasswordHashAlgorithm() {
		return passwordHashAlgorithm;
	}

	public void setPasswordHashAlgorithm(String passwordHashAlgorithm) {
		this.passwordHashAlgorithm = passwordHashAlgorithm;
	}

	public String createPassword() throws CryptographyException
	{
		KeyGenerator kgen;
		try
		{
			kgen = KeyGenerator.getInstance(keyGenerator);
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		kgen.init(passwordLength);
		
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		
		return new String(Hex.encodeHex(raw));
	}
	
	public byte[] createSalt() throws CryptographyException
	{
		SecureRandom random;
		try
		{
			random = SecureRandom.getInstance(randomAlgorithm);
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		byte[] salt = new byte[saltLength];
		random.nextBytes(salt);
		
		return salt;
	}
	
	public byte[]
	        decryptPayload(Parcel parcel, byte[] encryptedPayload) throws CryptographyException
	{
		SecretKey secret;
		IvParameterSpec ivSpec;
		byte[] payload;
		
		Cipher encryptionCipher;
		try
		{
			encryptionCipher = Cipher.getInstance(cipherAlgorithm);
		}
		catch(Exception e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		secret = generateKey(parcel.getPassword(), parcel.getSalt());
		ivSpec = generateIv(parcel.getSalt());
		
		try
		{
			encryptionCipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
			payload = encryptionCipher.doFinal(encryptedPayload);
		}
		catch(Exception e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		return payload;
	}
	
	public byte[] encryptPayload(Parcel parcel, byte[] payload) throws CryptographyException
	{
		SecretKey secret;
		IvParameterSpec ivSpec;
		byte[] encryptedPayload;
		
		Cipher encryptionCipher;
		try
		{
			encryptionCipher = Cipher.getInstance(cipherAlgorithm);
		}
		catch(Exception e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		secret = generateKey(parcel.getPassword(), parcel.getSalt());
		ivSpec = generateIv(parcel.getSalt());
		
		try
		{
			encryptionCipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
			encryptedPayload = encryptionCipher.doFinal(payload);
		}
		catch(Exception e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		return encryptedPayload;
	}
	
	private IvParameterSpec generateIv(byte[] salt)
	{
		byte[] iv = Arrays.copyOfRange(salt, 0, 16);
		return new IvParameterSpec(iv);
	}
	
	private SecretKey generateKey(String password, byte[] salt) throws CryptographyException
	{
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, pbeIterationCount,
		                                       pbeKeyLength);
		
		SecretKeyFactory factory;
		SecretKey tmp;
		try
		{
			factory = SecretKeyFactory.getInstance(pbeAlgorithm);
			tmp = factory.generateSecret(pbeKeySpec);
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new CryptographyException(e.getMessage());
		}
		catch(InvalidKeySpecException e)
		{
			throw new CryptographyException(e.getMessage());
		}
		
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), keyGenerator);
		
		return secret;
	}
	
	public byte[] hashPassword(String password, byte[] salt) throws CryptographyException
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(passwordHashAlgorithm);
			md.reset();
			md.update(salt);
			return md.digest(password.getBytes());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new CryptographyException(e.getMessage());
		}
	}

	public Long hash(String plainText) throws CryptographyException
    {
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] cipher = md.digest(plainText.getBytes());
			ByteBuffer converter = ByteBuffer.wrap(cipher);
			return converter.getLong();
		}
		catch(Exception e)
		{
			throw new CryptographyException(e.getMessage());
		}
    }
}
