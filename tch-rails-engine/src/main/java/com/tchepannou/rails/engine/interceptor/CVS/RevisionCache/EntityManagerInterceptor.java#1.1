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
import com.tchepannou.rails.core.util.EntityManagerThreadLocal;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;

/**
 * Open a new <code>EntityManager</code> prior the execution of the job
 * @author herve
 */
public class EntityManagerInterceptor
    implements JobInterceptor,
               MessageInterceptor,
               MailInterceptor,
               ActionInterceptor
{
    //-- JobInterceptor overrides
    @Override
    public void after (JobController controller, Throwable cause)
    {
        close ();
    }

    @Override
    public int before (JobController controller)
    {
        return Interceptor.CONTINUE;
    }

    //-- MessageInterceptor overrides
    public int before (MessageController controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (MessageController controller, Throwable cause)
    {
        close ();
    }

    //-- MailInterceptor overrides
    public int before (MailController controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (MailController controller)
    {
        close ();
    }

    public int before (Method method, MailController controller)
        throws IOException
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, MailController controller, Throwable e)
    {
        close ();
    }


    //-- MailInterceptor overrides
    public int before (ActionController controller)
        throws IOException
    {
        return Interceptor.CONTINUE;
    }

    public void after (ActionController controller)
        throws IOException
    {
        close ();
    }

    public int before (Method method, ActionController controller)
        throws IOException
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, ActionController controller, Throwable e)
        throws IOException
    {
        close ();
    }

    //-- Private
    private void close ()
    {
        if ( EntityManagerThreadLocal.isEntityManagerOpened () )
        {
            EntityManager em = EntityManagerThreadLocal.getEntityManager ();
            em.close ();
        }
    }

}
