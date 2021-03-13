package com.hezhu.edu.mapper;

import com.hezhu.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hezhu.edu.entity.frontvo.CourseWebVo;
import com.hezhu.edu.entity.vo.coursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
public interface CourseMapper extends BaseMapper<Course> {
    //1.根据课程id，查询最终课程信息
    public coursePublishVo getPublishCourseInfo(String courseId);

    //2.查询课程详情信息 - 前台
    CourseWebVo getBaseCourseInfo(String courseId);
}
