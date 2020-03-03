package com.lyt.springbootwsnettyserver.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.nio.charset.Charset;

public class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + ": " + binaryWebSocketFrame.content().toString(Charset.forName("UTF-8")));
        super.channelRead(channelHandlerContext, binaryWebSocketFrame);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
