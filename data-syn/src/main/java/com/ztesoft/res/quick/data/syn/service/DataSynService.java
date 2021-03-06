package com.ztesoft.res.quick.data.syn.service;

import com.ztesoft.res.quick.base.exception.BusinessException;
import com.ztesoft.res.quick.base.util.DateUtils;
import com.ztesoft.res.quick.base.util.ftp.FTPHelper;
import com.ztesoft.res.quick.base.util.ftp.FTPParamConfig;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynDoRecord;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynFtp;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTableField;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTask;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynDoRecordDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynFtpDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynTableFieldDao;
import com.ztesoft.res.quick.data.syn.model.repository.DataSynTaskDao;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    DataSynTaskDao dataSynTaskDao;

    @Autowired
    DataSynTableFieldDao dataSynTableFieldDao;

    /**
     * Task执行
     */
    @Transactional
    public void doDataSynTask(Long taskId) throws Exception {
        DataSynTask dataSynTask = dataSynTaskDao.findById(taskId);
        if (null != dataSynTask) {
            log.info("Do task [" + dataSynTask.getTaskName() + "] start");

            //Task执行记录
            Date start = new Date();
            Long startS = start.getTime();
            DataSynDoRecord dataSynDoRecord = new DataSynDoRecord();
            String time = DateUtils.dateToStr(start, dataSynTask.getFileNameTimeFormat());
            String fileName = dataSynTask.getFileNamePrefix() + dataSynTask.getFileNameSeparate() + time + dataSynTask.getFileNameExtension();
            String filePath = dataSynTask.getDir() + fileName;
            dataSynDoRecord.setFileName(fileName);
            dataSynDoRecord.setFilePath(filePath);
            dataSynDoRecord.setRowTotal(0);
            dataSynDoRecord.setConsumeTime(0L);
            dataSynDoRecord.setStartDate(start);
            dataSynDoRecord.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_TO_DO);
            dataSynDoRecord.setEndDate(start);
            dataSynDoRecord.setTaskId(dataSynTask.getTaskId());

            if (!isExist(dataSynTask.getTaskId(), filePath)) {
                FTPHelper ftpHelper = getFtpHelper(dataSynTask.getFtpId());
                try {
                    int rowTotal = 0;

                    //Task类型
                    if (DataSynConstant.DATA_SYN_TASK_TYPE_READ.equals(dataSynTask.getTaskType())) {
                        doDataSynReadTask(ftpHelper, dataSynTask, fileName, rowTotal, dataSynDoRecord);
                    } else if (DataSynConstant.DATA_SYN_TASK_TYPE_WRITE.equals(dataSynTask.getTaskType())) {
                        doDataSynWriteTask(ftpHelper, dataSynTask, fileName, rowTotal, dataSynDoRecord);
                    }

                    //保存Task执行记录
                    Date end = new Date();
                    Long endS = end.getTime();
                    dataSynDoRecord.setConsumeTime(endS - startS);
                    dataSynDoRecord.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_SUCCESSFUL);
                    dataSynDoRecord.setEndDate(end);
                    dataSynDoRecordDao.insert(dataSynDoRecord);
                } catch (BusinessException e) {
                    log.error(e.getMessage(), e);

                    //记录自定义异常，自定义异常不回滚
                    Date end = new Date();
                    Long endS = end.getTime();
                    dataSynDoRecord.setConsumeTime(endS - startS);
                    dataSynDoRecord.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_FAILED);
                    dataSynDoRecord.setEndDate(end);
                    dataSynDoRecord.setRemark(e.getMessage());
                    dataSynDoRecordDao.insert(dataSynDoRecord);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    //将异常抛出去，否则事务不回滚
                    throw e;
                } finally {
                    //延迟1分钟关闭，以免数据丢失
                    Timer timer = new Timer();// 实例化Timer类
                    timer.schedule(new TimerTask() {
                        public void run() {
                            try {
                                ftpHelper.closeFTPServer();
                            } catch (BusinessException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 60000);
                }
            }
            log.info("Do task [" + dataSynTask.getTaskName() + "] end");
        } else {
            log.warn("Task not found");
        }
    }

    /**
     * doDataSynReadTask
     */
    private void doDataSynReadTask(FTPHelper ftpHelper, DataSynTask dataSynTask, String fileName, int rowTotal, DataSynDoRecord dataSynDoRecord) throws Exception {
        if (ftpHelper.connFTPServer()) {
            ArrayList<String[]> dataList = ftpHelper.getFileContentForList(dataSynTask.getDir(), fileName, dataSynTask.getFileEncode());
            if (null != dataList && !dataList.isEmpty() && dataList.size() > 1) {
                rowTotal = dataList.size() - 1;
                log.info("File data row size : " + rowTotal);
                //字段定义
                List<DataSynTableField> fields = dataSynTableFieldDao.list(dataSynTask.getTaskId());
                String fieldNameStr = "";
                String fieldValueStr = "";
                for (int i = 0; i < fields.size(); i++) {
                    DataSynTableField field = fields.get(i);
                    if (DataSynConstant.DATA_SYN_TABLE_FIELD_IGNORE_FLAG_NO.equals(field.getIgnoreFlag())) {
                        if (StringUtils.isNotEmpty(fieldNameStr)) {
                            fieldNameStr += ", ";
                            fieldValueStr += ", ";
                        }

                        fieldNameStr += field.getFieldName();
                        fieldValueStr += ":" + field.getFieldName();
                    }
                }
                String sql = "insert into " + dataSynTask.getTableName() + " (" + fieldNameStr + ") values (" + fieldValueStr + ")";
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

                    log.debug("File data rows " + i + " : " + rowStr);

                    //插入数据
                    dataSynTableFieldDao.genericInsert(i, fields, sql, dataArr);
                }
            } else {
                log.warn("There is no data");
            }
        }

        dataSynDoRecord.setRowTotal(rowTotal);
    }

    /**
     * doDataSynWriteTask
     */
    private void doDataSynWriteTask(FTPHelper ftpHelper, DataSynTask dataSynTask, String fileName, int rowTotal, DataSynDoRecord dataSynDoRecord) throws Exception {
        //查询数据
        String sql = dataSynTask.getTableQuerySql();
        log.info("Table query sql : " + sql);
        //待写入数据
        List<String[]> writeDataList = new ArrayList<String[]>();
        //原始数据
        List<Map<String, Object>> dataList = dataSynTableFieldDao.genericList(sql);

        if (null != dataList && !dataList.isEmpty()) {
            rowTotal = dataList.size();
            log.info("Table data row size : " + rowTotal);
            //字段定义
            List<DataSynTableField> fields = dataSynTableFieldDao.list(dataSynTask.getTaskId());

            for (int i = 0; i < dataList.size(); i++) {
                //表头
                String[] header = new String[fields.size()];
                //待写入数据
                String[] writeData = new String[fields.size()];
                Map<String, Object> item = dataList.get(i);
                String fieldNameStr = "";
                String fieldValueStr = "";
                for (int j = 0; j < fields.size(); j++) {
                    DataSynTableField field = fields.get(j);

                    //表头初始化一次
                    if (0 == i) {
                        header[j] = field.getFieldName().toUpperCase();
                    }

                    if (StringUtils.isNotEmpty(fieldNameStr)) {
                        fieldNameStr += ", ";
                        fieldValueStr += ", ";
                    }

                    fieldNameStr += field.getFieldName();
                    fieldValueStr += item.get(field.getFieldName().toUpperCase());

                    writeData[j] = MapUtils.getString(item, field.getFieldName().toUpperCase(), StringUtils.EMPTY);
                }

                //加一次表头
                if (writeDataList.isEmpty()) {
                    writeDataList.add(header);
                }
                //加数据
                writeDataList.add(writeData);

                log.debug("Table data rows " + (i + 1) + " : " + fieldValueStr);
            }

            if (ftpHelper.connFTPServer()) {
                //写入数据
                ftpHelper.writeCsvFile(dataSynTask.getDir(), fileName, writeDataList, dataSynTask.getFileEncode());
            }
        } else {
            log.warn("There is no data");
        }

        dataSynDoRecord.setRowTotal(rowTotal);
    }

    /**
     * 检查已上传数据是否完整
     */
    private void checkUploadedData(FTPHelper ftpHelper, DataSynTask dataSynTask, String fileName, int rowTotal, DataSynDoRecord dataSynDoRecord) throws BusinessException {
        if (ftpHelper.connFTPServer()) {
            int uploadedRowTotal = 0;
            ArrayList<String[]> uploadedDataList = ftpHelper.getFileContentForList(dataSynTask.getDir(), fileName, dataSynTask.getFileEncode());
            if (null != uploadedDataList && !uploadedDataList.isEmpty() && uploadedDataList.size() > 1) {
                uploadedRowTotal = uploadedDataList.size() - 1;
                log.info("Uploaded file data row size : " + uploadedRowTotal);
                //检查行数是否一致
                if (uploadedRowTotal == rowTotal) {
                    //TODO 再检查最后一行数据是否一致
                    String[] dataArr = uploadedDataList.get(uploadedRowTotal);
                    String rowStr = "";
                    for (int j = 0; j < dataArr.length; j++) {
                        if (StringUtils.isNotEmpty(rowStr)) {
                            rowStr += ", ";
                        }
                        rowStr += dataArr[j];
                    }

                    log.info("Uploaded data last rows " + uploadedRowTotal + " : " + rowStr);
                } else {
                    log.warn("Check the uploaded file data is inconsistent total");
                }
            } else {
                log.warn("Check the uploaded file is no data");
            }
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

    /**
     * 根据Task和文件路径查询是否已经有已完成记录
     *
     * @param filePath 文件路径
     * @return boolean
     */
    private boolean isExist(Long taskId, String filePath) {
        boolean isExist = false;
        DataSynDoRecord dataSynDoRecordExample = new DataSynDoRecord();
        dataSynDoRecordExample.setTaskId(taskId);
        dataSynDoRecordExample.setState(DataSynConstant.DATA_SYN_DO_RECORD_STATE_SUCCESSFUL);
        dataSynDoRecordExample.setFilePath(filePath);
        DataSynDoRecord dataSynDoRecordExist = dataSynDoRecordDao.findByExample(dataSynDoRecordExample);
        if (null != dataSynDoRecordExist) {
            isExist = true;
            log.warn("The file [" + filePath + "] already complete");
        }
        return isExist;
    }
}
