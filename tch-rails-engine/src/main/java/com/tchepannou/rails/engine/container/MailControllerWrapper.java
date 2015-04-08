/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.MailContext;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.MailInterceptor;
import com.tchepannou.rails.core.api.MailService;
import com.tchepannou.rails.core.api.RenderService;
import com.tchepannou.rails.core.util.Util;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class MailControllerWrapper
{
    //-- Attribute
    private static final Logger LOG = Logger.getLogger (MailControllerWrapper.class);

    public static final String VAR_I18N = "i18n";
    public static final String VAR_UTIL = "util";


    //-- Attribute
    private ContainerContext _containerContext;
    private boolean _initialized;

    //-- Public method
    public void init (ContainerContext containerContext)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("init(" + containerContext + ")");
        }
        _containerContext = containerContext;
        _initialized = true;
    }

    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("destroy ()");
        }
        _containerContext = null;
        _initialized = false;
    }

    public void deliver (String action, Map data, MailController controller)
        throws MessagingException,  IOException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("deliver(" + action + "," + data + "," + controller + ")");
        }

        if (!_initialized)
        {
            throw new IllegalStateException ("Not initialized. call init() first");
        }

        initMailContext (data, controller);
        
        /* Pre-interceptor */
        int status = MailInterceptor.CONTINUE;
        List<MailInterceptor> interceptors = _containerContext.getMailInterceptors ();
        int size=interceptors.size ();
        for (int i=0 ; i<size ; i++)
        {
            MailInterceptor itc = interceptors.get (i);
            status = itc.before (controller);
            if (status != MailInterceptor.CONTINUE)
            {
                break;
            }
        }

        /* Service */
        try
        {
            if (status != MailInterceptor.STOP)
            {
                if (doPerformAction (action, controller))
                {
                    doDeliver (controller);
                }
            }
        }
        finally
        {
            /* Post-interceptor */
            for (int i=size-1 ; i>=0 ; i--)
            {
                MailInterceptor itc = interceptors.get (i);
                itc.after (controller);
            }
        }
    }



    //-- Object overrides
    @Override
    public String toString ()
    {
        return "MailControllerWrapper{}";
    }

    //-- Private methods
    private boolean doPerformAction (String action, MailController controller)
        throws IOException
    {
        Method method = getActionMethod (action, controller);
        if (method == null)
        {
            return true;
        }

        /* Pre-interceptor */
        int status = MailInterceptor.CONTINUE;
        List<MailInterceptor> interceptors = _containerContext.getMailInterceptors ();
        int size=interceptors.size ();
        for (int i=0 ; i<size ; i++)
        {
            MailInterceptor itc = interceptors.get (i);
            status = itc.before (method, controller);
            if (status != MailInterceptor.CONTINUE)
            {
                break;
            }
        }

        /* Execute the method */
        Throwable ex = null;
        try
        {
            if (status != Interceptor.STOP)
            {
                method.invoke (controller);
                initMessage (action, controller);
            }
        }
        catch (IllegalAccessException e)
        {
            ex = e;
        }
        catch (InvocationTargetException e)
        {
            ex = e;
        }
        finally
        {
            /* Post interceptors */
            for (int i=size-1 ; i>=0 ; i--)
            {
                MailInterceptor itc = interceptors.get (i);
                itc.after (method, controller, ex);
            }
        }
        return status == Interceptor.CONTINUE;
    }

    private void initMessage (String action, MailController controller)
        throws IOException
    {
        RenderService rs = (RenderService)_containerContext.findService (RenderService.class);
        if (rs == null)
        {
            throw new IllegalStateException ("Service not found: " + RenderService.class.getName ());
        }

        /* Generate content */
        Map data = new HashMap (controller.getViewVariables ());
        data.put (VAR_UTIL, new Util (_containerContext));
        data.put (VAR_I18N, controller.getI18n ());

        String template = "/mail/" + controller.getName () + "/" + action;
        StringWriter writer = new StringWriter ();
        rs.render (template, data, writer);

        String content = writer.toString ();
        String contentType = MimeUtil.getInstance ().getMimeTypeByFile (template);
        if (StringUtil.isEmpty (contentType))
        {
            contentType = MimeUtil.TEXT;
        }

        int i = content.indexOf ("\n");
        String subject = i > 0 ? content.substring (0, i) : "";
        String body = i > 0 ? content.substring (i+1) : "";
        controller.setBody (body);
        controller.setSubject (subject);
        controller.setContentType (contentType);
    }

    private void doDeliver (MailController controller)
        throws MessagingException
    {
        MailService ms = (MailService)_containerContext.findService (MailService.class);
        if (ms == null)
        {
            throw new IllegalStateException ("Service not found: " + MailService.class);
        }
        ms.send (controller);
    }

    private Method getActionMethod (String action, MailController controller)
    {
        try
        {
            int ext = action.lastIndexOf (".");
            String name = ext > 0 ? action.substring (0, ext) : action;
            return controller.getClass ().getMethod (name);
        }
        catch (NoSuchMethodException e)
        {
            return null;
        }
    }


    private void initMailContext (final Map data, MailController controller)
    {
        MailContext mc = new MailContext () {

            public ContainerContext getContainerContext ()
            {
                return _containerContext;
            }

            public Map getData ()
            {
                return data;
            }
        };
        controller.setMailContext (mc);
    }

    //-- Inner classes
}
