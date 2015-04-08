/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.util.EntityManagerThreadLocal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public abstract class AbstractTestCase
    extends TestCase
{
    //-- Static Attribute
    private static EntityManagerFactory _emf;
    private static long _uid = System.currentTimeMillis ();



    //-- Constructor
    public AbstractTestCase ()
    {
    }

    public AbstractTestCase (String name)
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
        System.out.println ("--- " + getClass().getSimpleName() + "." + getName () + "--------------------------------");
        
        if ( _emf == null )
        {
            String uname = getPersistenceUnitName ();
            if (uname != null)
            {
                _emf = Persistence.createEntityManagerFactory (uname);
                EntityManagerThreadLocal.init (_emf);
            }
        }
        
        if (getPersistenceUnitName () != null)
        {
            EntityManagerThreadLocal.getEntityManager ();
        }
    }

    @Override
    protected void tearDown ()
        throws Exception
    {
        System.out.println ("");
        
        if (getPersistenceUnitName () != null)
        {
            EntityManager em = EntityManagerThreadLocal.getEntityManager ();
            if (em.isOpen ())
            {
                em.close ();
            }
        }
    }

    //-- Protected
    protected synchronized long nextUID ()
    {
        return ++_uid;
    }

    protected EntityManager getEntityManager ()
    {
        return EntityManagerThreadLocal.getEntityManager ();
    }

    protected Object persist (ActiveRecord o)
        throws Exception
    {
        EntityManager em = getEntityManager ();
        EntityTransaction tx = em.getTransaction ();
        tx.begin ();
        if (o.isValid ())
        {
            em.persist (o);
            tx.commit ();

            return o;
        }
        else
        {
            tx.rollback ();
            return null;
        }
    }
}
