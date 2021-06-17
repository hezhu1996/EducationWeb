package com.hezhu.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.Course;
import com.hezhu.edu.entity.EduTeacher;
import com.hezhu.edu.service.CourseService;
import com.hezhu.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    //1.分页查询讲师(page:当前页，limit：当前页总数)
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        //1.1 新建一个pageTeacher对象
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //1.2 在service中查找数据库，并把分页信息放入pageTeacher，用map返回pageTeacher中的全部信息
        //因为前台需要手动写数据，比较底层，取的数据多
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);

        return R.ok().data("map", map);
    }

    //2.讲师详情的功能：根据id查询
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1.根据讲师id，查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        //2.根据讲师id查询所有课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<Course> courseList = courseService.list(wrapper); //可能讲多个课程

        return R.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}
