package com.hezhu.edu.service;

import com.hezhu.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
public interface VideoService extends IService<Video> {
    //根据课程id删除：小节
    void removeVideoByCourseId(String courseId);
}
