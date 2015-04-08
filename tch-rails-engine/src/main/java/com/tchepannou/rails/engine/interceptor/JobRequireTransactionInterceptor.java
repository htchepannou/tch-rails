/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.annotation.RequireTransaction;
import com.tchepannou.rails.core.api.ActionInterceptor;
import com.tchepannou.rails.core.api.JobContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.util.EntityTransactionThreadLocal;
import javax.persistence.EntityTransaction;

/**
 * This is the interceptor that start/commit/rollback transaction.
 * The interceptor will trigger the transaction if the JobController or the
 * action method has the annotation {@link RequireTransaction}
 * 
 * @author herve
 */
public class JobRequireTransactionInterceptor
    extends AbstractJobInterceptor
{
    //-- ActionInterceptor
    @Override
    public int before (JobController controller)
    {
        begin (controller);
        return ActionInterceptor.CONTINUE;
    }

    @Override
    public void after (JobController controller, Throwable e)
    {
        JobContext context = controller.getJobContext ();
        if (e == null && !context.isRollback ())
        {
            commit ();
        }
        else
        {
            rollback ();
        }
    }



    //-- Private methods
    private void begin (JobController controller)
    {
        if (isTransactionRequired (controller))
        {
            EntityTransaction tx = EntityTransactionThreadLocal.getTransaction ();
            tx.begin ();
        }
    }

    private void commit ()
    {
        EntityTransaction tx = EntityTransactionThreadLocal.getTransaction ();
        if ( tx != null && tx.isActive () )
        {
            tx.commit ();
        }
    }

    private void rollback ()
    {
        EntityTransaction tx = EntityTransactionThreadLocal.getTransaction ();
        if ( tx != null && tx.isActive () )
        {
            tx.rollback ();
        }
    }

    private boolean isTransactionRequired (JobController controller)
    {
        return controller.getClass ().getAnnotation (RequireTransaction.class) != null;
    }
}
