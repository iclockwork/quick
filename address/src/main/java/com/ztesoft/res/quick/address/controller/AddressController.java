package com.ztesoft.res.quick.address.controller;

import com.ztesoft.res.quick.address.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@RestController
@Controller
public class AddressController {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @RequestMapping("/address/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/address/test/transaction")
    public String testTransaction() {
        String result = "Success";
        try {
            addressService.testTransaction();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
            log.error(e.getMessage());
        }
        return result;
    }
}
