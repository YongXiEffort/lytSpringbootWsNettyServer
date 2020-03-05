package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.dao.AddFriendRequestMapper;
import com.lyt.springbootwsnettyserver.dao.UserRelationMapper;
import com.lyt.springbootwsnettyserver.model.DataContent;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddFriendReponseFrameHandler implements FrameHandler {

    @Autowired
    private AddFriendRequestMapper addFriendRequestMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {

        Integer action = dataContent.getAction();
        // 添加好友请求回复处理
        if (action == MsgActionEnum.ADD_FRIEND_REPONSE) {
            
        }

    }
}
