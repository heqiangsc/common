package com.jialing.swift.proxy.cglib;

import com.jialing.swift.service.IPersonService;
import com.jialing.swift.service.impl.XiaoLiServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGMeiPo implements MethodInterceptor {

    public Object getInstance(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return methodProxy.invokeSuper(o, objects);
    }

    public static void main(String[] args) {
        IPersonService personService = (IPersonService)new CGMeiPo().getInstance(XiaoLiServiceImpl.class);
        System.out.println(personService.getClass());
        personService.move();
    }
}
