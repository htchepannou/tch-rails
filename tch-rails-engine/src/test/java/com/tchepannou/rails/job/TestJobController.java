package com.tchepannou.rails.job;

import com.tchepannou.rails.core.api.JobController;

public class TestJobController extends JobController
{
    public static long _executed = 0;

    
    @Override
    public void execute ()
    {
        ++_executed;
    }
}
