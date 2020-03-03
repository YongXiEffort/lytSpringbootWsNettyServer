package com.lyt.springbootwsnettyserver.util;

import com.lyt.springbootwsnettyserver.model.DataContent;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextFrameUtil {

    public static DataContent parseDataContent(TextWebSocketFrame textWebSocketFrame) {
        // 1. 获取客户端发送的消息
        String content = textWebSocketFrame.text();
        // 2. 获取客户端发来的json数据并解析成DataContent
        DataContent dataContent = JsonUtils.fromJson(content, DataContent.class);
        return dataContent;
    }

}
