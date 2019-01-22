package com.ztesoft.res.quick.base.util.ftp;


import com.ztesoft.res.quick.base.exception.BusinessException;
import com.ztesoft.res.quick.base.util.cvs.CsvReader;
import com.ztesoft.res.quick.base.util.cvs.CsvWriter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * FTPHelper
 *
 * @author: fengwang
 * @date: 2018-2-7 15:38
 * @version: 1.0
 * @since: JDK 1.8
 */
public class FTPHelper {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(FTPHelper.class);

    /**
     * ENCODE
     */
    public static final String ENCODE = "GBK";

    public FTPParamConfig ftpParam = null;

    public FTPClient ftpClient = new FTPClient();

    private InputStream is = null;

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public FTPHelper() {
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    /**
     * 设置FTP参数
     *
     * @param ftpParam
     */
    public void setFTPParam(FTPParamConfig ftpParam) {
        this.ftpParam = ftpParam;
    }

    /**
     * 取得FTP参数
     *
     * @return the ftp param
     */
    public FTPParamConfig getFtpParam() {
        return ftpParam;
    }

    /**
     * 连接FTP服务器
     *
     * @return true, if conn FTP server
     * <p>
     * the interface exception
     */
    public boolean connFTPServer() throws BusinessException {
        if (ftpParam == null) {
            throw new BusinessException("没有配置FTP参数！");
        }
        try {
            if ((ftpClient != null) && !ftpClient.isConnected()) {
                ftpClient.connect(ftpParam.getIp(), ftpParam.getPort());
                log.info("Connect FTP");
                // 设置编码方式
                ftpClient.setControlEncoding(ENCODE);
				/*FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
				conf.setServerLanguageCode("zh");
				ftpClient.configure(conf);*/
                return ftpClient.login(ftpParam.getUser(), ftpParam.getPassword());
            }
            return false;
        } catch (IOException ex) {
            throw new BusinessException("连接FTP服务器失败：" + ex.getMessage());
        }
    }

    public void closeFTPServer() throws BusinessException {
        try {
            if ((ftpClient != null) && ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();

                log.info("Disconnect FTP");
            }
        } catch (IOException ex) {
            throw new BusinessException("关闭FTP服务器失败：" + ex.getMessage());
        }

    }

    public boolean delFile(String path, String fileName) throws BusinessException {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.changeWorkingDirectory(path);
                return ftpClient.dele(fileName) > 0;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BusinessException("删除" + path + "目录下文件" + fileName + "出错！\r\n：" + ex.getMessage());
        } finally {

        }
        return false;

    }

    public boolean moveFTPFile(String fromPath, String toPath, String fileName) {

        if ((this.ftpClient != null) && (this.ftpClient.isConnected())) {
            boolean flag;
            try {
                this.ftpClient.changeWorkingDirectory(fromPath);
                flag = this.ftpClient.rename(fileName, ".." + toPath + "/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    /**
     * 返回FTP目录下的文件列表
     *
     * @param ftpDirectory
     * @return
     */
    public FTPFile[] getFileList(String ftpDirectory) {
        FTPFile[] files = null;
        if (ftpClient == null)
            return null;
        try {
            if ((ftpClient != null) && ftpClient.isConnected()) {
                ftpClient.setControlEncoding(ENCODE); // 中文支持
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(ftpDirectory);
	            
	            /*FTPListParseEngine engine = ftpClient.initiateListParsing(ftpDirectory);
	            files = engine.getFiles();*/

                files = ftpClient.listFiles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    /**
     * 返回FTP目录下的文件列表
     *
     * @param ftpDirectory
     * @return
     */
    public Collection<FTPFile> listFiles(String ftpDirectory, FTPFileFilter filter, boolean recursive, boolean includeSubDirectories) {
        Collection<FTPFile> files = new LinkedList<>();
        if (ftpClient == null)
            return null;
        try {
            if ((ftpClient != null) && ftpClient.isConnected()) {
                ftpClient.setControlEncoding(ENCODE); // 中文支持
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(ftpDirectory);
                innerListFiles(files, ftpDirectory, filter, recursive, includeSubDirectories);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    /**
     * Finds files within a given directory (and optionally its
     * subdirectories). All files found are filtered by an IOFileFilter.
     *
     * @param files                 the collection of files found.
     * @param directory             the directory to search in.
     * @param filter                the filter to apply to files and directories.
     * @param includeSubDirectories indicates if will include the subdirectories themselves
     */
    private void innerListFiles(Collection<FTPFile> files, String directory,
                                FTPFileFilter filter, boolean recursive, boolean includeSubDirectories) throws IOException {
        FTPFile[] found = ftpClient.listFiles(directory, filter);
        if (found != null) {
            for (FTPFile file : found) {
                String filePath;
                if ("/".equals(directory)) {
                    filePath = file.getName();
                } else {
                    filePath = directory + "/" + file.getName();
                }
                log.info("Scan file [" + filePath + "]");
                if (file.isDirectory()) {
                    if (includeSubDirectories) {
                        files.add(file);
                    }
                    if (recursive) {
                        innerListFiles(files, filePath, filter, recursive, includeSubDirectories);
                    }
                } else {
                    files.add(file);
                }
            }
        }
    }

    public InputStream getFile(String remotePath, String fileName) {

        try {
            if ((ftpClient != null) && ftpClient.isConnected()) {
                ftpClient.setControlEncoding(ENCODE); // 中文支持
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(remotePath);
            }
            this.is = ftpClient.retrieveFileStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                is.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return is;
    }

    public void closeInputStream() {
        try {
            if (this.is != null) {
                this.is.close();
                this.is = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OutputStream storeFileStream(String remotePath, String fileName) {
        OutputStream os = null;
        try {
            if ((ftpClient != null) && ftpClient.isConnected()) {
                ftpClient.setControlEncoding(ENCODE); // 中文支持
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(remotePath);
                return ftpClient.storeFileStream(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return os;
    }

    public boolean completePendingCommand() {
        try {
            if ((ftpClient != null) && ftpClient.isConnected()) {
                return ftpClient.completePendingCommand();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String[]> getFileContentForList(String ftpPath, String remoteFileName, String encode) throws BusinessException {
        try {
            InputStream in = null;
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            if ((ftpClient != null) && ftpClient.isConnected()) {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(ftpPath);
                in = ftpClient.retrieveFileStream(remoteFileName);
                if (null != in) {
                    CsvReader reader = null;
                    reader = new CsvReader(in, Charset.forName(encode));
                    //reader.readHeaders();  //保留文件列头
                    while (reader.readRecord()) {
                        csvList.add(reader.getValues());
                    }
                    reader.close();
                    in.close();
                    return csvList;
                } else {
                    throw new BusinessException("获取文件内容：" + remoteFileName + " 出错！\r\n" + "文件不存在！");
                }
            }
            return csvList;
        } catch (IOException ex) {
            throw new BusinessException("获取文件内容：" + remoteFileName + " 出错！\r\n" + ex.getMessage());
        }
    }

    public void writeCsvFile(String remotePath, String fileName, List dataList, String encode) {
        OutputStream out = null;
        CsvWriter wr = null;
        try {
            if ((ftpClient != null) && ftpClient.isConnected() && dataList != null && !dataList.isEmpty()) {
                ftpClient.setControlEncoding(ENCODE); // 中文支持
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(remotePath);
                out = ftpClient.storeFileStream(fileName);
                wr = new CsvWriter(out, ',', Charset.forName(encode));
                for (int i = 0; i < dataList.size(); i++) {
                    wr.writeRecord((String[]) dataList.get(i));
                }
                wr.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wr != null) wr.close();
                if (out != null) out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }

        }
    }
}