/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobInterceptor;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.MailInterceptor;
import com.tchepannou.rails.core.api.MessageInterceptor;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.ServiceContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import junit.framework.TestCase;
import org.springframework.mock.web.MockServletContext;

/**
 *
 * @author herve
 */
public abstract class AbstractServiceTest
    extends TestCase
{
    //-- Attribute
    private MockServletContext _servletContext;
    private ContainerContext _containerContext;
    private Map<Class<? extends Service>, Service> _services;


    //-- Constructor
    public AbstractServiceTest ()
    {

    }
    public AbstractServiceTest (String name)
    {
        super (name);
    }

    //-- TestCase override
    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _servletContext = new MockServletContext ();
        _containerContext = createContainerContext ();
        _services = new HashMap<Class<? extends Service>, Service> ();
    }

    //-- Utility methods
    protected void registerService (Class<? extends Service> type, Service service)
    {
        _services.put (type, service);
    }
    
    protected ServiceContext createServiceContext ()
    {
        return new ServiceContext ()
        {
            public ContainerContext getContainerContext ()
            {
                return _containerContext;
            }
        };
    }

    protected ContainerContext createContainerContext ()
    {
        return new ContainerContext ()
        {
            public ServletContext getServletContext ()
            {
                return _servletContext;
            }

            public EntityManagerFactory getEntityManagerFactory ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public List<ActionInterceptor> getActionInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public Service findService (Class<? extends Service> type)
            {
                return _services != null ? _services.get (type) : null;
            }

            public String getBasePackage ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getLoginURL ()
            {
                return "/login";
            }

            public List<JobInterceptor> getJobInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public List<MailInterceptor> getMailInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public void deliver (String template, MailController mailer)
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public void deliver (String template, Map data, MailController mailer)
                throws IOException,
                       MessagingException
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public List<MessageInterceptor> getMessageInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            
        };
    }
}
