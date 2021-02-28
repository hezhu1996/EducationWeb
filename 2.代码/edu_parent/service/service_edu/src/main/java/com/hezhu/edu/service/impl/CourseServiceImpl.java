package com.hezhu.edu.service.impl;

import com.hezhu.edu.entity.Course;
import com.hezhu.edu.entity.CourseDescription;
import com.hezhu.edu.entity.vo.CourseInfoVo;
import com.hezhu.edu.entity.vo.coursePublishVo;
import com.hezhu.edu.mapper.CourseMapper;
import com.hezhu.edu.service.CourseDescriptionService;
import com.hezhu.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    //1.添加课程：基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.1 课程表：添加基本信息
        //1.2 将courseInfoVo对象转换为Course对象
        Course eduCourse = new Course();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //1.3 存入数据库
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            // 添加失败
            throw new HeZhuException(20001, "添加课程失败");
        }

        //2.简介表：添加课程简介
        //2.1 将courseInfoVo中的简介，set加入CourseDescription实体类的属性中
        CourseDescription courseDescription = new CourseDescription();
        //简介
        courseDescription.setDescription(courseInfoVo.getDescription());
        //id
        courseDescription.setId(eduCourse.getId());
        courseDescriptionService.save(courseDescription);
        //返回课程id
        return eduCourse.getId();
    }

    //2.根据课程id，查询课程基本信息（回显）
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程 表
        Course eduCourse = baseMapper.selectById(courseId);
        //2.查询描述 表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        //3.将课程 / 描述 数据，放入courseInfoVo类中
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        BeanUtils.copyProperties(courseDescription, courseInfoVo); //手动set也可以

        return courseInfoVo;
    }

    //3.修改课程信息,根据id查询当前课程（页面提交表单）
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表（MP必须输入Course类，因为数据库中存的这个类）
        Course eduCourse = new Course();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new HeZhuException(20001, "修改课程失败");
        }

        //2.修改描述表
        CourseDescription description = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        courseDescriptionService.updateById(description);
    }

    //4.根据课程id查询：publish最终信息
    @Override
    public coursePublishVo publishCourseInfo(String id) {
        coursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
}

























