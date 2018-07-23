package com.ztesoft.res.quick.data.syn.quartz;

import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.ScheduleJob;
import com.ztesoft.res.quick.data.syn.service.DataSynService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 异步任务工厂
 * version : 1.0
 */
public class AsyncJobFactory extends QuartzJobBean {
    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(AsyncJobFactory.class);

    /**
     * dataSynService
     */
    @Autowired
    private DataSynService dataSynService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("AsyncJobFactory execute");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(DataSynConstant.JOB_PARAM_KEY);
        LOG.info("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
        try {
            dataSynService.doDataSynTask(scheduleJob.getTaskId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
