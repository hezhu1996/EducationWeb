package com.hezhu.edu.service;

import com.hezhu.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hezhu.edu.entity.vo.CourseInfoVo;
import com.hezhu.edu.entity.vo.coursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
public interface CourseService extends IService<Course> {
    //1.添加课程：基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //2.根据课程id，查询课程基本信息（回显）
    CourseInfoVo getCourseInfo(String courseId);

    //3.修改课程信息（页面提交表单）
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //4.根据课程id查询：publish最终信息
    coursePublishVo publishCourseInfo(String id);
}
