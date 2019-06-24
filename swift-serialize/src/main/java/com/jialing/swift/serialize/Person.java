package com.jialing.swift.serialize;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.io.Serializable;

public class Person implements Serializable {

    public static int height = 2;

    @Protobuf(fieldType = FieldType.STRING, order = 1)
    private String name;

    @Protobuf(fieldType = FieldType.INT32, order = 2)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
