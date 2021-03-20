package com.hezhu.eduorder.service;

import com.hezhu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
public interface PayLogService extends IService<PayLog> {

    //1.生成微信支付二维码接口
    Map createNative(String orderNo);

    //2.查询订单状态：根据订单号
    Map<String, String> queryPayStatus(String orderNo);

    //3.添加记录到支付表，更新订单表订单状态
    void updateOrdersStatus(Map<String, String> map);
}
