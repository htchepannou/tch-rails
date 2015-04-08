/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.exception.JobException;

/**
 *
 * @author herve
 */
public interface JobService
    extends Service
{
    //-- Public method
    public void schedule (JobInfo job)
        throws JobException;

    //-- Inner classes
    /**
     * Job informations
     */
    public static interface JobInfo
    {
        public String getCronExpression ();

        public Class<? extends JobController> getControllerClass ();

        public Executor getExecutor ();

        public ContainerContext getContainerContext ();
    }

    /**
     * Execute the job
     */
    public static interface Executor
    {
        public void execute (JobController controller);
    }
}
