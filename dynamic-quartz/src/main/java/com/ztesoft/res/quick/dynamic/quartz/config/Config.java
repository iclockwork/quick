package com.ztesoft.res.quick.dynamic.quartz.config;

import com.dexcoder.dal.spring.JdbcDaoImpl;
import com.ztesoft.res.quick.dynamic.quartz.quartz.JobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Config
 *
 * @author: fengwang
 * @date: 2018-7-20 9:54
 * @version: 1.0
 * @since: JDK 1.7
 */
@Configuration
public class Config {
    @Autowired
    DataSource druidDataSource;

    @Autowired
    private JobFactory jobFactory;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(druidDataSource);
        jdbcTemplate.setFetchSize(350);
        return jdbcTemplate;
    }

    @Bean
    public JdbcDaoImpl getJdbcDaoImpl() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setJdbcTemplate(getJdbcTemplate());
        return jdbcDao;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(druidDataSource);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setSchedulerName("ClusterScheduler");
        schedulerFactoryBean.setStartupDelay(30);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextSchedulerContextKey");
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setAutoStartup(true);
        // 自定义Job Factory，用于Spring注入
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        Scheduler scheduler = null;
        try {
            scheduler = schedulerFactoryBean().getScheduler();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduler;
    }
}
