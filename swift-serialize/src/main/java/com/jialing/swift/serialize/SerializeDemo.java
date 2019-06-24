package com.jialing.swift.serialize;

import java.io.*;

public class SerializeDemo {



    public  static void serializePerson() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("person")));
        Person person = new Person();
        person.setName("张三");
        person.setAge(35);

        objectOutputStream.writeObject(person);
        objectOutputStream.flush();
        objectOutputStream.close();
    }


    public static void deserializePerson() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("person")));
        Person person = (Person) objectInputStream.readObject();
        System.out.println(person);

    }

    public static void main(String[] args) throws Exception {
        //serializePerson();
       deserializePerson();
    }
}
