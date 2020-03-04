package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.dao.ChatSingleMapper;
import com.lyt.springbootwsnettyserver.dao.UserRelationMapper;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignedFrameHandler implements FrameHandler {

    @Autowired
    private ChatSingleMapper chatSingleMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {

        Integer action = dataContent.getAction();
        // 4. 如果是心跳类型则忽略
        if (action == MsgActionEnum.SIGNED_TYPE) {
            chatSingleMapper.updateMsgReceptById(dataContent.getChatMsg().get(0).getMsgId());
            userRelationMapper.SignedMsgNotReceptCountSubOne(SessionUtil.getSession(channel).getUserId(), dataContent.getChatMsg().get(0).getSenderId());
        }

    }

}
