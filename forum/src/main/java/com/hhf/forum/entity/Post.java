package com.hhf.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Post {
    static private  String linuxPath;
    @Value("${hhf.connection.address}")
    public void setLinuxPath(String linuxPath){
        Post.linuxPath=linuxPath;
    }
    private int id;
    private String userId;
    private String title;
    private String content;
    private String createdAt;
    private int favoriteCount;
    private int viewCount;
    private int replyCount;
    private int statusHost;
    private int statusGuest;
    private String nickname;
    private int level;
    private int status;
    private String avatar;
    public String getAvatar() {
        return linuxPath+avatar;
    }
}
