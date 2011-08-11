package org.opensafety.hishare.service.interfaces.http;

public interface ChangeAuthenticationServerPassword
{
	
	String changePassword(String authenticationServerName, String authenticationServerPassword,
	                      String newAuthenticationServerPassword);
	
}
