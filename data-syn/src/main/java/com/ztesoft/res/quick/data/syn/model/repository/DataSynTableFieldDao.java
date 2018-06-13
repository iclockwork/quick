package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.base.util.DateUtils;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTableField;
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
    public Object insert(List<DataSynTableField> fields, String sql, String[] dataArr) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createSQLQuery(sql);
                for (int i = 0; i < dataArr.length; i++) {
                    DataSynTableField field = fields.get(i);
                    if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_NUMBER.equals(field.getFieldType())) {
                        Integer value = new Integer(dataArr[i]);
                        query.setInteger(i, value);
                    } else if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_STRING.equals(field.getFieldType())) {
                        String value = dataArr[i];
                        query.setString(i, value);
                    } else if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_DATE.equals(field.getFieldType())) {
                        Date value = DateUtils.strToDate(dataArr[i], DateUtils.DATE_PATTERN_SLASH_LONG);
                        query.setDate(i, value);
                    } else if (DataSynConstant.DATA_SYN_TABLE_FIELD_TYPE_DOUBLE.equals(field.getFieldType())) {
                        Double value = new Double(dataArr[i]);
                        query.setDouble(i, value);
                    }
                }
                int num = query.executeUpdate();
                return num;
            }
        });
    }
}
