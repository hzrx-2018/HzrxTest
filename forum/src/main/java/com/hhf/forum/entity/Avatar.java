package com.hhf.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;


@Data
@NoArgsConstructor
@Component                        //Compoent+static才可以使用配置的全局变量
public class Avatar {

   static private  String linuxPath;
@Value("${hhf.connection.address}")
public void setLinuxPath(String linuxPath){
    Avatar.linuxPath=linuxPath;
}


    public String getAvatar() {

        return linuxPath+avatar;
    }

    private String id;
    private String avatar;
    private String createdAt;
}
