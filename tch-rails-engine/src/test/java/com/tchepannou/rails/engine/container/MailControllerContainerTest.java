/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.ContainerContextImpl;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.MailService;
import com.tchepannou.rails.core.api.OptionService;
import com.tchepannou.rails.core.api.RenderService;
import com.tchepannou.rails.core.service.PropertiesOptionService;
import com.tchepannou.rails.core.service.VelocityRenderService;
import com.tchepannou.rails.mail.TestMailController;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class MailControllerContainerTest 
    extends TestCase
{
    private ContainerContext _cc;
    private MailController _controller;

    public MailControllerContainerTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        ContainerContextImpl cc = new ContainerContextImpl ();
        _cc = cc;
        cc.setBasePackage ("com.tchepannou.rails.engine.container");
        cc.register (RenderService.class, new VelocityRenderService ());
        cc.register (OptionService.class, new PropertiesOptionService ());
        cc.register (MailService.class, createMailService ());
    }

    public void testTXT ()
        throws Exception
    {
        MailControllerContainer container = new MailControllerContainer ();
        container.init (_cc);
        assertTrue("container not initialized", container.isInitialized ());

        MailController mc = new TestMailController ();
        mc.setFrom ("ray.sponsible@google.com");
        mc.addTo ("herve.tchepannou@gmail.com");
        Map data = new HashMap ();
        data.put ("name", "herve");
        container.deliver ("hello.txt", data, mc);

        assertNotNull ("no message sent", _controller);
        assertEquals ("from", mc.getFrom (), _controller.getFrom ());
        assertTrue ("to", _controller.getTo ().contains ("herve.tchepannou@gmail.com"));
        assertEquals ("subject", "Subject", _controller.getSubject ());
        assertEquals ("body", "Hello herve", _controller.getBody ());
        assertEquals ("contentType", "text/plain", _controller.getContentType ());
        assertEquals ("encoding", "utf-8", _controller.getEncoding ());
    }


    public void testHTML ()
        throws Exception
    {
        MailControllerContainer container = new MailControllerContainer ();
        container.init (_cc);
        assertTrue("container not initialized", container.isInitialized ());

        MailController mc = new TestMailController ();
        mc.setFrom ("ray.sponsible@google.com");
        mc.addTo ("herve.tchepannou@gmail.com");
        Map data = new HashMap ();
        data.put ("name", "herve");
        container.deliver ("hello.html", data, mc);

        assertNotNull ("no message sent", _controller);
        assertEquals ("from", mc.getFrom (), _controller.getFrom ());
        assertTrue ("to", _controller.getTo ().contains ("herve.tchepannou@gmail.com"));
        assertEquals ("subject", "Subject", _controller.getSubject ());
        assertEquals ("body", "<html><body>Hello </b>herve</b></body></html>", _controller.getBody ());
        assertEquals ("contentType", "text/html", _controller.getContentType ());
        assertEquals ("encoding", "utf-8", _controller.getEncoding ());
    }

    public MailService createMailService ()
    {
        return new MailService () {

            public void send (MailController controller)
                throws MessagingException
            {
                _controller = controller;
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }
        };
    }
}
