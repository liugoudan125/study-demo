package com.beiming.quartz.service;

import com.beiming.quartz.domain.QuartzCreateParam;
import com.beiming.quartz.domain.QuartzDetailParam;
import com.beiming.quartz.domain.QuartzJobDetailDto;
import com.beiming.quartz.domain.QuartzUpdateParam;
import org.quartz.SchedulerException;

import java.util.List;

public interface QuartzService {
    /**
     * 添加定时任务
     */
    void addJob(QuartzCreateParam param) throws SchedulerException;

    /**
     * 修改定时任务
     */
    void updateJob(QuartzUpdateParam param) throws SchedulerException;

    /**
     * 暂停定时任务
     */
    void pauseJob(QuartzDetailParam param) throws SchedulerException;

    /**
     * 恢复定时任务
     */
    void resumeJob(QuartzDetailParam param) throws SchedulerException;

    /**
     * 删除定时任务
     */
    void deleteJob(QuartzDetailParam param) throws SchedulerException;

    /**
     * 定时任务列表
     * @return
     */
    List<QuartzJobDetailDto> jobList() throws SchedulerException;

    /**
     * 定时任务详情
     */
    QuartzJobDetailDto jobDetail(QuartzDetailParam param) throws SchedulerException;
}
