package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTableField;
import org.springframework.stereotype.Repository;

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
}
