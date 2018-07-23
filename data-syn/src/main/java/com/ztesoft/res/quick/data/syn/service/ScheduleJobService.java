package com.ztesoft.res.quick.data.syn.service;

import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.ScheduleJob;
import com.ztesoft.res.quick.data.syn.model.repository.ScheduleJobDao;
import com.ztesoft.res.quick.data.syn.utils.ScheduleUtils;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务服务实现
 * version : 1.0
 */
@Service("scheduleJobService")
public class ScheduleJobService {

    /**
     * 调度工厂Bean
     */
    @Autowired
    private Scheduler scheduler;

    @Autowired
    ScheduleJobDao scheduleJobDao;

    @Transactional
    public void initScheduleJob() {
        List<ScheduleJob> scheduleJobList = scheduleJobDao.list();
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Transactional
    public Long insert(ScheduleJob scheduleJob) {
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        Date now = new Date();
        scheduleJob.setGmtCreate(now);
        scheduleJob.setGmtModify(now);
        scheduleJob = scheduleJobDao.insert(scheduleJob);
        return scheduleJob.getScheduleJobId();
    }

    @Transactional
    public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        ScheduleJob scheduleJobSaved = scheduleJobDao.findById(scheduleJob.getScheduleJobId());
        scheduleJobSaved.setJobName(scheduleJob.getJobName());
        scheduleJobSaved.setAliasName(scheduleJob.getAliasName());
        scheduleJobSaved.setJobGroup(scheduleJob.getJobGroup());
        scheduleJobSaved.setStatus(scheduleJob.getStatus());
        scheduleJobSaved.setCronExpression(scheduleJob.getCronExpression());
        scheduleJobSaved.setSync(scheduleJob.getSync());
        scheduleJobSaved.setTaskId(scheduleJob.getTaskId());
        scheduleJobSaved.setUrl(scheduleJob.getUrl());
        scheduleJobSaved.setDescription(scheduleJob.getDescription());
        Date now = new Date();
        scheduleJobSaved.setGmtModify(now);
        scheduleJobDao.update(scheduleJobSaved);
    }

    @Transactional
    public void delUpdate(ScheduleJob scheduleJob) {
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);

        ScheduleJob scheduleJobSaved = scheduleJobDao.findById(scheduleJob.getScheduleJobId());
        Date now = new Date();
        scheduleJobSaved.setGmtModify(now);
        //数据库直接更新即可
        scheduleJobDao.update(scheduleJobSaved);
    }

    @Transactional
    public void delete(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.findById(scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        scheduleJobDao.delete(scheduleJob);
    }

    public void runOnce(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.findById(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    public void pauseJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.findById(scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    public void resumeJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.findById(scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    @Transactional(readOnly = true)
    public ScheduleJob get(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.findById(scheduleJobId);
        return scheduleJob;
    }

    @Transactional(readOnly = true)
    public List<ScheduleJob> queryList(ScheduleJob scheduleJob) {
        List<ScheduleJob> scheduleJobList = scheduleJobDao.list();

        try {
            for (ScheduleJob vo : scheduleJobList) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                vo.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            //演示用，就不处理了
        }
        return scheduleJobList;
    }

    /**
     * 获取运行中的job列表
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ScheduleJob> queryExecutingJobList() {
        try {
            // 存放结果集
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();

            // 获取scheduler中的JobGroupName
            for (String group : scheduler.getJobGroupNames()) {
                // 获取JobKey 循环遍历JobKey
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob) jobDataMap.get(DataSynConstant.JOB_PARAM_KEY);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJob.setJobTrigger(trigger.getKey().getName());
                    scheduleJob.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJob.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if (triggerState.name().equals("NORMAL")) {
                        jobList.add(scheduleJob);
                    }
                }
            }

            /** 非集群环境获取正在执行的任务列表 */
            /**
             List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
             List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(executingJobs.size());
             for (JobExecutionContext executingJob : executingJobs) {
             ScheduleJobVo job = new ScheduleJobVo();
             JobDetail jobDetail = executingJob.getJobDetail();
             JobKey jobKey = jobDetail.getKey();
             Trigger trigger = executingJob.getTrigger();
             job.setJobName(jobKey.getName());
             job.setJobGroup(jobKey.getGroup());
             job.setJobTrigger(trigger.getKey().getName());
             Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
             job.setStatus(triggerState.name());
             if (trigger instanceof CronTrigger) {
             CronTrigger cronTrigger = (CronTrigger) trigger;
             String cronExpression = cronTrigger.getCronExpression();
             job.setCronExpression(cronExpression);
             }
             jobList.add(job);
             }*/

            return jobList;
        } catch (SchedulerException e) {
            return null;
        }

    }
}
