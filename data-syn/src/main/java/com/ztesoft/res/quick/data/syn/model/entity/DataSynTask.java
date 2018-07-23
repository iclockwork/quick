package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "QUICK_DATA_SYN_TASK")
public class DataSynTask extends JpaEntity<String> implements Serializable {
    private Long taskId;
    private String taskName;
    private Integer taskType;
    private Long ftpId;
    private String dir;
    private String fileNamePrefix;
    private String fileNameSeparate;
    private String fileNameTimeFormat;
    private String fileNameExtension;
    private String fileEncode;
    private String tableName;
    private String tableQuerySql;
    private String description;
    private Integer state;
    private Date createTime;
    private Date modifyTime;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_QUICK_DATA_SYN_TASK", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "TASK_ID", nullable = false, precision = 0)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "TASK_NAME", nullable = false, length = 100)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "TASK_TYPE", nullable = false)
    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
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
    @Column(name = "FILE_ENCODE", nullable = false, length = 16)
    public String getFileEncode() {
        return fileEncode;
    }

    public void setFileEncode(String fileEncode) {
        this.fileEncode = fileEncode;
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
    @Column(name = "TABLE_QUERY_SQL", nullable = false, length = 4000)
    public String getTableQuerySql() {
        return tableQuerySql;
    }

    public void setTableQuerySql(String tableQuerySql) {
        this.tableQuerySql = tableQuerySql;
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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "MODIFY_TIME", nullable = false)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSynTask that = (DataSynTask) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(taskName, that.taskName) &&
                Objects.equals(taskType, that.taskType) &&
                Objects.equals(ftpId, that.ftpId) &&
                Objects.equals(dir, that.dir) &&
                Objects.equals(fileNamePrefix, that.fileNamePrefix) &&
                Objects.equals(fileNameSeparate, that.fileNameSeparate) &&
                Objects.equals(fileNameTimeFormat, that.fileNameTimeFormat) &&
                Objects.equals(fileNameExtension, that.fileNameExtension) &&
                Objects.equals(fileEncode, that.fileEncode) &&
                Objects.equals(tableName, that.tableName) &&
                Objects.equals(tableQuerySql, that.tableQuerySql) &&
                Objects.equals(description, that.description) &&
                Objects.equals(state, that.state) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyTime, that.modifyTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(taskId, taskName, taskType, ftpId, dir, fileNamePrefix, fileNameSeparate, fileNameTimeFormat, fileNameExtension, fileEncode, tableName, tableQuerySql, description, state, createTime, modifyTime);
    }
}
