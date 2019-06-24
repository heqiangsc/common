package com.jialing.swift.serialize.json;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.jialing.swift.serialize.Person;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonDemo {


    private static Person init() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(18);
        return person;
    }

    public static void main(String[] args) throws IOException {
        execteWithJack();
        System.out.println("------------------");
        execteWithFast();
        System.out.println("------------------");
        execteWithProtoBuf();
        System.out.println("------------------");
        execteWithHessian();
    }

    private static void execteWithJack() throws IOException {
        Person person = init();
        System.out.println(person);
        ObjectMapper mapper = new ObjectMapper();
        byte[] writeBytes = null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            writeBytes = mapper.writeValueAsBytes(person);
        }
        System.out.println("jackson序列化:" + (System.currentTimeMillis() - start) + "ms : " + "总大小->" + writeBytes.length);
        Person person1 = mapper.readValue(writeBytes, Person.class);
        System.out.println(person1);

    }


    private static void execteWithFast() throws IOException {
        Person person = init();
        System.out.println(person);
       String text = null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            text = JSON.toJSONString(person);
        }
        System.out.println("fastson序列化:" + (System.currentTimeMillis() - start) + "ms : " + "总大小->" + text.getBytes().length);
        Person person1 = JSON.parseObject(text, Person.class);
        System.out.println(person1);

    }

    private static void execteWithProtoBuf() throws IOException {
        Person person = init();
        System.out.println(person);
        Codec<Person> personCodec = ProtobufProxy.create(Person.class, false);
        byte[] bytes=null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            bytes = personCodec.encode(person);
        }
        System.out.println("ProtoBuf序列化:" + (System.currentTimeMillis() - start) + "ms : " + "总大小->" + bytes.length);
        Person person1 = personCodec.decode(bytes);
        System.out.println(person1);

    }


    private static void execteWithHessian() throws IOException {
        Person person = init();
        System.out.println(person);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            ho.writeObject(person);
            if(i == 0) {
                System.out.println(os.toByteArray().length);
            }
        }
        System.out.println("Hessian序列化:" + (System.currentTimeMillis() - start) + "ms : " + "总大小->" + os.toByteArray().length);
        HessianInput hi = new HessianInput(new ByteArrayInputStream(os.toByteArray()));
        Person person1 = (Person) hi.readObject();
        System.out.println(person1);

    }
}
