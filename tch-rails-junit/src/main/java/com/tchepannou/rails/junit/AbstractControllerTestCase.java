/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.engine.container.Engine;
import com.tchepannou.rails.engine.container.EngineConfigurator;
import org.springframework.mock.web.MockServletContext;


/**
 *
 * @author herve
 */
public abstract class AbstractControllerTestCase
    extends AbstractTestCase
{
    //-- Attribute
    private MockServletContext _servletContext;
    private Engine _engine = new Engine ();

    //-- Constructor
    public AbstractControllerTestCase ()
    {
    }

    public AbstractControllerTestCase (String name)
    {
        super (name);
    }

    //-- Abstract methods
    public abstract String getPersistenceUnitName ();

    
    //-- TestCase override
    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _servletContext = new MockServletContext ();

        new EngineConfigurator ().configure (_engine, _servletContext);
        _engine.init (_servletContext);
    }

    @Override
    protected void tearDown ()
        throws Exception
    {
        super.tearDown ();

        _servletContext = null;        
        _engine.destroy ();
    }

    //-- Protected
    protected void registerService (Class<? extends Service> spec, Service service)
    {
        _engine.register (spec, service);
    }

    protected Service findService (Class<? extends Service> spec)
    {
        return _engine.findService (spec);
    }

    protected MockServletContext getServletContext ()
    {
        return _servletContext;
    }

    protected Engine getEngine ()
    {
        return _engine;
    }
}
