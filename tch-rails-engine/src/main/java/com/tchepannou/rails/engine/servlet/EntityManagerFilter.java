/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.servlet;

import com.tchepannou.rails.core.util.EntityManagerThreadLocal;
import com.tchepannou.rails.engine.util.ServletUtil;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 * This filter create a new <code>EntityManager</code> and store it into the
 * request attributes under the name {@link EntityManagerProvider#ENTITY_MANAGER_KEY}.
 * <p>
 * This filter has the following initialization parameters:
 * <ul>
 *      <li><code>persistence.unit.name</code> (REQUIRED): Name of the Persistence Unit of the <code>EntityManager</code>
 *      <li><code>transaction</code> (OPTIONAL): Start a new transaction at each request if <code>true</code> (Default: <code>true</code>)
 * </ul>
 * 
 * @author herve
 */
public class EntityManagerFilter
    implements Filter
{
    //-- Static Attributes ------------
    private static final Logger LOG = Logger.getLogger (EntityManagerFilter.class);
//    public static final String PARAM_PERSISTENCE_UNIT_NAME = "persistence.unit.name";

    //-- Filter overrides  -----------
    public void destroy ()
    {
        LOG.info ("Destroying");

        LOG.info ("Destroyed");
    }

    public void init (FilterConfig filterConfig)
    {
//        try
//        {
//            LOG.info ("Initializing");
//
//            /* Persistence Unit */
//            String puname = filterConfig.getInitParameter (PARAM_PERSISTENCE_UNIT_NAME);
//            LOG.debug ("Init-Parameter: " + PARAM_PERSISTENCE_UNIT_NAME + "=" + puname);
//            LOG.debug ("Creating EntityManagerFactory: " + puname);
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory (puname);
//            EntityManagerThreadLocal.init (emf);
//
//            LOG.info ("Initialized");
//        }
//        catch ( PersistenceException e )
//        {
//            LOG.error ("Initialization error", e);
//            throw e;
//        }
    }

    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException,
               ServletException
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("doFilter(" + ServletUtil.toRequestURL (request) + ")");
        }

        EntityManager em = EntityManagerThreadLocal.getEntityManager ();
        try
        {
            chain.doFilter (request, response);
        }
        finally
        {
            if ( em != null && em.isOpen () )
            {
                em.close ();
            }
        }
    }
}
