package com.jialing.swift.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class HelloServer {

    public static void main(String[] args) {
        try {
            ISayHello hello = new SayHelloImpl();
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/sayHello", hello);
            System.out.println("server start success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
