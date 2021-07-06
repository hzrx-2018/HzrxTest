package com.hhf.forum.config;

import com.hhf.forum.dto.MailDto;
import com.hhf.forum.util.Utils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @version 0.1
 * @ahthor haifeng
 * @date 2021/6/8 16:23
 */
//@Configuration
//@RabbitListener(queuesToDeclare = @Queue("mail"))
//public class MailConsumer {
//    @RabbitHandler
//    public void receive(MailDto mailDto) throws Exception {
//        System.out.println("我收到生产者的消息了");
//        Utils.sendEmailFromQQ(mailDto);
//    }
//}
