package com.lyt.springbootwsnettyserver.model;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {

    private String senderId;
    private String receiverId;
    private String msg;
    private String msgId;
    private Date sendTime;
    private String contentType;
    private String ifRecept;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getIfRecept() {
        return ifRecept;
    }

    public void setIfRecept(String ifRecept) {
        this.ifRecept = ifRecept;
    }
}
