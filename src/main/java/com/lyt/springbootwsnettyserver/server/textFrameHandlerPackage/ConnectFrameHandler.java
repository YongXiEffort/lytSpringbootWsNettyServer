package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.constant.MsgActionEnum;
import com.lyt.springbootwsnettyserver.model.DataContent;
import com.lyt.springbootwsnettyserver.model.Session;
import com.lyt.springbootwsnettyserver.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * 处理用户绑定到channel
 */
@Component
public class ConnectFrameHandler implements FrameHandler {

//    public static final ConnectFrameHandler INSTANCE = new ConnectFrameHandler();

//    private ConnectFrameHandler() { }

    @Override
    public void dealWithFrame(DataContent dataContent, Channel channel) {
        Integer action = dataContent.getAction();
        // 4. 如果是心跳类型则忽略
        if (action == MsgActionEnum.CONNECT_TYPE) {
            // 当建立连接时, 第一次open , 初始化channel,将channel和数据库中的用户做一个唯一的关联
            System.out.println(" --------------------------------------- ");
            System.out.println(" init user");
            String sendId = dataContent.getChatMsg().get(0).getSenderId();
            SessionUtil.bindSession(new Session(sendId, ""), channel);

            System.out.println(" init user finish ");
            System.out.println(" --------------------------------------- ");
        }
    }
}
