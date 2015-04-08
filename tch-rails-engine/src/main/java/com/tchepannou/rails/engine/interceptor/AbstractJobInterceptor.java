/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.JobInterceptor;


/**
 * Abstract implementation of {@link ActionInterceptor}
 *
 * @author herve
 */
public  class AbstractJobInterceptor
    implements JobInterceptor
{

    public int before (JobController controller)
    {
        return JobInterceptor.CONTINUE;
    }

    public void after (JobController controller, Throwable cause)
    {
    }
}
