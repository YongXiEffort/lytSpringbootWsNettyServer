package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.github.pagehelper.PageHelper;
import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.dao.ChatSingleMapper;
import com.lyt.springbootwsnettyserver.model.ChatMessage;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.util.JsonUtils;
import com.lyt.springbootwsnettyserver.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PullMsgFrameHandler implements FrameHandler {

    @Autowired
    private ChatSingleMapper chatSingleMapper;

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {
        Integer action = dataContent.getAction();
        // 4. 如果是心跳类型则忽略
        if (action == MsgActionEnum.PULL_CHAT_MSG) {
            System.out.println(" --------------------------------------- ");
            System.out.println(" 拉去用户和朋友的消息 ");
            PageHelper.startPage(0, 5);
            List<ChatMessage> chatMessageList =
                    chatSingleMapper.findChatMsgByReceptUser(SessionUtil.getSession(channel).getUserId(), dataContent.getChatMsg().get(0).getSenderId());
            DataContent repDataContent = new DataContent();
            repDataContent.setAction(MsgActionEnum.PULL_CHAT_MSG);
            repDataContent.setChatMsg(chatMessageList);
            repDataContent.setExtand("1"); // online
            System.out.println(" 拉去完毕，返回内容 ");
            channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(repDataContent)));
            System.out.println(" --------------------------------------- ");
        }

    }
}
