/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * This is an interceptor for dumping controller and request data in the log.
 *
 * @author herve
 */
public class ActionDebugInterceptor
    extends AbstractActionInterceptor
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (ActionDebugInterceptor.class);


    //-- ActionInterceptor override
    @Override
    public int before (ActionController controller)
    {
        HttpServletRequest req = controller.getActionContext ().getRequest ();

        LOG.debug ("");
        LOG.debug ("======================================================");
        LOG.debug ("URL: " + req.getRequestURL () + "?" + req.getQueryString ());
        //LOG.debug ("secured: " + ac1.isSecure ());
        if (req.getParameterNames ().hasMoreElements ())
        {
            LOG.debug ("");

            for (Enumeration names = req.getParameterNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                String[] values = req.getParameterValues ((String)name);
                if (values.length == 1)
                {
                    LOG.debug ("request.parameter[" + name + "]: " + values[0] );
                }
                else
                {
                    LOG.debug ("request.parameter[" + name + "]: {" + StringUtil.merge (values, ",") + "}" );
                }
            }
        }

        if (req.getHeaderNames ().hasMoreElements ())
        {
            LOG.debug ("");
            for (Enumeration names = req.getHeaderNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                Object value = req.getHeader ((String)name);
                LOG.debug ("request.header[" + name + "]: " + value );
            }
        }

        return ActionInterceptor.CONTINUE;
    }

    @Override
    public void after (ActionController controller)
    {
        HttpServletRequest req = controller.getActionContext ().getRequest ();

        HttpSession session = req.getSession (false);
        if (session != null && session.getAttributeNames ().hasMoreElements ())
        {
            LOG.debug ("");
            for (Enumeration names = session.getAttributeNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                Object value = session.getAttribute ((String)name);
                LOG.debug ("session.attribute[" + name + "]: " + value );
            }
        }

        if (req.getAttributeNames ().hasMoreElements ())
        {
            LOG.debug ("");
            for (Enumeration names = req.getAttributeNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                Object value = req.getAttribute ((String)name);
                LOG.debug ("request.attribute[" + name + "]: " + value );
            }
        }


        if (req.getCookies () != null && req.getCookies ().length > 0)
        {
            LOG.debug ("");
            for (Cookie cookie : req.getCookies ())
            {
                LOG.debug ("request.cookie[" + cookie.getName () + "]: " + cookie.getValue ());
            }
        }

        Map vars = controller.getViewVariables ();
        if (vars.size () > 0)
        {
            LOG.debug ("");
            for (Object var : vars.keySet ())
            {
                Object value = vars.get ((String)var);
                LOG.debug ("view.variable[" + var + "]: " + value );
            }
        }

        LOG.debug ("======================================================");
        LOG.debug ("");
    }

    @Override
    public void after (Method method, ActionController ac, Throwable thrwbl)
        throws IOException
    {
        super.after (method, ac, thrwbl);

        if (thrwbl != null)
        {
            LOG.debug ("Exception: " + thrwbl);
        }
    }



}
