package com.ztesoft.res.quick.data.syn.model.entity;

import com.ztesoft.res.quick.base.repository.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DataSynFtp
 *
 * @author: fengwang
 * @date: 2018-6-12 15:30
 * @version: 1.0
 * @since: JDK 1.7
 */
@Entity
@Table(name = "QUICK_DATA_SYN_FTP")
public class DataSynFtp extends JpaEntity<String> implements Serializable {
    private Long ftpId;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private Date createTime;
    private Date modifyTime;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "SEQ_QUICK_DATA_SYN_FTP", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "FTP_ID", nullable = false, precision = 0)
    public Long getFtpId() {
        return ftpId;
    }

    public void setFtpId(Long ftpId) {
        this.ftpId = ftpId;
    }

    @Basic
    @Column(name = "IP", nullable = false, length = 32)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "PORT", nullable = false)
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        DataSynFtp that = (DataSynFtp) o;
        return Objects.equals(ftpId, that.ftpId) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(port, that.port) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyTime, that.modifyTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ftpId, ip, port, username, password, createTime, modifyTime);
    }
}
