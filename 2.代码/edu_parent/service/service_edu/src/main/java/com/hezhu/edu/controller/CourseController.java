package com.hezhu.edu.controller;


import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.vo.CourseInfoVo;
import com.hezhu.edu.entity.vo.coursePublishVo;
import com.hezhu.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    //1.添加课程：基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加后，课程的id
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //2.根据课程id，查询课程基本信息（回显）
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //3.修改课程信息（页面提交表单）
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //4.根据课程id查询：publish最终信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        coursePublishVo coursePublish = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublish);
    }
}

