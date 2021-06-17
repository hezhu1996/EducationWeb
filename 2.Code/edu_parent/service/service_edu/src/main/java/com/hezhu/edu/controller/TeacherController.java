package com.hezhu.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.EduTeacher;
import com.hezhu.edu.entity.vo.TeacherQuery;
import com.hezhu.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */

@Api(tags = "讲师Controller")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class TeacherController {
    //访问地址：http://47.94.174.79:8001/edu/teacher/findAll
    //注入service
    @Autowired
    private TeacherService teacherService;

    //1.查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "所有讲师")
    @GetMapping("findAll") //findAll前面有没有"/"无所谓
    public R findAllTeacher() {
        //调用service方法，查询所有
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //2.逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}") //需要通过路径传值
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //3.分页查询讲师
    @ApiOperation("分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        //创建page对象，初始化参数
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法实现分页，数据都封装到pageTeacher中
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total", total).data("records", records);
    }

    //4.条件查询 + 分页
    @ApiOperation("条件查询 + 分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象，初始化参数
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构造条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //多条件动态组合 + 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //判断条件是否为空，不为空就拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //降序排列
        wrapper.orderByDesc("gmt_create");

        //调用方法实现分页，数据都封装到pageTeacher中
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total", total).data("records", records);
    }

    //添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据id查询讲师
    @ApiOperation("根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //根据id修改讲师
    @ApiOperation("根据id修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

