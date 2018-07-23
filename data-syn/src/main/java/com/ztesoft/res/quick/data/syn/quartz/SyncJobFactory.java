package com.ztesoft.res.quick.data.syn.quartz;


import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.ScheduleJob;
import com.ztesoft.res.quick.data.syn.service.DataSynService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 同步任务工厂
 * version : 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {
    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SyncJobFactory.class);

    /**
     * dataSynService
     */
    @Autowired
    private DataSynService dataSynService;

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("SyncJobFactory execute");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(DataSynConstant.JOB_PARAM_KEY);
        LOG.info("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
        try {
            dataSynService.doDataSynTask(scheduleJob.getTaskId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
