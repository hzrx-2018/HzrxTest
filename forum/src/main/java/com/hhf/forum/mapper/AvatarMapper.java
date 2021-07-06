package com.hhf.forum.mapper;

import com.hhf.forum.entity.Avatar;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AvatarMapper {

    @Insert("insert into avatar(avatar)values(#{avatar})")
    void uploadImage(String avatar);


    @Select("select id, avatar,created_at from avatar")
    List<Avatar> getAvatarList();
}
