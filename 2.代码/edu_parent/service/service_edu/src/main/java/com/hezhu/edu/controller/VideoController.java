package com.hezhu.edu.controller;


import com.hezhu.commonutils.R;
import com.hezhu.edu.client.VodClient;
import com.hezhu.edu.entity.Video;
import com.hezhu.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    //1.添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //2. 修改小节 = 根据id查询小节 + 修改小节
    //2.1 查询小节：根据id
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        Video eduVideo = videoService.getById(videoId);
        return R.ok().data("video", eduVideo);
    }

    //2.2 修改章节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //注入VodClient
    @Autowired
    private VodClient vodClient;

    //3.删除小节
    //TODO 需要完善：删除小节，需要同时删除视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        //注意：先删视频 + 再删小节 （videoId）

        //1.根据小节id，获取视频id
        Video eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        //2.删除视频：根据视频id(判断是否有视频id)
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAliyunVideo(videoSourceId);
        }

        //3.删除小节：根据小节id
        videoService.removeById(id);
        return R.ok();
    }
}

