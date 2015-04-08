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
@MessageSource (name="testTopicRails", type=MessageSource.Type.TOPIC)
public class TestTopicMessageController
    extends MessageController
{
    public static Object _msg;

    @Override
    public void onMessage (Object msg)
    {
        _msg = msg;
    }
}
