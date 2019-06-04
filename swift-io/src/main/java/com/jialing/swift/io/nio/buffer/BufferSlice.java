package com.jialing.swift.io.nio.buffer;

import java.nio.ByteBuffer;

public class BufferSlice {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.position(3);
        byteBuffer.limit(7);
        ByteBuffer slice = byteBuffer.slice();

        for (int i = 0; i < slice.capacity(); i++) {
            byte d = slice.get(i);
             d *= 10;
             slice.put(d);
        }
        byteBuffer.position( 0 );
        byteBuffer.limit( byteBuffer.capacity() );

        while (byteBuffer.remaining()>0) {
            System.out.println( byteBuffer.get() );
        }
    }
}
