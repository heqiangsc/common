package com.jialing.swift.io.nio.buffer;

import java.nio.ByteBuffer;

public class BufferWrap {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byte[] bytes = new byte[10];
        ByteBuffer cc = ByteBuffer.wrap(bytes);
    }
}
