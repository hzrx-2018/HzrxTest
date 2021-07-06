package com.hhf.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
public class Reply4post<r4rList> {
    static private  String linuxPath;
    @Value("${hhf.connection.address}")
    public void setLinuxPath(String linuxPath){
        Reply4post.linuxPath=linuxPath;
    }
    private List r4rList;
    private int id;
    private int postId;
    private String replyContent;
    private int userId;
    private String replyReplyCount;
    private String replyFavoriteCount;
    private String createdAt;
    private String avatar;
    private String nickname;
    private int level;
    public String getAvatar() {
        return linuxPath+avatar;
    }
}
