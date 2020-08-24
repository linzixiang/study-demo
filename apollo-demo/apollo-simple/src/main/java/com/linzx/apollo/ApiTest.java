package com.linzx.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

import java.util.concurrent.CountDownLatch;

public class ApiTest {

    public static void main(String[] args) throws InterruptedException {
        Config config = ConfigService.getAppConfig(); //config instance is singleton for each namespace and is never null
        String someKey = "http.timeout";
        String someDefaultValue = "2000";
        String value = config.getProperty(someKey, someDefaultValue);
        System.err.println("http.timeout:" + value);
        config.addChangeListener(new ConfigChangeListener() {

            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                System.out.println("Changes for namespace " + changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
                }
            }
        });
        new CountDownLatch(1).await();
    }

}
