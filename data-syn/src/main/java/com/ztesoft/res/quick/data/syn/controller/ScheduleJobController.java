package com.ztesoft.res.quick.data.syn.controller;

import com.ztesoft.res.quick.data.syn.model.entity.ScheduleJob;
import com.ztesoft.res.quick.data.syn.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务控制器
 * version : 1.0
 */
@Controller
public class ScheduleJobController {

    /**
     * job service
     */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 任务页面
     *
     * @return
     */
    @RequestMapping(value = "input-schedule-job", method = RequestMethod.GET)
    public String inputScheduleJob(ScheduleJob scheduleJob, ModelMap modelMap) {

        if (scheduleJob.getScheduleJobId() != null) {
            scheduleJob = scheduleJobService.get(scheduleJob.getScheduleJobId());
            modelMap.put("scheduleJob", scheduleJob);
        }

        return "input-schedule-job";
    }

    /**
     * 删除任务
     *
     * @return
     */
    @RequestMapping(value = "delete-schedule-job", method = RequestMethod.GET)
    public String deleteScheduleJob(Long scheduleJobId) {

        scheduleJobService.delete(scheduleJobId);

        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "run-once-schedule-job", method = RequestMethod.GET)
    public String runOnceScheduleJob(Long scheduleJobId) {

        scheduleJobService.runOnce(scheduleJobId);

        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 暂停
     *
     * @return
     */
    @RequestMapping(value = "pause-schedule-job", method = RequestMethod.GET)
    public String pauseScheduleJob(Long scheduleJobId) {
        scheduleJobService.pauseJob(scheduleJobId);
        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 恢复
     *
     * @return
     */
    @RequestMapping(value = "resume-schedule-job", method = RequestMethod.GET)
    public String resumeScheduleJob(Long scheduleJobId) {
        scheduleJobService.resumeJob(scheduleJobId);
        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 保存任务
     *
     * @param scheduleJob scheduleJob
     * @return
     */
    @RequestMapping(value = "save-schedule-job", method = RequestMethod.POST)
    public String saveScheduleJob(ScheduleJob scheduleJob) {

        //测试用随便设个状态
        scheduleJob.setStatus("1");

        if (scheduleJob.getScheduleJobId() == null) {
            scheduleJobService.insert(scheduleJob);
        } else {
            scheduleJobService.update(scheduleJob);
        }
        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 任务列表页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "list-schedule-job", method = RequestMethod.GET)
    public String listScheduleJob(ScheduleJob scheduleJob, ModelMap modelMap) {

        List<ScheduleJob> scheduleJobList = scheduleJobService.queryList(scheduleJob);
        modelMap.put("scheduleJobList", scheduleJobList);

        List<ScheduleJob> executingJobList = scheduleJobService.queryExecutingJobList();
        modelMap.put("executingJobList", executingJobList);

        return "list-schedule-job";
    }

}
