package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

/**
 * DataSynJob
 *
 * @author: fengwang
 * @date: 2018-6-12 15:31
 * @version: 1.0
 * @since: JDK 1.7
 */
@Entity
@Table(name = "QUICK_DATA_SYN_JOB")
public class DataSynJob extends JpaEntity<String> implements Serializable {
    private Long jobId;
    private String jobName;
    private String jobGroup;
    private String cronExpression;
    private Integer jobType;
    private Long ftpId;
    private String dir;
    private String fileNamePrefix;
    private String fileNameSeparate;
    private String fileNameTimeFormat;
    private String fileNameExtension;
    private String tableName;
    private String description;
    private Integer state;
    private Time createTime;
    private Time modifyTime;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_QUICK_DATA_SYN_JOB", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "JOB_ID", nullable = false, precision = 0)
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
    @Column(name = "JOB_GROUP", nullable = false, length = 32)
    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
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
    @Column(name = "JOB_TYPE", nullable = false)
    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "FTP_ID", nullable = false, precision = 0)
    public Long getFtpId() {
        return ftpId;
    }

    public void setFtpId(Long ftpId) {
        this.ftpId = ftpId;
    }

    @Basic
    @Column(name = "DIR", nullable = false, length = 100)
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Basic
    @Column(name = "FILE_NAME_PREFIX", nullable = false, length = 100)
    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    @Basic
    @Column(name = "FILE_NAME_SEPARATE", nullable = false, length = 8)
    public String getFileNameSeparate() {
        return fileNameSeparate;
    }

    public void setFileNameSeparate(String fileNameSeparate) {
        this.fileNameSeparate = fileNameSeparate;
    }

    @Basic
    @Column(name = "FILE_NAME_TIME_FORMAT", nullable = false, length = 100)
    public String getFileNameTimeFormat() {
        return fileNameTimeFormat;
    }

    public void setFileNameTimeFormat(String fileNameTimeFormat) {
        this.fileNameTimeFormat = fileNameTimeFormat;
    }

    @Basic
    @Column(name = "FILE_NAME_EXTENSION", nullable = false, length = 100)
    public String getFileNameExtension() {
        return fileNameExtension;
    }

    public void setFileNameExtension(String fileNameExtension) {
        this.fileNameExtension = fileNameExtension;
    }

    @Basic
    @Column(name = "TABLE_NAME", nullable = false, length = 100)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
    @Column(name = "STATE", nullable = false)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = false)
    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "MODIFY_TIME", nullable = false)
    public Time getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Time modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSynJob that = (DataSynJob) o;
        return Objects.equals(jobId, that.jobId) &&
                Objects.equals(jobName, that.jobName) &&
                Objects.equals(jobGroup, that.jobGroup) &&
                Objects.equals(cronExpression, that.cronExpression) &&
                Objects.equals(jobType, that.jobType) &&
                Objects.equals(ftpId, that.ftpId) &&
                Objects.equals(dir, that.dir) &&
                Objects.equals(fileNamePrefix, that.fileNamePrefix) &&
                Objects.equals(fileNameSeparate, that.fileNameSeparate) &&
                Objects.equals(fileNameTimeFormat, that.fileNameTimeFormat) &&
                Objects.equals(fileNameExtension, that.fileNameExtension) &&
                Objects.equals(tableName, that.tableName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(state, that.state) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyTime, that.modifyTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobId, jobName, jobGroup, cronExpression, jobType, ftpId, dir, fileNamePrefix, fileNameSeparate, fileNameTimeFormat, fileNameExtension, tableName, description, state, createTime, modifyTime);
    }
}
