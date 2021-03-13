package com.hezhu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.edu.entity.EduTeacher;
import com.hezhu.edu.mapper.TeacherMapper;
import com.hezhu.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, EduTeacher> implements TeacherService {

    //1.分页查询讲师
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        //1.分页条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id"); //根据讲师id做降序排列

        //2.根据条件在数据库查询，并把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageTeacher, wrapper);

        //3.把pageTeacher中的信息全部提取出来
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        //是否有下一页？
        boolean hasNext = pageTeacher.hasNext();
        //是否有上一页？
        boolean hasPrevious = pageTeacher.hasPrevious();


        //4.分页数据获取出来，并放入map
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
