/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobInterceptor;
import com.tchepannou.rails.core.api.MessageController;
import com.tchepannou.rails.core.api.MessageInterceptor;
import com.tchepannou.rails.core.api.User;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 *
 * @author herve
 */
public class I18nInterceptor 
    implements JobInterceptor, MessageInterceptor, ActionInterceptor
{
    //-- Private methods
//    private Locale getUserLocale (User usr)
//    {
//        return usr != null
//            ? usr.getLocale ()
//            : Locale.getDefault ();
//    }
//
//    private Locale getSiteLocale (ContainerContext cc)
//    {
//        OptionService os = (OptionService)cc.findService (OptionService.class);
//        String locale = os.get (OptionService.OPTION_SITE_LOCALE, null);
//        if ( locale != null )
//        {
//            for ( Locale loc: Locale.getAvailableLocales () )
//            {
//                if ( loc.toString ().equals (locale) )
//                {
//                    return loc;
//                }
//            }
//        }
//        return Locale.getDefault ();
//    }

    //-- JobIntercetor overrides
    public int before (JobController controller)
    {
        ContainerContext cc = controller.getJobContext ().getContainerContext ();
//        I18nThreadLocal.init (null, getSiteLocale (cc));
        return Interceptor.CONTINUE;
    }

    public void after (JobController controller, Throwable cause)
    {
    }

    //-- MessageIntercetor overrides
    public int before (MessageController controller)
    {
        ContainerContext cc = controller.getMessageContext ().getContainerContext ();
//        I18nThreadLocal.init (null, getSiteLocale (cc));
        return Interceptor.CONTINUE;
    }

    public void after (MessageController controller, Throwable cause)
    {
    }

    //-- ActionIntercetor overrides
    public int before (ActionController controller)
        throws IOException
    {
        ContainerContext cc = controller.getActionContext ().getContainerContext ();
        User user = controller.getUser ();
//        I18nThreadLocal.init (getUserLocale (user), getSiteLocale (cc));
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
    }
}
