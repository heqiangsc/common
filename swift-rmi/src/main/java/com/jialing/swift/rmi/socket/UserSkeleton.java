package com.jialing.swift.rmi.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UserSkeleton extends Thread {

    private UserServer userServer;

    public UserSkeleton(UserServer userServer) throws IOException {
        this.userServer = userServer;
    }

    @Override
    public void run() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            while (null != socket) {
                ObjectInputStream read = new ObjectInputStream(socket.getInputStream());
                String method = (String)read.readObject();
                if(method.equals("age")) {
                    int age = userServer.getAge();
                    ObjectOutputStream write = new ObjectOutputStream(socket.getOutputStream());
                    write.writeInt(age);
                    write.flush();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
