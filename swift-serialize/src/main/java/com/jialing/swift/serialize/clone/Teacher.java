package com.jialing.swift.serialize.clone;

import java.io.Serializable;

public class Teacher implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" + "name='"+name+'\'' + '}';
    }
}
