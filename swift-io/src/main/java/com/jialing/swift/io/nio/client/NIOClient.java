package com.jialing.swift.io.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {

    private InetSocketAddress address = new InetSocketAddress("localhost", 8080);
    private SocketChannel client;
    private Selector selector;

    private Charset charset = Charset.forName("UTF-8");

    public NIOClient() throws IOException {
        client = SocketChannel.open(address);
        client.configureBlocking(false);
        selector = Selector.open();
        client.register(selector, SelectionKey.OP_READ);
    }

    public void session() {
        new Reader().start();
        new Writer().start();
    }

    class Reader extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    int count = selector.select();
                    if (count <= 0) continue;
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey ks = iterator.next();
                        iterator.remove();
                        process(ks);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        private void process(SelectionKey key) throws IOException {
            if (key.isReadable()) {
                //使用 NIO 读取 Channel中的数据，这个和全局变量client是一样的，因为只注册了一个SocketChannel
                //client既能写也能读，这边是读
                SocketChannel sc = (SocketChannel) key.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content += charset.decode(buff);
                }

                System.out.println(content);
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }


    class Writer extends Thread {
        @Override
        public void run() {
            try{
                //在主线程中 从键盘读取数据输入到服务器端
                Scanner scan = new Scanner(System.in);
                while(scan.hasNextLine()){
                    String line = scan.nextLine();
                    if("".equals(line)) continue; //不允许发空消息

//		            client.register(selector, SelectionKey.OP_WRITE);
                    client.write(charset.encode(line));//client既能写也能读，这边是写
                }
                //scan.close();
            }catch(Exception e){

            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NIOClient().session();
    }

}


