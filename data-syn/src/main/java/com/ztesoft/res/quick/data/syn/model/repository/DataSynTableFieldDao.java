package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.base.util.DateUtils;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTableField;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * DataSynTableFieldDao
 *
 * @author: fengwang
 * @date: 2018-6-12 15:47
 * @version: 1.0
 * @since: JDK 1.7
 */
@Repository("dataSynTableFieldDao")
public class DataSynTableFieldDao extends BaseHibernateJpaRepository<DataSynTableField, Long> {
    /**
     * list
     */
    public List<DataSynTableField> list(Long jobId) {
        List<DataSynTableField> fields = (List<DataSynTableField>) this.getHibernateTemplate().findByNamedParam("from DataSynTableField t where t.jobId = :jobId order by t.fieldOrder asc", "jobId", jobId);

        return fields;
    }

    /**
     * insert
     */
    public Object insert(int index, List<DataSynTableField> fields, String sql, String[] dataArr) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                int num = 0;
                try {
                    Query query = session.createSQLQuery(sql);
                    for (int i = 0; i < dataArr.length; i++) {
                        DataSynTableField field = fields.get(i);
                        if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_NUMBER.equals(field.getFieldType())) {
                            Integer value = StringUtils.isNotEmpty(dataArr[i]) ? new Integer(dataArr[i]) : 0;
                            query.setInteger(i, value);
                        } else if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_STRING.equals(field.getFieldType())) {
                            String value = dataArr[i];
                            query.setString(i, value);
                        } else if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_DATE.equals(field.getFieldType())) {
                            Date value = StringUtils.isNotEmpty(dataArr[i]) ? DateUtils.strToDate(dataArr[i], DateUtils.DATE_PATTERN_SLASH_LONG) : null;
                            query.setDate(i, value);
                        } else if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_DOUBLE.equals(field.getFieldType())) {
                            Double value = StringUtils.isNotEmpty(dataArr[i]) ? new Double(dataArr[i]) : 0;
                            query.setDouble(i, value);
                        }
                    }
                    num = query.executeUpdate();
                    return num;
                } catch (Exception e) {
                    throw new RuntimeException("保存第" + index + "行数据出错！\r\n" + e.getMessage());
                }
            }
        });
    }
}
