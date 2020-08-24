package com.linzx.jvm.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task  implements Runnable{

    public void run() {
        int num = new Random().nextInt(1000);
        List<MemoryBean> memoryBeanList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // 将创建的bean放入新生代
            MemoryBean memoryBean = new MemoryBean();
            memoryBean.setMemory(new byte[1024]);
            memoryBeanList.add(memoryBean);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JvmGC.list.add(new Byte[1024]);
    }

}
