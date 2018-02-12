package com.ztesoft.res.quick.address.model.repository;

import com.ztesoft.res.quick.address.model.entity.Address;
import com.ztesoft.res.quick.base.repository.BaseHibernateJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TestDAO
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@Repository("addressDao")
public class AddressDao extends BaseHibernateJpaRepository<Address, Long> {
}
