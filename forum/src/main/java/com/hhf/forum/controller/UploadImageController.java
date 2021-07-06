package com.hhf.forum.controller;

import com.hhf.forum.entity.Avatar;
import com.hhf.forum.mapper.AvatarMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class UploadImageController {

    @Value("${upload.path}")
    private String uploadPath;
//    uploadPath= D:\software\forum/uploads/

    @Value("${hhf.connection.address}")
    private String linuxPath;

    @Autowired(required = false)
    AvatarMapper userMapper;

    @PostMapping("/api/image/upload")
    public Map uploadImage(MultipartFile file) throws IOException {


        Map<String,String> map=new HashMap<>();
        if(file.isEmpty()){
            map.put("message","上传文件不能为空");
            return map ;
        }

            long size=  file.getSize();
            if(size>8*Math.pow(1024,3)){
                map.put("message","单个上传文件大小不能超过8M");
                return map ;
            }

            int index=file.getOriginalFilename().lastIndexOf(".");
            String extName=file.getOriginalFilename().substring(index+1);
            if(!"png/jpg/jpeg/gif".contains(extName)){
                map.put("message","上传文件类型只能是png/jpg/jpeg/gif");
                return map ;
            }

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd/");
            String folderName=simpleDateFormat.format(new Date());

            String newFile= folderName+UUID.randomUUID().toString().
                    replaceAll("-","")+"."+extName;//文件新名字

            File dir=new File(uploadPath+folderName);//新文件将要保存的文件夹路径

            if(!dir.exists()){
                dir.mkdirs();
            }

            File finalFile =new File(uploadPath+newFile);//文件要

            file.transferTo(finalFile.getAbsoluteFile());

            map.put("fileName",newFile);
            userMapper.uploadImage(newFile);
            map.put("code","SUCCESS");


        return map;
    }

    @GetMapping("/api/image/download")
   public Map<String,Object>  getAvatatList(){
        System.out.println("linuxPath="+linuxPath);
        Map<String,Object> map=new HashMap<>();
        System.out.println("fsdfdsfdsf="+uploadPath);

        List<Avatar> list = userMapper.getAvatarList();

        map.put("data",list);
        map.put("code","SECCESS");
        Map<String,Object> result=new HashMap<>();
        result.put("data",map);
        return result;
   }
}
