package com.ztesoft.res.quick.base.repository;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * JpaEntity
 *
 * @author: fengwang
 * @date: 2018-2-7 14:58
 * @version: 1.0
 * @since: JDK 1.8
 */
@MappedSuperclass
public class JpaEntity<T extends Serializable> implements Entity {

}
