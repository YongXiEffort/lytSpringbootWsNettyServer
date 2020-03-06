package com.lyt.springbootwsnettyserver.dao;


import com.lyt.springbootwsnettyserver.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UserMapper {

    @Select("SELECT id,user_name FROM user WHERE id = #{userId}")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR)
    })
    User findByUserId(@Param("userId") String userId);

}
