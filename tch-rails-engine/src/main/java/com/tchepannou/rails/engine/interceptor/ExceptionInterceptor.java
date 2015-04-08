/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobInterceptor;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.MailInterceptor;
import com.tchepannou.rails.core.api.MessageController;
import com.tchepannou.rails.core.api.MessageInterceptor;
import com.tchepannou.rails.core.exception.MethodInvocationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author herve
 */
public class ExceptionInterceptor
    implements ActionInterceptor,
               JobInterceptor,
               MailInterceptor,
               MessageInterceptor
{
    //-- ActionInterceptor overrides
    public int before (ActionController controller)
        throws IOException
    {
        return Interceptor.CONTINUE;
    }

    public void after (ActionController controller)
        throws IOException
    {
    }

    public int before (Method method, ActionController controller)
        throws IOException
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, ActionController controller, Throwable e)
        throws IOException
    {
        handleException (e);
    }

    //-- JobInterceptor overrides
    public int before (JobController controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (JobController controller, Throwable cause)
    {
        try
        {
            handleException (cause);
        }
        catch (IOException e)
        {
            throw new MethodInvocationException (e);
        }
    }

    //-- ActionInterceptor overrides
    public int before (MailController controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (MailController controller)
    {
    }

    public int before (Method method, MailController controller)
        throws IOException
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, MailController controller, Throwable cause)
    {
        try
        {
            handleException (cause);
        }
        catch (IOException e)
        {
            throw new MethodInvocationException (e);
        }
    }



    //-- MessageInterceptor
    public int before (MessageController controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (MessageController controller, Throwable cause)
    {
        try
        {
            handleException (cause);
        }
        catch (IOException e)
        {
            throw new MethodInvocationException (e);
        }
    }


    //-- Private method
    private void handleException (Throwable e)
        throws IOException
    {
        if ( e == null )
        {
            return;
        }

        if ( e instanceof InvocationTargetException )
        {
            Throwable cause = e.getCause ();
            if ( cause instanceof RuntimeException )
            {
                throw ( RuntimeException ) cause;
            }
            else if ( cause instanceof IOException )
            {
                throw ( IOException ) cause;
            }
            else
            {
                throw new MethodInvocationException (e);
            }
        }
        else if ( e instanceof RuntimeException )
        {
            throw ( RuntimeException ) e;
        }
        else
        {
            throw new MethodInvocationException (e);
        }
    }
}
