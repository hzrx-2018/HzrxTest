package com.hhf.forum.controller;

import com.hhf.forum.dto.LoginUserRequestDto;
import com.hhf.forum.dto.MailDto;
import com.hhf.forum.dto.UserRequestDto;
import com.hhf.forum.entity.User;
import com.hhf.forum.mapper.UserMapper;
import com.hhf.forum.util.Utils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class UserController {
    @Autowired(required = false)
    UserMapper userMapper;

    @PostMapping("api/user/registe")
    public Map registeUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        Map<String, Object> map = new HashMap<>();
        int i = userMapper.checkRegisteUserame(userRequestDto);
        if (i > 0) {
            map.put("code", "ERROR");
            map.put("message", "该账户名已存在");
            return map;
        }

        int j = userMapper.checkRegisteNickname(userRequestDto);
        if (j > 0) {
            map.put("code", "ERROR");
            map.put("message", "该昵称已存在");
            return map;
        }


        try {
            userRequestDto.setLevel(0);
            userRequestDto.setStatus(0);
            int registeRow = userMapper.registeUser(userRequestDto);//插入注册表
            if (registeRow == 1) {
                String token = UUID.randomUUID().toString().replaceAll("-", "");
                int createTokenRow = userMapper.createToken(token, userRequestDto.getUserId());//建立一条登录token表数据
                if (createTokenRow == 1) {
                    User user = userMapper.whoami(token);
                    if (user != null) {
                        userMapper.updateLoginTime(userRequestDto.getUserId());
                        map.put("user", user);
                        map.put("token", token);
                        map.put("code", "SUCCESS");
                    } else {
                        map.put("code", "ERROR");
                        return map;
                    }
                } else {
                    map.put("code", "ERROR");
                    return map;
                }
            } else {
                map.put("code", "ERROR");
                return map;
            }


        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "ERROR");
        }
        return map;
    }


    @Autowired(required = false)
    HttpServletRequest request;

//    @Value("${fromMail}")
//    private String fromMail;
//
//    @Value("${mailCode}")
//    private String mailCode;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("api/user/login")
    public Map login(@Valid LoginUserRequestDto loginUserRequestDto) {

        Map<String, Object> map = new HashMap<>();
        try {
            User user = userMapper.login(loginUserRequestDto);
            System.out.println("user = " + user);
            if (user != null) {
                String token = UUID.randomUUID().toString().replaceAll("-", "");
                int createTokenRow = userMapper.createToken(token, user.getId());//建立一条登录token表数据
                if (createTokenRow == 1) {
                    User loginedUser = userMapper.whoami(token);
                    System.out.println("loginedUser = " + loginedUser);
                    if (loginedUser != null) {
                        userMapper.updateLoginTime(user.getId());
                        map.put("code", "SUCCESS");
                        map.put("user", loginedUser);
                        map.put("token", token);

                        //发邮件通知用户本人确认，你已经登录
                        //把这个交给rabbitmq去做

                        MailDto mailDto = MailDto.builder()
//                                .fromMail(fromMail)
                                .toMail(user.getMail())
//                                .mailCode(mailCode)
                                .nickName(user.getNickname())
                                .ipAddress(Utils.getIpAddress(request))
                                .build();
                        Utils.sendEmailFromQQ(mailDto);
                      // rabbitTemplate.convertAndSend("mail", mailDto);

                    } else {
                        map.put("code", "ERROR");
                        return map;
                    }
                } else {
                    map.put("code", "ERROR");
                    return map;
                }
            } else {
                map.put("code", "ERROR");
                map.put("message", "账户或密码错误");
                return map;
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "ERROR");
            return map;
        }

        return map;
    }

    @GetMapping("api/user/recentUser")
    public Map getRecentUser() {
        List<User> recentUsers = userMapper.findRecentUser();
        Map<String, Object> map = new HashMap<>();
        map.put("result", recentUsers);
        return map;
    }


    @PostMapping("api/user/admLogin")
    public Map admLogin(@Valid LoginUserRequestDto loginUserRequestDto) {

        Map<String, Object> map = new HashMap<>();
        try {
            User user = userMapper.admlogin(loginUserRequestDto);

            if (user != null) {
                String token = UUID.randomUUID().toString().replaceAll("-", "");
                int createTokenRow = userMapper.createToken(token, user.getId());//建立一条登录token表数据
                if (createTokenRow == 1) {

                    User loginedUser = userMapper.whoami(token);

                    if (loginedUser != null) {

                        // userMapper.updateLoginTime(user.getId());
                        map.put("code", "SUCCESS");
                        map.put("user", loginedUser);
                        map.put("token", token);
                    } else {
                        map.put("code", "ERROR");

                        return map;
                    }
                } else {
                    map.put("code", "ERROR");
                    return map;
                }
            } else {
                map.put("code", "ERROR");
                map.put("message", "账户或密码错误");
                return map;
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "ERROR");
            return map;
        }

        return map;
    }
}
