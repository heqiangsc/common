package com.jialing.swift.proxy.jdk;

import com.jialing.swift.service.IPersonService;
import com.jialing.swift.service.impl.XiaoLiServiceImpl;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MeiPo implements InvocationHandler {

    private IPersonService personService;

    public Object getInstance(IPersonService personService) {
        this.personService = personService;
        System.out.println(personService.getClass());
        return Proxy.newProxyInstance(personService.getClass().getClassLoader(), personService.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.personService, args);
    }

    public static void main(String[] args) throws Exception {
        IPersonService personService = (IPersonService) new MeiPo().getInstance(new XiaoLiServiceImpl());
        System.out.println(personService.getClass());
        personService.move();

        byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{IPersonService.class});
        FileOutputStream os = new FileOutputStream("/Users/heqiang/data/proxy/$Proxy0.class");
        os.write(data);
        os.close();

    }
}
