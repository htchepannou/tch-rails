/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Utility for providing instance of {@link EntityManager}
 *
 * @author herve
 */
public class EntityManagerThreadLocal
{
    //-- Static Attributes
    private static ThreadLocal<EntityManager> _em = new ThreadLocal<EntityManager> ();
    private static EntityManagerFactory _emf;

    //-- Public methods
    public static boolean isEntityManagerOpened ()
    {
        EntityManager em = _em.get ();
        return em != null && em.isOpen ();
    }

    public static EntityManager getEntityManager ()
    {
        EntityManager em = _em.get ();
        if (em == null || !em.isOpen ())
        {
            em = _emf.createEntityManager ();
            _em.set (em);
        }
        return em;
    }

    public static void init (EntityManagerFactory emf)
    {
        _emf = emf;
    }
}
