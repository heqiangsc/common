package com.jialing.swift.io.nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileInputProgram {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/heqiang/apps/cd_biz_demo/data/test.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

       fileChannel.read(byteBuffer);
       byteBuffer.flip();
       while (byteBuffer.remaining() > 0) {
           System.out.println((char)byteBuffer.get());
       }
       fileChannel.close();
        fileInputStream.close();
    }
}
