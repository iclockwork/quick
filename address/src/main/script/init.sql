drop table ADDR_SEGM_CHECK_TMP cascade constraints;

drop index IDX_ADDR_SEGM_FILE_NAME;

drop table ADDR_SEGM_FILE_RECORD cascade constraints;

drop sequence SEQ_ADDR_SEGM_CHECK_TMP;

drop sequence SEQ_ADDR_SEGM_FILE_RECORD;

create sequence SEQ_ADDR_SEGM_CHECK_TMP
increment by 1
start with 1
maxvalue 999999999
minvalue 1
cache 20;

create sequence SEQ_ADDR_SEGM_FILE_RECORD
increment by 1
start with 1
maxvalue 999999999
minvalue 1
cache 20;

/*==============================================================*/
/* Table: ADDR_SEGM_CHECK_TMP                                   */
/*==============================================================*/
create table ADDR_SEGM_CHECK_TMP
(
  ROW_NUM              NUMBER(24)           not null,
  REGION_NAME          VARCHAR2(200),
  V_STAND_NAME1        VARCHAR2(200),
  V_STAND_NAME2        VARCHAR2(200),
  V_STAND_NAME3        VARCHAR2(200),
  V_STAND_NAME4        VARCHAR2(200),
  V_STAND_NAME5        VARCHAR2(200),
  V_STAND_NAME6        VARCHAR2(200),
  V_STAND_NAME7        VARCHAR2(200),
  V_STAND_NAME8        VARCHAR2(200),
  V_STAND_NAME9        VARCHAR2(200),
  USE_TYPE             VARCHAR2(200),
  QUN_BIAOSHI          VARCHAR2(200),
  EQP_NAME             VARCHAR2(200),
  OLD_STAND_ID7        VARCHAR2(200),
  OLD_STAND_NAME7      VARCHAR2(400),
  CHECK_ERR            VARCHAR2(2000),
  EQP_TABLE            CHAR(1),
  BOTH_ID              VARCHAR2(200),
  constraint PK_ADDR_SEGM_CHECK_TMP primary key (ROW_NUM)
);

comment on table ADDR_SEGM_CHECK_TMP is
'地址校验';

comment on column ADDR_SEGM_CHECK_TMP.ROW_NUM is
'ROW_NUM';

comment on column ADDR_SEGM_CHECK_TMP.REGION_NAME is
'REGION_NAME';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME1 is
'V_STAND_NAME1';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME2 is
'V_STAND_NAME2';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME3 is
'V_STAND_NAME3';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME4 is
'V_STAND_NAME4';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME5 is
'V_STAND_NAME5';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME6 is
'V_STAND_NAME6';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME7 is
'V_STAND_NAME7';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME8 is
'V_STAND_NAME8';

comment on column ADDR_SEGM_CHECK_TMP.V_STAND_NAME9 is
'V_STAND_NAME9';

comment on column ADDR_SEGM_CHECK_TMP.USE_TYPE is
'USE_TYPE';

comment on column ADDR_SEGM_CHECK_TMP.QUN_BIAOSHI is
'QUN_BIAOSHI';

comment on column ADDR_SEGM_CHECK_TMP.EQP_NAME is
'EQP_NAME';

comment on column ADDR_SEGM_CHECK_TMP.OLD_STAND_ID7 is
'OLD_STAND_ID7';

comment on column ADDR_SEGM_CHECK_TMP.OLD_STAND_NAME7 is
'OLD_STAND_NAME7';

comment on column ADDR_SEGM_CHECK_TMP.CHECK_ERR is
'CHECK_ERR';

comment on column ADDR_SEGM_CHECK_TMP.EQP_TABLE is
'EQP_TABLE';

comment on column ADDR_SEGM_CHECK_TMP.BOTH_ID is
'BOTH_ID';

/*==============================================================*/
/* Table: ADDR_SEGM_FILE_RECORD                                 */
/*==============================================================*/
create table ADDR_SEGM_FILE_RECORD
(
  RECORD_ID            NUMBER(8)            not null,
  FILE_NAME            VARCHAR2(1024)       not null,
  FILE_PATH            VARCHAR2(1024)       not null,
  ADDRESS_TOTAL        NUMBER(8)            not null,
  REMARK               VARCHAR2(1024),
  STATE                NUMBER(1)            not null,
  CONSUME_TIME         NUMBER(12)           not null,
  SCAN_DATE            DATE                 not null,
  DOING_DATE           DATE,
  READ_DATE            DATE,
  constraint PK_ADDR_SEGM_FILE_RECORD primary key (RECORD_ID)
);

comment on table ADDR_SEGM_FILE_RECORD is
'地址文件读取记录';

comment on column ADDR_SEGM_FILE_RECORD.RECORD_ID is
'记录ID（批次号）';

comment on column ADDR_SEGM_FILE_RECORD.FILE_NAME is
'文件名';

comment on column ADDR_SEGM_FILE_RECORD.FILE_PATH is
'文件路径';

comment on column ADDR_SEGM_FILE_RECORD.ADDRESS_TOTAL is
'地址数';

comment on column ADDR_SEGM_FILE_RECORD.REMARK is
'备注';

comment on column ADDR_SEGM_FILE_RECORD.STATE is
'状态 0 未处理 1 处理中 2 处理成功 3 处理失败';

comment on column ADDR_SEGM_FILE_RECORD.CONSUME_TIME is
'耗费时间';

comment on column ADDR_SEGM_FILE_RECORD.SCAN_DATE is
'扫描时间';

comment on column ADDR_SEGM_FILE_RECORD.DOING_DATE is
'处理中时间';

comment on column ADDR_SEGM_FILE_RECORD.READ_DATE is
'读取时间';

/*==============================================================*/
/* Index: IDX_ADDR_SEGM_FILE_NAME                               */
/*==============================================================*/
create unique index IDX_ADDR_SEGM_FILE_NAME on ADDR_SEGM_FILE_RECORD (
  FILE_NAME ASC
);
