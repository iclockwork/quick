package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynFtp;
import org.springframework.stereotype.Repository;

/**
 * DataSynFtpDao
 *
 * @author: fengwang
 * @date: 2018-6-12 15:41
 * @version: 1.0
 * @since: JDK 1.7
 */
@Repository("dataSynFtpDao")
public class DataSynFtpDao extends BaseHibernateJpaRepository<DataSynFtp, Long> {
}
