package com.jialing.swift.catalina.servlet;

import com.jialing.swift.catalina.http.SwiftRequest;
import com.jialing.swift.catalina.http.SwiftResponse;
import com.jialing.swift.catalina.http.SwiftServlet;

public class FirstServlet extends SwiftServlet {

    @Override
    public void doGet(SwiftRequest request, SwiftResponse response) {
        super.doGet(request, response);
    }

    @Override
    public void doPost(SwiftRequest request, SwiftResponse response) {

    }
}
