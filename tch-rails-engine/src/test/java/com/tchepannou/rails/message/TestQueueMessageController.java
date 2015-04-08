/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.message;

import com.tchepannou.rails.core.annotation.MessageSource;
import com.tchepannou.rails.core.api.MessageController;

/**
 *
 * @author herve
 */
@MessageSource (name="testQueueRails", type=MessageSource.Type.QUEUE)
public class TestQueueMessageController
    extends MessageController
{
    public static Object _msg;
    
    @Override
    public void onMessage (Object msg)
    {
        _msg = msg;
    }

}
