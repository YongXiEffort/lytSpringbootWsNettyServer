package com.lyt.springbootwsnettyserver.model;

import java.io.Serializable;
import java.util.List;

public class DataContent implements Serializable {

    private Integer action;
    private List<ChatMessage> chatMsg;
    private String extand;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public List<ChatMessage> getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(List<ChatMessage> chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
