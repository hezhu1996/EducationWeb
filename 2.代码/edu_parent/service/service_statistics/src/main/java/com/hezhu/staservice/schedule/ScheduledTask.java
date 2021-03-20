package com.hezhu.staservice.schedule;

import com.hezhu.commonutils.R;
import com.hezhu.staservice.service.StatisticsDailyService;
import com.hezhu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Map;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    //1.每天凌晨1点执行定时，把前一天的数据进行数据查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public R task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        staService.registerCount(day);
        return R.ok();
    }
}
