package com.jialing.swift.custom;

import java.lang.reflect.Method;

public interface SwiftInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
