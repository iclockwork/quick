package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * DataSynTableField
 *
 * @author: fengwang
 * @date: 2018-6-12 15:31
 * @version: 1.0
 * @since: JDK 1.7
 */
@Entity
@Table(name = "QUICK_DATA_SYN_TABLE_FIELD")
public class DataSynTableField extends JpaEntity<String> implements Serializable {
    private Long fieldId;
    private String fieldName;
    private Integer fieldType;
    private Integer fieldOrder;
    private Long jobId;
    private Integer ignoreFlag;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_QUICK_DATA_SYN_TABLE_FIELD", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "FIELD_ID", nullable = false, precision = 0)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Basic
    @Column(name = "FIELD_NAME", nullable = false, length = 100)
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "FIELD_TYPE", nullable = false)
    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    @Basic
    @Column(name = "FIELD_ORDER", nullable = false)
    public Integer getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    @Basic
    @Column(name = "JOB_ID", nullable = false, precision = 0)
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "IGNORE_FLAG", nullable = false)
    public Integer getIgnoreFlag() {
        return ignoreFlag;
    }

    public void setIgnoreFlag(Integer ignoreFlag) {
        this.ignoreFlag = ignoreFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSynTableField that = (DataSynTableField) o;
        return Objects.equals(fieldId, that.fieldId) &&
                Objects.equals(fieldName, that.fieldName) &&
                Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(fieldOrder, that.fieldOrder) &&
                Objects.equals(jobId, that.jobId) &&
                Objects.equals(ignoreFlag, that.ignoreFlag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fieldId, fieldName, fieldType, fieldOrder, jobId, ignoreFlag);
    }
}
