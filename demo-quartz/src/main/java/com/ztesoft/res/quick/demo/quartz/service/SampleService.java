package com.ztesoft.res.quick.demo.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * SampleService
 *
 * @author: fengwang
 * @date: 2018-6-11 16:39
 * @version: 1.0
 * @since: JDK 1.7
 */
@Service
public class SampleService {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(SampleService.class);

    public void hello() {
        log.info("Hello World!");
    }
}
