package com.jialing.swift.io.nio.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferProgram {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("/Users/heqiang/apps/cd_biz_demo/data/test.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        output("初始化", byteBuffer);

        //先读一下
        fileChannel.read(byteBuffer);
        output("read", byteBuffer);

        //准备操作之前，先锁定操作范围
        byteBuffer.flip();
        output("调用flip()", byteBuffer);

        //调用get
        
    }


    public static void output(String step, ByteBuffer byteBuffer) {
        System.out.println(step + " : ");
        //容量，数组大小
        System.out.print("capacity: " + byteBuffer.capacity() + ", ");
        //当前操作数据所在的位置，也可以叫做游标
        System.out.print("position: " + byteBuffer.position() + ", ");
        //锁定值，flip，数据操作范围索引只能在position - limit 之间
        System.out.println("limit: " + byteBuffer.limit());
        System.out.println();
    }
}
