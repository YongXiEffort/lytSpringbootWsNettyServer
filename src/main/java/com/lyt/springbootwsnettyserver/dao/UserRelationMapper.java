package com.lyt.springbootwsnettyserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface UserRelationMapper {

    @Update("UPDATE user_relation SET last_chat_id = #{lastChatId} " +
            " WHERE (user_id = #{userId} and fri_user_id = #{friUserId}) or (user_id = #{friUserId} and fri_user_id = #{userId}) ")
    public int updateLastMsgId(@Param("lastChatId") String lastChatId,
                                       @Param("userId") String userId,
                                       @Param("friUserId") String friUserId);

    public Integer updateNotReceptCount(Map<String, Object> updateMap);

    @Update("UPDATE user_relation SET not_receptMsg_count = 0 " +
            " WHERE (user_id = #{currentUserId} and fri_user_id = #{friUserId}) ")
    public int pullMsgUpdateNotReceptCountToZero(@Param("currentUserId") String currentUserId, @Param("friUserId") String friUserId);

    @Update("UPDATE user_relation SET not_receptMsg_count = (not_receptMsg_count - 1) " +
            " WHERE (user_id = #{currentUserId} and fri_user_id = #{friUserId}) ")
    public int SignedMsgNotReceptCountSubOne(@Param("currentUserId") String currentUserId, @Param("friUserId") String friUserId);

}
