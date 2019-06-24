package com.jialing.swift.serialize.clone;

import java.io.IOException;

public class CloneDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teacher teacher = new Teacher();
        teacher.setName("张三");

        Student student = new Student();
        student.setAge(35);
        student.setName("沐风");
        student.setTeacher(teacher);

        Student student1 = (Student) student.deepClone();
        System.out.println(student);

        student1.getTeacher().setName("李四");
        System.out.println(student1);

    }


}
