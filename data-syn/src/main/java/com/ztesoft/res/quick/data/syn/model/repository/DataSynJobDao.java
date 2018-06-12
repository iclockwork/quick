package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DataSynJobDao
 *
 * @author: fengwang
 * @date: 2018-6-12 15:46
 * @version: 1.0
 * @since: JDK 1.7
 */
@Repository("dataSynJobDao")
public class DataSynJobDao extends BaseHibernateJpaRepository<DataSynJob, Long> {
    /**
     * 取一个任务（按时间最早）
     */
    public DataSynJob earliest() {
        DataSynJob job = null;
        List<DataSynJob> list = (List<DataSynJob>) this.getHibernateTemplate().findByNamedParam("from DataSynJob t where t.state = :state order by t.createTime asc", "state", DataSynConstant.DATA_SYN_JOB_STATE_ENABLE);
        if (null != list && !list.isEmpty()) {
            job = list.get(0);
        }
        return job;
    }
}
