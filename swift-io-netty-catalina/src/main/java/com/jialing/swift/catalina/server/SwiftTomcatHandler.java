package com.jialing.swift.catalina.server;

import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import javax.core.common.config.CustomConfig;

public class SwiftTomcatHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(SwiftTomcatHandler.class);

    static {
        CustomConfig.load("web.properties");

        for (String key : CustomConfig.getKeys()) {
            if

        }


    }
}
