package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.dao.AddFriendRequestMapper;
import com.lyt.springbootwsnettyserver.dao.ChatSingleMapper;
import com.lyt.springbootwsnettyserver.dao.UserRelationMapper;
import com.lyt.springbootwsnettyserver.domain.AddFriendRequest;
import com.lyt.springbootwsnettyserver.model.ChatMessage;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.util.JsonUtils;
import com.lyt.springbootwsnettyserver.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AddFriendReponseFrameHandler implements FrameHandler {

    @Autowired
    private AddFriendRequestMapper addFriendRequestMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;
    @Autowired
    private ChatSingleMapper chatSingleMapper;

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {

        Integer action = dataContent.getAction();
        // 添加好友请求回复处理
        if (action == MsgActionEnum.ADD_FRIEND_REPONSE) {
            if (dataContent.getChatMsg().get(0).getMsg().equals("1")) {
                Date currentDate = new Date();
                // 同意添加好友申请
                AddFriendRequest addFriendRequest = addFriendRequestMapper.findAddFriendRequestById(dataContent.getChatMsg().get(0).getMsgId());
                // 更改Add_FriendRequest表的接受标志为同意
                addFriendRequestMapper.updateIfReceptById("1", addFriendRequest.getId());
                // 更改Add_FriendRequest表的接受标志为同意
                // 发送一条问候消息并做记录(申请者发向被邀者)
                String uuidOne = UUID.randomUUID().toString().replaceAll("-", "");
                String greetContentOne = "您好我是" + SessionUtil.getSession(channel).getUserName() + ", 我们已经是好友啦, 来和我聊天吧!";
                chatSingleMapper.insert(uuidOne, addFriendRequest.getSendUserId(), addFriendRequest.getReceptUserId(), currentDate, "0", greetContentOne, "1");
                // 发送一条问候消息并做记录(申请者发向被邀者)
                // 发送一条问候消息并做记录(被邀者发向申请者)
                Channel sendUserChannel = SessionUtil.getChannel(addFriendRequest.getSendUserId());
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.SECOND, 1);
                currentDate = c.getTime();
                String uuidTwo = UUID.randomUUID().toString().replaceAll("-", "");
                String greetContentTwo = "您好我是" + SessionUtil.getSession(sendUserChannel).getUserName() + ", 我们已经是好友啦, 来和我聊天吧!";
                chatSingleMapper.insert(uuidTwo, addFriendRequest.getReceptUserId(), addFriendRequest.getSendUserId(), currentDate, "0", greetContentTwo, "1");
                // 发送一条问候消息并做记录(被邀者发向申请者
                // 存储好友关系
                userRelationMapper.insert(addFriendRequest.getSendUserId(), addFriendRequest.getReceptUserId(),"1", uuidTwo, 1);
                userRelationMapper.insert(addFriendRequest.getReceptUserId(), addFriendRequest.getSendUserId(),"1", uuidTwo, 1);
                // 存储好友关系
                // 通过channel发送消息(申请者发向被邀者)
                ChatMessage repChatMsg = new ChatMessage();
                repChatMsg.setSenderId(addFriendRequest.getSendUserId());
                repChatMsg.setReceiverId(addFriendRequest.getReceptUserId());
                repChatMsg.setMsgId(SessionUtil.getSession(sendUserChannel).getUserName()); //申请加好友者的用户名
                repChatMsg.setContentType("1");
                repChatMsg.setMsg(greetContentOne);
                List<ChatMessage> chatMessageList = new ArrayList<>();
                chatMessageList.add(repChatMsg);
                DataContent repDataContent = new DataContent();
                repDataContent.setAction(MsgActionEnum.ADD_FRIEND_REPONSE);
                repDataContent.setChatMsg(chatMessageList);
                repDataContent.setExtand(addFriendRequest.getId()); // 将添加好友请求表的记录ID存放到 extand，用于给被邀请者将导航栏的好友请求部分变成聊天形式
                channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(repDataContent)));
                // 通过channel发送消息(申请者发向被邀者)
                // 通过channel发送消息(被邀者发向申请者)
                repChatMsg.setSenderId(addFriendRequest.getReceptUserId());
                repChatMsg.setReceiverId(addFriendRequest.getSendUserId());
                repChatMsg.setMsgId(SessionUtil.getSession(channel).getUserName()); //被邀者的用户名
                repChatMsg.setContentType("1");
                repChatMsg.setMsg(greetContentTwo);
                chatMessageList.clear();
                chatMessageList.add(repChatMsg);
                repDataContent.setChatMsg(chatMessageList);
                sendUserChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(repDataContent)));
                // 通过channel发送消息(被邀者发向申请者)

            } else if (dataContent.getChatMsg().get(0).getMsg().equals("0")) {
                // 拒绝添加好友申请
                addFriendRequestMapper.updateIfReceptById("2", dataContent.getChatMsg().get(0).getMsgId());
            }
        }

    }
}
