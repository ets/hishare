package org.opensafety.webapp.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.opensafety.BaseSpringJunitTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseControllerTestCase extends BaseSpringJunitTestCase {

    private int smtpPort = 25250;

    @Autowired
    protected MailSender mailSender;
    
    @Override
    protected void setUp() throws Exception {          
        smtpPort = smtpPort + (int) (Math.random() * 100);
        // change the port on the mailSender so it doesn't conflict with an
        // existing SMTP server on localhost
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) this.mailSender;
        mailSender.setPort(getSmtpPort());
        mailSender.setHost("localhost");        
    }
    
    @Override
    protected void tearDown() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
        
    protected int getSmtpPort() {
        return smtpPort;
    }

    /**
     * Convenience methods to make tests simpler
     * @return a MockHttpServletRequest with a POST to the specified URL
     * @param url the URL to post to
     */
    public MockHttpServletRequest newPost(String url) {
        return new MockHttpServletRequest("POST", url);
    }

    public MockHttpServletRequest newGet(String url) {
        return new MockHttpServletRequest("GET", url);
    }

    private Field[] getDeclaredFields(Class clazz) {
        Field[] f = new Field[0];
        Class superClazz = clazz.getSuperclass();
        Collection<Field> rval = new ArrayList<Field>();
        
        if (superClazz != null) {
            rval.addAll(Arrays.asList(getDeclaredFields(superClazz)));
        }
        
        rval.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return rval.toArray(f);
    }
    
    
}
