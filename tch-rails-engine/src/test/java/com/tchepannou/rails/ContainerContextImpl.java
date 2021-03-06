/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails;

import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobInterceptor;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.MailInterceptor;
import com.tchepannou.rails.core.api.MessageInterceptor;
import com.tchepannou.rails.core.api.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import org.springframework.mock.web.MockServletContext;

/**
 *
 * @author herve
 */
public class ContainerContextImpl 
    implements ContainerContext
{
    private String _basePackage;
    private String _loginURL = "/login";
    private ServletContext _servletContext = new MockServletContext ();
    private EntityManagerFactory _entityManagerFactory;
    private List<ActionInterceptor> _actionInterceptors = new ArrayList<ActionInterceptor> ();
    private List<JobInterceptor> _jobInterceptors = new ArrayList<JobInterceptor> ();
    private List<MailInterceptor> _mailInterceptors = new ArrayList<MailInterceptor> ();
    private List<MessageInterceptor> _messagingInterceptors = new ArrayList<MessageInterceptor> ();
    private Map<Class<? extends Service>, Service> _services = new HashMap<Class<? extends Service>, Service> ();

    public void register (Class<? extends Service> type, Service service)
    {
        service.init (new ServiceContextImpl (this));
        _services.put (type, service);
    }

    public void registerActionInterceptor (ActionInterceptor interceptor)
    {
        _actionInterceptors.add (interceptor);
    }
    
    public void registerJobInterceptor (JobInterceptor interceptor)
    {
        _jobInterceptors.add (interceptor);
    }

    public void registerMailInterceptor (MailInterceptor interceptor)
    {
        _mailInterceptors.add (interceptor);
    }

    public void registerMessagingInterceptor (MessageInterceptor interceptor)
    {
        _messagingInterceptors.add (interceptor);
    }

    public Service findService (Class<? extends Service> type)
    {
        return _services.get (type);
    }


    public String getBasePackage ()
    {
        return _basePackage;
    }

    public void setBasePackage (String basePackage)
    {
        this._basePackage = basePackage;
    }

    public ServletContext getServletContext ()
    {
        return _servletContext;
    }

    public void setServletContext (ServletContext servletContext)
    {
        this._servletContext = servletContext;
    }

    public EntityManagerFactory getEntityManagerFactory ()
    {
        return _entityManagerFactory;
    }

    public void setEntityManagerFactory (EntityManagerFactory entityManagerFactory)
    {
        this._entityManagerFactory = entityManagerFactory;
    }

    public List<ActionInterceptor> getActionInterceptors ()
    {
        return _actionInterceptors;
    }

    public List<MailInterceptor> getMailInterceptors ()
    {
        return _mailInterceptors;
    }

    public List<MessageInterceptor> getMessageInterceptors ()
    {
        return _messagingInterceptors;
    }

    public Map<Class<? extends Service>, Service> getServices ()
    {
        return _services;
    }

    /**
     * @return the _loginURL
     */
    public String getLoginURL ()
    {
        return _loginURL;
    }

    /**
     * @param loginURL the _loginURL to set
     */
    public void setLoginURL (String loginURL)
    {
        this._loginURL = loginURL;
    }

    public List<JobInterceptor> getJobInterceptors ()
    {
        return _jobInterceptors;
    }

    public void deliver (String template, Map data, MailController mailer)
        throws IOException,
               MessagingException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }
}
