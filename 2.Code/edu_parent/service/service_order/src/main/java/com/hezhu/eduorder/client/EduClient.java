package com.hezhu.eduorder.client;

import com.hezhu.commonutils.vo.CourseWebOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
