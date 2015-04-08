/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.quartz;

import com.tchepannou.rails.core.api.JobService;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.exception.JobException;
import com.tchepannou.rails.core.service.AbstractService;
import java.text.ParseException;
import java.util.Date;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author herve
 */
public class QuartzService
    extends AbstractService
    implements JobService
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (QuartzService.class);
    public static final String EXECUTOR_KEY = "executor_key";
    public static final String CONTROLLER_CLASS_KEY = "controller_class_key";
    public static final String CONTAINER_CONTEXT_KEY = "container_context_key";

    //-- Attributes
    private Scheduler _scheduler;

    //-- Service override
    @Override
    public void init (ServiceContext context)
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("init(" + context + ")");
        }
        super.init (context);

        try
        {
            LOG.info ("Initializing");

            /* Initialize quartz */
            StdSchedulerFactory factory = new StdSchedulerFactory ();

            factory.initialize ();

            _scheduler = factory.getScheduler ();
            _scheduler.start ();

            LOG.info ("Initialized");
        }
        catch ( SchedulerException e )
        {
            throw new InitializationException ("Unable to start", e);
        }
    }

    @Override
    public void destroy ()
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("destroy()");
        }

        try
        {
            LOG.info ("Destroying");
            _scheduler.shutdown ();
            LOG.info ("Destroyed");
        }
        catch ( SchedulerException e )
        {
            LOG.warn ("Unable to stop Quartz scheduler", e);
        }
    }

    //-- JobService overrides
    public void schedule (JobInfo job)
        throws JobException
    {
        try
        {
            String name = job.getControllerClass ().getName ();
            String cron = job.getCronExpression ();
            CronTrigger trigger = new CronTrigger (name, name, cron);
            trigger.setStartTime (new Date ());

            JobDetail xjob = new JobDetail (name, name, JobImpl.class);
            JobDataMap map = xjob.getJobDataMap ();
            map.put (CONTROLLER_CLASS_KEY, job.getControllerClass ());
            map.put (CONTAINER_CONTEXT_KEY, job.getContainerContext ());
            map.put (EXECUTOR_KEY, job.getExecutor ());

            if ( LOG.isDebugEnabled () )
            {
                LOG.debug ("scheduling " + job.getCronExpression () + "/" + job.getControllerClass ().getName ());
            }
            _scheduler.scheduleJob (xjob, trigger);
        }
        catch (ParseException e)
        {
            throw new JobException ("Invalid cron excepression: " + job.getCronExpression (), e);
        }
        catch (SchedulerException e)
        {
            throw new JobException ("Scheduler error", e);
            
        }
    }
}
