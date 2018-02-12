package com.ztesoft.res.quick.address.controller;

import com.ztesoft.res.quick.address.service.AddressFileRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AddressFileRecordController
 *
 * @author: fengwang
 * @date: 2018-2-7 15:40
 * @version: 1.0
 * @since: JDK 1.8
 */
@RestController
@Controller
public class AddressFileRecordController {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddressFileRecordController.class);

    @Autowired
    private AddressFileRecordService addressFileRecordService;

    @RequestMapping("/address/file/scan")
    public String read() {
        String result = "Success";
        try {
            addressFileRecordService.scanAddressFileFromFTP();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/address/file/doing")
    public String doingAddressFile() {
        String result = "Success";
        try {
            addressFileRecordService.doingAddressFile();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/address/file/read")
    public String readAddressFile() {
        String result = "Success";
        try {
            addressFileRecordService.readAddressFileFromFTP();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
            log.error(e.getMessage());
        }
        return result;
    }
}
