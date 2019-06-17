package com.jialing.swift.catalina.server;

import com.jialing.swift.catalina.http.SwiftRequest;
import com.jialing.swift.catalina.http.SwiftResponse;
import com.jialing.swift.catalina.http.SwiftServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.log4j.Logger;

import javax.core.common.config.CustomConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SwiftTomcatHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(SwiftTomcatHandler.class);
    private static final Map<Pattern, Class<?>> servletMapping = new HashMap<>();

    static {
        CustomConfig.load("web.properties");

        for (String key : CustomConfig.getKeys()) {
            if (key.startsWith("servlet")) {
                String name = key.replaceFirst("servlet.", "");
                if (name.contains(".")) {
                    name = name.substring(0, name.indexOf("."));
                } else {
                    continue;
                }
                String pattern = CustomConfig.getString("servlet." + name + ".urlPattern");
                pattern = pattern.replaceAll("\\*", ".*");
                String className = CustomConfig.getString("servlet." + name + ".className");
                if (!servletMapping.containsKey(Pattern.compile(pattern))) {
                    try {
                        servletMapping.put(Pattern.compile(pattern), Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest r = (HttpRequest) msg;
            SwiftRequest request = new SwiftRequest(ctx, r);
            SwiftResponse response = new SwiftResponse(ctx, r);
            String uri = request.getUri();
            String method = request.getMethod();

            logger.info(String.format("uri:%s, method:%s", uri, method));

            boolean hasPattern = false;
            for (Map.Entry<Pattern, Class<?>> entry : servletMapping.entrySet()) {
                if (entry.getKey().matcher(uri).matches()) {
                    SwiftServlet swiftServlet = (SwiftServlet) entry.getValue().newInstance();
                    if ("get".equalsIgnoreCase(method)) {
                        swiftServlet.doGet(request, response);
                    } else {
                        swiftServlet.doPost(request, response);
                    }
                    hasPattern = true;
                }
            }
            if (!hasPattern) {
                String out = String.format("404 not found uri:%s for method: %s", uri, method);
                response.write(out, 404);
                return;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
