package org.opensafety.hishare.dao.interfaces;

public interface PayloadDao
{
	boolean savePayload(String location, byte[] payload);
	byte[] retrievePayload(String location);
}
