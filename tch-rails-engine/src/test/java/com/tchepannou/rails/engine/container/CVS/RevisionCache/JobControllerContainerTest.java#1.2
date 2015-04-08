/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.JobException;
import com.tchepannou.rails.job.TestJobController;
import com.tchepannou.rails.ContainerContextImpl;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobService;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class JobControllerContainerTest 
    extends TestCase
{
    private ContainerContext _cc;
    private JobControllerContainer _container;

    public JobControllerContainerTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        ContainerContextImpl cc = new ContainerContextImpl ();
        _cc = cc;
        cc.setBasePackage ("com.tchepannou.rails");
        cc.register (JobService.class, createJobService ());
    }

    public void testAll ()
        throws Exception
    {
        _container = new JobControllerContainer ();
        _container.register ("0 * * * * ?", TestJobController.class);

        try
        {
            _container.init (_cc);
            assertTrue ("initialized", _container.isInitialized ());

//            System.out.println ("Waiting 90s....");
//            Thread.sleep (90000);
//            assertTrue ("executed", TestJobController._executed > 0);
        }
        finally
        {
            _container.destroy ();
            assertFalse ("initialized", _container.isInitialized ());
        }
    }


    private JobService createJobService ()
    {
        return new JobService () {

            public void schedule (JobInfo job)
                throws JobException
            {
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }
        };
    }
}
