package com.linzx.simple;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.compiler.Compiler;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.container.Main;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Protocol;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DubboProviderApplication {

    /**
     * spring方式启动
     */
    public static void main1( String[] args ) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/application.xml");
        applicationContext.start();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

        // 加载静态扩展点
        Protocol myProtocol = ExtensionLoader
                .getExtensionLoader(Protocol.class)
                .getExtension("myProtocol");

        // 加载自适应扩展点

        // @Adaptive标记Compiler实现类
        Compiler compiler = ExtensionLoader
                .getExtensionLoader(Compiler.class)
                .getAdaptiveExtension();
        // compiler.compile();
        // @Adaptive标记Protocol接口方法
        Protocol protocol = ExtensionLoader
                .getExtensionLoader(Protocol.class)
                .getAdaptiveExtension(); // 返回生成的类Protocol$Adaptive，实现了Protocol接口。有url参数驱动适配不同的Protocol实现
        // protocol.export();

        // 激活扩展点
        URL url = new URL("", "", 0);
        url = url.addParameter("cache", "cache");
        List<Filter> filterList = ExtensionLoader.getExtensionLoader(Filter.class).getActivateExtension(url, "cache");
    }

    /**
     * 使用Dubbo提供的启动方法
     */
    public static void main(String[] args) {
        // SpringContainer、LogbackContainer、Log4jContainer三种容器
        Main.main(new String[]{"spring", "log4j"});
    }

}
