package com.lyt.springbootwsnettyserver.domain;

import java.util.Date;

public class ChatSingle {

    private String id;
    private String fromUserId;
    private String receptUserId;
    private Date sendTime;
    private String ifRecept;
    private String content;
    private String contentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getReceptUserId() {
        return receptUserId;
    }

    public void setReceptUserId(String receptUserId) {
        this.receptUserId = receptUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getIfRecept() {
        return ifRecept;
    }

    public void setIfRecept(String ifRecept) {
        this.ifRecept = ifRecept;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
