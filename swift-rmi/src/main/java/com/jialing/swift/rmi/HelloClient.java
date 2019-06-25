package com.jialing.swift.rmi;

import java.rmi.Naming;

public class HelloClient {

    public static void main(String[] args) {
        try {
            ISayHello hello = (ISayHello) Naming.lookup("rmi://localhost:8888/sayHello");
            System.out.println(hello);
            System.out.println(hello.sayHello("菲菲"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
