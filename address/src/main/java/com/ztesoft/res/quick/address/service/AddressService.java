package com.ztesoft.res.quick.address.service;

import com.ztesoft.res.quick.address.model.entity.Address;
import com.ztesoft.res.quick.address.model.repository.AddressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TestService
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@Service("addressService")
public class AddressService {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    AddressDao addressDao;

    @Transactional
    public void testTransaction() {
        Address address1 = new Address();
        address1.setRegionName("宝成名苑(合建)");
        address1.setStandName1("江苏省");
        address1.setStandName2("南京市");
        address1.setStandName3("溧水区");
        address1.setStandName4(null);
        address1.setStandName5("宝塔路");
        address1.setStandName6("23号宝城名苑小区");
        address1.setStandName7("1栋");
        address1.setStandName8("1单元");
        address1.setStandName9("101室");
        address1.setUseType("是");
        address1.setMark("KDXQ");
        address1.setEqpName(null);
        address1.setOldStandId7("000102140000003444007095");
        address1.setOldStandName7("南京市溧水区宝塔路23号宝成名苑小区(合建)(KDXQ)1栋1单元101室");
        address1.setCheckErr(null);
        address1.setEqpTable(null);
        address1.setBothId("B0205CF");
        this.addressDao.insert(address1);

        int i = 1 / 0;

        Address address2 = new Address();
        address2.setRegionName("宝成名苑(合建)");
        address2.setStandName1("江苏省");
        address2.setStandName2("南京市");
        address2.setStandName3("溧水区");
        address2.setStandName4(null);
        address2.setStandName5("宝塔路");
        address2.setStandName6("23号宝城名苑小区");
        address2.setStandName7("1栋");
        address2.setStandName8("1单元");
        address2.setStandName9("102室");
        address2.setUseType("是");
        address2.setMark("KDXQ");
        address2.setEqpName(null);
        address2.setOldStandId7("000102140000003444006527");
        address2.setOldStandName7("南京市溧水区宝塔路23号宝成名苑小区(合建)(KDXQ)1栋1单元102室");
        address2.setCheckErr(null);
        address2.setEqpTable(null);
        address2.setBothId("B0205CF");
        this.addressDao.insert(address2);
    }
}
