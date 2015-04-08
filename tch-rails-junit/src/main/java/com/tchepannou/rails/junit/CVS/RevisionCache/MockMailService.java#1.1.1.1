/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.MailService;
import com.tchepannou.rails.core.api.ServiceContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

/**
 * Mock implementation of SMTPService for testing email to be sent
 * 
 * @author herve
 */
public class MockMailService
    implements MailService
{
    //-- Attribute
    private MailController _controller;
    
    
    //-- Getter/Setter
    public MailController getController ()
    {
        return _controller;
    }

    public String getBody ()
    {
        return _controller != null ? _controller.getBody () : null;
    }

    public String getFrom ()
    {
        return _controller.getFrom ();
    }

    public List<String> getTo ()
    {
        return _controller != null ? _controller.getTo () : new ArrayList<String> ();
    }

    public boolean containsTo (String email)
    {
        return getTo ().contains (email);
    }

    public List<String> getCc ()
    {
        return _controller != null ? _controller.getCc () : new ArrayList<String> ();
    }

    public boolean containsCc (String email)
    {
        return getCc ().contains (email);
    }
        
    public List<String> getBcc ()
    {
        return _controller != null ? _controller.getBcc () : new ArrayList<String> ();
    }

    public boolean containsBcc (String email)
    {
        return getBcc ().contains (email);
    }

    public String getSubject ()
    {
        return _controller != null ? _controller.getSubject () : null;
    }

    public List<File> getAttachments ()
    {
        return _controller != null ? _controller.getAttachments () : new ArrayList<File> ();
    }

    public boolean isMessageWasSent ()
    {
        return _controller != null;
    }


    //-- SMTPService overrides
    public void send (MailController controller)
        throws MessagingException
    {
        _controller = controller;
    }

    public void init (ServiceContext context)
    {
        _controller = null;
    }

    public void destroy ()
    {
        _controller = null;
    }
}
