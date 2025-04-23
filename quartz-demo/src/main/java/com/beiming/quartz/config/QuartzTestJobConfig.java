package com.beiming.quartz.config;

import com.beiming.quartz.job.QuartzTestJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzTestJobConfig {
//    @Bean
    public JobDetail quartzTestJobDetail() {
        return JobBuilder.newJob(QuartzTestJob.class)
                .withIdentity(QuartzTestJob.class.getSimpleName())
                .storeDurably()
                .usingJobData("data", "test")
                .build();
    }

//    @Bean
    public Trigger quartzTestJobTrigger() {

        // 0/1 * * * * ?
        return TriggerBuilder.newTrigger()
                .forJob(QuartzTestJob.class.getSimpleName())
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();
    }
}
