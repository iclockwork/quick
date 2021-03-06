package com.ztesoft.res.quick.base.repository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * BaseHibernateJpaRepository
 *
 * @author: fengwang
 * @date: 2018-2-7 14:58
 * @version: 1.0
 * @since: JDK 1.8
 */
public abstract class BaseHibernateJpaRepository<T extends Entity, ID extends Serializable> extends HibernateDaoSupport implements BaseJpaRepository<T, ID> {
    @Autowired
    protected SessionFactory sessionFactory;

    private Class<T> clazz;

    @PostConstruct
    public void setupService() {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void setupEntityClass(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public T insert(T object) {
        this.getHibernateTemplate().save(object);
        return object;
    }

    @Override
    public T update(T object) {
        this.getHibernateTemplate().update(object);
        return object;
    }

    @Override
    public T insertOrUpdate(T object) {
        this.getHibernateTemplate().saveOrUpdate(object);
        return object;
    }

    @Override
    public void delete(T object) {
        this.getHibernateTemplate().delete(object);
    }

    @Override
    public T findById(ID id) {
        this.initClazz();
        return (T) this.getHibernateTemplate().get(clazz, id);
    }

    @Override
    public T findByExample(T object) {
        T obj = null;
        List<T> list = this.getHibernateTemplate().findByExample(object);
        if (null != list && !list.isEmpty()) {
            obj = list.get(0);
        }

        return obj;
    }

    @Override
    public List<Map<String, Object>> findAllBySql(String sql, List<Object> values) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (null != values) {
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i, values.get(i));
            }
        }
        return query.list();
    }

    private void initClazz() {
        if (clazz == null) {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            clazz = (Class) params[0];
        }
    }
}
