package com.hhf.forum.controller;

import com.hhf.forum.annotation.ReadOnly;
import com.hhf.forum.dto.PostRequestDto;
import com.hhf.forum.dto.Reply4ReplyRequestDto;
import com.hhf.forum.dto.ReplyRequestDto;
import com.hhf.forum.entity.*;
import com.hhf.forum.interceptor.UserContext;
import com.hhf.forum.mapper.PostMapper;
import com.hhf.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin

@RestController
public class PostController {

    @Autowired(required = false)
    PostMapper postMapper;
    @Autowired(required = false)
    UserMapper userMapper;

    @Cacheable(cacheNames = "findPosts_name",key = "'findPosts'" )
//    @Cacheable(key = "'findPosts'" )
    @ReadOnly
    @GetMapping("/api/post/posts")
    public Map<String, Object> findPosts() {

        Map<String, Object> map = new HashMap<>();

        List<PostAndUser> posts = postMapper.findPosts();
        System.out.println("查询从数据库拿数据//没使用缓存");
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa这次访问我了,我是主主主主主主主主主主主主");
        map.put("code", "SUCCESS");
        map.put("data", posts);
        return map;
    }

    @PostMapping("/api/post/create")
    public Map<String,Object> createPost(  PostRequestDto postRequestDto){


//    User user=userMapper.whoami(postRequestDto.getToken());
        User user= UserContext.getUser();

        Map<String, Object> map = new HashMap<>();
        if(user!=null){
            postRequestDto.setUserId(user.getId());
            int row=postMapper.createPost(postRequestDto);

            if(row==1){
                map.put("code","SUCCESS");
                return map;
            }else{
                map.put("code","ERROR");
                return map;
            }

        }else{
            map.put("code","ERROR");
            return map;
        }
    }

    @Cacheable(cacheNames = "findPostById",key = "'postId:'+#postId" )
    @GetMapping("api/post/find")
    public Map<String,Object> findPostById( int postId,int isReply,String token){
        System.out.println("明细从数据库拿数据····························");
        System.out.println("aaaaaaaaaaaa"+isReply);//直接看明细是0  评论后刷新页面看明细是 1
        if(isReply==0){
            postMapper.updateViewCount(postId);
        }
//        TaskExecutor
//        TaskScheduler
        Map<String,Object> map=new HashMap<>();
        Post post=postMapper.findPostById(postId);
        User user=userMapper.whoami(token);
        System.out.println(user);
        System.out.println(post);
        if(user==null){
            post.setStatus(0);
            System.out.println("AAAAAAAAAAAAAAAAAAA");
        }else{
            MyFavorite myFavorite=postMapper.selectMyFavoriteStatus(user.getId(),postId);
            System.out.println("bbbbbbbbbbbbbbbbbbb"+myFavorite);
            if(myFavorite!=null){
                post.setStatus(myFavorite.getStatus());
            }else{
                post.setStatus(0);
            }

        }

        System.out.println("11111111111111post1111111111111111 = " + post);

        List<Reply4post> reply4posts=postMapper.findReply4post(postId);


        Map<Integer,Object> r4rMap=new HashMap<>();
        List<List> r4rList = new ArrayList<>();
        for(Reply4post reply4post: reply4posts){
            int replyId=reply4post.getId();
            List<Reply4Reply> reply4Replys = postMapper.findReply4Reply(replyId);
            reply4post.setR4rList(reply4Replys);
        }

        map.put("code","SUCCESS");
        map.put("data",post);
        map.put("dataList",reply4posts);


        return map;
    }
    @PostMapping("api/post/reply")
    public Map createReply4Post( ReplyRequestDto replyRequestDto){

//        User user=userMapper.whoami(replyRequestDto.getToken());
        User user= UserContext.getUser();
        Map<String,Object> map=new HashMap<>();
        if(user!=null){
            replyRequestDto.setUserId(user.getId());
            postMapper.updateReplyCount(replyRequestDto.getPostId());
            int i=postMapper.replyPost(replyRequestDto);

            if(i==1){
                map.put("code","SUCCESS");
                return map;
            }
        }
       map.put("code","ERROR");
    return map;
    }

    @PostMapping("api/post/reply4reply")
    public Map createReply4Reply( Reply4ReplyRequestDto reply4ReplyRequestDto){

//        User user=userMapper.whoami(reply4ReplyRequestDto.getToken());
        User user= UserContext.getUser();
        String toNickname=userMapper.findNickname(reply4ReplyRequestDto.getToUserId());

        Map<String,Object> map=new HashMap<>();
        if(user!=null){
            reply4ReplyRequestDto.setFromUserId(user.getId());
            reply4ReplyRequestDto.setFromNickname(user.getNickname());
            reply4ReplyRequestDto.setToNickname(toNickname);
            reply4ReplyRequestDto.setR4rReplyCount(0);
            reply4ReplyRequestDto.setR4rFavoriteCount(0);
            int i=postMapper.reply4reply(reply4ReplyRequestDto);
            postMapper.updateReply4ReplyCount(reply4ReplyRequestDto.getReplyId());
            if(i==1){
                map.put("code","SUCCESS");
                return map;
            }
        }
        map.put("code","ERROR");
        return map;
    }
    @GetMapping("api/post/myPosts")
    public Map findMyposts(){
        Map<String,Object> map=new HashMap<>();
        User user=UserContext.getUser();
        if(user==null){
            map.put("code","ERROR");
            return map;
        }
        List<Post> result= postMapper.findMyPosts(user.getId());
        map.put("code","SUCCESS");
        map.put("data",result);
        return map;
    }
    @GetMapping("api/post/myFavotitePosts")
    public Map findMyFavotitePosts(){
        Map<String,Object> map=new HashMap<>();
        User user=UserContext.getUser();
        if(user==null){
            map.put("code","ERROR");
            return map;
        }
        List<Post> result= postMapper.findMyFavoritePosts(user.getId());
        map.put("code","SUCCESS");
        map.put("data",result);
        return map;
    }
    @PutMapping("api/post/deleteMyPost/{postId}")
    public Map deleteMyPost(@PathVariable int postId){
        System.out.println("postIdaaaaaaaaaaaaaaa="+postId);
        Map<String,Object> map=new HashMap<>();
        User user=UserContext.getUser();
        if(user==null){
            map.put("code","ERROR");
            return map;
        }
        int i=postMapper.deleteMyPost(user.getId(),postId);
        map.put("code","SUCCESS");

        return map;
    }

    @PostMapping("api/post/updateMyFavoriteStatus")
    public Map updateMyFavoriteStatus(int flag, int postId){

        System.out.println("111111111111111111111111111flag = " + flag);
        Map<String,Object> map=new HashMap<>();
        User user=UserContext.getUser();
        if(user==null){
            map.put("code","ERROR");
            return map;
        }

        postMapper.updatePostFavoriteCount(flag,postId);
        int i=postMapper.updateFavoriteStatus(user.getId(),postId);

        if(i==0){
            postMapper.insertMyFavoritePost(user.getId(),postId);
        }


        map.put("code","SUCCESS");

        return map;
    }

}
