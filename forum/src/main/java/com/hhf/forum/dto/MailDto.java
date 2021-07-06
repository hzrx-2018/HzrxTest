package com.hhf.forum.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 0.1
 * @ahthor haifeng
 * @date 2021/6/8 16:18
 */
@Data
@Builder
public class MailDto implements Serializable {
    private String fromMail;
    private String toMail;
    private String mailCode;
    private String nickName;
    private String ipAddress;
}
