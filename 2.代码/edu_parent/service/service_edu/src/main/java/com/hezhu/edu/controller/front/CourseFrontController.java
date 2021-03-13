package com.hezhu.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.Course;
import com.hezhu.edu.entity.chapter.ChapterVo;
import com.hezhu.edu.entity.frontvo.CourseFrontVo;
import com.hezhu.edu.entity.frontvo.CourseWebVo;
import com.hezhu.edu.service.ChapterService;
import com.hezhu.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;


    //1.条件查询+分页
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> pageCourse = new Page<>(page, limit);
        //根据条件查询课程
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    //2.查询课程详情信息
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        //格局课程id，编写sql语句查询课程信息(多表连查)
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterByCourseId(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}
