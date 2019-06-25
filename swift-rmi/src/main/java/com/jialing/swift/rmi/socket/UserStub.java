package com.jialing.swift.rmi.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserStub extends User {

    private Socket socket;

    public UserStub() throws IOException {
        socket = new Socket("localhost", 8888);

    }

    @Override
    public int getAge() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject("age");
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return objectInputStream.readInt();
    }
}
