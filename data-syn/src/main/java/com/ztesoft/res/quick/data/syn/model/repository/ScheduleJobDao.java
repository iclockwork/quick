package com.ztesoft.res.quick.data.syn.model.repository;

import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import com.ztesoft.res.quick.data.syn.DataSynConstant;
import com.ztesoft.res.quick.data.syn.model.entity.ScheduleJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScheduleJobDao
 *
 * @author: fengwang
 * @date: 2018-7-23 10:13
 * @version: 1.0
 * @since: JDK 1.7
 */
@Repository("scheduleJobDao")
public class ScheduleJobDao extends BaseHibernateJpaRepository<ScheduleJob, Long> {
    /**
     * list
     */
    public List<ScheduleJob> list() {
        List<ScheduleJob> jobs = (List<ScheduleJob>) this.getHibernateTemplate().findByNamedParam("from ScheduleJob t where t.status = :state order by t.scheduleJobId desc", "state", DataSynConstant.SCHEDULE_JOB_STATUS_ENABLE);

        return jobs;
    }

    /**
     * list
     */
    public List<ScheduleJob> list(ScheduleJob scheduleJob) {
        List<ScheduleJob> jobs = (List<ScheduleJob>) this.getHibernateTemplate().findByExample(scheduleJob);

        return jobs;
    }
}
