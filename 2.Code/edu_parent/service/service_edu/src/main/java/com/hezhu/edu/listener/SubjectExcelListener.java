package com.hezhu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.edu.entity.Subject;
import com.hezhu.edu.entity.excel.SubjectData;
import com.hezhu.edu.service.SubjectService;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import org.springframework.stereotype.Component;

// @Component
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //使用@Component和 @Autowired SubjectService同样可以操作数据库

    //* 因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //* 不能实现数据库操作
    public SubjectService subjectService;

    //无参构造
    public SubjectExcelListener() {}
    //有参构造
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //1. 一行一行读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //1.1如果excel中没有数据
        if(subjectData == null){
            throw new HeZhuException(20001, "The file is empty");
        }

        //1.2.一行一行读取， 每次读取两个值， 第一个值一级分类，第二个值二级分类
        //1.2.1 判断一级是否重复
        Subject existOneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        //1.2.2 如果没有相同的一级分类，添加
        if(existOneSubject == null){
            //设置课程属性
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            //存入数据库
            subjectService.save(existOneSubject);
        }

        //1.2.3 判断二级是否重复
        String pid = existOneSubject.getId(); //得到parent_id
        Subject existTwoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null){
            //设置课程属性
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            //存入数据库
            subjectService.save(existTwoSubject);
        }
    }

    //1.3 判断一级分类能否重复添加
    private Subject existOneSubject(String name) {
        //数据库条件查询
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //1.4 判断二级分类能否重复添加
    private Subject existTwoSubject(String name, String pid) {
        //数据库条件查询
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        Subject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    //2.读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
