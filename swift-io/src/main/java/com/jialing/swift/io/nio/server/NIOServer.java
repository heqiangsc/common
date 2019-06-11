package com.jialing.swift.io.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    private int port = 8080;
    private InetSocketAddress address = null;
    private Selector selector;

    public NIOServer() throws IOException {
        address = new InetSocketAddress(this.port);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(address);
        server.configureBlocking(false);
        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("nio server 已启动， port" + this.port);
    }

    public void listen() throws IOException {
        while (true) {
            int count = selector.select();
            if (count <= 0) continue;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                process(key);
                iterator.remove();
            }
        }
    }

    public void process(SelectionKey sk) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (sk.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) sk.channel();
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_READ);
        } else if (sk.isReadable()) {
            SocketChannel client = (SocketChannel) sk.channel();
            int len = client.read(buffer);
            if (len > 0) {
                String content = new String(buffer.array(), 0, len);
                System.out.println(content);
                client.register(selector, SelectionKey.OP_WRITE);
            }
            buffer.clear();
        } else if (sk.isWritable()) {
            SocketChannel client = (SocketChannel) sk.channel();
            client.write(ByteBuffer.wrap("hello world".getBytes()));
            client.close();
        }


    }

    public static void main(String[] args) throws IOException {
       new NIOServer().listen();
    }
}
