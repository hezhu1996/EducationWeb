package com.hezhu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hezhu.oss.service.OssService;
import com.hezhu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;


@Service
public class OssServiceImpl implements OssService {

    Queue<Integer> queue = new PriorityQueue<>();

    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        try {
            //1.创建oss实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            //2.上传文件流
            InputStream inputStream = file.getInputStream();


            //2.1 实际文件名
            String fileName = file.getOriginalFilename();

            //2.2 添加唯一随机值(替换"-"为空)
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            //2.3 把文件按日期分类
            //2.3.1 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;


            //2.3 上传文件到oss
            //Param1： bucket名称
            //Param2: 上传到oss的文件路径+文件名称 aa/bb/1.jpg
            //Param3: 上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            //3.关闭oss
            ossClient.shutdown();

            //4.上传文件路径返回(手动拼接)
            //https://edu-hezhu.oss-us-east-1.aliyuncs.com/wallhaven-x8888d.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;

            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
