/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.annotation.RequireUser;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class ActionRequireUserInterceptor 
    extends AbstractActionInterceptor
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (ActionRequireUserInterceptor.class);


    //-- AbstractActionInterceptor override
    @Override
    public int before (Method method, ActionController ac)
        throws IOException
    {
        RequireUser annotation = method.getAnnotation (RequireUser.class);
        return checkSecurity (annotation, ac);
    }

    @Override
    public int before (ActionController ac)
        throws IOException
    {
        RequireUser annotation = ac.getClass ().getAnnotation (RequireUser.class);
        return checkSecurity (annotation, ac);

    }


    //-- Protected
    protected final int checkSecurity (RequireUser annotation, ActionController ac)
        throws IOException
    {
        if (annotation != null)
        {
            User user = ac.getUser ();
            if (user == null)
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("User required. Redirecting to login page");
                }
                ac.redirectToLogin ();
                ac.getResource ().output (ac.getResponse ());
                return Interceptor.STOP;
            }
            else
            {
                boolean allow = hasPermission (annotation, ac);
                if (!allow)
                {
                    String msg = "Permission denied. Permission required: " + StringUtil.merge(annotation.permissions (), ",");
                    LOG.error (msg);
                    ac.getResponse ().sendError (401, msg);
                    return Interceptor.STOP;
                }
            }
        }
        return Interceptor.CONTINUE;

    }

    protected final boolean hasPermission(RequireUser annotation, ActionController ac)
    {
        boolean allow = false;
        String[] permissions = annotation.permissions ();
        if (permissions != null && permissions.length > 0)
        {
            if (permissions.length == 1)
            {
                String permission = permissions[0];
                allow = StringUtil.isEmpty (permission) || hasPermission (permission, ac);
            }
            else
            {
                for (String permission : permissions)
                {
                    if (!StringUtil.isEmpty (permission) && hasPermission (permission, ac))
                    {
                        allow = true;
                        break;
                    }
                }
            }
        }
        else
        {
            allow = true;
        }
        return allow;
    }

    protected boolean hasPermission (String permission, ActionController controller)
    {
        User user = controller.getUser ();
        return user != null && user.hasPermission (permission);
    }
}
