/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobContext;
import com.tchepannou.rails.core.api.JobController;

/**
 *
 * @author herve
 */
public abstract class AbstractJobControllerTestCase
    extends AbstractControllerTestCase
{
    private boolean _rollback;

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _rollback = false;
    }

    public void execute(JobController controller)
    {
        final ContainerContext cc = getEngine ().createContainerContext ();
        JobContext jc = new JobContext () {

            public ContainerContext getContainerContext ()
            {
                return cc;
            }

            public boolean isRollback ()
            {
                return _rollback;
            }

            public void setRollback (boolean rollback)
            {
                _rollback = rollback;
            }
        };
        controller.setJobContext (jc);
        controller.execute ();
    }
}
