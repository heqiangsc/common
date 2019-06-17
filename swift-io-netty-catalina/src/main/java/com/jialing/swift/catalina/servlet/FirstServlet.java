package com.jialing.swift.catalina.servlet;

import com.jialing.swift.catalina.http.SwiftRequest;
import com.jialing.swift.catalina.http.SwiftResponse;
import com.jialing.swift.catalina.http.SwiftServlet;

public class FirstServlet extends SwiftServlet {

    @Override
    public void doGet(SwiftRequest request, SwiftResponse response) {
        this.doPost(request, response);
    }

    @Override
    public void doPost(SwiftRequest request, SwiftResponse response) {
        String param = "name";
        String str = request.getParameter(param);
        response.write(param + ":" + str,200);
    }
}
