package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.model.ChatMessage;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理聊天文本消息
 */
@Component
public class ChatFrameHandler implements FrameHandler {

//    public static final ChatFrameHandler INSTANCE = new ChatFrameHandler();

//    private ChatFrameHandler() { }

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {
        Integer action = dataContent.getAction();
        // 4. 如果是心跳类型则忽略
        if (action == MsgActionEnum.CHAT_TYPE) {
            System.out.println(" --------------------------------------- ");
            System.out.println(" 发送人Id : " + dataContent.getChatMsg().get(0).getSenderId());
            System.out.println(" 收信人Id : " + dataContent.getChatMsg().get(0).getReceiverId());
            System.out.println(" 消息内容 : " + dataContent.getChatMsg().get(0).getMsg());
            System.out.println(" --------------------------------------- ");

            ChatMessage repChatMsg = new ChatMessage();
            repChatMsg.setMsg("服务器返回你发送的消息 : " + dataContent.getChatMsg().get(0).getMsg());
            repChatMsg.setReceiverId("123456");
            repChatMsg.setReceiverId(dataContent.getChatMsg().get(0).getSenderId());
            List<ChatMessage> chatMessageList = new ArrayList<>();
            chatMessageList.add(repChatMsg);
            DataContent repDataContent = new DataContent();
            repDataContent.setAction(MsgActionEnum.CHAT_TYPE);
            repDataContent.setChatMsg(chatMessageList);

            channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(repDataContent)));
        }
    }
}
