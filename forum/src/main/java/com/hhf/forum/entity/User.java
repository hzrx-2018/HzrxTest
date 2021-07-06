package com.hhf.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class User {
    static private String linuxPath;

    @Value("${hhf.connection.address}")
    public void setLinuxPath(String linuxPath) {
        User.linuxPath = linuxPath;
    }

    public String getAvatar() {
        return linuxPath + avatar;
    }

    private int id;
    private String username;
    private String nickname;
    private String password;
    private String avatarId;
    private String avatar;
    private String mail;
    private String createdAt;
    private String updatedAt;
    private String logintime;
    private int level;
    private int status;
}
