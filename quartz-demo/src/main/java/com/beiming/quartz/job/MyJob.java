package com.beiming.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * MyJob-2024/4/1-10:45
 */
public class MyJob extends QuartzJobBean {

//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("MyJob: " + new Date());
//    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + ": " + context.getJobDetail().getJobDataMap().get("date"));
    }
}
