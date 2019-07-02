package com.jialing.swift.threadpool;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadHistory3 {
    private static ThreadPool threadPool = ThreadPoolManager.getThreadPool(100);
    public static void main(String[] args) {
        ServerSocket server;
        try {
            server = new ServerSocket(8080);
            while (true) {
                Socket socket = server.accept();
                Runnable task = () -> handleRequest(socket);
                threadPool.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void handleRequest(Socket socket) {
        try {

            InputStream inputStream = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len;
            if ((len = inputStream.read(buff)) > 0) {
                String msg = new String(buff, 0, len);
                System.out.println(msg);
            } else {
                System.out.println("bad request");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
