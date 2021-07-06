package com.hhf.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Reply4ReplyRequestDto {
    private String token;
    private int replyId;
    private int toUserId;
    private int postId;
    private int fromUserId;
    private String reply4replyContent;
    private String fromNickname;
    private String toNickname;
    private int r4rReplyCount=0;
    private int r4rFavoriteCount=0;
}
