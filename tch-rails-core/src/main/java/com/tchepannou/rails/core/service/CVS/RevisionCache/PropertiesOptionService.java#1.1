/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.OptionService;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.InitializationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Implementation of {@link OptionService} that loads the options from the
 * file <code>/WEB-INF/rails-option.properties</code>
 *
 * @author herve
 */
public class PropertiesOptionService
    extends AbstractService
    implements OptionService
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (PropertiesOptionService.class);

    //-- Attributes
    private List<String> __names = null;
    private Properties _properties = new Properties ();


    //-- OptionService override
    public List<String> getNames ()
    {
        if (__names == null)
        {
            __names = new ArrayList<String> (_properties.stringPropertyNames ());
        }
        return __names;
    }

    public String get (String name, String defaultValue)
    {
        String value = _properties.getProperty (name);
        return value != null ? value : defaultValue;
    }

    public void set (String name, String value)
    {
        _properties.setProperty (name, value);
        __names = null;
    }

    //-- Service overrides
    @Override
    public void init (ServiceContext context)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("init(" + context + ")");
        }
        super.init (context);

        try
        {
            _properties = loadConfigurationAsProperties ("rails-option.properties");
        }
        catch (FileNotFoundException e)
        {
            LOG.warn ("User configuration not available");
        }
        catch (IOException e)
        {
            throw new InitializationException ("Initialization error", e);
        }
    }

    @Override
    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("destroy()");
        }
        super.destroy ();

        _properties.clear ();
        __names = null;

    }
}
