/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Utility for providing instance of {@link EntityTransaction}
 *
 * @author herve
 */
public class EntityTransactionThreadLocal
{
    //-- Static Attributes
    private static ThreadLocal<EntityTransaction> _tx = new ThreadLocal<EntityTransaction> ();

    //-- Public methods
    public static boolean isInTransaction ()
    {
        EntityTransaction tx = _tx.get ();

        return tx != null && tx.isActive ();
    }
    
    public static EntityTransaction getTransaction ()
    {
        EntityTransaction tx = _tx.get ();
        if (tx == null || !tx.isActive ())
        {
            EntityManager em = EntityManagerThreadLocal.getEntityManager ();
            tx = em.getTransaction ();
            _tx.set (tx);
        }
        return tx;
    }

    public static void init ()
    {
        _tx.remove ();
    }
}
