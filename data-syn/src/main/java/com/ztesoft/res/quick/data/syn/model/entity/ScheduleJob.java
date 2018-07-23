package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * ScheduleJob
 *
 * @author: fengwang
 * @date: 2018-7-23 10:02
 * @version: 1.0
 * @since: JDK 1.7
 */
@Entity
@Table(name = "QUICK_DATA_SYN_SCHEDULE")
public class ScheduleJob extends JpaEntity<String> implements Serializable {
    private Long scheduleJobId;
    private String jobName;
    private String aliasName;
    private String jobGroup;
    private String jobTrigger;
    private String status;
    private String cronExpression;
    private String sync;
    private Long taskId;
    private String url;
    private String description;
    private Date gmtCreate;
    private Date gmtModify;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_SCHEDULE_JOB", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "SCHEDULE_JOB_ID", nullable = false, precision = 0)
    public Long getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Long scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    @Basic
    @Column(name = "JOB_NAME", nullable = false, length = 100)
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "ALIAS_NAME", nullable = false, length = 100)
    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    @Basic
    @Column(name = "JOB_GROUP", nullable = false, length = 32)
    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    @Basic
    @Column(name = "JOB_TRIGGER", nullable = false, length = 100)
    public String getJobTrigger() {
        return jobTrigger;
    }

    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger;
    }

    @Basic
    @Column(name = "STATUS", nullable = false, length = 32)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CRON_EXPRESSION", nullable = false, length = 100)
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Basic
    @Column(name = "SYNC", nullable = false, length = 1)
    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    @Basic
    @Column(name = "TASK_ID", nullable = true, precision = 0)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "URL", nullable = true, length = 100)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "GMT_CREATE", nullable = false)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "GMT_MODIFY", nullable = false)
    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleJob that = (ScheduleJob) o;
        return Objects.equals(scheduleJobId, that.scheduleJobId) &&
                Objects.equals(jobName, that.jobName) &&
                Objects.equals(aliasName, that.aliasName) &&
                Objects.equals(jobGroup, that.jobGroup) &&
                Objects.equals(jobTrigger, that.jobTrigger) &&
                Objects.equals(status, that.status) &&
                Objects.equals(cronExpression, that.cronExpression) &&
                Objects.equals(sync, that.sync) &&
                Objects.equals(url, that.url) &&
                Objects.equals(description, that.description) &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModify, that.gmtModify);
    }

    @Override
    public int hashCode() {

        return Objects.hash(scheduleJobId, jobName, aliasName, jobGroup, jobTrigger, status, cronExpression, sync, url, description, gmtCreate, gmtModify);
    }
}
