package com.jialing.swift.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

    private ServerSocket server;

    public BioServer(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("server 已经启动");
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = server.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            if ((len = inputStream.read(buffer)) > 0) {
                String msg = new String(buffer, 0, len);
                System.out.println("收到" + msg);

            }
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello, client".getBytes());
            outputStream.flush();
        }


    }

    public static void main(String[] args) throws IOException {
        new BioServer(8080).listen();
    }
}
