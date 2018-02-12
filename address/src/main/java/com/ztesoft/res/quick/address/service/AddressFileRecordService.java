package com.ztesoft.res.quick.address.service;

import com.ztesoft.res.quick.address.AddressConstant;
import com.ztesoft.res.quick.address.model.entity.Address;
import com.ztesoft.res.quick.address.model.entity.AddressFileRecord;
import com.ztesoft.res.quick.address.model.repository.AddressDao;
import com.ztesoft.res.quick.address.model.repository.AddressFileRecordDao;
import com.ztesoft.res.quick.base.util.DateUtils;
import com.ztesoft.res.quick.base.util.ftp.FTPHelper;
import com.ztesoft.res.quick.util.excel.ExcelLogs;
import com.ztesoft.res.quick.util.excel.ExcelUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * AddressFileRecordService
 *
 * @author: fengwang
 * @date: 2018-2-7 15:38
 * @version: 1.0
 * @since: JDK 1.8
 */
@Service("addressFileRecordService")
public class AddressFileRecordService {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddressFileRecordService.class);

    @Value("${quick.address.local.data.file.path}")
    private String addressLocalDataFilePath;

    @Autowired
    AddressFileRecordDao addressFileRecordDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    FTPHelper ftpHelper;

    /**
     * 扫描文件
     */
    @Transactional
    public void scanAddressFile() {
        String[] extensions = {"xls", "xlsx"};
        String encoding = System.getProperty("file.encoding");
        log.info("System file encoding[" + encoding + "]");
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(addressLocalDataFilePath), extensions, true);
        log.info("Scan files [" + files.size() + "]");
    }

    /**
     * 扫描文件
     */
    @Transactional
    public void scanAddressFileFromFTP() {
        try {
            log.info("Scan files start");
            if (ftpHelper.connFTPServer()) {
                FTPClient ftpClient = ftpHelper.getFtpClient();
                if ((ftpClient != null) && ftpClient.isConnected()) {
                    ftpClient.setControlEncoding(ftpHelper.ENCODE); // 中文支持
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    //按天扫描
                    String directory = ftpHelper.getFtpParam().getFtpDataFilePath() + DateUtils.dateToStr(new Date(), DateUtils.DATE_PATTERN_DEFAULT_SHORT);
                    innerListFiles(ftpClient, directory, true);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            ftpHelper.closeFTPServer();
            log.info("Scan files end");
        }
    }

    /**
     * 取一个未处理文件为处理中（按时间最早）
     */
    @Transactional
    public void doingAddressFile() {
        AddressFileRecord addressFileRecordTodo = addressFileRecordDao.earliestToDo();
        if (null != addressFileRecordTodo) {
            log.info("Doing file [" + addressFileRecordTodo.getFileName() + "]");
            //更新状态
            addressFileRecordTodo.setState(AddressConstant.ADDRESS_FILE_RECORD_STATE_DOING);
            //更新
            addressFileRecordDao.update(addressFileRecordTodo);
        } else {
            log.warn("There is no record to do");
        }
    }

    /**
     * 取一个处理中的文件进行读取（按时间最早）
     */
    @Transactional
    public void readAddressFile() {
        AddressFileRecord addressFileRecordDoing = addressFileRecordDao.earliestDoing();
        if (null != addressFileRecordDoing) {
            //开始时间
            Long start = System.currentTimeMillis();
            log.info("Read file [" + addressFileRecordDoing.getFileName() + "] start");

            //读取EXCEL
            try {
                InputStream inputStream = new FileInputStream(addressFileRecordDoing.getFilePath());
                //错误log集合
                ExcelLogs logs = new ExcelLogs();
                Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, logs);
                if (null != importExcel && !importExcel.isEmpty()) {
                    for (Map m : importExcel) {
                        //保存
                        this.saveAddress(m, addressFileRecordDoing.getRecordId());
                    }

                    //更新数量
                    addressFileRecordDoing.setAddressTotal(importExcel.size());
                    //更新状态
                    addressFileRecordDoing.setState(AddressConstant.ADDRESS_FILE_RECORD_STATE_SUCCESSFUL);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //更新状态
                addressFileRecordDoing.setState(AddressConstant.ADDRESS_FILE_RECORD_STATE_FAILED);
                //更新备注
                addressFileRecordDoing.setRemark(e.getMessage());
            }

            //结束时间
            Long end = System.currentTimeMillis();
            //更新耗费时间
            addressFileRecordDoing.setConsumeTime(end - start);
            //更新
            addressFileRecordDao.update(addressFileRecordDoing);
            log.info("Read file [" + addressFileRecordDoing.getFileName() + "] end");
        } else {
            log.warn("There is no record doing");
        }
    }

    /**
     * 取一个处理中的文件进行读取（按时间最早）
     */
    @Transactional
    public void readAddressFileFromFTP() {
        AddressFileRecord addressFileRecordDoing = addressFileRecordDao.earliestDoing();
        if (null != addressFileRecordDoing) {
            //开始时间
            Long start = System.currentTimeMillis();
            log.info("Read file [" + addressFileRecordDoing.getFileName() + "] start");

            //读取EXCEL
            try {
                if (ftpHelper.connFTPServer()) {
                    FTPClient ftpClient = ftpHelper.getFtpClient();
                    if ((ftpClient != null) && ftpClient.isConnected()) {
                        ftpClient.setControlEncoding(ftpHelper.ENCODE); // 中文支持
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        ftpClient.enterLocalPassiveMode();
                        InputStream inputStream = ftpClient.retrieveFileStream(addressFileRecordDoing.getFilePath());
                        //错误log集合
                        ExcelLogs logs = new ExcelLogs();
                        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, logs);
                        if (null != importExcel && !importExcel.isEmpty()) {
                            for (Map m : importExcel) {
                                //保存
                                this.saveAddress(m, addressFileRecordDoing.getRecordId());
                            }

                            //更新数量
                            addressFileRecordDoing.setAddressTotal(importExcel.size());
                            //更新状态
                            addressFileRecordDoing.setState(AddressConstant.ADDRESS_FILE_RECORD_STATE_SUCCESSFUL);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //更新状态
                addressFileRecordDoing.setState(AddressConstant.ADDRESS_FILE_RECORD_STATE_FAILED);
                //更新备注
                addressFileRecordDoing.setRemark(e.getMessage());
            } finally {
                ftpHelper.closeFTPServer();
            }

            //结束时间
            Long end = System.currentTimeMillis();
            //更新耗费时间
            addressFileRecordDoing.setConsumeTime(end - start);
            //更新
            addressFileRecordDao.update(addressFileRecordDoing);
            log.info("Read file [" + addressFileRecordDoing.getFileName() + "] end");
        } else {
            log.warn("There is no record doing");
        }
    }

    /**
     * 根据文件名查询是否已经有读取记录
     *
     * @param fileName 文件名
     * @return boolean
     */
    private boolean isExist(String fileName) {
        boolean isExist = false;
        AddressFileRecord addressFileRecordExample = new AddressFileRecord();
        addressFileRecordExample.setFileName(fileName);
        AddressFileRecord addressFileRecordExist = addressFileRecordDao.findByExample(addressFileRecordExample);
        if (null != addressFileRecordExist) {
            isExist = true;
            log.warn("The file named [" + fileName + "] already exists");
        }
        return isExist;
    }

    /**
     * 保存地址
     *
     * @param object   地址属性
     * @param recordId 记录ID（批次号）
     */
    private void saveAddress(Map object, Long recordId) {
        Address address = new Address();
        address.setRegionName(MapUtils.getString(object, 0));
        address.setStandName1(MapUtils.getString(object, 1));
        address.setStandName2(MapUtils.getString(object, 2));
        address.setStandName3(MapUtils.getString(object, 3));
        address.setStandName4(MapUtils.getString(object, 4));
        address.setStandName5(MapUtils.getString(object, 5));
        address.setStandName6(MapUtils.getString(object, 6));
        address.setStandName7(MapUtils.getString(object, 7));
        address.setStandName8(MapUtils.getString(object, 8));
        address.setStandName9(MapUtils.getString(object, 9));
        address.setUseType(MapUtils.getString(object, 10));
        address.setMark(MapUtils.getString(object, 11));
        address.setEqpName(MapUtils.getString(object, 12));
        address.setOldStandId7(MapUtils.getString(object, 13));
        address.setOldStandName7(MapUtils.getString(object, 14));
        address.setCheckErr(null);
        address.setEqpTable(null);
        address.setBothId(recordId.toString());
        this.addressDao.insert(address);
    }

    /**
     * innerListFiles
     */
    private void innerListFiles(FTPClient ftpClient, String directory, boolean recursive) throws IOException {
        FTPFile[] found = ftpClient.listFiles(new String(directory.getBytes(ftpHelper.ENCODE), "iso-8859-1"));
        if (found != null) {
            for (FTPFile file : found) {
                String fileName = file.getName();
                String filePath;
                if ("/".equals(directory)) {
                    filePath = file.getName();
                } else {
                    filePath = directory + "/" + file.getName();
                }
                log.info("Scan file [" + filePath + "]");
                if (file.isDirectory() && !fileName.equals(".") && !fileName.equals("..")) {
                    if (recursive) {
                        innerListFiles(ftpClient, filePath, recursive);
                    }
                } else {
                    if ((fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) && !isExist(fileName)) {
                        //文件记录入库
                        AddressFileRecord addressFileRecord = new AddressFileRecord();
                        Date now = new Date();
                        addressFileRecord.setFileName(file.getName());
                        addressFileRecord.setFilePath(filePath);
                        addressFileRecord.setAddressTotal(0);
                        addressFileRecord.setState(AddressConstant.ADDRESS_FILE_RECORD_STATE_TO_DO);
                        addressFileRecord.setConsumeTime(0L);
                        addressFileRecord.setRecordDate(now);
                        addressFileRecordDao.insert(addressFileRecord);
                    }
                }
            }
        }
    }
}
