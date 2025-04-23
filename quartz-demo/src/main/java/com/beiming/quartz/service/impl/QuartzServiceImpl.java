package com.beiming.quartz.service.impl;

import com.beiming.quartz.domain.QuartzCreateParam;
import com.beiming.quartz.domain.QuartzDetailParam;
import com.beiming.quartz.domain.QuartzJobDetailDto;
import com.beiming.quartz.domain.QuartzTriggerDetailDto;
import com.beiming.quartz.domain.QuartzUpdateParam;
import com.beiming.quartz.service.QuartzService;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerUtils;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void addJob(QuartzCreateParam param) throws SchedulerException {
        String clazzName = param.getJobClazz();
        String jobName = param.getJobName();
        String jobGroup = param.getJobGroup();
        String cron = param.getCron();
        String description = param.getDescription();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
//        checkJobExist(jobKey);

        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到任务类：" + clazzName);
        }
        JobDataMap jobDataMap = new JobDataMap();
        if (param.getJobDataMap() != null) {
            param.getJobDataMap().forEach(jobDataMap::put);
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .usingJobData(jobDataMap)
                .build();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        String triggerId = jobKey.getGroup() + jobKey.getName();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .withIdentity(triggerId)
                .withDescription(description)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);

        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    @Override
    public void updateJob(QuartzUpdateParam param) throws SchedulerException {
        String jobName = param.getJobName();
        String jobGroup = param.getJobGroup();
        String cron = param.getCron();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        String triggerId = jobKey.getGroup() + jobKey.getName();

        checkJobExist(jobKey);

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerId);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        TriggerBuilder<?> triggerBuilder = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .withIdentity(triggerId);

        Trigger trigger = triggerBuilder.build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @Override
    public void pauseJob(QuartzDetailParam param) throws SchedulerException {
        String jobName = param.getJobName();
        String jobGroup = param.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        checkJobExist(jobKey);

        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(QuartzDetailParam param) throws SchedulerException {
        String jobName = param.getJobName();
        String jobGroup = param.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        checkJobExist(jobKey);

        scheduler.resumeJob(jobKey);
    }

    @Override
    public void deleteJob(QuartzDetailParam param) throws SchedulerException {
        String jobName = param.getJobName();
        String jobGroup = param.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        checkJobExist(jobKey);
        
        // 先暂停再删除
        scheduler.pauseJob(jobKey);
        scheduler.deleteJob(jobKey);
    }

    @Override
    public List<QuartzJobDetailDto> jobList() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<QuartzJobDetailDto> jobDtoList = new ArrayList<>();
        for (JobKey jobKey : scheduler.getJobKeys(matcher)) {
            QuartzJobDetailDto jobDto = getJobDtoByJobKey(jobKey);
            jobDtoList.add(jobDto);
        }
        return jobDtoList;
    }

    @Override
    public QuartzJobDetailDto jobDetail(QuartzDetailParam param) throws SchedulerException {
        String jobName = param.getJobName();
        String jobGroup = param.getJobGroup();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        return getJobDtoByJobKey(jobKey);
    }

    /*************** 私有方法 ***************/
    private void checkJobExist(JobKey jobKey) throws SchedulerException {
        if (!scheduler.checkExists(jobKey)) {
            throw new RuntimeException("该定时任务不存在：" + jobKey.getName());
        }
    }
    
    public QuartzJobDetailDto getJobDtoByJobKey(JobKey jobKey) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        List<Trigger> triggerList = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

        QuartzJobDetailDto jobDto = new QuartzJobDetailDto();
        jobDto.setJobClazz(jobDetail.getJobClass().toString());
        jobDto.setJobName(jobKey.getName());
        jobDto.setJobGroup(jobKey.getGroup());
        jobDto.setJobDataMap(jobDetail.getJobDataMap());

        List<QuartzTriggerDetailDto> triggerDtoList = new ArrayList<>();
        for (Trigger trigger : triggerList) {
            QuartzTriggerDetailDto triggerDto = new QuartzTriggerDetailDto();
            triggerDto.setTriggerName(trigger.getKey().getName());
            triggerDto.setTriggerGroup(trigger.getKey().getGroup());
            triggerDto.setDescription(trigger.getDescription());

            if (trigger instanceof CronTriggerImpl) {
                CronTriggerImpl cronTriggerImpl = (CronTriggerImpl) trigger;
                String cronExpression = cronTriggerImpl.getCronExpression();
                triggerDto.setCron(cronExpression);

                // 最近10次的触发时间
                List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 10);
                triggerDto.setRecentFireTimeList(dates);
            }

            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            triggerDto.setTriggerState(triggerState.toString());

            triggerDtoList.add(triggerDto);
        }
        jobDto.setTriggerDetailDtoList(triggerDtoList);
        return jobDto;
    }
}
