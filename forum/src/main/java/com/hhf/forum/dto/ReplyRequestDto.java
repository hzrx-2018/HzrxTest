package com.hhf.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyRequestDto {

    private int postId;
    private String replyContent;
    private String token;
    private int replyReplyCount;
    private int replyFavoriteCount;
    private int userId;

}
