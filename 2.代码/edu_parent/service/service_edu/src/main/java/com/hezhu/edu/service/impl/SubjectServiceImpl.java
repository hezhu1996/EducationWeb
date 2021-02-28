package com.hezhu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.edu.entity.Subject;
import com.hezhu.edu.entity.excel.SubjectData;
import com.hezhu.edu.entity.subject.OneSubject;
import com.hezhu.edu.entity.subject.TwoSubject;
import com.hezhu.edu.listener.SubjectExcelListener;
import com.hezhu.edu.mapper.SubjectMapper;
import com.hezhu.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-23
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {

        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();

            //调用方法读取
            //参数1：读取的文件； 参数二：文件对应实体类； 参数3：监听器
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询所有一级分类；parentID = 0
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<Subject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2.查询所有二级分类；parentID != 0
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<Subject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3.封装一级分类
        //遍历oneSubjectList，依次放入finalSubjectList
        for(int i = 0; i < oneSubjectList.size(); i++){
            //查出的结果
            Subject subject1 = oneSubjectList.get(i);
            //放入oneSubject
            OneSubject oneSubject = new OneSubject();
            //使用工具类:赋值，相当于 set（get）
            BeanUtils.copyProperties(subject1, oneSubject);
            //加入finalSubject
            finalSubjectList.add(oneSubject);

            //4.封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类
            for(int j = 0; j < twoSubjectList.size(); j++){
                //获取每个二级分类
                Subject subject2 = twoSubjectList.get(j);
                //判断二级分类parent_id和一级分类id是否一样
                if (subject2.getParentId().equals(subject1.getId())) {
                    //把查出来的二级分类放入twoSubject
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject2, twoSubject);
                    //放入最终的二级分类集合
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把二级分类再放入一级分类中
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
