package com.ztesoft.res.quick.data.syn.service;

import com.ztesoft.res.quick.base.util.DateUtils;
import com.ztesoft.res.quick.base.util.ftp.FTPHelper;
import com.ztesoft.res.quick.base.util.ftp.FTPParamConfig;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynDoRecord;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynFtp;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynJob;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTableField;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynDoRecordDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynFtpDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynJobDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynTableFieldDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 取一个Job执行（按时间最早）
     */
    @Transactional
    public void doDataSynJob() {
        DataSynJob dataSynJob = dataSynJobDao.earliest();
        if (null != dataSynJob) {
            log.info("Do job [" + dataSynJob.getJobName() + "] start");

            //Job执行记录
            Date start = new Date();
            Long startS = start.getTime();
            DataSynDoRecord dataSynDoRecord = new DataSynDoRecord();
            String time = DateUtils.dateToStr(start, dataSynJob.getFileNameTimeFormat());
            String fileName = dataSynJob.getFileNamePrefix() + dataSynJob.getFileNameSeparate() + time + dataSynJob.getFileNameExtension();
            String filePath = dataSynJob.getDir() + fileName;
            dataSynDoRecord.setFileName(fileName);
            dataSynDoRecord.setFilePath(filePath);
            dataSynDoRecord.setRowTotal(0);
            dataSynDoRecord.setConsumeTime(0L);
            dataSynDoRecord.setStartDate(start);
            dataSynDoRecord.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_TO_DO);
            dataSynDoRecord.setEndDate(start);
            dataSynDoRecord.setJobId(dataSynJob.getJobId());

            FTPHelper ftpHelper = getFtpHelper(dataSynJob.getFtpId());
            try {
                int rowTotal = 0;
                if (ftpHelper.connFTPServer()) {
                    FTPClient ftpClient = ftpHelper.getFtpClient();
                    if ((ftpClient != null) && ftpClient.isConnected()) {
                        ftpClient.setControlEncoding(ftpHelper.ENCODE);
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        ftpClient.enterLocalPassiveMode();

                        ArrayList<String[]> dataList = ftpHelper.getFileContentForList(dataSynJob.getDir(), fileName, dataSynJob.getFileEncode());
                        if (null != dataList && !dataList.isEmpty() && dataList.size() > 1) {
                            rowTotal = dataList.size() - 1;
                            log.info("File data row size : " + rowTotal);
                            //字段定义
                            List<DataSynTableField> fields = dataSynTableFieldDao.list(dataSynJob.getJobId());
                            String fieldNameStr = "";
                            String fieldValueStr = "";
                            for (int i = 0; i < fields.size(); i++) {
                                DataSynTableField field = fields.get(i);
                                if (StringUtils.isNotEmpty(fieldNameStr)) {
                                    fieldNameStr += ", ";
                                    fieldValueStr += ", ";
                                }
                                fieldNameStr += field.getFieldName();
                                fieldValueStr += "?";
                            }
                            String sql = "insert into " + dataSynJob.getTableName() + " (" + fieldNameStr + ") values (" + fieldValueStr + ")";
                            log.info("Table save sql : " + sql);

                            //数据，第一行为表头，从第二行开始读取
                            for (int i = 1; i < dataList.size(); i++) {
                                String[] dataArr = dataList.get(i);
                                String rowStr = "";
                                for (int j = 0; j < dataArr.length; j++) {
                                    if (StringUtils.isNotEmpty(rowStr)) {
                                        rowStr += ", ";
                                    }
                                    rowStr += dataArr[j];
                                }

                                log.info("File data rows " + i + " : " + rowStr);

                                //插入数据
                                dataSynTableFieldDao.insert(fields, sql, dataArr);
                            }
                        } else {
                            log.warn("There is no data");
                        }
                    }
                }

                //保存Job执行记录
                Date end = new Date();
                Long endS = end.getTime();
                dataSynDoRecord.setRowTotal(rowTotal);
                dataSynDoRecord.setConsumeTime(endS - startS);
                dataSynDoRecord.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_SUCCESSFUL);
                dataSynDoRecord.setEndDate(end);
                dataSynDoRecordDao.insert(dataSynDoRecord);
            } catch (Exception e) {
                log.error(e.getMessage(), e);

                Date end = new Date();
                Long endS = end.getTime();
                dataSynDoRecord.setConsumeTime(endS - startS);
                dataSynDoRecord.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_FAILED);
                dataSynDoRecord.setEndDate(end);
                dataSynDoRecord.setRemark(e.getMessage());
                dataSynDoRecordDao.insert(dataSynDoRecord);
            } finally {
                ftpHelper.closeFTPServer();
            }
            log.info("Do job [" + dataSynJob.getJobName() + "] end");
        } else {
            log.warn("There is no job doing");
        }
    }

    /**
     * getFtpHelper
     */
    private FTPHelper getFtpHelper(Long FtpId) {
        DataSynFtp dataSynFtp = dataSynFtpDao.findById(FtpId);
        FTPParamConfig ftpParamConfig = new FTPParamConfig();
        ftpParamConfig.setIp(dataSynFtp.getIp());
        ftpParamConfig.setPort(dataSynFtp.getPort());
        ftpParamConfig.setUser(dataSynFtp.getUsername());
        ftpParamConfig.setPassword(dataSynFtp.getPassword());
        FTPHelper ftpHelper = new FTPHelper();
        ftpHelper.setFTPParam(ftpParamConfig);

        return ftpHelper;
    }
}
