/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.quartz;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobService;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author herve
 */
public class JobImpl
    implements Job
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (JobImpl.class);

    //-- Attributes
    private boolean _rollback;

    //-- Job overrides
    public void execute (JobExecutionContext jec)
        throws JobExecutionException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("execute (" + jec + ")");
        }

        JobController controller = createJobController (jec);
        JobDataMap jdm = jec.getJobDetail ().getJobDataMap ();
        JobService.Executor executor = (JobService.Executor)jdm.get (QuartzService.EXECUTOR_KEY);

        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("executing " + controller);
        }
        executor.execute (controller);
    }


    //-- Private methods
    private JobController createJobController (JobExecutionContext jec)
    {
        JobDataMap jdm = jec.getJobDetail ().getJobDataMap ();
        Class<? extends JobController> clazz = (Class<? extends JobController>)jdm.get (QuartzService.CONTROLLER_CLASS_KEY);
        try
        {
            JobController controller = clazz.newInstance ();
            JobContext context = createJobContext (jec);
            controller.setJobContext (context);
            return controller;
        }
        catch ( InstantiationException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + clazz, ex);
        }
        catch ( IllegalAccessException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + clazz, ex);
        }
    }

    private JobContext createJobContext (final JobExecutionContext jec)
    {
        return new JobContext () {

            public ContainerContext getContainerContext ()
            {
                JobDataMap jdm = jec.getJobDetail ().getJobDataMap ();
                return (ContainerContext)jdm.get (QuartzService.CONTAINER_CONTEXT_KEY);
            }

            public boolean isRollback ()
            {
                return _rollback;
            }

            public void setRollback (boolean rollback)
            {
                _rollback  = rollback;
            }
        };
    }

}
