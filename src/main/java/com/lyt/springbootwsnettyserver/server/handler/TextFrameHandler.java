package com.lyt.springbootwsnettyserver.server.handler;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage.*;
import com.lyt.springbootwsnettyserver.util.SpringUtil;
import com.lyt.springbootwsnettyserver.util.TextFrameUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;


@ChannelHandler.Sharable
public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static final TextFrameHandler INSTANCE = new TextFrameHandler();

    private Map<Integer, FrameHandler> handlerMap;

    private TextFrameHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(MsgActionEnum.CONNECT_TYPE, SpringUtil.getBean(ConnectFrameHandler.class));
        handlerMap.put(MsgActionEnum.CHAT_TYPE, SpringUtil.getBean(ChatFrameHandler.class));
        handlerMap.put(MsgActionEnum.PULL_CHAT_MSG, SpringUtil.getBean(PullMsgFrameHandler.class));
        handlerMap.put(MsgActionEnum.SIGNED_TYPE, SpringUtil.getBean(SignedFrameHandler.class));
        handlerMap.put(MsgActionEnum.ADD_FRIEND, SpringUtil.getBean(AddFriendFrameHandler.class));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        Channel currentChanenl = channelHandlerContext.channel();
        // 1. 获取客户端发送的消息
        String content = textWebSocketFrame.text();
        System.out.println("  content:  "+content);
        // 2. 获取客户端发来的json数据并解析成DataContent
        DataContent dataContent = TextFrameUtil.parseDataContent(textWebSocketFrame);
        // 3. 获取文本消息类型
        Integer action = dataContent.getAction();
        System.out.println("  action:  " + action);

        // 4. 如果是心跳类型则忽略
        if (action != MsgActionEnum.KEEPALIVE_TYPE) {
            handlerMap.get(action).dealWithFrame(dataContent, currentChanenl);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" 关闭连接!!! ");
        super.channelInactive(ctx);
    }
}
