package com.jialing.swift.catalina.http;

import com.jialing.swift.catalina.exception.SwiftException;

public abstract class SwiftServlet {

    public void doGet(SwiftRequest request, SwiftResponse response) throws SwiftException {
        throw new SwiftException("SwiftServlet not support doGet method!");
    }

    public void doPost(SwiftRequest request, SwiftResponse response) throws SwiftException {
        throw new SwiftException("SwiftServlet not support doPost method!");
    }
}
