/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobService;
import com.tchepannou.rails.core.api.JobService.Executor;
import com.tchepannou.rails.core.api.JobService.JobInfo;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.exception.JobException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Default implementation of {@link ActionControllerProvider}.
 *
 * @author herve
 */
public class JobControllerContainer
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (JobControllerContainer.class);
    public static final int START_DELAY = 5;
    
    
    //-- Attribute
    private ContainerContext _containerContext;
    private List<JobService.JobInfo> _jobs = new ArrayList<JobInfo> ();
    private Map<JobService.JobInfo, JobService.Executor> _executors = new HashMap<JobInfo, Executor> ();
    private boolean _initialized;
//    private Scheduler _scheduler;
    
    //-- Constructeur
    public JobControllerContainer ()
    {
    }


    //-- Public method
    public void init (ContainerContext containerContext)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("init(" + containerContext + ")");
        }
        _containerContext = containerContext;

        try
        {
            initJobs ();
            _initialized = true;
        }
        catch (JobException e)
        {
            throw new InitializationException ("Unable to initialize the jobs", e);
        }
    }

    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("destroy()");
        }

        _jobs.clear ();
        _executors.clear ();
        _initialized = false;
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    public void register (final String cronExpression, final Class<? extends JobController> controller)
    {
        JobService.JobInfo job = new JobService.JobInfo ()
        {
            public String getCronExpression ()
            {
                return cronExpression;
            }

            public Class<? extends JobController> getControllerClass ()
            {
                return controller;
            }

            public Executor getExecutor ()
            {
                return _executors.get (this);
            }

            public ContainerContext getContainerContext ()
            {
                return _containerContext;
            }
        };
        _executors.put (job, new JobControllerWrapper ());
        _jobs.add (job);
    }

    public List<JobService.JobInfo> getJobs ()
    {
        return _jobs;
    }

    private void initJobs ()
        throws JobException
    {
        JobService js = (JobService)_containerContext.findService (JobService.class);
        for (JobInfo job : _jobs)
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("scheduling " + job.getCronExpression () + "/" + job.getControllerClass ().getName ());
            }
            js.schedule (job);
        }
    }
}
