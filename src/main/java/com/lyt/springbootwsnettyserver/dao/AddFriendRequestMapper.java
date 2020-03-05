package com.lyt.springbootwsnettyserver.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

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

}
