package org.opensafety.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.annotation.Resource;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;
import org.opensafety.BaseSpringJunitTestCase;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;

public class EncryptionTest extends BaseSpringJunitTestCase {
    //~ Instance fields ========================================================

    @Resource
    protected Encryption encryption;
    
    @Test
    public void testReversiblyEncryptSomething() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {
        String plainPassword = "ohmythisissecure";
        Parcel parcel = new Parcel();
        try {
            parcel.setSalt(this.encryption.createSalt());
            
            byte[] hashedPassword = encryption.hashPassword(plainPassword, parcel.getSalt());        
            parcel.setHashedPassword(hashedPassword);
            
            byte[] plainPayload = "this is the plain text of a payload...a very very short payload.".getBytes();
            
            byte[] encryptedPayload = encryption.encryptPayload(parcel, plainPayload);
            byte[] decryptedPayload = encryption.decryptPayload(parcel, encryptedPayload);
            
            assertEquals("Decryption did not reconstitute encrypted payload!",new String(decryptedPayload),new String(plainPayload));
        } catch (CryptographyException e) {            
            e.printStackTrace();
            fail("Encountered Exception.");
        }        
    }

}
