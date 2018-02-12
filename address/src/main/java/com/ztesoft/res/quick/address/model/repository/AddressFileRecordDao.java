package com.ztesoft.res.quick.address.model.repository;

import com.ztesoft.res.quick.address.AddressConstant;
import com.ztesoft.res.quick.address.model.entity.AddressFileRecord;
import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AddressFileRecordDao
 *
 * @author: fengwang
 * @date: 2018-2-8 14:41
 * @version: 1.0
 * @since: JDK 1.8
 */
@Repository("addressFileRecordDao")
public class AddressFileRecordDao extends BaseHibernateJpaRepository<AddressFileRecord, Long> {
    /**
     * 取一个未处理文件（按时间最早）
     */
    public AddressFileRecord earliestToDo() {
        AddressFileRecord addressFileRecord = null;
        List<AddressFileRecord> list = (List<AddressFileRecord>) this.getHibernateTemplate().findByNamedParam("from AddressFileRecord t where t.state = :state order by t.recordId asc", "state", AddressConstant.ADDRESS_FILE_RECORD_STATE_TO_DO);
        if (null != list && !list.isEmpty()) {
            addressFileRecord = list.get(0);
        }
        return addressFileRecord;
    }

    /**
     * 取一个处理中文件（按时间最早）
     */
    public AddressFileRecord earliestDoing() {
        AddressFileRecord addressFileRecord = null;
        List<AddressFileRecord> list = (List<AddressFileRecord>) this.getHibernateTemplate().findByNamedParam("from AddressFileRecord t where t.state = :state order by t.recordId asc", "state", AddressConstant.ADDRESS_FILE_RECORD_STATE_DOING);
        if (null != list && !list.isEmpty()) {
            addressFileRecord = list.get(0);
        }
        return addressFileRecord;
    }
}
