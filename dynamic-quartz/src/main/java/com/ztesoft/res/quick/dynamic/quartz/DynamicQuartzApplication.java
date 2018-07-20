package com.ztesoft.res.quick.dynamic.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoQuartzApplication
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@RestController
@SpringBootApplication
@EnableAutoConfiguration
@ImportAutoConfiguration
public class DynamicQuartzApplication {
    static Logger logger = LoggerFactory.getLogger(DynamicQuartzApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DynamicQuartzApplication.class, args);
    }
}
