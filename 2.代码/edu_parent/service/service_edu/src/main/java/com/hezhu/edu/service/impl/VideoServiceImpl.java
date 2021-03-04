package com.hezhu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.edu.entity.Video;
import com.hezhu.edu.mapper.VideoMapper;
import com.hezhu.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    //根据课程id删除：小节
    //TODO 删除小节，删除对应视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据条件，在数据库中查找
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        //删除条件所对应小节
        baseMapper.delete(wrapper);
    }

}
