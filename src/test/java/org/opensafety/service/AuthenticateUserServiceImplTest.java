
package org.opensafety.service;

import org.junit.Test;
import org.opensafety.BaseSpringJunitTestCase;
import org.opensafety.hishare.service.interfaces.http.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticateUserServiceImplTest extends BaseSpringJunitTestCase {
	  
    @Autowired
    AuthenticateUser authenticateUser;
	    		
    /**
     * This is not intended to be a real test - just a placeholder/example of a service test
     */    
	@Test
    public void testAuthenticate() throws Exception {
	    String authId = this.authenticateUser.authenticate("joe", "openid.google.com", "a super secret");
	    assertNotNull("No authId returned.",authId);
	}

		
}
