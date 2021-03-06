package com.hezhu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.edu.client.VodClient;
import com.hezhu.edu.entity.Video;
import com.hezhu.edu.mapper.VideoMapper;
import com.hezhu.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STIconSetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    //注入VodClient
    @Autowired
    private VodClient vodClient;


    //根据课程id删除：小节
    //TODO 删除小节，删除对应视频
    @Override
    public void removeVideoByCourseId(String courseId) {

        //注意：先删视频，再删小节

        //1.根据课程id -> 查询课程所有的视频id
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id"); //只查询特定字段的信息
        List<Video> eduVideoList = baseMapper.selectList(wrapperVideo); //返回集合，包含所有符合course_id的Video对象

        //2.List<Video> 变成 List<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            Video eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }

        //3.调用Feign：删除多个阿里云视频
        if(videoIds.size() > 0){
            vodClient.deleteBatch(videoIds);
        }

        //4.根据条件，在数据库中查找
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        //5.删除条件所对应小节
        baseMapper.delete(wrapper);
    }

}
