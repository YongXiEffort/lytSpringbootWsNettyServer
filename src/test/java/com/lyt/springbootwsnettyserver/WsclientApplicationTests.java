package com.lyt.springbootwsnettyserver;

import com.lyt.springbootwsnettyserver.dao.ChatSingleMapper;
import com.lyt.springbootwsnettyserver.dao.UserRelationMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsclientApplicationTests {

    @Autowired
    private ChatSingleMapper chatSingleMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;

    @Test
    @Transactional
    @Rollback(true)
    public void test() throws Exception {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("lastChatId", "123456789");
        updateMap.put("userId", "AAA");
        updateMap.put("friUserId", "BBB");
        Integer notReceptMsgCount = userRelationMapper.updateNotReceptCount(updateMap);
        System.out.println("notReceptMsgCount : " + notReceptMsgCount);
        Assert.assertEquals(notReceptMsgCount, Integer.valueOf(1));
    }

}
