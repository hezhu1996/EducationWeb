package com.hezhu.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.commonutils.R;
import com.hezhu.staservice.client.UcenterClient;
import com.hezhu.staservice.entity.StatisticsDaily;
import com.hezhu.staservice.mapper.StatisticsDailyMapper;
import com.hezhu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author He Zhu
 * @since 2021-03-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //1.统计某一天的注册人数, 生成统计数据
    @Override
    public void registerCount(String day) {
        //* 为了防止向数据库重复添加相同日期的数据，先删除当前已有数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //1.远程调用得到
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        //2.把获取到的数据，添加到数据库
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day); //统计日期

        //虚拟数据
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));


        //3.数据加入数据库
        baseMapper.insert(sta);

    }

    //2.图表显示，返回两部分数据。日期json数组，数量json数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //1.查询数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type); //这里前端选定的type和数据库中字段一致（只查某一个数据）
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //2.两部分数据：日期 和 日期对应的数量
        //前端要求json结构，后端对应java代码是list集合
        //创建两个list集合，日期list和数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //3.遍历查询所有数据list集合，进行封装
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            //封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应的数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //4.整合数据到map
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);

        return map;
    }
}
