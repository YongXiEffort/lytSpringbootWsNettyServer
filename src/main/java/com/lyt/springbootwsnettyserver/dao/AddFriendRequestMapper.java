package com.lyt.springbootwsnettyserver.dao;

import com.lyt.springbootwsnettyserver.domain.AddFriendRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface AddFriendRequestMapper {

    @Insert("INSERT INTO add_friendrequest(id, send_user_id, recept_user_id, send_time, if_recept, invitationMsg) " +
            " VALUES(#{id}, #{sendUserId}, #{receptUserId}, #{sendTime}, #{ifRecept}, #{invitationMsg})")
    int insert(@Param("id") String id,
               @Param("sendUserId") String sendUserId,
               @Param("receptUserId") String receptUserId,
               @Param("sendTime") Date sendTime,
               @Param("ifRecept") String ifRecept,
               @Param("invitationMsg") String invitationMsg);

    @Update("UPDATE add_friendrequest SET if_recept = #{ifRecept} WHERE id = #{id} ")
    void updateIfReceptById(@Param("ifRecept") String ifRecept ,@Param("id") String id);

    /**
     * 获取当前用户和某人的聊天内容
     * @return
     */
    @Select("SELECT id, send_user_id, recept_user_id " +
            " FROM add_friendrequest " +
            " WHERE id = #{id} ")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="send_user_id", property="sendUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="recept_user_id", property="receptUserId", jdbcType=JdbcType.VARCHAR),
    })
    AddFriendRequest findAddFriendRequestById(@Param("id") String id);

}
