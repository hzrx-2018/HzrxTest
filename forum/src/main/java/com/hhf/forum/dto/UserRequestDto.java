package com.hhf.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private int userId;
    @NotBlank(message = "账户名不能为空")
    private String username;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "必须选择一个头像")
    private String avatarId;
    @NotBlank(message = "邮箱不能为空")
    private String mail;
    private int level;
    private int status;
}
