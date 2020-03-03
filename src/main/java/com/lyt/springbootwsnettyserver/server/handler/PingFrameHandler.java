package com.lyt.springbootwsnettyserver.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;

public class PingFrameHandler extends SimpleChannelInboundHandler<PingWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PingWebSocketFrame pingWebSocketFrame) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + ": 心跳");
        channelHandlerContext.writeAndFlush(new PongWebSocketFrame(pingWebSocketFrame.content().retain()));
    }

}
