package com.hhf.forum.mapper;

import com.hhf.forum.dto.LoginUserRequestDto;
import com.hhf.forum.dto.UserRequestDto;
import com.hhf.forum.entity.Avatar;
import com.hhf.forum.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Options(useGeneratedKeys = true,keyProperty = "userId")
    @Insert("insert into user(username,nickname,password,avatar_id,mail,level,status)values" +
            "(#{username},#{nickname},#{password},#{avatarId},#{mail},#{level},#{status})")
    int registeUser(UserRequestDto userRequestDto);

    @Select("select user.*,avatar.avatar avatar from user,token, avatar where " +
            "user.id=token.user_id and user.avatar_id=avatar.id and token=#{token}")
    User whoami(@Param("token") String token);

    @Select("select * from user where username=#{username} and password=#{password}")
    User login(LoginUserRequestDto loginUserRequestDto);
    @Select("select * from user where username=#{username} and password=#{password} and status=2")
    User admlogin(LoginUserRequestDto loginUserRequestDto);

    @Insert("insert into token (user_id,token) values(#{userId},#{token})")
    int createToken(@Param("token") String token,@Param("userId") int userId);

    @Select("select nickname from user where id=#{id}")
    String findNickname(@Param("id") int id);

    @Select("select count(*) from user where username=#{username} ")
    int checkRegisteUserame(UserRequestDto userRequestDto);

    @Select("select count(*) from user where nickname=#{nickname}")
    int checkRegisteNickname(UserRequestDto userRequestDto);

    @Update("update user set logintime=now() where id =#{userId}")
    int updateLoginTime(@Param("userId") int userId);

    @Select("select user.*,avatar.avatar from user,avatar where user.avatar_id=avatar.id ORDER BY  logintime desc limit 3")
    List<User> findRecentUser();
}
