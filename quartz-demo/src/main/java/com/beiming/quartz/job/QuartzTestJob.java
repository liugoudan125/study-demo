package com.beiming.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class QuartzTestJob implements Job {
//    @Override
//    protected void executeInternal(org.quartz.JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("QuartzTestJob: " + new Date());
//    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("QuartzTestJob: " + new Date());
    }
}
