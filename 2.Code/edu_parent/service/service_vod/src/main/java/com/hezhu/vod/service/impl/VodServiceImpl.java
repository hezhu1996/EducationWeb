package com.hezhu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.hezhu.commonutils.R;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import com.hezhu.vod.service.VodService;
import com.hezhu.vod.utils.ConstantVodUtils;
import com.hezhu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        /**
         * accessKeyId：id
         * accessKeySecret：秘钥
         * title：阿里云显示名称
         * fileName：上传文件原始名称
         * inputStream：上传文件输入流，由file得到
         */

        try {

            String fileName = file.getOriginalFilename();

            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET,
                                                                  title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }

            return videoId;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除多个阿里云视频的方法
    @Override
    public void removeMultiAliyunVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            //调用初始化对象的方法进行删除
            client.getAcsResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw new HeZhuException(20001, "删除视频失败");
        }
    }
}
