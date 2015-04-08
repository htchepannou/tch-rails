/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.OptionService;
import com.tchepannou.rails.core.api.RenderService;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;



/**
 * Abstract implementation of {@link Service}
 * 
 * @author herve
 */
public  abstract class AbstractService
    implements Service
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (AbstractService.class);

    
    //-- Attribute
    private ServiceContext _serviceContext;
    private boolean _initialized;

    //-- Public method
    public ServiceContext getServiceContext ()
    {
        return _serviceContext;
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    protected UserService getUserService ()
    {
        return (UserService)getService (UserService.class);
    }

    protected RenderService getRenderService ()
    {
        return (RenderService)getService (RenderService.class);
    }

    protected OptionService getOptionService ()
    {
        return (OptionService)getService (OptionService.class);
    }

    protected Service getService (Class type)
    {
        Service service = (Service)findService (type);
        if (service == null)
        {
            throw new IllegalStateException ("Service not found: " + type);
        }
        return service;

    }

    protected Service findService (Class<? extends Service> type)
    {
        return _serviceContext.getContainerContext ().findService (type);
    }


    
    //-- Service override
    public void init (ServiceContext context)
    {
        _serviceContext = context;
        _initialized = true;
    }

    public void destroy ()
    {
        _serviceContext = null;
        _initialized = false;
    }


    //-- Protected methods
    protected Properties loadConfigurationAsProperties (String confname)
        throws IOException
    {
        ServletContext context = getServiceContext ().getContainerContext ().getServletContext ();
        String path = context.getRealPath ("/WEB-INF/" + confname);
        Properties p = new Properties ();
        if (path != null)
        {
            File f = new File (path);
            InputStream in = null;
            try
            {
                in = new FileInputStream (f);
                p.load (in);

                if (LOG.isDebugEnabled ())
                {
                    for (String name : p.stringPropertyNames ())
                    {
                        LOG.debug ("    " + name + "=" + p.getProperty (name));
                    }
                }

            }
            finally
            {
                if (in != null)
                {
                    in.close ();
                }
            }
        }
        return p;
    }

}
