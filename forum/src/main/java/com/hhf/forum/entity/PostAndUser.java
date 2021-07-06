package com.hhf.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Component
public class PostAndUser implements Serializable
{
    static private  String linuxPath;
    @Value("${hhf.connection.address}")
    public void setLinuxPath(String linuxPath){
        PostAndUser.linuxPath=linuxPath;
    }
    private String id;
    private String userId;
    private String title;

    public String getAvatar() {
        return linuxPath+avatar;
    }

    private String content;
    private String createdAt;
    private String favoriteCount;
    private String viewCount;
    private String replyCount;
    private String statusHost;
    private String statusGuest;


    private String nickname;
    private String avatar;
    private String level;
}
