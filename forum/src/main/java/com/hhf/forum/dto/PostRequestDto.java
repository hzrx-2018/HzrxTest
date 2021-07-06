package com.hhf.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private int id;
    private int userId;
    private String title;
    private String content;

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = 0;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = 0;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = 0;
    }

    public void setStatusHost(int statusHost) {
        this.statusHost = 0;
    }

    public void setStatusGuest(int statusGuest) {
        this.statusGuest = 0;
    }

    private String createdAt;
    private int favoriteCount;
    private int viewCount;
    private int replyCount;
    private int statusHost;
    private int statusGuest;
    private String token;



}

