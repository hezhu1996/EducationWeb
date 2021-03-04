package com.hezhu.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.Course;
import com.hezhu.edu.entity.vo.CourseInfoVo;
import com.hezhu.edu.entity.vo.CourseQuery;
import com.hezhu.edu.entity.vo.coursePublishVo;
import com.hezhu.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //5.课程最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    //6.查询课程列表
    @ApiOperation("查询课程列表")
    @GetMapping("getCourseList")
    public R findAllCourse(){
        List<Course> list = courseService.list(null);
        return R.ok().data("courseList", list);
    }

    //7.分页查询课程
    @ApiOperation("分页查询课程")
    @GetMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable long current, @PathVariable long limit){
        //创建page对象
        Page<Course> pageCourse = new Page<>(current, limit);

        //调用方法实现分页，数据封装到pageCourse中
        courseService.page(pageCourse, null);

        //总记录数
        long total = pageCourse.getTotal();
        //所有记录
        List<Course> records = pageCourse.getRecords();

        return R.ok().data("total", total).data("records", records);
    }

    //8.查询课程列表：条件查询 + 分页
    @ApiOperation("条件查询 + 分页")
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){

        //创建page参数，初始化对象
        Page<Course> pageCourse = new Page<>(current, limit);

        //调用service，查询条件
        courseService.pageQuery(pageCourse, courseQuery);

        //所有记录
        List<Course> records = pageCourse.getRecords();

        //所有条目
        long total = pageCourse.getTotal();

        return R.ok().data("total", total).data("records", records);
    }

    //9.删除课程：根据课程id
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}




























