package com.hhf.forum.util;

import com.hhf.forum.dto.MailDto;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @version 0.1
 * @ahthor haifeng
 * @date 2021/6/8 15:06
 */
public class Utils {
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    @Autowired
    static MailSender mailSender;

    public static void sendEmailFromQQ(MailDto mailDto) throws Exception {

//        System.out.println("准备发邮件的内容是：" + mailDto.getIpAddress());
//        Properties p = new Properties();
//        p.setProperty("mail.host", "smtp.qq.com");
//        p.setProperty("mail.transport.protocol", "smtp");
//        p.setProperty("mail.smtp.auth", "true");
////
////        MailSSLSocketFactory sf=new MailSSLSocketFactory();
////        sf.setTrustAllHosts(true);
////        p.put("mail.smtp.ssl.enable","true");
////        p.put("mail.smtp.ssl.socketFactory", sf);
//
//        Session session = Session.getDefaultInstance(p, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(mailDto.getFromMail(), mailDto.getMailCode());
//            }
//        });
//        //session.setDebug(true);
//        Transport t = session.getTransport();
//
//        t.connect("smtp.qq.com", mailDto.getMailCode());
//
//        MimeMessage m = new MimeMessage(session);
//
//
//        m.setFrom(new InternetAddress(mailDto.getFromMail()));
//        m.setRecipients(Message.RecipientType.TO, new InternetAddress(mailDto.getToMail()).toString());
//        m.setSubject("" +
//                mailDto.getNickName() +
//                "  您好：");
//
//        m.setContent("您的帐号 " +
//                "jack" +
//                " 于" +
//                DateFormat.getDateTimeInstance().format(new Date()) +
//                "登录风铃小厨，IP地址为：" +
//                mailDto.getIpAddress() +
//                "如果非本人操作，请及时修改密码", "text/html;charset=utf-8");


//        ==============================
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(
                mailDto.getNickName() +
                        "  您好：");
        simpleMailMessage.setText("您的帐号 " +
                "jack" +
                " 于" +
                DateFormat.getDateTimeInstance().format(new Date()) +
                "登录风铃小厨，IP地址为：" +
                mailDto.getIpAddress() +
                "如果非本人操作，请及时修改密码");
        simpleMailMessage.setTo(mailDto.getToMail());
        simpleMailMessage.setFrom(mailDto.getFromMail());
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        mailSender.send(simpleMailMessage);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//        ================================


//        t.sendMessage(m, m.getAllRecipients());
//        System.out.println("我发送了邮件");
//        t.close();
    }
}
