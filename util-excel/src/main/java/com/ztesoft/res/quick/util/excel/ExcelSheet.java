package com.ztesoft.res.quick.util.excel;

import java.util.Collection;
import java.util.Map;

/**
 * 用于汇出多个sheet的Vo The <code>ExcelSheet</code>
 *
 * @author sargeras.wang
 * @version 1.0, Created at 2013年10月25日
 */
public class ExcelSheet<T> {
    private String sheetName;
    private Map<String, String> headers;
    private Collection<T> dataSet;

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Excel页签名称
     *
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * Excel表头
     *
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Excel数据集合
     *
     * @return the dataSet
     */
    public Collection<T> getDataSet() {
        return dataSet;
    }

    /**
     * @param dataSet the dataSet to set
     */
    public void setDataSet(Collection<T> dataSet) {
        this.dataSet = dataSet;
    }

}
