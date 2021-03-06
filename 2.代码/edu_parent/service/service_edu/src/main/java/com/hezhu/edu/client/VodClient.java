package com.hezhu.edu.client;

import com.hezhu.commonutils.R;
import com.hezhu.edu.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //2.删除视频：根据视频ID
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable("videoId") String videoId);

    //3.删除多个阿里云视频的方法
    //参数：多个视频id - List
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
