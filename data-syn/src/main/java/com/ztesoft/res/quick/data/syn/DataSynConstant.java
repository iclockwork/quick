package com.ztesoft.res.quick.data.syn;

/**
 * AddressConstant
 *
 * @author: fengwang
 * @date: 2018-2-7 15:46
 * @version: 1.0
 * @since: JDK 1.8
 */
public class DataSynConstant {
    /**
     * 状态 0 未处理 1 处理中 2 处理成功 3 处理失败
     */
    public static final Integer DATA_SYN_DO_RECORD_STATE_TO_DO = 0;

    /**
     * 状态 0 未处理 1 处理中 2 处理成功 3 处理失败
     */
    public static final Integer DATA_SYN_DO_RECORD_STATE_DOING = 1;

    /**
     * 状态 0 未处理 1 处理中 2 处理成功 3 处理失败
     */
    public static final Integer DATA_SYN_DO_RECORD_STATE_SUCCESSFUL = 2;

    /**
     * 状态 0 未处理 1 处理中 2 处理成功 3 处理失败
     */
    public static final Integer DATA_SYN_DO_RECORD_STATE_FAILED = 3;

    /**
     * 任务类型 1 数据文件读 2 数据文件写
     */
    public static final Integer DATA_SYN_JOB_TYPE_READ = 1;

    /**
     * 任务类型 1 数据文件读 2 数据文件写
     */
    public static final Integer DATA_SYN_JOB_TYPE_WRITE = 2;

    /**
     * 状态 0 未启用 1 启用
     */
    public static final Integer DATA_SYN_JOB_STATE_DISABLED = 0;

    /**
     * 状态 0 未启用 1 启用
     */
    public static final Integer DATA_SYN_JOB_STATE_ENABLE = 1;

    /**
     * 字段类型 1 数字 2 字符串 3 时间 4 浮点
     */
    public static final Integer DATA_SYN_TABLE_FIELD_TYPE_NUMBER = 1;

    /**
     * 字段类型 1 数字 2 字符串 3 时间 4 浮点
     */
    public static final Integer DATA_SYN_TABLE_FIELD_TYPE_STRING = 2;

    /**
     * 字段类型 1 数字 2 字符串 3 时间 4 浮点
     */
    public static final Integer DATA_SYN_TABLE_FIELD_TYPE_DATE = 2;

    /**
     * 字段类型 1 数字 2 字符串 3 时间 4 浮点
     */
    public static final Integer DATA_SYN_TABLE_FIELD_TYPE_DOUBLE = 2;
}
