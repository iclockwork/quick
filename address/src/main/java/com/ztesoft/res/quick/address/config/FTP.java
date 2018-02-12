package com.ztesoft.res.quick.address.config;

import com.ztesoft.res.quick.base.util.ftp.FTPHelper;
import com.ztesoft.res.quick.base.util.ftp.FTPParamConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FTP
 *
 * @author: fengwang
 * @date: 2018-2-11 14:35
 * @version: 1.0
 * @since: JDK 1.8
 */
@Configuration
public class FTP {
    @Value("${quick.address.ftp.ip}")
    String ip;

    @Value("${quick.address.ftp.port}")
    int port;

    @Value("${quick.address.ftp.username}")
    String username;

    @Value("${quick.address.ftp.password}")
    String password;

    @Value("${quick.address.ftp.data.file.path}")
    String dataFilePath;

    @Value("${quick.address.ftp.his.file.path}")
    String hisFilePath;

    @Bean
    public FTPHelper ftpHelper() {
        FTPParamConfig ftpParamConfig = new FTPParamConfig();
        ftpParamConfig.setIp(ip);
        ftpParamConfig.setPort(port);
        ftpParamConfig.setUser(username);
        ftpParamConfig.setPassword(password);
        ftpParamConfig.setFtpDataFilePath(dataFilePath);
        ftpParamConfig.setFtpHisFilePath(hisFilePath);
        FTPHelper ftpHelper = new FTPHelper();
        ftpHelper.setFTPParam(ftpParamConfig);
        return ftpHelper;
    }
}
