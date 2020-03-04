package com.lyt.springbootwsnettyserver.dao;

import com.lyt.springbootwsnettyserver.model.ChatMessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface ChatSingleMapper {

    /**
     * 获取当前用户和某人的聊天内容
     * @return
     */
    @Select("SELECT cs.id, cs.from_user_id, cs.recept_user_id, cs.send_time, cs.content, cs.if_recept " +
            " FROM chat_single cs " +
            " WHERE ((cs.from_user_id = #{currentUserId} AND cs.recept_user_id = #{firUserId}) OR " +
            " (cs.from_user_id = #{firUserId} AND cs.recept_user_id = #{currentUserId})) " +
            " ORDER BY cs.send_time ")
    @Results({
            @Result(column="id", property="msgId", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="from_user_id", property="senderId", jdbcType=JdbcType.VARCHAR),
            @Result(column="recept_user_id", property="receiverId", jdbcType=JdbcType.VARCHAR),
            @Result(column="send_time", property="sendTime", jdbcType=JdbcType.DATE),
            @Result(column="content", property="msg", jdbcType=JdbcType.VARCHAR),
            @Result(column="if_recept", property="ifRecept", jdbcType=JdbcType.VARCHAR),
    })
    List<ChatMessage> findChatMsgByReceptUser(@Param("currentUserId") String currentUserId, @Param("firUserId") String firUserId);

    @Insert("INSERT INTO chat_single(id, from_user_id, recept_user_id, send_time, if_recept, content, content_type) " +
            " VALUES(#{id}, #{fromUserId}, #{receptUserId}, #{sendTime}, #{ifRecept}, #{content}, #{contentType})")
    int insert(@Param("id") String id,
               @Param("fromUserId") String fromUserId,
               @Param("receptUserId") String receptUserId,
               @Param("sendTime") Date sendTime,
               @Param("ifRecept") String ifRecept,
               @Param("content") String content,
               @Param("contentType") String contentType);

    @Update("UPDATE chat_single SET if_recept = '1' " +
            " WHERE (from_user_id = #{friUserId} and recept_user_id = #{currentUserId}) ")
    public int updateLastMsgId(@Param("friUserId") String friUserId,
                               @Param("currentUserId") String currentUserId);

    @Update("UPDATE chat_single SET if_recept = '1' WHERE id = #{msgId} ")
    public int updateMsgReceptById(@Param("msgId") String msgId);

}
