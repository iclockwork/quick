package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
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
    private Long rowTotal;
    private String remark;
    private Integer state;
    private Long consumeTime;
    private Time startDate;
    private Time endDate;
    private Long jobId;

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
    public Long getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(Long rowTotal) {
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
    public Time getStartDate() {
        return startDate;
    }

    public void setStartDate(Time startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE", nullable = true)
    public Time getEndDate() {
        return endDate;
    }

    public void setEndDate(Time endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "JOB_ID", nullable = false, precision = 0)
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
                Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(recordId, fileName, filePath, rowTotal, remark, state, consumeTime, startDate, endDate, jobId);
    }
}
