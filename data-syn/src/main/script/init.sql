drop table QUICK_DATA_SYN_DO_RECORD cascade constraints;

drop table QUICK_DATA_SYN_FTP cascade constraints;

drop index IDX_QUICK_DATA_SYN_JOB;

drop table QUICK_DATA_SYN_JOB cascade constraints;

drop index IDX_QUICK_DATA_SYN_TABLE_FIELD;

drop table QUICK_DATA_SYN_TABLE_FIELD cascade constraints;

drop sequence SEQ_QUICK_DATA_SYN_DO_RECORD;

drop sequence SEQ_QUICK_DATA_SYN_FTP;

drop sequence SEQ_QUICK_DATA_SYN_JOB;

drop sequence SEQ_QUICK_DATA_SYN_TABLE_FIELD;

create sequence SEQ_QUICK_DATA_SYN_DO_RECORD
increment by 1
start with 1
maxvalue 999999999
minvalue 1
cache 20;

create sequence SEQ_QUICK_DATA_SYN_FTP
increment by 1
start with 1
maxvalue 999999999
minvalue 1
cache 20;

create sequence SEQ_QUICK_DATA_SYN_JOB
increment by 1
start with 1
maxvalue 999999999
minvalue 1
cache 20;

create sequence SEQ_QUICK_DATA_SYN_TABLE_FIELD
increment by 1
start with 1
maxvalue 999999999
minvalue 1
cache 20;

/*==============================================================*/
/* Table: QUICK_DATA_SYN_DO_RECORD                              */
/*==============================================================*/
create table QUICK_DATA_SYN_DO_RECORD
(
  RECORD_ID            NUMBER(8)            not null,
  FILE_NAME            VARCHAR2(1024)       not null,
  FILE_PATH            VARCHAR2(1024)       not null,
  ROW_TOTAL            NUMBER(8),
  REMARK               VARCHAR2(1024),
  STATE                NUMBER(1)            not null,
  CONSUME_TIME         NUMBER(12),
  START_DATE           DATE                 not null,
  END_DATE             DATE,
  JOB_ID               NUMBER(8)            not null,
  constraint PK_QUICK_DATA_SYN_DO_RECORD primary key (RECORD_ID)
);

comment on table QUICK_DATA_SYN_DO_RECORD is
'地址文件读取记录';

comment on column QUICK_DATA_SYN_DO_RECORD.RECORD_ID is
'主键';

comment on column QUICK_DATA_SYN_DO_RECORD.FILE_NAME is
'文件名';

comment on column QUICK_DATA_SYN_DO_RECORD.FILE_PATH is
'文件路径';

comment on column QUICK_DATA_SYN_DO_RECORD.ROW_TOTAL is
'数据行数';

comment on column QUICK_DATA_SYN_DO_RECORD.REMARK is
'备注';

comment on column QUICK_DATA_SYN_DO_RECORD.STATE is
'状态 0 未处理 1 处理中 2 处理成功 3 处理失败';

comment on column QUICK_DATA_SYN_DO_RECORD.CONSUME_TIME is
'耗费时间';

comment on column QUICK_DATA_SYN_DO_RECORD.START_DATE is
'开始时间';

comment on column QUICK_DATA_SYN_DO_RECORD.END_DATE is
'结束时间';

comment on column QUICK_DATA_SYN_DO_RECORD.JOB_ID is
'任务ID';

/*==============================================================*/
/* Table: QUICK_DATA_SYN_FTP                                    */
/*==============================================================*/
create table QUICK_DATA_SYN_FTP
(
  FTP_ID               NUMBER(8)            not null,
  IP                   VARCHAR2(32)         not null,
  PORT                 NUMBER(8)            not null,
  USERNAME             VARCHAR2(32)         not null,
  PASSWORD             VARCHAR2(32)         not null,
  CREATE_TIME          DATE                 not null,
  MODIFY_TIME          DATE                 not null,
  constraint PK_QUICK_DATA_SYN_FTP primary key (FTP_ID)
);

comment on table QUICK_DATA_SYN_FTP is
'FTP服务器配置';

comment on column QUICK_DATA_SYN_FTP.FTP_ID is
'主键';

comment on column QUICK_DATA_SYN_FTP.IP is
'IP';

comment on column QUICK_DATA_SYN_FTP.PORT is
'端口';

comment on column QUICK_DATA_SYN_FTP.USERNAME is
'用户名';

comment on column QUICK_DATA_SYN_FTP.PASSWORD is
'密码';

comment on column QUICK_DATA_SYN_FTP.CREATE_TIME is
'创建时间';

comment on column QUICK_DATA_SYN_FTP.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: QUICK_DATA_SYN_JOB                                    */
/*==============================================================*/
create table QUICK_DATA_SYN_JOB
(
  JOB_ID               NUMBER(8)            not null,
  JOB_NAME             VARCHAR2(100)        not null,
  JOB_GROUP            VARCHAR2(32)         not null,
  CRON_EXPRESSION      VARCHAR2(100)        not null,
  JOB_TYPE             NUMBER(1)            not null,
  FTP_ID               NUMBER(8)            not null,
  DIR                  VARCHAR2(100)        not null,
  FILE_NAME_PREFIX     VARCHAR2(100)        not null,
  FILE_NAME_SEPARATE   VARCHAR2(8)          not null,
  FILE_NAME_TIME_FORMAT VARCHAR2(100)        not null,
  FILE_NAME_EXTENSION  VARCHAR2(100)        not null,
  FILE_ENCODE          VARCHAR2(16)         not null,
  TABLE_NAME           VARCHAR2(100)        not null,
  TABLE_QUERY_SQL      VARCHAR2(4000),
  DESCRIPTION          VARCHAR2(1024),
  STATE                NUMBER(1)            not null,
  CREATE_TIME          DATE                 not null,
  MODIFY_TIME          DATE                 not null,
  constraint PK_QUICK_DATA_SYN_JOB primary key (JOB_ID)
);

comment on table QUICK_DATA_SYN_JOB is
'数据同步任务';

comment on column QUICK_DATA_SYN_JOB.JOB_ID is
'主键';

comment on column QUICK_DATA_SYN_JOB.JOB_NAME is
'任务名称';

comment on column QUICK_DATA_SYN_JOB.JOB_GROUP is
'任务分组';

comment on column QUICK_DATA_SYN_JOB.CRON_EXPRESSION is
'任务调度规则';

comment on column QUICK_DATA_SYN_JOB.JOB_TYPE is
'任务类型 1 数据文件读 2 数据文件写';

comment on column QUICK_DATA_SYN_JOB.FTP_ID is
'FTP服务器配置ID';

comment on column QUICK_DATA_SYN_JOB.DIR is
'文件存放目录';

comment on column QUICK_DATA_SYN_JOB.FILE_NAME_PREFIX is
'文件前缀';

comment on column QUICK_DATA_SYN_JOB.FILE_NAME_SEPARATE is
'文件名分隔符';

comment on column QUICK_DATA_SYN_JOB.FILE_NAME_TIME_FORMAT is
'文件名时间格式';

comment on column QUICK_DATA_SYN_JOB.FILE_NAME_EXTENSION is
'文件名后缀';

comment on column QUICK_DATA_SYN_JOB.FILE_ENCODE is
'文件编码格式 UTF-8 GBK';

comment on column QUICK_DATA_SYN_JOB.TABLE_NAME is
'数据存放表';

comment on column QUICK_DATA_SYN_JOB.TABLE_QUERY_SQL is
'数据查询语句';

comment on column QUICK_DATA_SYN_JOB.DESCRIPTION is
'任务描述';

comment on column QUICK_DATA_SYN_JOB.STATE is
'状态 0 未启用 1 启用';

comment on column QUICK_DATA_SYN_JOB.CREATE_TIME is
'创建时间';

comment on column QUICK_DATA_SYN_JOB.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Index: IDX_QUICK_DATA_SYN_JOB                                */
/*==============================================================*/
create unique index IDX_QUICK_DATA_SYN_JOB on QUICK_DATA_SYN_JOB (
  JOB_NAME ASC,
  JOB_GROUP ASC
);

/*==============================================================*/
/* Table: QUICK_DATA_SYN_TABLE_FIELD                            */
/*==============================================================*/
create table QUICK_DATA_SYN_TABLE_FIELD
(
  FIELD_ID             NUMBER(8)            not null,
  FIELD_NAME           VARCHAR2(100)        not null,
  FIELD_TYPE           NUMBER(1)            not null,
  FIELD_ORDER          NUMBER(3)            not null,
  JOB_ID               NUMBER(8)            not null,
  IGNORE_FLAG          NUMBER(1)            not null,
  TIME_FORMAT          VARCHAR2(100),
  constraint PK_QUICK_DATA_SYN_TABLE_FIELD primary key (FIELD_ID)
);

comment on table QUICK_DATA_SYN_TABLE_FIELD is
'数据存放表字段配置';

comment on column QUICK_DATA_SYN_TABLE_FIELD.FIELD_ID is
'主键';

comment on column QUICK_DATA_SYN_TABLE_FIELD.FIELD_NAME is
'字段名称';

comment on column QUICK_DATA_SYN_TABLE_FIELD.FIELD_TYPE is
'字段类型 1 数字 2 字符串 3 时间 4 浮点';

comment on column QUICK_DATA_SYN_TABLE_FIELD.FIELD_ORDER is
'字段序号';

comment on column QUICK_DATA_SYN_TABLE_FIELD.JOB_ID is
'任务ID';

comment on column QUICK_DATA_SYN_TABLE_FIELD.IGNORE_FLAG is
'是否忽略 0 否 1 是 注意设置忽略的字段必须非必填';

comment on column QUICK_DATA_SYN_TABLE_FIELD.TIME_FORMAT is
'时间格式';

/*==============================================================*/
/* Index: IDX_QUICK_DATA_SYN_TABLE_FIELD                        */
/*==============================================================*/
create unique index IDX_QUICK_DATA_SYN_TABLE_FIELD on QUICK_DATA_SYN_TABLE_FIELD (
  FIELD_NAME ASC,
  JOB_ID ASC
);
