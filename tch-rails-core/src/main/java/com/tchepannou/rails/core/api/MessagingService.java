/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * This service start JMS Container
 * @author herve
 */
public interface MessagingService
    extends Service
{
    public ConnectionFactory getConnectionFactory ();

    public void sendToQueue (String destination, Serializable msg)
        throws JMSException;

    public void sendToTopic (String destination, Serializable msg)
        throws JMSException;
}
