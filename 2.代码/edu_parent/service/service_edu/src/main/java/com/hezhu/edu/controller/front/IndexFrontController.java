package com.hezhu.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.Course;
import com.hezhu.edu.entity.EduTeacher;
import com.hezhu.edu.service.CourseService;
import com.hezhu.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    //查询前8条热门课程，查询前四条名师
    @GetMapping("index")
    public R index() {
        //查询前8个热门课程
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<Course> eduCourseList = courseService.list(courseWrapper);

        //查询前4个名师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 8");
        List<EduTeacher> eduTeacherList = teacherService.list(teacherWrapper);

        return R.ok().data("eduCourseList", eduCourseList).data("eduTeacherList", eduTeacherList);
    }
}
