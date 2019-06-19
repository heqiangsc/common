package com.jialing.swift.custom;

import com.jialing.swift.service.IPersonService;
import com.jialing.swift.service.impl.XiaoLiServiceImpl;

import java.lang.reflect.Method;

public class SwiftMeipo implements SwiftInvocationHandler {

    private IPersonService personService;


    public Object getInstance(IPersonService personService) {
        this.personService = personService;
        Class clazz = personService.getClass();
        System.out.println("生成代理类的class:" + clazz);
        return SwiftProxy.newProxyInstance(new SwiftClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.personService, args);
    }


    public static void main(String[] args) {
        IPersonService personService = (IPersonService) new SwiftMeipo().getInstance(new XiaoLiServiceImpl());
        System.out.println(personService.getClass());
        personService.move();
    }
}
