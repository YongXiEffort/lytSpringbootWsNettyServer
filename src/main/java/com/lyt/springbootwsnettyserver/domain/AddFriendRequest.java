package com.lyt.springbootwsnettyserver.domain;

import java.util.Date;

public class AddFriendRequest {

    private String id;
    private String sendUserId;
    private String receptUserId;
    private Date sendTime;
    private String ifRecept;
    private String invitationMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
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

    public String getInvitationMsg() {
        return invitationMsg;
    }

    public void setInvitationMsg(String invitationMsg) {
        this.invitationMsg = invitationMsg;
    }
}
