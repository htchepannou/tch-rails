/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.annotation.WebTemplate;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Interceptor;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

/**
 * This interceptor store in the response attribute the template information
 * 
 * @author herve
 */
public class ActionWebTemplateInterceptor
    extends AbstractActionInterceptor
{
    //-- Static Attribute
    public static final String ATTR_TEMPLATE = "com.tchepannou.rails.template_key";
    public static final String ATTR_CONTROLLER = "com.tchepannou.rails.controller_key";

    
    //-- ActionInterceptor override
    @Override
    public int before (ActionController ac)
        throws IOException
    {
        WebTemplate template = ac.getClass ().getAnnotation (WebTemplate.class);
        if (template != null)
        {
            applyTemplate (ac, template);
        }

        return Interceptor.CONTINUE;
    }

    @Override
    public int before (Method method, ActionController ac)
        throws IOException
    {
        WebTemplate template = method.getAnnotation (WebTemplate.class);
        if (template != null)
        {
            applyTemplate (ac, template);
        }

        return Interceptor.CONTINUE;
    }
    
    private void applyTemplate(ActionController ac, WebTemplate template)
    {
        HttpServletRequest request = ac.getRequest ();
        request.setAttribute (ATTR_TEMPLATE, template);
        request.setAttribute (ATTR_CONTROLLER, ac);
    }
}
