package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.dao.ChatSingleMapper;
import com.lyt.springbootwsnettyserver.dao.UserRelationMapper;
import com.lyt.springbootwsnettyserver.model.ChatMessage;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.util.JsonUtils;
import com.lyt.springbootwsnettyserver.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 处理聊天文本消息
 */
@Component
public class ChatFrameHandler implements FrameHandler {

    @Autowired
    private ChatSingleMapper chatSingleMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {
        Integer action = dataContent.getAction();
        // 4. 如果是心跳类型则忽略
        if (action == MsgActionEnum.CHAT_TYPE) {
            System.out.println(" --------------------------------------- ");
            System.out.println(" 发送人Id : " + SessionUtil.getSession(channel).getUserName());
            System.out.println(" 收信人Id : " + dataContent.getChatMsg().get(0).getReceiverId());
            System.out.println(" 消息内容 : " + dataContent.getChatMsg().get(0).getMsg());
            System.out.println(" --------------------------------------- ");

            String msgId = UUID.randomUUID().toString().replaceAll("-","");
            // 存储到数据库
            try {
                chatSingleMapper.insert(msgId,
                        SessionUtil.getSession(channel).getUserId(),
                        dataContent.getChatMsg().get(0).getReceiverId(), new Date(),
                        "0", dataContent.getChatMsg().get(0).getMsg(), "1");
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("lastChatId", msgId);
                updateMap.put("userId", dataContent.getChatMsg().get(0).getReceiverId());
                updateMap.put("friUserId", SessionUtil.getSession(channel).getUserId());
                userRelationMapper.updateNotReceptCount(updateMap);
                userRelationMapper.updateLastMsgId(msgId,
                        dataContent.getChatMsg().get(0).getReceiverId(),
                        SessionUtil.getSession(channel).getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 存储到数据库

            // 组装返回消息
            Channel friChannel = SessionUtil.getChannel(dataContent.getChatMsg().get(0).getReceiverId());
            if (friChannel != null) {
                ChatMessage repChatMsg = new ChatMessage();
                repChatMsg.setSenderId(SessionUtil.getSession(channel).getUserId());
                repChatMsg.setReceiverId(dataContent.getChatMsg().get(0).getReceiverId());
                repChatMsg.setSendTime(new Date());
                repChatMsg.setContentType("1");
                repChatMsg.setMsgId(msgId);
                repChatMsg.setMsg(dataContent.getChatMsg().get(0).getMsg());
                List<ChatMessage> chatMessageList = new ArrayList<>();
                chatMessageList.add(repChatMsg);
                DataContent repDataContent = new DataContent();
                repDataContent.setAction(MsgActionEnum.CHAT_TYPE);
                repDataContent.setChatMsg(chatMessageList);
                repDataContent.setExtand(String.valueOf(userRelationMapper.findNotReceptMsgCountByBothUserId(dataContent.getChatMsg().get(0).getReceiverId(), SessionUtil.getSession(channel).getUserId())));

                // 发送消息到另一个用户
                friChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(repDataContent)));
            }
            // 组装返回消息
        }
    }
}
