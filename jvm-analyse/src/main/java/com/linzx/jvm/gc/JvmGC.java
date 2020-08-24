package com.linzx.jvm.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * jvm 性能调优分析
 */
public class JvmGC {

    public static final List list = new ArrayList();

    /**
     * 堆大小60m；新生代大小5m（Eden区占8,即4m，两个Survivor区占2,即1m）；动态年龄判断阈值15
     * jvm参数：-Xms60m -Xmx60m -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
     *
     * 查看当前设置的参数：java -XX:+PrintFlagsFinal
     * 查看jvm先pid：jps
     * 查看堆大小：jmap -heap [PID]
     * 导出堆文件：jmap -dump:format=b,file=heap.hprof [PID]
     *
     * 日志分析
     *  0.436: [GC (Allocation Failure) [PSYoungGen: 4096K->512K(4608K)] 4096K->920K(60928K), 0.0011644 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     *  18.853: [GC (Allocation Failure) [PSYoungGen: 4607K->512K(4608K)] 5016K->4656K(60928K), 0.0060966 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     *  28.109: [GC (Allocation Failure) [PSYoungGen: 4608K->512K(4608K)] 8752K->8884K(60928K), 0.0031455 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     *  ......
     *  122.898: [Full GC (Ergonomics) [PSYoungGen: 512K->0K(4608K)] [ParOldGen: 52513K->8616K(56320K)] 53025K->8616K(60928K), [Metaspace: 3934K->3934K(1056768K)], 0.0201392 secs] [Times: user=0.17 sys=0.00, real=0.02 secs]
     *  什么时候触发Eden区GC？当Eden去满4m时，开始PSYoungGen（PS指Parallel Scavenge回收器） GC
     *  什么是否触发Full GC？
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Thread.sleep(10000);
        while (true) {
            for (int i = 0; i < 20; i++) {
                executorService.execute(new Task());
            }
            Thread.sleep(1000);
        }

    }

}
