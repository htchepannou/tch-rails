/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.util.EntityManagerThreadLocal;
import com.tchepannou.rails.core.util.I18nThreadLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.jms.JMSException;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class JobController
{
    //-- Static Methods
    private static final Logger LOG = Logger.getLogger (JobController.class);

    //-- Attributes
    private JobContext _jobContext;

    //-- Public methods
    /**
     * This method must be override
     */
    public void execute ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.debug ("execute()");
        }
    }

    public void setJobContext (JobContext context)
    {
        _jobContext =  context;
    }

    public JobContext getJobContext ()
    {
        return _jobContext;
    }

    public I18n getI18n ()
    {
        return I18nThreadLocal.getI18n ();
    }


    //-- Protected methods
    /**
     * Deliver an email
     *
     * @param template Name of the email template. The email is located at <code>/mail/<i>mailer.name()</i>/<i>template</i></code>
     * @param data  Data to apply to the template
     * @param mailer Email controller
     *
     * @throws IOException if any error while loading the email template
     * @throws MessagingException if any error while sending the email
     */
    protected void deliver (String template, Map data, MailController mailer)
        throws IOException, MessagingException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("deliver(" + template + "," + data + "," + mailer + ")");
        }
        getJobContext ().getContainerContext ().deliver (template, data, mailer);
    }


    protected void sendToQueue (String destination, Serializable message)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("sendToQueue(" + destination + "," + message + ")");
        }
        try
        {
            getMessagingService ().sendToQueue (destination, message);
        }
        catch (JMSException e)
        {
            throw new IllegalStateException ("JMS error", e);
        }
    }

    protected void sendToTopic(String destination, Serializable message)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("sendToTopic(" + destination + "," + message + ")");
        }
        try
        {
            getMessagingService ().sendToTopic (destination, message);
        }
        catch (JMSException e)
        {
            throw new IllegalStateException ("JMS error", e);
        }
    }

    protected EntityManager getEntityManager ()
    {
        return EntityManagerThreadLocal.getEntityManager ();
    }


    protected UserService getUserService ()
    {
        return (UserService)getService (UserService.class);
    }

    protected RenderService getRenderService ()
    {
        return (RenderService)getService (RenderService.class);
    }

    protected OptionService getOptionService ()
    {
        return (OptionService)getService (OptionService.class);
    }

    protected MessagingService getMessagingService ()
    {
        return (MessagingService)getService (MessagingService.class);
    }

    protected Service getService (Class type)
    {
        Service service = (Service)findService (type);
        if (service == null)
        {
            throw new IllegalStateException ("Service not found: " + type);
        }
        return service;

    }
    protected Service findService (Class<? extends Service> type)
    {
        return getJobContext ().getContainerContext ().findService (type);
    }

}
