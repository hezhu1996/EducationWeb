package com.hezhu.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.commonutils.JwtUtils;
import com.hezhu.commonutils.R;
import com.hezhu.commonutils.vo.CourseWebOrderVo;
import com.hezhu.edu.client.OrdersClient;
import com.hezhu.edu.entity.Course;
import com.hezhu.edu.entity.chapter.ChapterVo;
import com.hezhu.edu.entity.frontvo.CourseFrontVo;
import com.hezhu.edu.entity.frontvo.CourseWebVo;
import com.hezhu.edu.service.ChapterService;
import com.hezhu.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;


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
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //格局课程id，编写sql语句查询课程信息(多表连查)
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterByCourseId(courseId);

        //判断课程是否被购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(memberId == null){
            return R.error().code(28004).message("请先登录");
        }
        boolean buyCourse = ordersClient.isBuyCourse(courseId, memberId);


        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy", buyCourse);
    }

    //5.service_order：根据课程id，获取全部课程信息
    @PostMapping("getCourseInfoOrder/{courseId}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable String courseId) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(courseId);
        CourseWebOrderVo courseWebOrderVo = new CourseWebOrderVo();
        BeanUtils.copyProperties(courseInfo, courseWebOrderVo);

        return courseWebOrderVo;
    }
}
