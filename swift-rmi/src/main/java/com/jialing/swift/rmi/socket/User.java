package com.jialing.swift.rmi.socket;

import java.io.IOException;

public class User {

    private int age;

    public int getAge() throws IOException {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
