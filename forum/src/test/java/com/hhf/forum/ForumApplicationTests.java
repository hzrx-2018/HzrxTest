package com.hhf.forum;

import com.hhf.forum.controller.PostController;
import com.hhf.forum.dto.PostRequestDto;
import com.hhf.forum.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class ForumApplicationTests {
private static final int MAX_THREADS=3;
private CountDownLatch count=new CountDownLatch(MAX_THREADS);
    @Autowired
    PostController postController;
    @Test
    void contextLoads() throws InterruptedException {
//        PostRequestDto p =new PostRequestDto();
//        p.setUserId(1);
//        p.setTitle("aaa");
//        for (int i = 0; i < 100; i++) {
//            postController.createPost( p);
//        }
        for (int i = 0; i < MAX_THREADS; i++) {
            Thread t = new Thread(()->{
                count.countDown();
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String, Object> map=postController.findPosts();
                System.out.println(map);
            });
            t.start();
        }

        Thread.sleep(2000);

    }

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    DataSourceProperties dataSourceProperties;

//    @Autowired(required = false)
//    JavaMailSenderImpl javaMailSender;


    @Test
    void contextLoads1() {
        DataSource dataSource= applicationContext.getBean(DataSource.class);
        System.out.println(dataSource);
        System.out.println(dataSource.getClass().getName());
        System.out.println(dataSourceProperties);

//        javaMailSender.send();

    }

}
