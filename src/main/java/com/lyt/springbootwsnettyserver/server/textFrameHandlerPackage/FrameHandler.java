package com.lyt.springbootwsnettyserver.server.textFrameHandlerPackage;

import com.lyt.springbootwsnettyserver.model.DataContent;
import io.netty.channel.Channel;

public interface FrameHandler {

    public void dealWithFrame(DataContent dataContent, Channel channel);

}
