package com.ztesoft.res.quick.demo.quartz.job;

import com.ztesoft.res.quick.demo.quartz.service.SampleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SampleJob
 *
 * @author: fengwang
 * @date: 2018-6-11 16:40
 * @version: 1.0
 * @since: JDK 1.7
 */
public class SampleJob implements Job {
    @Autowired
    private SampleService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        service.hello();
    }
}
