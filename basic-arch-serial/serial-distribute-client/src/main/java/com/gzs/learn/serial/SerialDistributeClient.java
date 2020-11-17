package com.gzs.learn.serial;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gzs.learn.serial.dubbo.DubboSerialDistributeService;
import com.gzs.learn.serial.dubbo.DubboSerialManagerService;
import com.gzs.learn.serial.inf.DataStatus;
import com.gzs.learn.serial.inf.SerialGroup;

public class SerialDistributeClient {
    String groupName = "order";

    @Test
    public void testCreateKey() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "/META-INF/SerialDistributeClient.xml" });
        context.start();
        DubboSerialManagerService service = context.getBean(DubboSerialManagerService.class);
        SerialGroup group = new SerialGroup();
        group.setName(groupName);
        group.setStat(DataStatus.ENABLE);
        group.setVersion(1);
        group.setStep(10000);
        group.setMin(102010211022L);
        group.setMax(102099999999L);
        group.setPart(8);
        group.setTsup(System.currentTimeMillis());
        group.setUpid(1);
        service.createSerialGroup(group);

        context.close();

    }

    @Test
    public void testGetKey() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "/META-INF/SerialDistributeClient.xml" });
        context.start();
        DubboSerialDistributeService distributeService = context.getBean(DubboSerialDistributeService.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(distributeService.getSerial(groupName, 10000));
        }
        System.out.println(distributeService.getSerial(groupName, 1));
        context.close();
    }

    @Test
    public void testGetSnowflakeKey() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "/META-INF/SerialDistributeClient.xml" });
        context.start();
        DubboSerialDistributeService distributeService = context.getBean(DubboSerialDistributeService.class);
        long id = distributeService.getSnowflake();
        System.out.println(((id<<44)>>44)>>17);
        context.close();
    }
}
