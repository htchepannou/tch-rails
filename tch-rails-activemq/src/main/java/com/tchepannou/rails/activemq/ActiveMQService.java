/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.activemq;

import com.tchepannou.rails.core.api.MessagingService;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.service.AbstractService;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class ActiveMQService
    extends AbstractService
    implements MessagingService
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (ActiveMQService.class);

    //-- Attribute
    private String _url;
    private String _user;
    private String _password;
    private ActiveMQConnectionFactory __cf;

    //-- MessagingService override
    public ConnectionFactory getConnectionFactory ()
    {
        if (__cf == null)
        {
            __cf = new ActiveMQConnectionFactory (_user, _password, _url);
        }
        return __cf;
    }


    public void sendToQueue (String destination, Serializable msg)
        throws JMSException
    {
        send(destination, msg, true);
    }

    public void sendToTopic (String destination, Serializable msg)
        throws JMSException
    {
        send(destination, msg, false);
    }

    //-- Service override
    @Override
    public void init (ServiceContext context)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("init(" + context + ")");
        }
        super.init (context);

        try
        {
            LOG.info ("Initializing");
            configure ();
            LOG.info ("Initialized");
        }
        catch (IOException e)
        {
            throw new InitializationException ("Unable to start", e);
        }
    }

    @Override
    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("destroy()");
        }

        LOG.info ("Destroying");
        super.destroy ();
        __cf = null;
        LOG.info ("Destroyed");
    }


    //-- Private methods
    private void send (String destination, Serializable msg, boolean toQueue)
        throws JMSException
    {
        ConnectionFactory factory = getConnectionFactory ();
        Connection connection = factory.createConnection();
        connection.start();
        try
        {
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Message message = session.createObjectMessage (msg);
            Destination dst = toQueue ? session.createQueue(destination) : session.createTopic (destination);
            MessageProducer producer = session.createProducer(dst);
            producer.send (message);
            session.commit ();
        }
        finally
        {
            connection.close ();
        }
    }

    private void configure ()
        throws IOException
    {
        Properties props = loadConfigurationAsProperties ("rails-activemq.properties");
        _url = props.getProperty ("url");
        _user = props.getProperty ("user");
        _password = props.getProperty ("password");        
    }


    //-- Getter/Setter
    public String getUrl ()
    {
        return _url;
    }

    public String getUser ()
    {
        return _user;
    }

    public String getPassword ()
    {
        return _password;
    }
}
