/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.MessageController;
import com.tchepannou.rails.engine.container.MessageControllerContainer;
import com.tchepannou.rails.engine.container.MessageControllerWrapper;
import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.command.ActiveMQObjectMessage;

/**
 *
 * @author herve
 */
public abstract class AbstractMessageControllerTestCase
    extends AbstractControllerTestCase
{
    //-- Public method
    public void onMessage (Serializable obj, Class <? extends MessageController> clazz)
        throws JMSException
    {
        MessageControllerContainer container = getEngine ().getMessageContainer ();
        MessageControllerWrapper ctl = container.getMessageControllerWrapper (clazz);
        if (ctl != null)
        {
            Message msg = createMessage (obj);
            ctl.onMessage (msg);
        }
    }


    //-- Private methods
    private Message createMessage (Serializable msg)
        throws JMSException
    {
        ActiveMQObjectMessage omsg = new ActiveMQObjectMessage ();
        omsg.setObject (msg);

        return omsg;
    }
}
