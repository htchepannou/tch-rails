/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobInterceptor;
import com.tchepannou.rails.core.api.MailInterceptor;
import com.tchepannou.rails.core.api.MessageInterceptor;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.exception.ConfigurationException;
import com.tchepannou.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author herve
 */
public class EngineConfigurator
{
    //-- Attribute
    private static final Logger LOG = Logger.getLogger (EngineConfigurator.class);
    
    
    //-- Public method
    public void configure (Engine engine, ServletContext sc)
        throws ConfigurationException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("configure(" + engine + "," + sc + ")");
        }
        
        try
        {
            Document doc = load (sc);
            if (doc == null)
            {
                throw new ConfigurationException ("WEB-INF/rails.xml not found");
            }
            Element root = doc.getRootElement ();
            for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
            {
                Element elt = ( Element ) it.next ();
                handle (engine, elt);
            }
        }
        catch (DocumentException e)
        {
            throw new ConfigurationException ( "Invalid configuration file", e);
        }
        catch (ClassNotFoundException e)
        {
            throw new ConfigurationException ( "Configuration error", e);
        }
        catch (InstantiationException e)
        {
            throw new ConfigurationException ( "Configuration error", e);
        }
        catch (IllegalAccessException e)
        {
            throw new ConfigurationException ( "Configuration error", e);
        }
        catch (IOException e)
        {
            throw new ConfigurationException ( "Configuration error", e);
        }
    }

    
    //-- Private
    private void handle(Engine engine, Element elt)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        String name = elt.getName ();
        String value = elt.getTextTrim ();
        if (LOG.isDebugEnabled () && !StringUtil.isEmpty (value))
        {
            LOG.debug (name + "=" + value);
        }
        if ( "login-url".equalsIgnoreCase (name) )
        {
            engine.setLoginURL (value);
        }
        else if ( "base-package".equalsIgnoreCase (name) )
        {
            engine.setBasePackage (value);
        }
        else if ("persistence-unit".equalsIgnoreCase (name))
        {
            engine.setPersistenceUnit (value);
        }
        else if ( "services".equalsIgnoreCase (name) )
        {
            configureServices (engine, elt);
        }
        else if ("action-interceptors".equalsIgnoreCase (name) )
        {
            configureActionInterceptors (engine, elt);
        }
        else if ("job-interceptors".equalsIgnoreCase (name) )
        {
            configureJobInterceptors (engine, elt);
        }
        else if ("mail-interceptors".equalsIgnoreCase (name) )
        {
            configureMailInterceptors (engine, elt);
        }
        else if ("message-interceptors".equalsIgnoreCase (name) )
        {
            configureMessageInterceptors (engine, elt);
        }
        else if ("jobs".equalsIgnoreCase (name) )
        {
            configureJobs (engine, elt);
        }
    }

    private void configureServices (Engine engine, Element root)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
        {
            Element elt = ( Element ) it.next ();
            String name = elt.getName ();
            String value = elt.getTextTrim ();
            if ( "service".equalsIgnoreCase (name) )
            {
                String type = elt.attributeValue ("type");
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Service: " + type + "=" + value);
                }

                Class typeClass = Class.forName (type);
                Service service = ( Service ) Class.forName (value).newInstance ();
                engine.register (typeClass, service);
            }
        }
    }

    private void configureJobs (Engine engine, Element root)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
        {
            Element elt = ( Element ) it.next ();
            String name = elt.getName ();
            String value = elt.getTextTrim ();
            if ( "job".equalsIgnoreCase (name) )
            {
                String expr = elt.attributeValue ("expr");
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Job: " + expr + "=" + value);
                }

                Class<? extends JobController> clazz = ( Class<? extends JobController> ) Class.forName (value);
                engine.getJobContainer ().register (expr, clazz);
            }
        }
    }

    private void configureActionInterceptors (Engine engine, Element root)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
        {
            Element elt = ( Element ) it.next ();
            String name = elt.getName ();
            String value = elt.getTextTrim ();
            if ( "interceptor".equalsIgnoreCase (name) )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Action Interceptor: " + value);
                }

                ActionInterceptor interceptor = ( ActionInterceptor ) Class.forName (value).newInstance ();
                engine.register (interceptor);
            }
        }
    }

    private void configureJobInterceptors (Engine engine, Element root)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
        {
            Element elt = ( Element ) it.next ();
            String name = elt.getName ();
            String value = elt.getTextTrim ();
            if ( "interceptor".equalsIgnoreCase (name) )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Job Interceptor: " + value);
                }

                JobInterceptor interceptor = ( JobInterceptor ) Class.forName (value).newInstance ();
                engine.register (interceptor);
            }
        }
    }

    private void configureMailInterceptors (Engine engine, Element root)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
        {
            Element elt = ( Element ) it.next ();
            String name = elt.getName ();
            String value = elt.getTextTrim ();
            if ( "interceptor".equalsIgnoreCase (name) )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Mail Interceptor: " + value);
                }

                MailInterceptor interceptor = ( MailInterceptor ) Class.forName (value).newInstance ();
                engine.register (interceptor);
            }
        }
    }

    private void configureMessageInterceptors (Engine engine, Element root)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        for ( Iterator it = root.elements ().iterator (); it.hasNext (); )
        {
            Element elt = ( Element ) it.next ();
            String name = elt.getName ();
            String value = elt.getTextTrim ();
            if ( "interceptor".equalsIgnoreCase (name) )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Message Interceptor: " + value);
                }

                MessageInterceptor interceptor = ( MessageInterceptor ) Class.forName (value).newInstance ();
                engine.register (interceptor);
            }
        }
    }

    private Document load (ServletContext sc)
        throws IOException,
               DocumentException
    {
        String path = "/WEB-INF/rails.xml";
        String xpath = sc.getRealPath (path);
        if (xpath != null)
        {
            File file = new File (xpath);
            SAXReader reader = new SAXReader ();
            Document doc = reader.read (file);
            return doc;
        }
        else
        {
            return null;
        }
    }
}
