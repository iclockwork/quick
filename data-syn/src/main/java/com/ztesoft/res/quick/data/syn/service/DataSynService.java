package com.ztesoft.res.quick.data.syn.service;

import com.ztesoft.res.quick.base.util.DateUtils;
import com.ztesoft.res.quick.base.util.ftp.FTPHelper;
import com.ztesoft.res.quick.base.util.ftp.FTPParamConfig;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynFtp;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynJob;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynDoRecordDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynFtpDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynJobDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynTableFieldDao;
import com.ztesoft.res.quick.util.excel.ExcelLogs;
import com.ztesoft.res.quick.util.excel.ExcelUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * DataSynService
 *
 * @author: fengwang
 * @date: 2018-6-12 16:10
 * @version: 1.0
 * @since: JDK 1.7
 */
@Service("dataSynService")
public class DataSynService {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(DataSynService.class);

    @Autowired
    DataSynDoRecordDao dataSynDoRecordDao;

    @Autowired
    DataSynFtpDao dataSynFtpDao;

    @Autowired
    DataSynJobDao dataSynJobDao;

    @Autowired
    DataSynTableFieldDao dataSynTableFieldDao;

    /**
     * 取一个处理中的文件进行读取（按时间最早）
     */
    @Transactional
    public void doDataSynJob() {
        DataSynJob dataSynJob = dataSynJobDao.earliest();
        if (null != dataSynJob) {
            //开始时间
            Long start = System.currentTimeMillis();
            log.info("Do job [" + dataSynJob.getJobName() + "] start");

            //查询FTP配置
            DataSynFtp dataSynFtp = dataSynFtpDao.findById(dataSynJob.getFtpId());
            FTPParamConfig ftpParamConfig = new FTPParamConfig();
            ftpParamConfig.setIp(dataSynFtp.getIp());
            ftpParamConfig.setPort(dataSynFtp.getPort());
            ftpParamConfig.setUser(dataSynFtp.getUsername());
            ftpParamConfig.setPassword(dataSynFtp.getPassword());
            FTPHelper ftpHelper = new FTPHelper();
            ftpHelper.setFTPParam(ftpParamConfig);

            //读取EXCEL
            try {
                if (ftpHelper.connFTPServer()) {
                    FTPClient ftpClient = ftpHelper.getFtpClient();
                    if ((ftpClient != null) && ftpClient.isConnected()) {
                        ftpClient.setControlEncoding(ftpHelper.ENCODE); // 中文支持
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        ftpClient.enterLocalPassiveMode();
                        String time = DateUtils.dateToStr(new Date(), dataSynJob.getFileNameTimeFormat());
                        String filePath = dataSynJob.getDir() + dataSynJob.getFileNamePrefix() + dataSynJob.getFileNameSeparate() + time + dataSynJob.getFileNameExtension();
                        InputStream inputStream = ftpClient.retrieveFileStream(filePath);
                        //错误log集合
                        ExcelLogs logs = new ExcelLogs();
                        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, logs);
                        if (null != importExcel && !importExcel.isEmpty()) {
                            log.info("File data rows :" + importExcel.size());
                        }
                    }
                }
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            } finally {
                ftpHelper.closeFTPServer();
            }

            //结束时间
            Long end = System.currentTimeMillis();
            log.info("Do job [" + dataSynJob.getJobName() + "] end");
        } else {
            log.warn("There is no job doing");
        }
    }
}
