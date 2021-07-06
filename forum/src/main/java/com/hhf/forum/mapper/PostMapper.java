package com.hhf.forum.mapper;

import com.hhf.forum.dto.PostRequestDto;
import com.hhf.forum.dto.Reply4ReplyRequestDto;
import com.hhf.forum.dto.ReplyRequestDto;
import com.hhf.forum.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PostMapper {

    @Select("select post.*,user.level,user.nickname,avatar.avatar avatar from post,user," +
            "avatar where user.id=post.user_id and avatar.id=user.avatar_id and post.status_host=0 and post.status_guest=0")
    List<PostAndUser> findPosts();

    @Insert("insert into post(user_id,title,content,favorite_count,view_count,reply_count,status_host,status_guest)" +
            "values(#{userId},#{title},#{content},#{favoriteCount},#{viewCount},#{replyCount},#{statusHost},#{statusGuest})")
    int createPost(PostRequestDto postRequestDto);

    @Select("select reply4post.*,avatar.avatar,user.nickname,user.level from reply4post,user,avatar " +
            "where user.avatar_id=avatar.id and user.id=reply4post.user_id and reply4post.post_id=#{postId}")
    List<Reply4post> findReply4post(@Param("postId") int postId);

    @Select("select post.*,user.nickname,user.level,avatar.avatar,myfavorite.status from post " +
            "join  user on user.id=post.user_id " +
            "join avatar on user.avatar_id=avatar.id " +
            "left join myfavorite on myfavorite.post_id=post.id and user.id=myfavorite.user_id " +
            "where   post.id=#{postId} and post.status_host=0 and post.status_guest=0")
    Post findPostById(@Param("postId") int postId);

    @Update("update post set view_count=view_count+1 where id=#{postId}")
    int updateViewCount(@Param("postId") int postId);

    @Update("update post set reply_count=reply_count+1 where id=#{postId}")
    int updateReplyCount(@Param("postId") int postId);

    @Insert("insert into reply4post(post_id,reply_content,user_id,reply_favorite_count,reply_reply_count)" +
            "values(#{postId},#{replyContent},#{userId},#{replyFavoriteCount},#{replyReplyCount})")
    int replyPost(ReplyRequestDto replyRequestDto);

    @Insert("insert into reply4reply(from_user_id,reply_id,reply4reply_content,to_user_id,post_id,to_nickname,from_nickname,r4r_favorite_count,r4r_reply_count)" +
            "values(#{fromUserId},#{replyId},#{reply4replyContent},#{toUserId},#{postId},#{toNickname},#{fromNickname},#{r4rFavoriteCount},#{r4rReplyCount})")
    int reply4reply(Reply4ReplyRequestDto reply4ReplyRequestDto);

    @Update("update reply4post set reply_reply_count=reply_reply_count+1 where id=#{replyId}")
    int updateReply4ReplyCount(@Param("replyId") int replyId);

    @Select("select reply4reply.*,avatar.avatar from reply4reply,avatar,user where " +
            "user.avatar_id=avatar.id and user.id=reply4reply.from_user_id and reply_id=#{replyId}")
    List<Reply4Reply> findReply4Reply(@Param("replyId") int replyId);

    @Select("select * from post where user_id=#{userId} and post.status_host=0 and post.status_guest=0")
    List<Post> findMyPosts(@Param("userId") int userId);

    @Select("SELECT post.* FROM myfavorite,post where post.id=myfavorite.post_id and " +
            "post.user_id=#{userId} and post.status_host=0 and post.status_guest=0 and myfavorite.status=1")
    List<Post> findMyFavoritePosts(@Param("userId") int userId);

    @Update("update post set status_guest=1 where user_id=#{userId} and id=#{postId}")
    int deleteMyPost(@Param("userId") int userId,@Param("postId") int postId);

    @Update("update myfavorite set status= if(status=1,0,1) ,updated_at=now() where user_id=#{userId} and post_id=#{postId}")
    int updateFavoriteStatus(int userId,int postId);
    @Insert("insert into myfavorite (user_id,post_id,status)values(#{userId},#{postId},1)")
    void insertMyFavoritePost(int userId,int postId);

    @Update("update post set favorite_count= if(#{flag}=0,favorite_count+1,favorite_count-1)  where id=#{postId}")
    int updatePostFavoriteCount(int flag,int postId);

    @Select("select status from myfavorite where user_id=#{userId} and post_id=#{postId}")
    MyFavorite selectMyFavoriteStatus(int userId, int postId);
}
