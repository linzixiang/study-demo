package com.linzx.redission;

public class PersonService {

    private PersonProperties personProperties;

    public PersonService(PersonProperties personProperties) {
        this.personProperties = personProperties;
    }

    public void sayHello() {
        String message = String.format("大家好，我叫: %s, 今年 %s岁, 性别: %s",
                personProperties.getName(), personProperties.getAge(), personProperties.getSex());
        System.out.println(message);
    }
}
