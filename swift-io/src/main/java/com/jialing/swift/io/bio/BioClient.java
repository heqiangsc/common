package com.jialing.swift.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 8080);

        OutputStream outputStream = client.getOutputStream();
        outputStream.write("hello, server!".getBytes());
        outputStream.flush();
        byte[] buffer = new byte[1024];
        int len = 0;
        InputStream inputStream = client.getInputStream();
        if ((len = inputStream.read(buffer)) > 0) {
            System.out.println(new String(buffer, 0, len));
        }
        client.close();

    }
}
