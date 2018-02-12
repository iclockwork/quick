package com.ztesoft.res.quick.address.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * AddrSegmCheckTmp
 *
 * @author: fengwang
 * @date: 2018/2/7 14:21
 * @version: 1.0
 * @since: JDK 1.8
 */
@Table(name = "addr_segm_file_record")
@Entity
public class AddressFileRecord extends JpaEntity<String> implements Serializable {
    private Long recordId;
    private String fileName;
    private String filePath;
    private Integer addressTotal;
    private String remark;
    private Integer state;
    private Long consumeTime;
    private Date recordDate;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "seq_addr_segm_file_record", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "record_id", nullable = false, length = 24)
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "file_name", nullable = false, length = 1024)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_path", nullable = false, length = 1024)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "address_total", nullable = false, length = 8)
    public Integer getAddressTotal() {
        return addressTotal;
    }

    public void setAddressTotal(Integer addressTotal) {
        this.addressTotal = addressTotal;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 1)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "consume_time", nullable = false, length = 12)
    public Long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Long consumeTime) {
        this.consumeTime = consumeTime;
    }

    @Column(name = "record_date", nullable = false)
    @Basic
    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
