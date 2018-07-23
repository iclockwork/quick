package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.DataSynTask;
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
@Repository("dataSynTaskDao")
public class DataSynTaskDao extends BaseHibernateJpaRepository<DataSynTask, Long> {
    /**
     * list
     */
    public List<DataSynTask> list() {
        List<DataSynTask> jobs = (List<DataSynTask>) this.getHibernateTemplate().findByNamedParam("from DataSynTask t where t.state = :state order by t.createTime asc", "state", DataSynConstant.DATA_SYN_TASK_STATE_ENABLE);

        return jobs;
    }

    /**
     * 取一个任务（按时间最早）
     */
    public DataSynTask earliest() {
        DataSynTask job = null;
        List<DataSynTask> list = (List<DataSynTask>) this.getHibernateTemplate().findByNamedParam("from DataSynTask t where t.state = :state order by t.createTime asc", "state", DataSynConstant.DATA_SYN_TASK_STATE_ENABLE);
        if (null != list && !list.isEmpty()) {
            job = list.get(0);
        }
        return job;
    }
}
