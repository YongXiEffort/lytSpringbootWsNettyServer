package com.lyt.springbootwsnettyserver.constant;

import com.lyt.springbootwsnettyserver.model.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
