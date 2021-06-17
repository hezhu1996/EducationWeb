package com.hezhu.staservice.service;

import com.hezhu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author He Zhu
 * @since 2021-03-17
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //1.统计某一天的注册人数, 生成统计数据
    void registerCount(String day);

    //2.图表显示，返回两部分数据。日期json数组，数量json数据
    Map<String, Object> getShowData(String type, String begin, String end);
}
