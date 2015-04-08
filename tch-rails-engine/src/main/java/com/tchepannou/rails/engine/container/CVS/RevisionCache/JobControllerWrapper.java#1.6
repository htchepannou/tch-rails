/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobInterceptor;
import com.tchepannou.rails.core.api.JobService;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class JobControllerWrapper
    implements JobService.Executor
{
    //-- Static attribute
    private static final Logger LOG = Logger.getLogger (JobControllerWrapper.class);

    //-- Public method
    @Override
    public void execute (JobController controller)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("execute(" + controller + ")");
        }
        
        /* Pre-interceptor */
        int status = JobInterceptor.CONTINUE;
        ContainerContext containerContext = controller.getJobContext ().getContainerContext ();
        List<JobInterceptor> interceptors = containerContext.getJobInterceptors ();
        int size=interceptors.size ();
        for (int i=0 ; i<size ; i++)
        {
            JobInterceptor itc = interceptors.get (i);
            status = itc.before (controller);
            if (status != JobInterceptor.CONTINUE)
            {
                break;
            }
        }

        /* Service */
        Throwable cause = null;
        try
        {
            if (status != JobInterceptor.STOP)
            {
                controller.execute ();
            }
        }
        finally
        {
            /* Post-interceptor */
            for (int i=size-1 ; i>=0 ; i--)
            {
                JobInterceptor itc = interceptors.get (i);
                itc.after (controller, cause);
            }
        }
    }   
}
