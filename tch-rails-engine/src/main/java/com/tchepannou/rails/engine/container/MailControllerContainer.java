/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.MailController;
import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;

/**
 * Default implementation of {@link ActionControllerProvider}.
 *
 * @author herve
 */
public class MailControllerContainer
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (MailControllerContainer.class);
    
    
    //-- Attribute
    private boolean _initialized;
    private MailControllerWrapper _wrapper = new MailControllerWrapper ();
    
    //-- Constructeur
    public MailControllerContainer ()
    {
    }


    //-- Public method
    public void init (ContainerContext containerContext)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("init(" + containerContext + ")");
        }
        _wrapper.init (containerContext);
        _initialized = true;
    }

    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("destroy()");
        }

        _wrapper.destroy ();
        _initialized = false;
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    public void deliver (String action, Map data, MailController controller)
        throws MessagingException, IOException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("deliver(" + action + "," + data + "," + controller + ")");
        }
        _wrapper.deliver (action, data, controller);
    }
}
