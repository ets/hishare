package org.opensafety.webapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.opensafety.hishare.controller.AuthenticateUserController;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.HandlerAdapter;

public class AuthRequestHandlerTestCase extends BaseControllerTestCase {
   
    @Resource
    private HandlerAdapter handlerAdapter;
    @Resource
    private AuthenticateUserController authenticateUserController;
    
    
    @Override
    protected void setUp() throws Exception {
        
    }

    /**
     * This is not intended to be a real test - just a placeholder/example of a controller test
     */
    @Test
    public void testAuthenticate() {
        MockServletContext ctx = new MockServletContext();        
        MockHttpServletRequest req = this.newPost("/AuthenticateUser.hishare");
        req.addHeader("referer", "http://meetup.com");
        req.addParameter("password", "reallysecret");
        req.addParameter("authenticationServerName", "openid.com");
        req.addParameter("authenticationServerPassword", "really secret");
        
        MockHttpServletResponse resp = new MockHttpServletResponse();
        try {
            this.handlerAdapter.handle(req, resp, authenticateUserController);
            assertEquals("Did not receive OK status response",HttpServletResponse.SC_OK,resp.getStatus());            
        } catch (Exception e) {
            log.error("Exception while processing request: ",e);
            e.printStackTrace();
            this.fail("Failing after Exception: "+e.getMessage());
        }
    }    
 

}
