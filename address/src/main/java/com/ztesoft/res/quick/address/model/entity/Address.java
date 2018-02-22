package com.ztesoft.res.quick.address.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AddrSegmCheckTmp
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@Table(name = "addr_segm_check_tmp")
@Entity
public class Address extends JpaEntity<String> implements Serializable {
    private Long rowNum;
    private String regionName;
    private String standName1;
    private String standName2;
    private String standName3;
    private String standName4;
    private String standName5;
    private String standName6;
    private String standName7;
    private String standName8;
    private String standName9;
    private String useType;
    private String mark;
    private String eqpName;
    private String oldStandId7;
    private String oldStandName7;
    private String checkErr;
    private String eqpTable;
    private String bothId;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "seq_addr_segm_check_tmp", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "row_num", nullable = false, length = 24)
    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    @Basic
    @Column(name = "region_name", nullable = true, length = 80)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "v_stand_name1", nullable = true, length = 200)
    public String getStandName1() {
        return standName1;
    }

    public void setStandName1(String standName1) {
        this.standName1 = standName1;
    }

    @Basic
    @Column(name = "v_stand_name2", nullable = true, length = 200)
    public String getStandName2() {
        return standName2;
    }

    public void setStandName2(String standName2) {
        this.standName2 = standName2;
    }

    @Basic
    @Column(name = "v_stand_name3", nullable = true, length = 200)
    public String getStandName3() {
        return standName3;
    }

    public void setStandName3(String standName3) {
        this.standName3 = standName3;
    }

    @Basic
    @Column(name = "v_stand_name4", nullable = true, length = 200)
    public String getStandName4() {
        return standName4;
    }

    public void setStandName4(String standName4) {
        this.standName4 = standName4;
    }

    @Basic
    @Column(name = "v_stand_name5", nullable = true, length = 200)
    public String getStandName5() {
        return standName5;
    }

    public void setStandName5(String standName5) {
        this.standName5 = standName5;
    }

    @Basic
    @Column(name = "v_stand_name6", nullable = true, length = 200)
    public String getStandName6() {
        return standName6;
    }

    public void setStandName6(String standName6) {
        this.standName6 = standName6;
    }

    @Basic
    @Column(name = "v_stand_name7", nullable = true, length = 200)
    public String getStandName7() {
        return standName7;
    }

    public void setStandName7(String standName7) {
        this.standName7 = standName7;
    }

    @Basic
    @Column(name = "v_stand_name8", nullable = true, length = 200)
    public String getStandName8() {
        return standName8;
    }

    public void setStandName8(String standName8) {
        this.standName8 = standName8;
    }

    @Basic
    @Column(name = "v_stand_name9", nullable = true, length = 200)
    public String getStandName9() {
        return standName9;
    }

    public void setStandName9(String standName9) {
        this.standName9 = standName9;
    }

    @Basic
    @Column(name = "use_type", nullable = true, length = 200)
    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    @Basic
    @Column(name = "qun_biaoshi", nullable = true, length = 200)
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Basic
    @Column(name = "eqp_name", nullable = true, length = 200)
    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    @Basic
    @Column(name = "old_stand_id7", nullable = true, length = 200)
    public String getOldStandId7() {
        return oldStandId7;
    }

    public void setOldStandId7(String oldStandId7) {
        this.oldStandId7 = oldStandId7;
    }

    @Basic
    @Column(name = "old_stand_name7", nullable = true, length = 400)
    public String getOldStandName7() {
        return oldStandName7;
    }

    public void setOldStandName7(String oldStandName7) {
        this.oldStandName7 = oldStandName7;
    }

    @Basic
    @Column(name = "check_err", nullable = true, length = 2000)
    public String getCheckErr() {
        return checkErr;
    }

    public void setCheckErr(String checkErr) {
        this.checkErr = checkErr;
    }

    @Basic
    @Column(name = "eqp_table", nullable = true, length = 1)
    public String getEqpTable() {
        return eqpTable;
    }

    public void setEqpTable(String eqpTable) {
        this.eqpTable = eqpTable;
    }

    @Basic
    @Column(name = "both_id", nullable = true, length = 200)
    public String getBothId() {
        return bothId;
    }

    public void setBothId(String bothId) {
        this.bothId = bothId;
    }
}
