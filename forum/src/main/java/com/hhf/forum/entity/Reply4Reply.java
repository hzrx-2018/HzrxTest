package com.hhf.forum.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Reply4Reply {
    static private  String linuxPath;
    @Value("${hhf.connection.address}")
    public void setLinuxPath(String linuxPath){
        Reply4Reply.linuxPath=linuxPath;
    }
    private int id;
    private int fromUserId;
    private int replyId;
    private String reply4replyContent;
    private int toUserId;
    private int postId;
    private String avatar;
    private String createdAt;
    private String toNickname;
    private String fromNickname;
    private int r4rReplyCount;
    private int r4rFavoriteCount;
    public String getAvatar() {
        return linuxPath+avatar;
    }
}
