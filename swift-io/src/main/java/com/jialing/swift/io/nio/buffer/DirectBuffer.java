package com.jialing.swift.io.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBuffer {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/heqiang/apps/cd_biz_demo/data/test.txt");
        FileChannel fis = fileInputStream.getChannel();

        //把刚刚读取的内容写入到一个新的文件中
        String outfile = "/Users/heqiang/apps/cd_biz_demo/data/testCopy.txt";
        FileOutputStream fout = new FileOutputStream(outfile);
        FileChannel fcout = fout.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);

        while (true) {
            byteBuffer.clear();
            int len = fis.read(byteBuffer);
            if (len == -1) {
                break;
            }
            byteBuffer.flip();
            fcout.write(byteBuffer);
        }


    }
}
