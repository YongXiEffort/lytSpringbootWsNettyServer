package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.dao.AddFriendRequestMapper;
import com.lyt.springbootwsnettyserver.model.ChatMessage;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.util.JsonUtils;
import com.lyt.springbootwsnettyserver.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AddFriendFrameHandler implements FrameHandler {

    @Autowired
    private AddFriendRequestMapper addFriendRequestMapper;

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {

        Integer action = dataContent.getAction();
        // 添加好友请求
        if (action == MsgActionEnum.ADD_FRIEND) {
            System.out.println(" --------------------------------------- ");
            System.out.println(" 添加好友请求 请求用户 : " + SessionUtil.getSession(channel).getUserId()
                    + " 接受用户 : " + dataContent.getChatMsg().get(0).getReceiverId());
            // 保存添加好友请求到数据库
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            addFriendRequestMapper.insert(uuid, SessionUtil.getSession(channel).getUserId(),
                    dataContent.getChatMsg().get(0).getReceiverId(), new Date(), "0", dataContent.getChatMsg().get(0).getMsg());
            // 发送添加好友请求到被邀用户
            Channel friChannel = SessionUtil.getChannel(dataContent.getChatMsg().get(0).getReceiverId());
            List<ChatMessage> chatMessageList = new ArrayList<>();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSenderId(SessionUtil.getSession(channel).getUserName());
            chatMessage.setMsg(dataContent.getChatMsg().get(0).getMsg());
            chatMessage.setMsgId(uuid);
            chatMessageList.add(chatMessage);
            DataContent repDataContent = new DataContent();
            repDataContent.setAction(MsgActionEnum.ADD_FRIEND);
            repDataContent.setChatMsg(chatMessageList);
            repDataContent.setExtand(dataContent.getExtand());
            System.out.println(" 发送被邀请求到朋友用户 ");
            friChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(repDataContent)));
            System.out.println(" --------------------------------------- ");
        }

    }

}
