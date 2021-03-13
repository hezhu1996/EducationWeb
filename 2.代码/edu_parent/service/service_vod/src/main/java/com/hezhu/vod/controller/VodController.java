package com.hezhu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hezhu.commonutils.R;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import com.hezhu.vod.service.VodService;
import com.hezhu.vod.utils.ConstantVodUtils;
import com.hezhu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Soundbank;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //1.上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) { //得到上传文件
        String videoId = vodService.uploadVideoAly(file); //返回视频Id

        return R.ok().data("videoId", videoId);
    }

    //2.删除视频：根据视频ID
    @DeleteMapping("removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(videoId);
            //调用初始化对象的方法进行删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HeZhuException(20001, "删除视频失败");
        }
    }

    //3.删除多个阿里云视频的方法
    //参数：多个视频id - List
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMultiAliyunVideo(videoIdList);
        return R.ok();
    }

    //4.根据视频id，获取视频token
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //1.初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //2.创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //3.向request设置id
            request.setVideoId(id);
            //4.调用方法获得凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();

            return R.ok().data("playAuth", playAuth);
        }
        catch (Exception e){
            throw new HeZhuException(20001, "获取阿里云视频凭证失败");
        }
    }

}
