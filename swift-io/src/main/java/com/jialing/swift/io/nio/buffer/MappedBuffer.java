package com.jialing.swift.io.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedBuffer {


    static private final int start = 0;
    static private final int size = 1024;

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("/Users/heqiang/apps/cd_biz_demo/data/test.txt", "rw");
        FileChannel fileChannel = file.getChannel();
        //把缓冲区跟文件系统进行一个映射关联
        //只要操作缓冲区里面的内容，文件内容也会跟着改变
        MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);


        file.close();
    }
}
