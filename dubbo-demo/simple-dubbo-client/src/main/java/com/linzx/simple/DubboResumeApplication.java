package com.linzx.simple;

import com.linzx.api.dto.DemoParamDto;
import com.linzx.api.dto.DemoResDto;
import com.linzx.api.service.IDemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 */
public class DubboResumeApplication {

    public static void main( String[] args ) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/application.xml");
        applicationContext.start();
        IDemoService demoService = (IDemoService) applicationContext.getBean("demoService");
        DemoParamDto demoParamDto = new DemoParamDto();
        DemoResDto demoResDto = demoService.helloDemo(demoParamDto);
        System.out.println(demoResDto);
        new CountDownLatch(1).await();
    }

}
