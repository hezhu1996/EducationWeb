package com.hezhu.edu.controller;


import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.subject.OneSubject;
import com.hezhu.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-23
 */

@Api("课程分类管理") //swagger 名称
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //1.添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @ApiOperation("Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //1.获取上传的excel文件 MultipartFile
        //返回错误提示信息
        subjectService.saveSubject(file, subjectService);
        //判断返回集合是否为空
        return R.ok();
    }

    //2.课程分类列表(树形)
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //1.返回所有一级数据（其中包含二级数据）
        List<OneSubject> list = subjectService.getAllOneTwoSubject();

        return R.ok().data("list", list);
    }


}

