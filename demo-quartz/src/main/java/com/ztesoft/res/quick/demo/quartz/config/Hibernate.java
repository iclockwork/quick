package com.ztesoft.res.quick.demo.quartz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Hibernate
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@Configuration
@EnableTransactionManagement
public class Hibernate {
    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.packagesToScan}")
    private String folder;

    @Autowired
    DataSource druidDataSource;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean session = new LocalSessionFactoryBean();
        session.setDataSource(druidDataSource);
        session.setPackagesToScan(folder);
        Properties hibernate = new Properties();
        hibernate.put("hibernate.dialect", dialect);
        hibernate.put("hibernate.show_sql", "false");
        hibernate.put("hibernate.hbm2ddl.auto", "none");
        session.setHibernateProperties(hibernate);
        return session;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager m = new HibernateTransactionManager();
        m.setSessionFactory(sessionFactory().getObject());
        return m;
    }
}
