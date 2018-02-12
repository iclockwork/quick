package com.ztesoft.res.quick.base.util.ftp;

/**
 * FTPParamConfig
 *
 * @author: fengwang
 * @date: 2018-2-7 15:38
 * @version: 1.0
 * @since: JDK 1.8
 */
public class FTPParamConfig {
    private String ip;
    private int port;
    private String user;
    private String password;

    private String remoteFileName;
    private String remotePath;
    private String localFileName;
    private String localPath;

    private String ftpDataFilePath;
    private String ftpHisFilePath;
    private String ftpLogFilePath;


    public FTPParamConfig() {
    }

    public FTPParamConfig(String ip, int port) {
        this.ip = ip;
        if (port <= 0) {
            this.port = 21;
        } else {
            this.port = port;
        }
    }

    public FTPParamConfig(String ip, int port, String user, String password) {
        this.ip = ip;
        if (port <= 0) {
            this.port = 21;
        } else {
            this.port = port;
        }
        this.user = user;
        this.password = password;

        System.out.println(" FTPParamConfig ip  " + ip + "  port  " + port + "  user  " + user + "  password  " + password);
    }

    public FTPParamConfig(String ip, int port, String user, String password, String rtPath, String rtFile) {
        this.ip = ip;
        if (port <= 0) {
            this.port = 21;
        } else {
            this.port = port;
        }
        this.user = user;
        this.password = password;
        this.remotePath = rtPath == null ? "" : rtPath;
        this.remoteFileName = rtFile == null ? "" : rtFile;

        System.out.println("FTPParamConfig  ip  " + ip + "  port  " + port + "  user  " + user + "  password  " + password + "  rtPath  " + rtPath + "  rtFile  " + rtFile);
    }

    public FTPParamConfig(String ip, int port, String user, String password,
                          String remoteFileName, String remotePath, String localFileName,
                          String localPath, String ftpDataFilePath, String ftpHisFilePath,
                          String ftpLogFilePath) {
        super();
        this.ip = ip;
        if (port <= 0) {
            this.port = 21;
        } else {
            this.port = port;
        }
        this.user = user;
        this.password = password;
        this.remoteFileName = remoteFileName;
        this.remotePath = remotePath;
        this.localFileName = localFileName;
        this.localPath = localPath;
        this.ftpDataFilePath = ftpDataFilePath;
        this.ftpHisFilePath = ftpHisFilePath;
        this.ftpLogFilePath = ftpLogFilePath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getLocalFileName() {
        return localFileName;
    }

    public void setLocalFileName(String localFileName) {
        this.localFileName = localFileName;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getFtpDataFilePath() {
        return ftpDataFilePath;
    }

    public void setFtpDataFilePath(String ftpDataFilePath) {
        this.ftpDataFilePath = ftpDataFilePath;
    }

    public String getFtpHisFilePath() {
        return ftpHisFilePath;
    }

    public void setFtpHisFilePath(String ftpHisFilePath) {
        this.ftpHisFilePath = ftpHisFilePath;
    }

    public String getFtpLogFilePath() {
        return ftpLogFilePath;
    }

    public void setFtpLogFilePath(String ftpLogFilePath) {
        this.ftpLogFilePath = ftpLogFilePath;
    }

    @Override
    public String toString() {
        return "FTPParamConfig [ip=" + ip + ", port=" + port + ", user=" + user
                + ", password=" + password + ", remoteFileName="
                + remoteFileName + ", remotePath=" + remotePath
                + ", localFileName=" + localFileName + ", localPath="
                + localPath + ", ftpDataFilePath=" + ftpDataFilePath
                + ", ftpHisFilePath=" + ftpHisFilePath + ", ftpLogFilePath="
                + ftpLogFilePath + "]";
    }

}
