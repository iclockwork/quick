package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DataSynDoRecord
 *
 * @author: fengwang
 * @date: 2018-6-12 15:30
 * @version: 1.0
 * @since: JDK 1.7
 */
@Entity
@Table(name = "QUICK_DATA_SYN_DO_RECORD")
public class DataSynDoRecord extends JpaEntity<String> implements Serializable {
    private Long recordId;
    private String fileName;
    private String filePath;
    private Integer rowTotal;
    private String remark;
    private Integer state;
    private Long consumeTime;
    private Date startDate;
    private Date endDate;
    private Long taskId;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_QUICK_DATA_SYN_DO_RECORD", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "RECORD_ID", nullable = false, precision = 0)
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "FILE_NAME", nullable = false, length = 1024)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "FILE_PATH", nullable = false, length = 1024)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "ROW_TOTAL", nullable = true, precision = 0)
    public Integer getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(Integer rowTotal) {
        this.rowTotal = rowTotal;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 1024)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    @Column(name = "CONSUME_TIME", nullable = true, precision = 0)
    public Long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Long consumeTime) {
        this.consumeTime = consumeTime;
    }

    @Basic
    @Column(name = "START_DATE", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "TASK_ID", nullable = false, precision = 0)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSynDoRecord that = (DataSynDoRecord) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(filePath, that.filePath) &&
                Objects.equals(rowTotal, that.rowTotal) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(state, that.state) &&
                Objects.equals(consumeTime, that.consumeTime) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(recordId, fileName, filePath, rowTotal, remark, state, consumeTime, startDate, endDate, taskId);
    }
}
