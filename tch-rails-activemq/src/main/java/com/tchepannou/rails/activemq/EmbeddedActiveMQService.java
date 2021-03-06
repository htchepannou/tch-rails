/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;

import com.tchepannou.rails.activemq.ActiveMQService;
import com.tchepannou.rails.core.api.MessagingService;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.InitializationException;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class EmbeddedActiveMQService
    extends ActiveMQService
    implements MessagingService
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (EmbeddedActiveMQService.class);

    //-- Attribute
    private BrokerService _broker;


    //-- Service override
    @Override
    public void init (ServiceContext context)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("init(" + context + ")");
        }

        super.init (context);

        _broker = new BrokerService();
        String url = getUrl ();
        if (url.startsWith ("vm://"))
        {
            int i=url.indexOf ("?");
            String name= i > 0 ? url.substring (5, i) : url.substring (5);
            _broker.setBrokerName (name);
        }
        try
        {
            _broker.addConnector(getUrl ());
            _broker.start ();
        }
        catch (Exception e)
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
        super.destroy ();

        if (_broker != null)
        {
            try
            {
                _broker.stop ();
            }
            catch (Exception e)
            {
                LOG.warn ("Unexpected error while stopping", e);
            }
        }

    }
}
