package com.hezhu.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */
public interface TeacherService extends IService<EduTeacher> {

    //1.分页查询讲师
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
